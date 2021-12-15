package com.ironhack.Sentimentamap.MicroGeo.proxy;

import com.ironhack.Sentimentamap.MicroGeo.dto.QueryDTO;
import com.ironhack.Sentimentamap.MicroGeo.dto.TweetDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "tweet-database-service")
public interface TweetDBProxy {

    @GetMapping("/api/v1/tweetdb/mapdata")
    List<TweetDataDTO> getPlaceData(@RequestParam String dateTimeStart, @RequestParam String dateTimeEnd);
}
