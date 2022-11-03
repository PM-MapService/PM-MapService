package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.BuildingEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuildingRepository {
    int insert(BuildingEntity buildingEntity);
    BuildingEntity select(int buildingId);
    int update(BuildingEntity buildingEntity);
    int delete(int buildingId);
    BuildingEntity selectByName(String name);
}
