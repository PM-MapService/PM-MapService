package com.capstone.team5.pmmap.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    public final String errorMassage;
}
