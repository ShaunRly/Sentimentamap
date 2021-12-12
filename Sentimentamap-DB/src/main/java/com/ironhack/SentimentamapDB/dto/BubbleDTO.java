package com.ironhack.SentimentamapDB.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BubbleDTO {

    private long count;
    private double compound;
    private String matchingRule;

}
