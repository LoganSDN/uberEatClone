package com.uberClone.uberClone.repositories;

import com.uberClone.uberClone.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    @Query(value = "SELECT u FROM Users u where u.login = ?1 and u.password = ?2 ")
    Optional login(String username, String password);
    Optional<Users> findByLogin(String login);
}
