package com.ironhack.Sentimentamap.MicroGeo.dao;

import com.fasterxml.jackson.databind.JsonNode;
import com.ironhack.Sentimentamap.MicroGeo.models.Cord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrackedPlaceCords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String placeName;
    @Embedded
    private Cord cords;

    public TrackedPlaceCords(String label, JsonNode jsonNode) {

        this.placeName = label;
        this.cords = new Cord(
                jsonNode.get("geonames").get(0).get("lat").asDouble(),
                jsonNode.get("geonames").get(0).get("lng").asDouble()
        );
    }
}
