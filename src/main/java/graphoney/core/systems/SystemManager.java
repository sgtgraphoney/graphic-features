package graphoney.core.systems;

import java.util.HashMap;
import java.util.Map;

public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, System> systems = new HashMap<>();

    private SystemManager() {
        SystemLoader loader = new SystemLoader();
        systems.putAll(loader.loadSystems());
    }



}
