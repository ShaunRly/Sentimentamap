package com.ironhack.SentimentamapDB.dto;

import com.ironhack.SentimentamapDB.model.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapDataDTO {

    private float compound;
    private String matchingRules;
}
