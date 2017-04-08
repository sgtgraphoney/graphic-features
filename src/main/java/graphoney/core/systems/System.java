package graphoney.core.systems;

import java.util.List;
import java.util.concurrent.Callable;

public interface System {

    void initialize();

    void terminate();

    boolean isTaskSplitSupported();

    List<Callable<Boolean>> getSplittedTask(int subtaskCount);

    Callable<Boolean> getFullTask();

    boolean isReady();

}
