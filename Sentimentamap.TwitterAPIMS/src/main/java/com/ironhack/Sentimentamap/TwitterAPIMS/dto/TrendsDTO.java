package com.ironhack.Sentimentamap.TwitterAPIMS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrendsDTO {

    private String name;
    private String url;
    private String promoted_content;
    private String query;
    private int tweet_volume;

    public TrendsDTO(JSONObject trends) throws JSONException {
        this.name = trends.getString("name");
        this.url = trends.getString("url");
        this.promoted_content = trends.getString("promoted_content");
        this.query = trends.getString("query");
//        if(trends.getString("tweet_volume") != null) {
//            this.tweet_volume = Integer.parseInt(trends.getString("tweet_volume"));
//        }
//        else {
//            this.tweet_volume = 0;
//        }
    }

    @Override
    public String toString() {
        return "TrendsDTO{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", promoted_content='" + promoted_content + '\'' +
                ", query='" + query + '\'' +
                ", tweet_volume=" + tweet_volume +
                '}';
    }
}
