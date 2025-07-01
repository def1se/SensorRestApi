package ru.ashitok.spring.SensorRestApi.Consumer;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import ru.ashitok.spring.SensorRestApi.models.Measurement;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        System.out.println(restTemplate.getForObject("http://localhost:8080/measurements", String.class));

//        RestTemplate restTemplate = new RestTemplate();
//
//        for (int i = 0; i < 1000; i++) {
//            Map<String, Object> jsonToSend = new HashMap<>();
//
//            double min = -100.0;
//            double max = 100.0;
//            Random random = new Random();
//                jsonToSend.put("value", random.nextDouble() * (max - min) + min);
//                jsonToSend.put("raining", random.nextBoolean());
//                jsonToSend.put("sensor", Map.of("name", "Sergey"));
//
//            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToSend);
//
//            System.out.println(restTemplate.postForObject("http://localhost:8080/measurements/add", request, String.class));
//        }
    }
}
