package com.example.bankback.data.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "creditor")
    private String creditor;

    @Column(name = "debtor")
    private String debtor;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = true)
    private LocalDateTime dateExecuted;

    @Column(nullable = false, length = 3)
    private String currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCreditor() {
        return creditor;
    }

    public void setCreditor(String creditor) {
        this.creditor = creditor;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateExecuted() {
        return dateExecuted;
    }

    public void setDateExecuted(LocalDateTime dateExecuted) {
        this.dateExecuted = dateExecuted;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(amount, that.amount)) return false;
        if (!Objects.equals(creditor, that.creditor)) return false;
        if (!Objects.equals(debtor, that.debtor)) return false;
        if (!Objects.equals(dateCreated, that.dateCreated)) return false;
        if (!Objects.equals(dateExecuted, that.dateExecuted)) return false;
        return Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (creditor != null ? creditor.hashCode() : 0);
        result = 31 * result + (debtor != null ? debtor.hashCode() : 0);
        result = 31 * result + (dateCreated != null ? dateCreated.hashCode() : 0);
        result = 31 * result + (dateExecuted != null ? dateExecuted.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", creditor='" + creditor + '\'' +
                ", debtor='" + debtor + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateExecuted=" + dateExecuted +
                ", currency='" + currency + '\'' +
                '}';
    }
}

