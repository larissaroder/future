package br.com.db1.model;

import java.math.BigDecimal;

public class Money {

    private BigDecimal value;
    private Rate rate;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", rate=" + rate +
                '}';
    }
}
