package com.ironhack.Sentimentamap.MicroGeo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sentiment {
    private float compound;
    private float neg;
    private float neu;
    private float pos;

    @Override
    public String toString() {
        return "Sentiment{" +
                "compound=" + compound +
                ", neg=" + neg +
                ", neu=" + neu +
                ", pos=" + pos +
                '}';
    }
}
