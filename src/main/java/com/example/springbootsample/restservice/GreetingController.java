package com.example.springbootsample.restservice;

import com.example.springbootsample.consumingrest.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private static RestTemplate restTemplate;
    public GreetingController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/consuming")
    public Quote consuming() {
        return restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
    }

    @GetMapping("/consuming2")
    public String consuming2(@Autowired RestTemplate restTemplate) {
        return restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class).toString();
    }
}
