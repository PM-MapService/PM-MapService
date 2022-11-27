package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.AlternativeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlternativeRepository {

    int insert(AlternativeEntity alternativeEntity);
    AlternativeEntity select(int alternativeId);
    AlternativeEntity selectByName(String name);
}
