package br.com.db1.service;

import br.com.db1.infrastructure.TaskExecutorPool;
import br.com.db1.model.Money;
import br.com.db1.model.Rate;
import br.com.db1.task.TaskMoneyCallable;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MoneyService {

    @Autowired
    private RateService rateService;

    private static Logger LOGGER = Logger.getLogger(MoneyService.class);

    private AtomicReference<Money> moneyAtomic = new AtomicReference<>();

    private List<Future<Money>> moeyList = Collections.synchronizedList(Lists.newLinkedList());

    /**
     * obtem uma lista de dinheiro com valores aleatórios e vai somando os valores entre eles sincronizados
     * endpoint que cria uma lista de dinheiro com moedas próprias
     * o valor do dinheiro
     * @return
     */
    public List<Money> useAtomicReference() {
        CompletionService executor = TaskExecutorPool.createCompletionService();

        Rate rate = new Rate("AtomicTest", "AT", 0.0);
        List<Money> listOfMoney = getListOfMoney();


        moneyAtomic.set(new Money(new BigDecimal(0.0), rate));
        listOfMoney.forEach(money -> executeFuture(executor, money, rate, moneyAtomic));

        List<Money> moneyList = new ArrayList<>();
        moeyList.forEach(r -> mountListOfMoney(r, moneyList));
        return moneyList;

    }

    /**
     * endpoint para ser usado no exercício de map reduce
     * @return
     */
    public List<Money> getListOfMoney() {
        List<Money> moneyList = Lists.newLinkedList();
        rateService.getRates().forEach(rate -> {
            BigDecimal min = new BigDecimal(100.0);
            BigDecimal max = new BigDecimal(2000.0);
            BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
            moneyList.add(new Money(randomBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP), rate));
        });
        return moneyList;
    }

    private void executeFuture(CompletionService executor, Money money, Rate rate, AtomicReference<Money> moneyAtomic) {
        Future thread = executor.submit(new TaskMoneyCallable(money, rate, moneyAtomic));
        moeyList.add(thread);
    }

    private synchronized void mountListOfMoney(Future<Money> r, List<Money> moneyList) {
        try {
            synchronized (moneyList) {
                moneyList.add(r.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }
}
