package com.capstone.team5.pmmap.controller;

import com.capstone.team5.pmmap.dto.ErrorResponse;
import com.capstone.team5.pmmap.dto.ParkingAreaDetailDto;
import com.capstone.team5.pmmap.dto.ParkingAreaRequestDto;
import com.capstone.team5.pmmap.dto.ParkingAreaResponseDto;
import com.capstone.team5.pmmap.service.ParkingAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingAreaController {

    private final ParkingAreaService parkingAreaService;
    @PostMapping("/api/parking-areas")
    public ResponseEntity<?> createParkingArea(@RequestBody ParkingAreaRequestDto parkingAreaRequestDto){
      try {
          parkingAreaService.createParkingArea(parkingAreaRequestDto);
          return ResponseEntity.status(HttpStatus.CREATED).body("주차구역 추가 성공");
      }catch (Exception e){
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
      }
    }

    @GetMapping("/api/parking-areas")
    public ResponseEntity<?> loadAllParkingAreas(){
       try {
           List<ParkingAreaResponseDto> allParkingAreas = parkingAreaService.findAllParkingAreas();
           return ResponseEntity.status(HttpStatus.OK).body(allParkingAreas);
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
       }
    }

    @GetMapping("/api/parking-areas/{id}")
    public ResponseEntity<?> findParkingArea(@PathVariable int id){
        try{
            ParkingAreaDetailDto parkingAreaDetailDto = parkingAreaService.findParkingArea(id);
            return ResponseEntity.status(HttpStatus.OK).body(parkingAreaDetailDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
        }
    }

    
    @GetMapping("/api/parking-areas/building")
    public ResponseEntity<?> searchParkingAreasByBuilding(@RequestParam String buildingName){
        try {
            List<ParkingAreaResponseDto> parkingAreasByBuilding = parkingAreaService.findParkingAreasByBuilding(buildingName);
            return ResponseEntity.status(HttpStatus.OK).body(parkingAreasByBuilding);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
        }
    }
}
