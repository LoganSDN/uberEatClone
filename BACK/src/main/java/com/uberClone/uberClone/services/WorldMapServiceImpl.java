package com.uberClone.uberClone.services;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uberClone.uberClone.dtos.WorldMapDto;
import com.uberClone.uberClone.services.interfaces.RestaurantsService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import com.uberClone.uberClone.services.interfaces.WorldMapService;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WorldMapServiceImpl implements WorldMapService {
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatusCode status;

    public WorldMapServiceImpl() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        headers.add("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8");
        headers.add("Content-Type", "application/json; charset=utf-8");
        headers.add("Authorization", "5b3ce3597851110001cf6248855fb94a4d8644a5a9f07663e726a472");
    }

//    @Value(value = "${openRouteAPI}")
    final static String api_key = "5b3ce3597851110001cf6248855fb94a4d8644a5a9f07663e726a472";
    public WorldMapDto getDirection(List<String> locations, String destination){
        String Url = String.format("https://api.openrouteservice.org/v2/matrix/driving-car");
        locations.add("["+ destination + "]");
        String Body = String.format("{\"locations\": %s, \"destinations\":[%d]}",locations.toString().strip(),locations.size() - 1);
        HttpEntity<String> requestEntity = new HttpEntity<String>(Body, headers);
        ResponseEntity<WorldMapDto> responseEntity = rest.postForEntity(Url,requestEntity, WorldMapDto.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }


}
