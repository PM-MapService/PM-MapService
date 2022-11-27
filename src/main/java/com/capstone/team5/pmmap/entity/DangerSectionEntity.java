package com.capstone.team5.pmmap.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DangerSectionEntity {
    private int dangerSectionId;
    private String name;
    private String roadType;
    private double startLatitude;
    private double startLongitude;
    private double finishLatitude;
    private double finishLongitude;
    private int alternativeId;
}
