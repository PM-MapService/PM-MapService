package com.capstone.team5.pmmap.repository;

import com.capstone.team5.pmmap.entity.NodeEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NodeReposiotry {
    int insert(NodeEntity nodeEntity);
    NodeEntity select(int nodeId);
    NodeEntity selectByName(String nodeName);
}
