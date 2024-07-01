package com.example;

import com.example.Entity.Wallet;
import com.example.Repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/wallet-service")
public class WalletController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WalletRepo walletRepo;

    @GetMapping("/add-money-status/{pgTxnId}")
    public ResponseEntity<String>addMoneyStatus(@PathVariable String pgTxnId){
        PGPaymentStatusDto pgPaymentStatusDto = restTemplate.getForObject("http://localhost:9090/pg-service/payment-status/"+pgTxnId,PGPaymentStatusDto.class);
        if(pgPaymentStatusDto.getStatus().equalsIgnoreCase("SUCCESS")){
            Wallet wallet = walletRepo.findByUserId(pgPaymentStatusDto.getUserId());
            wallet.setBalance(wallet.getBalance() + pgPaymentStatusDto.getAmount());
            walletRepo.save(wallet);
            return ResponseEntity.ok("Wallet Updated");

        }
        return ResponseEntity.ok("PG Txn Failed");
    }
    @PostMapping("/add-money")
    public ResponseEntity<AddMoneyResponse> addMoney(@RequestBody AddMoneyRequest addMoneyRequest){
        addMoneyRequest.setMerchantId(1l);
        AddMoneyResponse addMoneyResponse = restTemplate.postForObject("http://localhost:9090/pg-service/init-payment", addMoneyRequest, AddMoneyResponse.class);
        return ResponseEntity.ok(addMoneyResponse);
    }


}
