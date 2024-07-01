package com.example.Config;

import com.example.Entity.Wallet;
import com.example.Repo.WalletRepo;
import com.example.Service.WalletService;
import com.example.TxnInitPayload;
import com.example.UserCreatedPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;


@Configuration
public class KafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPPER = new ObjectMapper();

    @Autowired
    private WalletRepo walletRepo;

    @Autowired
    private WalletService walletService;


    @KafkaListener(topics = "${user.created.topic}",groupId = "walletApp")
    public void consumeUserCreatedTopic(ConsumerRecord payload) throws JsonProcessingException{
        UserCreatedPayload userCreatedPayload = OBJECT_MAPPPER.readValue(payload.value().toString(), UserCreatedPayload.class);
        MDC.put("requestId",userCreatedPayload.getRequestId());
        LOGGER.info("Read from kafka : {} ",userCreatedPayload);
        Wallet wallet = new  Wallet();
        wallet.setBalance(1000.00);
        wallet.setUserId(userCreatedPayload.getUserId());
        wallet.setUserEmail(userCreatedPayload.getUserEmail());
        walletRepo.save(wallet);
        MDC.clear();


    }
    @KafkaListener(topics = "${txn.init.topic}",groupId = "wallet")
    public void consumeTxnInt(ConsumerRecord payload) throws JsonProcessingException, ExecutionException,InterruptedException {
        TxnInitPayload txnInitPayload = OBJECT_MAPPPER.readValue(payload.value().toString(), TxnInitPayload.class);
        MDC.put("requestId",txnInitPayload.getRequestId());
        LOGGER.info("Read from kafka : {} ",txnInitPayload);
        walletService.walletTransaction(txnInitPayload);




    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }








}
