package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.BuildingEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuildingRepository {
    BuildingEntity selectByName(String name);
}
