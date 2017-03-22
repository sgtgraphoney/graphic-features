package graphoney.core.systems;

import graphoney.utils.logging.Logger;

import java.util.*;

public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private SystemManager() {
    }

    private Map<String, System> activeSystems = new HashMap<>();
    private List<System> terminatingSystems = new LinkedList<>();

    public void runSystems(System... systems) {
        for (System system : systems) {
            this.activeSystems.put(system.getName(), system);
            system.start();
        }
    }

    public void terminateSystem(String name) {
        System system = activeSystems.get(name);
        system.terminate();
        terminatingSystems.add(system);
        activeSystems.remove(name);
    }

    public void terminateAllSystems() {
        Logger.printInfo("Terminating all systems...");
        activeSystems.values().forEach(System::terminate);
        terminatingSystems.addAll(activeSystems.values());
        activeSystems.clear();
    }

    public boolean terminationFinished() {
        terminatingSystems.removeIf(System::isTerminated);
        return terminatingSystems.isEmpty();
    }

    public void transferDataToSystem(String systemName, Object data) {
        System system = activeSystems.get(systemName);

        if (system == null) {
            Logger.printError("Could not transfer data to system '" + systemName + "': no such active system.");
            return;
        }

        try {
            system.transferData(data);
        } catch (IllegalArgumentException e) {
            Logger.printError("Could not transfer data to system '" + systemName + "': illegal data type.");
        }
    }

}
