package com.example.Service;

import com.example.Dto.TxnRequestDto;
import com.example.Dto.TxnStatusDto;
import com.example.Entity.Transaction;
import com.example.Repo.TransactionRepo;
import com.example.TxnInitPayload;
import com.example.TxnStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
@Service
public class TransactionService {

    private static Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepo transactionRepo;

    @Value("${txn.init.topic}")
    private String txnInitTopic;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public String doTransaction(TxnRequestDto txnRequestDto) throws ExecutionException, InterruptedException {
        Transaction transaction = new Transaction();
        transaction.setFromUserId(txnRequestDto.getFromUserId());
        transaction.setToUserId(txnRequestDto.getToUserId());
        transaction.setAmount(txnRequestDto.getAmount());
        transaction.setReason(txnRequestDto.getComment());
        transaction.setTxnId(UUID.randomUUID().toString());
        transaction.setStatus(TxnStatus.PENDING);
        transaction = transactionRepo.save(transaction);
        TxnInitPayload txnInitPayload = new TxnInitPayload();
        txnInitPayload.setId(transaction.getId());
        txnInitPayload.setFromUserId(transaction.getFromUserId());
        txnInitPayload.setToUserId(transaction.getToUserId());
        txnInitPayload.setAmount(transaction.getAmount());
        txnInitPayload.setRequestId(MDC.get("requestId"));
        Future<SendResult<String,Object>> future  = kafkaTemplate.send(txnInitTopic,transaction.getFromUserId().toString(),txnInitPayload);
        LOGGER.info("Pushed txnint payload to kafka: {}", future.get());
        return transaction.getTxnId();
    }

    public TxnStatusDto getStatus(String txnId) {
        Transaction transaction = transactionRepo.findByTxnId(txnId);
        TxnStatusDto txnStatusDto = new TxnStatusDto();
        if (transaction != null) {
            txnStatusDto.setStatus(transaction.getStatus().toString());
            txnStatusDto.setReason(transaction.getReason());
        }
        return txnStatusDto;

    }

}








