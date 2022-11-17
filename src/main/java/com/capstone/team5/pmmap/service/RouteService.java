package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.dto.RouteRequestDto;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RouteService {
    public Response findRoute() throws IOException {

        String tmapAppKey = "l7xxcf8d3af1899b4f168f7a593671f0c749";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"startX\":126.92365493654832,\"startY\":37.556770374096615,\"endX\":126.92432158129688,\"endY\":37.55279861528311,\"startName\":\"%EC%B6%9C%EB%B0%9C\",\"endName\":\"%EB%8F%84%EC%B0%A9\"}", mediaType);
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
