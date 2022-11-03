package com.capstone.team5.pmmap.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlternativeEntity {
    private int alternativeId;
    private String name;
    private List<Integer> nodes;
}
