package com.capstone.team5.pmmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingAreaDetailDto {
    String name;
    String image;
    String imageMap;
    String buildingName;
}
