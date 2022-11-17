package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.dto.RouteRequestDto;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RouteService {
    public Response findRoute(double startLng, double startLat, double endLng, double endLat) throws IOException {

        String tmapAppKey = "l7xxcf8d3af1899b4f168f7a593671f0c749";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"startX\":"+startLng+",\"startY\":"+startLat+",\"endX\":"+endLng+",\"endY\":"+endLat+",\"startName\":\"출발지\",\"endName\":\"도착지\"}", mediaType);
        Request request = new Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", tmapAppKey)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }
}
