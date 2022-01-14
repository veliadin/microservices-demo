package com.microservicesdemo.twittertokafka.runner.impl;

import com.microservicesdemo.twittertokafka.config.TwitterToKafkaServiceConfigData;
import com.microservicesdemo.twittertokafka.exception.TwitterToKafkaServiceException;
import com.microservicesdemo.twittertokafka.listener.TwitterKafkaStatusListener;
import com.microservicesdemo.twittertokafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockKafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;

    private final TwitterKafkaStatusListener twitterKafkaStatusListener;

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

    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\" " +
            "\"id\":\"{1}\" " +
            "\"text\":\"{2}\" " +
            "\"user\":{\"id\":\"{3}\"} " +
            "}";

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";



    public MockKafkaStreamRunner(TwitterToKafkaServiceConfigData configData, TwitterKafkaStatusListener statusListener) {
        this.twitterToKafkaServiceConfigData = configData;
        this.twitterKafkaStatusListener = statusListener;
    }


    @Override
    public void start() throws TwitterException {
        String[] keywords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        int minTweetLength = twitterToKafkaServiceConfigData.getMockMinTweetLength();
        int maxTweetLength = twitterToKafkaServiceConfigData.getMockMaxTweetLength();
        Long mockSleepMs = twitterToKafkaServiceConfigData.getMockSleepMs();
        LOG.info("Stating mock filtering twitter streams for keywords  {}",keywords);
        while (true){
            String formattedTweetAsRawJson = getFormattedTweet(keywords, minTweetLength,maxTweetLength);
            Status status = TwitterObjectFactory.createStatus(formattedTweetAsRawJson);
            twitterKafkaStatusListener.onStatus(status);
            sleep(mockSleepMs);
        }
    }

    private void sleep(Long mockSleepMs) {
        try {
            Thread.sleep(mockSleepMs);
        }catch (InterruptedException e){
            throw new TwitterToKafkaServiceException("Error while sleeping for waiting new status to create!!");
        }
    }

    private String getFormattedTweet(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[] {
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomTweetContent(keywords,minTweetLength,maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };
        String tweet = tweetAsRawJson;
        for (int i =0; i<params.length;i++){
            tweet = tweet.replace("{"+i+"}",params[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength+1)+minTweetLength;
        for (int i =0 ; i < tweetLength; i++){
            tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
        }
        return tweet.toString().trim();
    }
}
