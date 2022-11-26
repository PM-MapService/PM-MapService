package com.capstone.team5.pmmap.service;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RouteService {
    public Response findRoute(double startLng, double startLat, double endLng, double endLat) throws IOException {

        String tmapAppKey = "l7xxcf8d3af1899b4f168f7a593671f0c749";
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create("{\"startX\":"+(float)startLng+",\"startY\":"+(float)startLat+",\"endX\":"+(float)endLng+",\"endY\":"+(float)endLat+",\"startName\":\"출발지\",\"endName\":\"도착지\"}", mediaType);
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

    public String changeRoute(String responseStr) throws JSONException {

        JSONObject changed = new JSONObject();
        JSONObject original = new JSONObject(responseStr);
        JSONArray features = original.getJSONArray("features");
        int index=0;
        boolean change = false;
        for(int i=0; i<features.length(); i++){
            JSONObject feature = features.getJSONObject(i);
            JSONObject geometry = feature.getJSONObject("geometry");
            if(geometry.getString("type").equals("LineString")){
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                JSONArray coordinate1  = coordinates.getJSONArray(0);
                Double lng1 = coordinate1.getDouble(0);
                Double lat1 = coordinate1.getDouble(1);
                JSONArray coordinate2 = coordinates.getJSONArray(1);
                Double lng2 = coordinate2.getDouble(0);
                Double lat2 = coordinate2.getDouble(1);

                if(lng1 == 126.6536066844918&& lat1 == 37.45006244351926&& lng2 == 126.65408719190675&& lat2 == 37.45021798947014){
                    change = true;
                    index = i-2;
                }
            }
        }
        if(!change){
           return responseStr;
        }
        JSONArray changeFeatures = new JSONArray();
        for(int i=0; i<=index;i++) {
            changeFeatures.put(features.get(i));
        }

        JSONObject p = makePoint(126.6536066844918, 37.45006244351926,"매점 앞 좌회전 후, 10m 이동", 12);
        changeFeatures.put(p);
        JSONObject l = makeLine(126.6536066844918,37.45006244351926, 126.65351502502821,37.45010410372546);
        changeFeatures.put(l);
        p = makePoint(126.65351502502821,37.45010410372546, "우회전 후 173m 이동",13);
        changeFeatures.put(p);
        l = makeLine(126.65351502502821,37.45010410372546, 126.65445934549194,37.45146785073327);
        changeFeatures.put(l);

        p = makePoint(126.65445934549194,37.45146785073327, "쪽문에서 우회전 후 150m 이동", 13);
        changeFeatures.put(p);
        l = makeLine(126.65445934549194,37.45146785073327, 126.65661749626233,37.45095405965877);
        changeFeatures.put(l);

        p = makePoint(126.65661749626233,37.45095405965877, "우회전 후 34m 이동", 13);
        changeFeatures.put(p);
        l = makeLine(126.65661749626233,37.45095405965877, 126.65672027222361,37.45068187089347);
        changeFeatures.put(l);

        p= makePoint(126.65672027222361,37.45068187089347, "도착", 201);
        changeFeatures.put(p);

        changed.put("features", changeFeatures);

        return changed.toString();
    }

    public JSONObject makePoint(double lng, double lat, String desc, int turnType) throws JSONException {
        JSONArray coordinate = new JSONArray();

        coordinate.put(lng);
        coordinate.put(lat);

        JSONObject geometry = new JSONObject();
        geometry.put("type", "Point");
        geometry.put("coordinates", coordinate);

        JSONObject properties = new JSONObject();
        properties.put("description", desc);
        properties.put("turnType", turnType);

        JSONObject feature = new JSONObject();
        feature.put("geometry", geometry);
        feature.put("properties", properties);

        return feature;
    }

    public JSONObject makeLine(double lng, double lat, double endLng, double endLat) throws JSONException {
        JSONArray coordinates = new JSONArray();
        JSONArray start = new JSONArray();
        start.put(lng);
        start.put(lat);
        coordinates.put(start);
        JSONArray end = new JSONArray();
        end.put(endLng);
        end.put(endLat);
        coordinates.put(end);
        /*
        for(int i=0; i<coordinatesList.size(); i++){
            List<Float> coordinateList = coordinatesList.get(i);
            JSONArray coordinate = new JSONArray();
            coordinate.put(coordinateList.get(0));
            coordinate.put(coordinateList.get(1));
            coordinates.put(coordinate);
        }*/

        JSONObject geometry = new JSONObject();
        geometry.put("type", "LineString");
        geometry.put("coordinates", coordinates);

        JSONObject feature = new JSONObject();
        feature.put("geometry", geometry);

        return feature;
    }
}
