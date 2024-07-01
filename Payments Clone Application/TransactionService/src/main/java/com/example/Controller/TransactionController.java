package com.example.Controller;

import com.example.Dto.TxnRequestDto;
import com.example.Dto.TxnStatusDto;
import com.example.Service.TransactionService;
import com.example.TxnStatus;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/txn")
public class TransactionController {
    private static Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String>doTransaction(@RequestBody @Valid TxnRequestDto txnRequestDto) throws ExecutionException,InterruptedException {
        LOGGER.info("Starting transaction :{} ",txnRequestDto);
       String txnId =  transactionService.doTransaction(txnRequestDto);
        return ResponseEntity.accepted().body(txnId);
    }

    @GetMapping("/status/{txnId}")
    public ResponseEntity<TxnStatusDto>getTxnStatus(@PathVariable String txnId){
       TxnStatusDto txnStatusDto = transactionService.getStatus(txnId);
        return ResponseEntity.ok(txnStatusDto);


    }




}
