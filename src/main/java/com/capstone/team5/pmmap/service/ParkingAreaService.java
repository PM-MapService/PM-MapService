package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.dto.ParkingAreaDetailDto;
import com.capstone.team5.pmmap.dto.ParkingAreaRequestDto;
import com.capstone.team5.pmmap.dto.ParkingAreaResponseDto;
import com.capstone.team5.pmmap.entity.BuildingEntity;
import com.capstone.team5.pmmap.entity.ParkingAreaEntity;
import com.capstone.team5.pmmap.repository.BuildingRepository;
import com.capstone.team5.pmmap.repository.ParkingAreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<ParkingAreaResponseDto> findAllParkingAreas(){
       try {
           List<ParkingAreaEntity> parkingAreaEntities = parkingAreaRepository.selectAll();
           List<ParkingAreaResponseDto> result = new ArrayList<>();
           for (ParkingAreaEntity parkingAreaEntity : parkingAreaEntities) {
               ParkingAreaResponseDto parkingAreaResponseDto = ParkingAreaResponseDto.builder()
                       .parkingAreaId(parkingAreaEntity.getParkingAreaId())
                       .name(parkingAreaEntity.getName())
                       .latitude(parkingAreaEntity.getLatitude())
                       .longitude(parkingAreaEntity.getLongitude())
                       .build();
               result.add(parkingAreaResponseDto);
           }
           return result;
       }catch (Exception e){
           throw e;
       }
    }

    public ParkingAreaDetailDto findParkingArea(int parkingAreaId){
        try{
            ParkingAreaEntity parkingAreaEntity = parkingAreaRepository.select(parkingAreaId);
            int buildingId = parkingAreaEntity.getBuildingId();

            BuildingEntity buildingEntity = buildingRepository.select(buildingId);
            String buildingName = buildingEntity.getName();

            ParkingAreaDetailDto parkingAreaDetailDto = ParkingAreaDetailDto.builder()
                    .name(parkingAreaEntity.getName())
                    .image(parkingAreaEntity.getImage())
                    .imageMap(parkingAreaEntity.getImageMap())
                    .buildingName(buildingName)
                    .build();
            return parkingAreaDetailDto;
        }catch (Exception e){
            throw e;
        }
    }
}
