package com.capstone.team5.pmmap.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class NodeEntity {
    private int nodeId;
    private String name;
    private double latitude;
    private double longitude;
    private String description;
    private int turnType;
}
