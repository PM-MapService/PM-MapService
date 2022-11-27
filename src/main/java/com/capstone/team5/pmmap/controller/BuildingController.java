package com.capstone.team5.pmmap.controller;

import com.capstone.team5.pmmap.dto.ErrorResponse;
import com.capstone.team5.pmmap.dto.NodeCoordinate;
import com.capstone.team5.pmmap.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BuildingController {

    final BuildingService buildingService;

    @GetMapping("/api/building")
    public ResponseEntity<?> searchBuildingLatLonByName(@RequestParam String buildingName){
        try {
            NodeCoordinate buildingCoordinate = buildingService.getCoordinateByName(buildingName);
            return ResponseEntity.status(HttpStatus.OK).body(buildingCoordinate);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("오류 발생"));
        }
    }
}
