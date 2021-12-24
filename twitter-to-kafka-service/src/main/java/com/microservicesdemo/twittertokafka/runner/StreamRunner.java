package com.microservicesdemo.twittertokafka.runner;

import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

@Component
public interface StreamRunner {
    void start() throws TwitterException;
}
