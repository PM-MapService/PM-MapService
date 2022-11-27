package com.capstone.team5.pmmap.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuildingEntity {
    private int buildingId;
    private String name;
    private double latitude;
    private double longitude;
}
