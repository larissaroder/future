package br.com.db1.model;

public class Rate {
    private String name;
    private String code;
    private Double rate;

    protected Rate() {
    }

    public Rate(String name, String code, Double rate) {
        this.name = name;
        this.code = code;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "name='" + name +
                ", code='" + code +
                ", rate=" + rate +
                '}';
    }
}