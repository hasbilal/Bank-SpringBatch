package org.id.bankspringbatch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class BankTransaction {

    @Id
    private Long id;
    private Long accountId;
    private Date transactionDate;
    //Attribute non persistent
    @Transient
    private String strTransactionDate;
    private String transactionType;
    private BigDecimal amount;


}
