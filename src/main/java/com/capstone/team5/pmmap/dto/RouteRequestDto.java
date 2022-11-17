package com.capstone.team5.pmmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteRequestDto {
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
}
