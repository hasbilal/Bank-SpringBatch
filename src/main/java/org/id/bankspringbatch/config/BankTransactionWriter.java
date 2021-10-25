package org.id.bankspringbatch.config;

import org.id.bankspringbatch.entities.BankTransaction;
import org.id.bankspringbatch.repositories.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankTransactionWriter implements ItemWriter<BankTransaction> {

    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    public BankTransactionWriter(BankTransactionRepository bankTransactionRepository) {
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
        bankTransactionRepository.saveAll(list);


    }
}
