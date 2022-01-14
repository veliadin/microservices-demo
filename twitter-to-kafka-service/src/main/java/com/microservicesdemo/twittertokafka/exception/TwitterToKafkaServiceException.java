package com.microservicesdemo.twittertokafka.exception;

import com.microservicesdemo.twittertokafka.TwitterToKafkaServiceApplication;

public class TwitterToKafkaServiceException extends RuntimeException{

    public TwitterToKafkaServiceException(){
        super();
    }

    public TwitterToKafkaServiceException(String message){
        super(message);
    }

    public TwitterToKafkaServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
