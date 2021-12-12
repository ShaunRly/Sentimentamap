package com.ironhack.Sentimentamap.TwitterAPIMS.Config;

import feign.codec.Decoder;

import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableFeignClients
@EnableScheduling
public class FeignResponseDecoderConfig {

    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    /**
     * @return
     */
    @Bean
    Encoder feignEncoder() {
        return new SpringEncoder(messageConverters);
    }

    /**
     * @return
     */
    @Bean
    Decoder feignDecoder() {
        return new SpringDecoder(messageConverters);
    }
}
