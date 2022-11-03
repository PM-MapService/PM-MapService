package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.dto.ParkingAreaRequestDto;
import com.capstone.team5.pmmap.entity.BuildingEntity;
import com.capstone.team5.pmmap.entity.ParkingAreaEntity;
import com.capstone.team5.pmmap.repository.BuildingRepository;
import com.capstone.team5.pmmap.repository.ParkingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAreaService {

    private final ParkingAreaRepository parkingAreaRepository;
    private final BuildingRepository buildingRepository;

    public void createParkingArea(ParkingAreaRequestDto parkingAreaRequestDto){
        try {
            BuildingEntity buildingEntity = buildingRepository.selectByName(parkingAreaRequestDto.getBuildingName());
            int buildingId = buildingEntity.getBuildingId();

            ParkingAreaEntity entity = ParkingAreaEntity.builder()
                    .name(parkingAreaRequestDto.getName())
                    .latitude(parkingAreaRequestDto.getLatitude())
                    .longitude(parkingAreaRequestDto.getLongitude())
                    .image(parkingAreaRequestDto.getImage())
                    .imageMap(parkingAreaRequestDto.getImageMap())
                    .buildingId(buildingId)
                    .build();

            parkingAreaRepository.insert(entity);
        }catch (Exception e) {
            throw e;
        }
    }
}
