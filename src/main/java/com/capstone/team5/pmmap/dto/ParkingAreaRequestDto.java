package com.capstone.team5.pmmap.dto;

import lombok.Data;

@Data
public class ParkingAreaRequestDto {
    String name;
    double latitude;
    double longitude;
    String image;
    String imageMap;
    String buildingName;
}
