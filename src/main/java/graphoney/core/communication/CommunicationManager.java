package graphoney.core.communication;

import graphoney.core.systems.System;
import graphoney.core.systems.SystemManager;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingTarget;

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

    private final Map<System, List<System>> consumers = new HashMap<>();

    private void registerConsumers() {
        Map<String, System> systems = SystemManager.getInstance().getSystems();

        for (Map.Entry<String, System> entry : systems.entrySet()) {
            List<System> suppliers = new ArrayList<>();

            System consumer = entry.getValue();
            if (consumer.getSuppliersNames() == null) {
                continue;
            }

            for (String name : consumer.getSuppliersNames()) {
                if (name.equals(entry.getKey())) {
                    Logger.log(LoggingTarget.ERROR, "Failed to register system '" + name + "' as supplier for itself.");
                    continue;
                }

                System supplier = systems.get(name);
                if (supplier == null) {
                    Logger.log(LoggingTarget.ERROR, "Failed to register system '" + name + "' as supplier.");
                } else {
                    suppliers.add(supplier);
                    Logger.log(LoggingTarget.DEBUG, "System '" + name + "' registered as supplier for system '"
                            + entry.getKey() + "'.");
                }
            }

            consumers.put(entry.getValue(), suppliers);
        }
    }

}
