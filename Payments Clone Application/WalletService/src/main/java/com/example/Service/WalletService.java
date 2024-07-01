package com.example.Service;

import com.example.Entity.Wallet;
import com.example.Repo.WalletRepo;
import com.example.TxnCompletedPayload;
import com.example.TxnInitPayload;
import com.example.WalletUpdatePayload;
import jakarta.transaction.Transactional;
import org.springframework.kafka.support.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class WalletService {
    private static Logger LOGGER = LoggerFactory.getLogger(WalletService.class);
    @Autowired
    private WalletRepo walletRepo;

    @Value("${txn.completed.topic}")
    private String txnCompletedTopic;

    @Value("${wallet.updated.topic}")
    private String walletUpdatedTopic;

    @Autowired
    KafkaTemplate<String,Object>kafkaTemplate;

    @Transactional
    public void walletTransaction(TxnInitPayload txnInitPayload)throws ExecutionException,InterruptedException{
        LOGGER.info("Process Wallet Transaction");
        TxnCompletedPayload txnCompletedPayload = TxnCompletedPayload.builder()
                .id(txnInitPayload.getId())
                .requestId(txnInitPayload.getRequestId())
                .build();
        Wallet fromWallet = walletRepo.findByUserId(txnInitPayload.getFromUserId());
        if(fromWallet.getBalance()<txnInitPayload.getAmount()){
            txnCompletedPayload.setSuccess(false);
            txnCompletedPayload.setReason("Insufficient Funds");

        }

        else{
            Wallet toWallet = walletRepo.findByUserId(txnInitPayload.getToUserId());
            toWallet.setBalance(txnInitPayload.getAmount()+toWallet.getBalance());
            fromWallet.setBalance(fromWallet.getBalance()-txnInitPayload.getAmount());
            txnCompletedPayload.setSuccess(true);

            WalletUpdatePayload walletUpdatePayload1 = WalletUpdatePayload.builder()
                    .balance(fromWallet.getBalance())
                    .userEmail(fromWallet.getUserEmail())
                    .requestId(txnCompletedPayload.getRequestId())
                    .build();

            WalletUpdatePayload walletUpdatePayload2 = WalletUpdatePayload.builder()
                    .balance(toWallet.getBalance())
                    .userEmail(toWallet.getUserEmail())
                    .requestId(txnCompletedPayload.getRequestId())
                    .build();

            Future<SendResult<String,Object>> future1  = kafkaTemplate.send(walletUpdatedTopic,txnInitPayload.getFromUserId().toString(),walletUpdatePayload1);
            LOGGER.info("Pushed WalletUpdated topic to kafka: {}",future1.get());
            Future<SendResult<String,Object>> future2  = kafkaTemplate.send(walletUpdatedTopic,txnInitPayload.getToUserId().toString(),walletUpdatePayload2);
            LOGGER.info("Pushed WalletUpdated topic to kafka: {}",future2.get());
        }

        Future<SendResult<String,Object>> future  = kafkaTemplate.send(txnCompletedTopic,txnInitPayload.getFromUserId().toString(),txnCompletedPayload);
        LOGGER.info("Pushed TxnCompleted to kafka: {}",future.get());



        }


    }



