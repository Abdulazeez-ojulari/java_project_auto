package org.afripay.afripay.transactions.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.afripay.afripay.transactions.dto.TransactionDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
public class Transactions {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    
    @Column(name = "rrn")
    private String rrn;
    
    @Column(name = "pan")
    private String pan;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "status")
    private String statu
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date createdDate;
    
    public static TransactionDTO getTransactionDTO(Transactions transactions) {
        TransactionDTO transactionDTO = new TransactionDTO();
        BeanUtils.copyProperties(transactions, transactionDTO);
        return transactionDTO;
    }
}