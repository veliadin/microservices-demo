package com.microservicesdemo.twittertokafka.runner.impl;

import com.microservicesdemo.twittertokafka.config.TwitterToKafkaServiceConfigData;
import com.microservicesdemo.twittertokafka.listener.TwitterKafkaStatusListener;
import com.microservicesdemo.twittertokafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

import java.util.Random;

@Component
public class MockKafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData configData;

    private final TwitterKafkaStatusListener statusListener;

    private static final Random RANDOM = new Random();

    private static final String WORDS[] = new String[] {
            "dummy",
            "Letraset",
            "Latin",
            "more",
            "Evil",
            "variations",
            "anything",
            "ethics",
            "accompanied",
            "chunks",
            "sentence"
    };

    public MockKafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterKafkaStatusListener statusListener) {
        this.configData = configData;
        this.statusListener = statusListener;
    }


    @Override
    public void start() throws TwitterException {

    }
}
