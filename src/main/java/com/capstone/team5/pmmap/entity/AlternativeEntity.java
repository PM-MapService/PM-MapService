package com.capstone.team5.pmmap.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AlternativeEntity {
    private int alternativeId;
    private String name;
    private List<Integer> nodes;
}
