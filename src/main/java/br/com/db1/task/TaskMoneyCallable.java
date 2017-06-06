package br.com.db1.task;

import br.com.db1.model.Money;
import br.com.db1.model.Rate;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

public class TaskMoneyCallable implements Callable<Money> {

    private Rate rate;

    public TaskMoneyCallable(Rate rate) {
        this.rate = rate;
    }

    @Override
    public Money call() throws Exception {
        return new Money(generateRandomBigDecimalFromRange(), rate);
    }

    public static BigDecimal generateRandomBigDecimalFromRange() {
        BigDecimal min = new BigDecimal(100.0);
        BigDecimal max = new BigDecimal(2000.0);
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
