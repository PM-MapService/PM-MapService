package com.capstone.team5.pmmap.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingAreaEntity {
    private int parkingAreaId;
    private String name;
    private double latitude;
    private double longitude;
    private String image;
    private String imageMap;
    private int buildingId;
}
