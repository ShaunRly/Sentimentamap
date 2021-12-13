package com.ironhack.Sentimentamap.TwitterAPIMS.Main;

import com.github.scribejava.core.model.Response;
import com.ironhack.Sentimentamap.TwitterAPIMS.Proxy.SentimentProxy;
import com.ironhack.Sentimentamap.TwitterAPIMS.dto.TrendsDTO;
import com.ironhack.Sentimentamap.TwitterAPIMS.dto.TweetDTO;
import io.github.redouane59.twitter.IAPIEventListener;
import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.stream.StreamRules;
import io.github.redouane59.twitter.dto.tweet.TweetV2;
import io.github.redouane59.twitter.signature.TwitterCredentials;
import org.apache.http.impl.client.HttpClients;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.Future;

@Component
@EnableFeignClients
@EnableAsync
public class TwitterStreamer {

    private final SentimentProxy sentimentProxy;

    public TwitterStreamer(SentimentProxy sentimentProxy){
        this.sentimentProxy = sentimentProxy;
    }

    public Map<String, String> lastRules = new HashMap<>();

    public TwitterClient twitterClient = new TwitterClient(TwitterCredentials.builder()
            .accessToken("AAAAAAAAAAAAAAAAAAAAAMNHWAEAAAAAmjAtURmNJnppCu%2FZmYfCClOOx0U%3D2lV9JEZv3cJ2MbEVFqwRs0S7Ijs4neLSMCn4swQJvjgfF8aiEb")
            .apiKey("d3epwKMaUdJxRxf4h3MuCaSh3")
            .apiSecretKey("KmigqPIghizd3wWKRfvhtMxYwUETXrd2KezG15IjYs1SCGoyPK")
            .build());

    public void mainLoop() throws JSONException, IOException, URISyntaxException {
        scheduledRuleMethod();
        tweetStream();
    }

    @Async
    private void tweetStream() {

        IAPIEventListener eventListener = new IAPIEventListener() {
            @Override
            public void onStreamError(int i, String s) {
                System.out.println(s);
            }

            @Override
            public void onTweetStreamed(Tweet tweet) {
                TweetV2 tweetV2 = (TweetV2) tweet;
                List<String> matchingRules = new ArrayList<>();
                for (StreamRules.StreamRule streamRules : tweetV2.getMatchingRules()){
                    matchingRules.add(streamRules.getTag());
                }

                TweetDTO tweetDTO = new TweetDTO(
                        tweet.getText(),
                        tweet.getCreatedAt(),
                        tweet.getGeo().getCoordinates() != null ? tweet.getGeo().getCoordinates().toString() : "No Geo Location",
                        matchingRules
                );


                try {
                    sentimentProxy.postTweetsForSentimentAnalysis(tweetDTO);
                } catch (Exception e){
                    System.out.println(e);
                }
            }

            @Override
            public void onUnknownDataStreamed(String s) {
                System.out.println("UNKNOWN - " + s);
            }

            @Override
            public void onStreamEnded(Exception e) {
                System.out.println(e);
            }
        };

        Future<Response> tweetStream = twitterClient.startFilteredStream(eventListener);

    }

    @Scheduled(cron = " 0 0/30 * * * *")
    @Async
    public void scheduledRuleMethod() throws JSONException, URISyntaxException, IOException {
        List<String> trends = getTrends();
        Map<String, String> trendRules = buildTrendRules(trends);

        Map<String, String> fullRules = trendRules;
        fullRules.put("(#NewYork OR New York) sample:1", "Place1/New York");
        fullRules.put("(#London OR London) sample:1", "Place2/London");
        fullRules.put("(#Manchester OR Manchester) sample:1", "Place3/Manchester");
        fullRules.put("(#Barcelona OR Barcelona) sample:1", "Place4/Barcelona");
        fullRules.put("(#Paris OR Paris) sample:1", "Place5/Paris");

        System.out.println(fullRules);

        //Temp fix for some trends producing invalid JSON requests
        try {
            setupRules(twitterClient.getBearerToken(), fullRules);
            lastRules = fullRules;
        } catch (Exception e){
            setupRules(twitterClient.getBearerToken(), lastRules);
        }
    }

