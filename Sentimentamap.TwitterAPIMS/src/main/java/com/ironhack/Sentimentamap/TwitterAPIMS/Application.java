package com.ironhack.Sentimentamap.TwitterAPIMS;

import com.ironhack.Sentimentamap.TwitterAPIMS.Main.TwitterStreamer;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws JSONException, IOException, URISyntaxException {
		SpringApplication.run(TwitterStreamer.class, args);
	}

}
