package com.capstone.team5.pmmap.controller;

import com.capstone.team5.pmmap.dto.ErrorResponse;
import com.capstone.team5.pmmap.dto.RouteRequestDto;
import com.capstone.team5.pmmap.service.RouteService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/api/route")
    public ResponseEntity<?> findRoute(){
        try{
            Response response = routeService.findRoute();
            return ResponseEntity.status(HttpStatus.OK).body(response.body().string());
        }catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("IOException 발생"));
        }
    }
}
