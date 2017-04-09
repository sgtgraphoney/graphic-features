package graphoney.core;

import graphoney.core.tasks.TaskManager;

import java.util.concurrent.ExecutionException;

public class Init {

    public static void main(String[] args) {

        try {
            TaskManager.getInstance().executeTasks();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
