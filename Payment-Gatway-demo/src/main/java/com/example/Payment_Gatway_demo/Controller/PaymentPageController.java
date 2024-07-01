package com.example.Payment_Gatway_demo.Controller;

import com.example.Payment_Gatway_demo.Repo.MerchantRepo;
import com.example.Payment_Gatway_demo.Service.TransactionService;
import com.example.Payment_Gatway_demo.entity.Merchant;
import com.example.Payment_Gatway_demo.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/payment-page")
public class PaymentPageController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private MerchantRepo merchantRepo;

    @GetMapping("/{txnId}")
    public ModelAndView getMenu(@PathVariable String txnId) {
        ModelAndView modelAndView = new ModelAndView("PaymentsPage.html");

        Transaction transaction = transactionService.getTransaction(txnId);
        Merchant merchant = merchantRepo.findById(transaction.getMerchantId()).get();
        modelAndView.getModelMap().put("merchantName", merchant.getName());
        modelAndView.getModelMap().put("amount", transaction.getAmount());
        modelAndView.getModelMap().put("txnId", txnId);
        modelAndView.getModelMap().put("actionUrl", "/pg-service/doPayment/" + txnId);

        return modelAndView;
    }
}