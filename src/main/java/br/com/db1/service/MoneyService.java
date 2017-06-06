package br.com.db1.service;

import br.com.db1.infrastructure.TaskExecutorPool;
import br.com.db1.model.Money;
import br.com.db1.model.Rate;
import br.com.db1.task.TaskMoneyCallable;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class MoneyService {

    private static Logger LOGGER = Logger.getLogger(MoneyService.class);

    public List<Money> getListOfMoney(List<Rate> rates) {
        CompletionService executor = TaskExecutorPool.createCompletionService();
        List<Future<Money>> result = Lists.newLinkedList();

        rates.forEach(rate -> executeFuture(executor, result, rate));

        List<Money> moneyList = new ArrayList<>();
        result.forEach(r -> mountListOfMoney(r, moneyList));
        return moneyList;

    }

    private void executeFuture(CompletionService executor, List<Future<Money>> result, Rate rate) {
        Future thread = executor.submit(new TaskMoneyCallable(rate));
        result.add(thread);
    }

    private void mountListOfMoney(Future<Money> r, List<Money> moneyList) {
        try {
            moneyList.add(r.get());
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info(e.getMessage(), e);
        }
    }
}
