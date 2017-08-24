package br.com.db1.task;

import br.com.db1.model.Money;
import br.com.db1.model.Rate;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public class TaskMoneyCallable implements Callable<AtomicReference<Money>> {

    private Rate rate;

    private AtomicReference<Money> money = new AtomicReference<>();

    public TaskMoneyCallable(Rate rate) {
        this.rate = rate;
    }

    @Override
    public AtomicReference<Money> call() throws Exception {
        money.set(new Money(generateRandomBigDecimalFromRange(), rate));
        return money;
    }

    public static BigDecimal generateRandomBigDecimalFromRange() {
        BigDecimal min = new BigDecimal(100.0);
        BigDecimal max = new BigDecimal(2000.0);
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP);
    }
}
