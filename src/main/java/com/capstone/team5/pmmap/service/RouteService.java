package com.capstone.team5.pmmap.service;

import com.capstone.team5.pmmap.entity.AlternativeEntity;
import com.capstone.team5.pmmap.entity.DangerSectionEntity;
import com.capstone.team5.pmmap.entity.NodeEntity;
import com.capstone.team5.pmmap.repository.AlternativeRepository;
import com.capstone.team5.pmmap.repository.DangerSectionRepository;
import com.capstone.team5.pmmap.repository.NodeRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final DangerSectionRepository dangerSectionRepository;
    private final AlternativeRepository alternativeRepository;
    private final NodeRepository nodeRepository;

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

        DangerSectionEntity danger = dangerSectionRepository.selectByName("경사_매점뒤");
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

                //lng1 == 126.6536066844918&& lat1 == 37.45006244351926&& lng2 == 126.65408719190675&& lat2 == 37.45021798947014

                if(lng1 == danger.getStartLongitude()&& lat1 == danger.getStartLatitude()&& lng2 == danger.getFinishLongitude() && lat2 == danger.getFinishLatitude()){
                    change = true;
                    index = i;
                }
            }
        }
        if(!change){
           return responseStr;
        }
        JSONArray changeFeatures = new JSONArray();
        for(int i=0; i<index;i++) {
            changeFeatures.put(features.get(i));
        }
        int alternativeId = danger.getAlternativeId();
        //AlternativeEntity alternativeEntity = alternativeRepository.select(alternativeId);
        //List<Integer> alternativeNodes = alternativeEntity.getNodes();
        List<Integer> alternativeNodes = Arrays.asList(8, 9, 10, 11, 12);
        for(int i=0; i<alternativeNodes.size(); i++){
            int nodeId = alternativeNodes.get(i);
            NodeEntity node = nodeRepository.select(nodeId);
            System.out.println(nodeId);
            JSONObject p = makePoint(node.getLongitude(), node.getLatitude(), node.getDescription(), node.getTurnType());
            changeFeatures.put(p);

            if(i!=alternativeNodes.size()-1){
                int nextId = alternativeNodes.get(i+1);
                NodeEntity next = nodeRepository.select(nextId);
                JSONObject l = makeLine(node.getLongitude(), node.getLatitude(), next.getLongitude(), next.getLatitude());
                changeFeatures.put(l);
            }

        }
        int dist=calDistanceFromFeatures(changeFeatures);

        JSONObject firstPoint= (JSONObject) changeFeatures.get(0);
        JSONObject firstProperties= (JSONObject) firstPoint.getJSONObject("properties");
        firstProperties.put("totalDistance",dist);
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

    public int calDistanceFromFeatures(JSONArray features) throws JSONException {
        double distance=0;
        double curlon,curlat;
        double prelon =0,prelat = 0;
        int cnt=0;
        for(int i = 0; i < features.length(); i++){
            JSONObject data=(JSONObject) features.get(i);
            JSONObject geometry=data.getJSONObject("geometry");
            if(geometry.getString("type").equals("Point")){
                if(cnt==0) {
                    JSONArray coord= (JSONArray) geometry.get("coordinates");
                    prelon=coord.getDouble(1);
                    prelat=coord.getDouble(0);}
                else{
                    JSONArray coord= (JSONArray) geometry.get("coordinates");
                    curlon=coord.getDouble(1);
                    curlat=coord.getDouble(0);
                    distance+=calDistance(curlat,curlon,prelat,prelon);
                    prelat=curlat;
                    prelon=curlon;
                }
                cnt++;
            }
        }
        return (int)distance;
    }
    public double calDistance(double lat0, double lon0,double lat, double lon) {
        double theta = lon0 - lon;
        double dist = Math.sin(deg2rad(lat0)) * Math.sin(deg2rad(lat)) + Math.cos(deg2rad(lat0)) * Math.cos(deg2rad(lat)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;
        return dist;
    }
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
