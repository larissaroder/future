package br.com.db1.task;

import br.com.db1.model.Money;
import br.com.db1.model.Rate;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Classe para o controle da concorrência
 */
public class TaskMoneyCallable implements Callable<Money> {

    private Money money;

    private AtomicReference<Money> moneyAtomic;

    private Rate rate;

    public TaskMoneyCallable(Money money, Rate rate, AtomicReference<Money> moneyAtomic) {
        this.money = money;
        this.rate = rate;
        this.moneyAtomic = moneyAtomic;
    }

    @Override
    public Money call() throws Exception {
        return this.moneyAtomic.accumulateAndGet(new Money(money.getValue(), rate), (money1, money2) ->
                new Money(money1.getValue().add(money2.getValue()), rate));
    }
}
