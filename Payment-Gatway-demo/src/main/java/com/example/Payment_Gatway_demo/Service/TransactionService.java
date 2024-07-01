package com.example.Payment_Gatway_demo.Service;

import com.example.Payment_Gatway_demo.Repo.MerchantRepo;
import com.example.Payment_Gatway_demo.Repo.TransactionRepo;
import com.example.Payment_Gatway_demo.dto.PaymentInitResponse;
import com.example.Payment_Gatway_demo.dto.PaymentPageRequest;
import com.example.Payment_Gatway_demo.dto.TransactionDetailDto;
import com.example.Payment_Gatway_demo.entity.Merchant;
import com.example.Payment_Gatway_demo.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private MerchantRepo merchantRepo;

    public TransactionDetailDto getStatus(String txnId){
        Transaction transaction = transactionRepo.findByTxnId(txnId);
        TransactionDetailDto transactionDetailDto = TransactionDetailDto.builder()
                .userId(transaction.getUserId())
                .status(transaction.getStatus())
                .amount(transaction.getAmount())
                .build();
        return transactionDetailDto;

    }

    public Transaction getTransaction(String txnId){
        Transaction transaction = transactionRepo.findByTxnId(txnId);
        return transaction;
    }

    public String doPaymentAndRedirect(String txnId){
        Transaction transaction = transactionRepo.findByTxnId(txnId);
        transaction.setStatus("SUCCESS");
        transactionRepo.save(transaction);
        Merchant merchant = merchantRepo.findById(transaction.getMerchantId()).get();
        // CAll API of WebHook to update status in merchant system
        String url = merchant.getRedirectionUrl()+txnId;
        return url;
    }

    public PaymentInitResponse generatePaymentPage(PaymentPageRequest request){
        String txnId = UUID.randomUUID().toString();
        Transaction transaction = Transaction.builder()
                .merchantId(request.getMerchantId())
                .amount(request.getAmount())
                .status("PENDING")
                .txnId(txnId)
                .userId(request.getUserId())
                .build();
        transactionRepo.save(transaction);
        String url = "http://localhost:9090/payment-page/"+txnId;
        PaymentInitResponse paymentInitResponse = PaymentInitResponse.builder()
                .txnId(txnId)
                .url(url)
                .build();
        return paymentInitResponse;
    }

}


