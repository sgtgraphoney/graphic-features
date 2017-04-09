package graphoney.core.communication;

import graphoney.core.systems.System;
import graphoney.core.systems.SystemManager;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicationManager {

    private static final CommunicationManager INSTANCE = new CommunicationManager();

    public static CommunicationManager getInstance() {
        return INSTANCE;
    }

    private CommunicationManager() {
        registerConsumers();
    }

    private final Map<System, List<System>> watchers = new HashMap<>();

    private void registerConsumers() {
        Map<String, System> systems = SystemManager.getInstance().getSystems();

        for (System consumer : systems.values()) {
            List<System> suppliers = new ArrayList<>();

            for (String name : consumer.getSuppliersNames()) {
                System supplier = systems.get(name);
                if (supplier == null) {
                    Logger.log(LoggingLevel.ERROR, "Failed to register system " + name + " as supplier.");
                } else {
                    suppliers.add(supplier);
                }
            }

            watchers.put(consumer, suppliers);
        }
    }

}
