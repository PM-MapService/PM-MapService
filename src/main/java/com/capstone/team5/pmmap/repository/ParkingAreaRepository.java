package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.ParkingAreaEntity;

import java.util.List;

public interface ParkingAreaRepository {
    int insert(ParkingAreaEntity parkingAreaEntity);
    ParkingAreaEntity select(int parkingAreaId);
    int update(ParkingAreaEntity parkingAreaEntity);
    int delete(int parkingAreaId);

    List<ParkingAreaEntity> selectAll();
    List<ParkingAreaEntity> selectByBuilding(int buildingId);
}
