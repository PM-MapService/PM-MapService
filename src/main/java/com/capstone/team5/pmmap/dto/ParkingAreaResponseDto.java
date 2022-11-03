package com.capstone.team5.pmmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingAreaResponseDto {
    int parkingAreaId;
    String name;
    double latitude;
    double longitude;
}
