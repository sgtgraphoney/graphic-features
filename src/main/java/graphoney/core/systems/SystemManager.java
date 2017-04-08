package graphoney.core.systems;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private final SystemLoader loader = new SystemLoader();
    private final Map<String, System> systems = new HashMap<>();

    private SystemManager() {
        systems.putAll(loader.loadSystems());
        for (String s : systems.keySet()) {
            java.lang.System.out.println(s);
        }
    }



}
