package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.dto.NodeCoordinate;
import com.capstone.team5.pmmap.entity.BuildingEntity;
import com.capstone.team5.pmmap.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;

    public NodeCoordinate getCoordinateByName(String buildingName){
        BuildingEntity buildingEntity = buildingRepository.selectByName(buildingName);
        NodeCoordinate node = NodeCoordinate.builder()
                .longitude(buildingEntity.getLongitude())
                .latitude(buildingEntity.getLatitude())
                .build();
        return node;
    }

}
