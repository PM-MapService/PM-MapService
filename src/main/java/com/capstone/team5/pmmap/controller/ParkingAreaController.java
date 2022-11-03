package com.capstone.team5.pmmap.controller;

import com.capstone.team5.pmmap.dto.ErrorResponse;
import com.capstone.team5.pmmap.dto.ParkingAreaRequestDto;
import com.capstone.team5.pmmap.service.ParkingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ParkingAreaController {

    private final ParkingAreaService parkingAreaService;
    @PostMapping("/api/parking-areas")
    public ResponseEntity<?> createParkingArea(@RequestBody ParkingAreaRequestDto parkingAreaRequestDto){
      try {
          parkingAreaService.createParkingArea(parkingAreaRequestDto);
          return ResponseEntity.status(HttpStatus.CREATED).body("주차구역 추가 성공");
      }catch (Exception e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
      }
    }
}
