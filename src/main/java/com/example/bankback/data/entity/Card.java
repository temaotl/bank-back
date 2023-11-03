package com.example.bankback.data.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean blocked;

    @Column
    private LocalDate dateLocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public LocalDate getDateLocked() {
        return dateLocked;
    }

    public void setDateLocked(LocalDate dateLocked) {
        this.dateLocked = dateLocked;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (blocked != card.blocked) return false;
        if (!Objects.equals(id, card.id)) return false;
        if (!Objects.equals(name, card.name)) return false;
        if (!Objects.equals(dateLocked, card.dateLocked)) return false;
        return Objects.equals(account, card.account);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (dateLocked != null ? dateLocked.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blocked=" + blocked +
                ", dateLocked=" + dateLocked +
                ", account=" + account +
                '}';
    }
}