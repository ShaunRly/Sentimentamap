package com.ironhack.Sentimentamap.TwitterAPIMS;

import com.ironhack.Sentimentamap.TwitterAPIMS.Main.TwitterStreamer;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
@EnableFeignClients
public class TwitterStreamService implements CommandLineRunner{

	@Autowired
	TwitterStreamer twitterStreamer;

	public static void main(String[] args) {
		SpringApplication.run(TwitterStreamService.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		twitterStreamer.mainLoop();
	}
}
