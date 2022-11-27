package com.capstone.team5.pmmap.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NodeCoordinate {
    private double longitude;
    private double latitude;
}
