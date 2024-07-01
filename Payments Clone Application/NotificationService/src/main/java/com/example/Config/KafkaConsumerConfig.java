package com.example.Config;


import com.example.UserCreatedPayload;
import com.example.WalletUpdatePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
public class KafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPPER = new ObjectMapper();

    @Autowired
    private JavaMailSender javaMailSender;


    @KafkaListener(topics = "${user.created.topic}",groupId = "email")
    public void consumeUserCreatedTopic(ConsumerRecord payload) throws JsonProcessingException{
        UserCreatedPayload userCreatedPayload = OBJECT_MAPPPER.readValue(payload.value().toString(), UserCreatedPayload.class);
        MDC.put("requestId",userCreatedPayload.getRequestId());
        LOGGER.info("Read from kafka : {} ",userCreatedPayload);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("msrkreddy111@gmail.com");
        simpleMailMessage.setSubject("Welcome "+userCreatedPayload.getUserName());
        simpleMailMessage.setText("Hi "+userCreatedPayload.getUserName()+", Welcome in payments wallet ");
        simpleMailMessage.setCc("msrkreddy2001@gmail.com");
        simpleMailMessage.setTo(userCreatedPayload.getUserEmail());
        javaMailSender.send(simpleMailMessage);
        MDC.clear();


    }
    @KafkaListener(topics = "${wallet.updated.topic}",groupId = "wallet")
    public void consumeWalletUpdatedTopic(ConsumerRecord payload) throws JsonProcessingException {
        WalletUpdatePayload walletUpdatePayload = OBJECT_MAPPPER.readValue(payload.value().toString(), WalletUpdatePayload.class);
        MDC.put("requestId", walletUpdatePayload.getRequestId());
        LOGGER.info("Read from kafka WalletUpdated  : {} ", walletUpdatePayload);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("msrkreddy111@gmail.com");
        simpleMailMessage.setSubject("Updated Wallet balance");
        simpleMailMessage.setText("Hi,Your Update balance is" + walletUpdatePayload.getBalance());
        simpleMailMessage.setCc("msrkreddy2001@gmail.com");
        simpleMailMessage.setTo(walletUpdatePayload.getUserEmail());
        javaMailSender.send(simpleMailMessage);
        MDC.clear();

    }









}
