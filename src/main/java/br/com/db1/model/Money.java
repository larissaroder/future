package br.com.db1.model;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class Money {

    private BigDecimal value;
    private Rate rate;

    protected Money() {
    }
    public Money(BigDecimal value, Rate rate) {
        this.value = value;
        this.rate = rate;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Rate getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", rate=" + rate +
                '}';
    }
}
