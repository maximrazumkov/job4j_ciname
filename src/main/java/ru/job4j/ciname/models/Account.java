package ru.job4j.ciname.models;

import java.util.Objects;

public class Account {
    private Integer id;
    private Integer hallId;
    private Double amount;
    private String phone;
    private String fullname;

    public Account(Integer id, Integer hallId, Double amount, String phone, String fullname) {
        this.id = id;
        this.hallId = hallId;
        this.amount = amount;
        this.phone = phone;
        this.fullname = fullname;
    }

    public Account(Integer hallId, Double amount, String phone, String fullname) {
        this.hallId = hallId;
        this.amount = amount;
        this.phone = phone;
        this.fullname = fullname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", hallId=" + hallId +
                ", amount=" + amount +
                ", phone='" + phone + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
