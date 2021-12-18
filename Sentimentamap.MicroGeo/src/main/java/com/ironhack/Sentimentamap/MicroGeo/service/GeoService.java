package com.ironhack.Sentimentamap.MicroGeo.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.Sentimentamap.MicroGeo.dao.TrackedPlaceCords;
import com.ironhack.Sentimentamap.MicroGeo.dto.QueryDTO;
import com.ironhack.Sentimentamap.MicroGeo.dto.TweetDataDTO;
import com.ironhack.Sentimentamap.MicroGeo.models.Cord;
import com.ironhack.Sentimentamap.MicroGeo.proxy.TweetDBProxy;
import com.ironhack.Sentimentamap.MicroGeo.repository.TrackedPlaceCordRepository;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.geotools.geojson.geom.GeometryJSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeoService {

    @Autowired
    TweetDBProxy tweetDBProxy;

    private String geoCodeUsername = "Emprahh";

    @Autowired
    TrackedPlaceCordRepository trackedPlaceCordRepository;

    public FeatureCollection generateMapData(String dateTimeStart, String dateTimeEnd) throws URISyntaxException, IOException {
        System.out.println(dateTimeStart);
        System.out.println(dateTimeEnd);
        List<TweetDataDTO> placeTweets = tweetDBProxy.getPlaceData(dateTimeStart, dateTimeEnd) ;
        List<Feature> featureList = new ArrayList<>();
        for (var tweet : placeTweets){
            if(tweet.getMatchingRules().split("/").length == 2) {
                String label = tweet.getMatchingRules().split("/")[1];
                label = label.substring(0, label.length() - 2);
                Cord cord = getCordsByPlaceName(label);
                Feature feature = Feature.fromGeometry(Point.fromLngLat(cord.getLongitude(), cord.getLatitude()));
                feature.addNumberProperty("sentiment", tweet.getCompound());
                featureList.add(feature);
            }
        }
        System.out.println(featureList);
        return FeatureCollection.fromFeatures(featureList);
    }

    public Cord getCordsByPlaceName(String label) throws URISyntaxException, IOException {
        Optional<TrackedPlaceCords> foundTrackedPlace = trackedPlaceCordRepository.findByPlaceName(label);

        if(foundTrackedPlace.isPresent()){
            return foundTrackedPlace.get().getCords();
        } else {
            HttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom()
                            .setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();

            URIBuilder uriBuilder = new URIBuilder("http://api.geonames.org/searchJSON?q=" + label.replaceAll("\\s+", "") + "&maxRows=10&username=" + geoCodeUsername);
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            HttpResponse response = httpClient.execute(httpGet);
            httpGet.setHeader("content-type", "application/json");
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);

            TrackedPlaceCords trackedPlaceCords = new TrackedPlaceCords(label, jsonNode);
            trackedPlaceCordRepository.save(trackedPlaceCords);
            return trackedPlaceCords.getCords();
        }
    }

}