    private List<String> getTrends() throws URISyntaxException, IOException, JSONException {
        List<String> trends = new ArrayList<>();
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/1.1/trends/place.json?id=1");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", "AAAAAAAAAAAAAAAAAAAAAMNHWAEAAAAAmjAtURmNJnppCu%2FZmYfCClOOx0U%3D2lV9JEZv3cJ2MbEVFqwRs0S7Ijs4neLSMCn4swQJvjgfF8aiEb"));
        HttpResponse response = httpClient.execute(httpGet);
        httpGet.setHeader("content-type", "application/json");
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            JSONArray json = new JSONArray(EntityUtils.toString(entity, "UTF-8"));
            JSONObject jsonObject = json.getJSONObject(0);
            List<String> trendsKeywordList = new ArrayList<>();
            int topTrendsLimit = 5;
            int counter = 0;
            while(trendsKeywordList.size() < 5) {
                TrendsDTO trendsDTO = new TrendsDTO((JSONObject) jsonObject.getJSONArray("trends").get(counter));
                if(trendsDTO.getQuery().length() < 30) {
                    trendsKeywordList.add(trendsDTO.getName());
                }
                counter++;
            }
            return trendsKeywordList;
        }
        throw new JSONException("No JSON Content");
    }

    private Map<String, String> buildTrendRules(List<String> trends){
        Map<String, String> trendRules = new HashMap<>();
        int trendCounter = 1;
        for (var trend : trends){
            trendRules.put("(" + trend + ")" + " sample:1", "Trend" + trendCounter + "/" + trend);
            trendCounter++;
        }
        return trendRules;
    }

    private void setupRules(String bearerToken, Map<String, String> rules) throws IOException, URISyntaxException, JSONException {
        List<String> existingRules = getRules(bearerToken);
        if (existingRules.size() > 0) {
            deleteRules(bearerToken, existingRules);
        }
        createRules(bearerToken, rules);

    }

    /*
     * Helper method to create rules for filtering
     * */
    private void createRules(String bearerToken, Map<String, String> rules) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpPost.setHeader("content-type", "application/json");
        StringEntity body = new StringEntity(getFormattedString("{\"add\": [%s]}", rules));
        httpPost.setEntity(body);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            System.out.println(EntityUtils.toString(entity, "UTF-8"));
        }
    }

    /*
     * Helper method to get existing rules
     * */
    private List<String> getRules(String bearerToken) throws URISyntaxException, IOException, JSONException {
        List<String> rules = new ArrayList<>();
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("content-type", "application/json");
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            JSONObject json = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
            if (json.length() > 1) {
                JSONArray array = (JSONArray) json.get("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = (JSONObject) array.get(i);
                    rules.add(jsonObject.getString("id"));
                }
            }
        }
        return rules;
    }

    /*
     * Helper method to delete rules
     * */
    private void deleteRules(String bearerToken, List<String> existingRules) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpPost.setHeader("content-type", "application/json");
        StringEntity body = new StringEntity(getFormattedString("{ \"delete\": { \"ids\": [%s]}}", existingRules));
        httpPost.setEntity(body);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            System.out.println(EntityUtils.toString(entity, "UTF-8"));
        }
    }

    private String getFormattedString(String string, List<String> ids) {
        StringBuilder sb = new StringBuilder();
        if (ids.size() == 1) {
            return String.format(string, "\"" + ids.get(0) + "\"");
        } else {
            for (String id : ids) {
                sb.append("\"" + id + "\"" + ",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }

    private String getFormattedString(String string, Map<String, String> rules) {
        StringBuilder sb = new StringBuilder();
        if (rules.size() == 1) {
            String key = rules.keySet().iterator().next();
            return String.format(string, "{\"value\": \"" + key + "\", \"tag\": \"" + rules.get(key) + "\"}");
        } else {
            for (Map.Entry<String, String> entry : rules.entrySet()) {
                String value = entry.getKey();
                String tag = entry.getValue();
                sb.append("{\"value\": \"" + value + "\", \"tag\": \"" + tag + "\"}" + ",");
            }
            String result = sb.toString();
            return String.format(string, result.substring(0, result.length() - 1));
        }
    }

}
