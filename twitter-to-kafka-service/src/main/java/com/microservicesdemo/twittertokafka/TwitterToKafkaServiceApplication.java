package com.microservicesdemo.twittertokafka;

import com.microservicesdemo.twittertokafka.config.TwitterToKafkaServiceConfigData;
import com.microservicesdemo.twittertokafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
    private final TwitterToKafkaServiceConfigData configData;
    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData configData,
                                            StreamRunner streamRunner) {
        this.configData = configData;
        this.streamRunner = streamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("App Stated...");
        LOGGER.info(Arrays.toString(configData.getTwitterKeywords().toArray(new String[] {})));
        LOGGER.info(configData.getWelcomeMessage());
        streamRunner.start();

    }
}
