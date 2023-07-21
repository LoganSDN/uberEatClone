package com.uberClone.uberClone.services;

import com.corundumstudio.socketio.SocketIOServer;
import com.uberClone.uberClone.dtos.WorldMapDto;
import com.uberClone.uberClone.entities.*;
import com.uberClone.uberClone.repositories.DriverViewRepository;
import com.uberClone.uberClone.repositories.RoleRepository;
import com.uberClone.uberClone.repositories.UserRepository;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import com.uberClone.uberClone.services.interfaces.WorldMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RestaurantsService restaurantsService;
    @Autowired
    DriverViewRepository driverViewRepository;
    @Autowired
    private SocketIOServer socketServer;
    @Autowired
    private WorldMapService worldMapService;

    @Override
    @Transactional
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByName(role.getName()).orElse(null);
            System.out.println(existingRole);
            if (existingRole != null) {
                roles.add(existingRole);
            }
        }
        user.setRoles(roles);
        Address address = new Address();
        if (user.getAddress() != null) {
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setZIP(user.getAddress().getZIP());
            address.setLat(user.getAddress().getLat());
            address.setLng(user.getAddress().getLng());
            address.setUser(user);
            user.setAddress(address);
        }
        else {
            user.setAddress(null);
        }
        return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String id) {
        this.userRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    private static Map<Long, List<UUID>> rejectList = new HashMap<Long, List<UUID>>();
    public void findDriverForOrder(Order order){
        addRejectedList(order.getId(), null);
        List<DriverView> drivers= this.driverViewRepository.findAllByStatusContainingAndSocketIdNotNullOrderByIdAsc("WORKING");
        String restaurantPoint = order.getRestaurant().getAddress().getLng() + "," +order.getRestaurant().getAddress().getLat();
        List<String> locations = new ArrayList<>();
        System.out.println("Rejected List for order" + order+ " is : " + rejectList.get(order.getId()));
        System.out.println("Drivers on driverView:" + drivers);
        List<DriverView> driversToRemove = new ArrayList<>();
        for (DriverView driver : drivers
             ) {
                if (rejectList.get(order.getId()).contains(driver.getSocketId())){
                    System.out.println(driver + " has rejected already");
                    driversToRemove.add(driver);
                    continue;
                }
                locations.add("[" +driver.getLng().toString() + "," + driver.getLat().toString() + "]");
        }
        drivers.removeAll(driversToRemove);
        switch (locations.size()){
            case 0:
                 removeRejectedList(order.getId());
                 if (!drivers.isEmpty())
                    findDriverForOrder(order);
                 break;
            case 1:
                socketServer.getClient(drivers.get(drivers.size() - 1).getSocketId()).sendEvent("NewOrder", order.toString());
                break;
            default:
                WorldMapDto durations = worldMapService.getDirection(locations, restaurantPoint);
                DriverView driver = findClosetDriver(durations, drivers);
                System.out.println("Our driver is : "+ driver.toString());
                socketServer.getClient(driver.getSocketId()).sendEvent("NewOrder", order.toString());
                break;
        }
    }
    private DriverView findClosetDriver(WorldMapDto durations, List<DriverView> drivers){
        AtomicInteger count = new AtomicInteger(0);
        Integer id = -1;
        Double min = Double.MAX_VALUE;
        for (Double[] dur : durations.getDurations()){
            if (min > dur[0] && count.get() != durations.getDurations().length -1) {
                min = dur[0];
                id = count.get();
            }
            count.incrementAndGet();
        }
        return drivers.get(id);
    }
    public void removeRejectedList(Long orderId){
        rejectList.remove(orderId);
    }

    public void addRejectedList(Long orderId, UUID userId){
        rejectList.putIfAbsent(orderId, new ArrayList<>());
        if (userId != null){
         rejectList.get(orderId).add(userId);
         System.out.println("Adding " + userId + " to list of rejected" + rejectList.get(orderId));
        }

    }

    public void updateSocketUser(User user, UUID socketId){
        if (socketId != null){
            user.setSocketId(socketId);
            user.setStatus("WORKING");
        }
        else{
            user.setSocketId(null);
            user.setStatus("Ah bah ça prends des pauses en plus");
        }
        this.userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }

    public Optional<User> getBySocketId(UUID id){
        return this.userRepository.findBySocketId(id);
    }
}
