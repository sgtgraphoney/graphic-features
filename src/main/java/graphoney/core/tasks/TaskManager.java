package graphoney.core.tasks;

import graphoney.core.environment.EnvironmentManager;
import graphoney.core.environment.EnvironmentVariableException;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingTarget;

import java.util.concurrent.*;

public class TaskManager {

    private static final TaskManager INSTANCE = new TaskManager();

    public static TaskManager getInstance() {
        return INSTANCE;
    }

    private static final int DEFAULT_THREADS_NUMBER = 4;

    private final TaskScheduler taskScheduler = new TaskScheduler();
    private final ThreadPoolExecutor threadPool;

    private TaskManager() {
        int threadsCount = loadThreadsCount();
        threadPool = new ThreadPoolExecutor(threadsCount, threadsCount, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
    }

    public void execute() throws InterruptedException, ExecutionException {
        threadPool.invokeAll(taskScheduler.getPreparedTasks());

        while (threadPool.getActiveCount() > 0) {
            Future<Boolean> finished = threadPool.submit(() -> true);
            finished.get();
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    private int loadThreadsCount() {
        try {
            return (int) EnvironmentManager.getInstance().getVariable("THREADS_NUMBER");
        } catch (EnvironmentVariableException e) {
            Logger.log(LoggingTarget.ERROR, e.getMessage() + " Creating " + DEFAULT_THREADS_NUMBER
                    + " threads in pool.");
            return DEFAULT_THREADS_NUMBER;
        }
    }

}
