package br.com.db1.infrastructure;

import java.util.concurrent.*;

public final class TaskExecutorPool {

    private static final Integer FIX_THREADS = Runtime.getRuntime().availableProcessors() * 2;

    private TaskExecutorPool() {
    }

    private static ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(FIX_THREADS);
    }

    public static CompletionService createCompletionService() {
        return new ExecutorCompletionService(createExecutorService());
    }

    public static Executor createExecutor() {
        return createExecutorService();
    }

}