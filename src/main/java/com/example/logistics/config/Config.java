//package com.example.logistics.config;
//
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.time.Duration;
//
//import static org.modelmapper.convention.MatchingStrategies.STRICT;
//
//@Configuration
//@RequiredArgsConstructor
//public class Config {
//    @Bean
//    public ModelMapper modelMapper(){
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(STRICT);
//        mapper.getConfiguration().setSkipNullEnabled(true);
//        return mapper;
//    }
//
//    @Bean
//    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
//        restTemplateBuilder.setConnectTimeout(Duration.ofMinutes(5));
//        restTemplateBuilder.setReadTimeout(Duration.ofMinutes(5));
//        return restTemplateBuilder.build();
//    }
//
//    @Bean
//    public WebClient.Builder webclient(){
//        return WebClient.builder();
//    }
//}
