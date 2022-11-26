package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.DangerSectionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DangerSectionRepository {

    int insert(DangerSectionEntity dangerSectionEntity);
    DangerSectionEntity select(int dangerSectionId);
    DangerSectionEntity selectByName(String dangerSectionName);
}
