package graphoney.core.systems;

import graphoney.core.environment.EnvironmentVariableException;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingTarget;

import java.util.HashMap;
import java.util.Map;

public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, System> systems = new HashMap<>();

    private SystemManager() {
        loadSystems();
    }

    private void loadSystems() {
        SystemLoader loader = new SystemLoader();
        try {
            systems.putAll(loader.loadSystems());
        } catch (EnvironmentVariableException e){
            Logger.log(LoggingTarget.ERROR, "Failed to load systems. " + e.getMessage());
        }
    }

    public Map<String, System> getSystems() {
        return systems;
    }



}
