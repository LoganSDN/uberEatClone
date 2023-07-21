package com.uberClone.uberClone.services.interfaces;

import com.uberClone.uberClone.dtos.WorldMapDto;

import java.util.List;

public interface WorldMapService {
    public WorldMapDto getDirection(List<String> locations, String Destination);
}
