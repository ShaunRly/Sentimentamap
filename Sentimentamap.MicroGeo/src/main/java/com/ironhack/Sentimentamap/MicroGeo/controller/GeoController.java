package com.ironhack.Sentimentamap.MicroGeo.controller;

import com.ironhack.Sentimentamap.MicroGeo.models.Cord;
import com.ironhack.Sentimentamap.MicroGeo.service.GeoService;
import com.mapbox.geojson.FeatureCollection;
import org.geotools.geojson.geom.GeometryJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoController {

    @Autowired
    GeoService geoService;

    @GetMapping("/data")
    public String getMapData(@RequestParam String dateTimeStart, @RequestParam String dateTimeEnd) throws URISyntaxException, IOException {
        return geoService.generateMapData(dateTimeStart, dateTimeEnd).toJson();
    }


}
