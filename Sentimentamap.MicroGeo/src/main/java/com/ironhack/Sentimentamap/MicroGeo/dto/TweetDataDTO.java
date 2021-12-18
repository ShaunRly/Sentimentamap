package com.ironhack.Sentimentamap.MicroGeo.dto;

import com.ironhack.Sentimentamap.MicroGeo.models.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embedded;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TweetDataDTO {

    private float compound;
    private String matchingRules;
}
