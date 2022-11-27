package com.capstone.team5.pmmap.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AlternativeEntity {
    private int alternativeId;
    private String name;
    private int[] nodes;
}
