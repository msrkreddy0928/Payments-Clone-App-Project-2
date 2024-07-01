package com.example.Config;

import com.example.Entity.Transaction;
import com.example.Repo.TransactionRepo;
import com.example.TxnCompletedPayload;
import com.example.TxnInitPayload;
import com.example.TxnStatus;
import com.example.UserCreatedPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.ExecutionException;


@Configuration
public class KafkaConsumerConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    private static ObjectMapper OBJECT_MAPPPER = new ObjectMapper();

    @Autowired
    private TransactionRepo transactionRepo;

    @KafkaListener(topics = "${txn.completed.topic}",groupId = "TransactionApp")
    public void consumeUserCreatedTopic(ConsumerRecord payload) throws JsonProcessingException{
        TxnCompletedPayload txnCompletedPayload = OBJECT_MAPPPER.readValue(payload.value().toString(), TxnCompletedPayload.class);
        MDC.put("requestId",txnCompletedPayload.getRequestId());
        LOGGER.info("Read from kafka : {} ",txnCompletedPayload);
        Transaction transaction = transactionRepo.findById(txnCompletedPayload.getId()).get();
        if(txnCompletedPayload.getSuccess()){
            transaction.setStatus(TxnStatus.SUCCESS);
        }
        else{
            transaction.setStatus(TxnStatus.FAILED);
            transaction.setReason(txnCompletedPayload.getReason());
        }
           transactionRepo.save(transaction);


    }


    }









