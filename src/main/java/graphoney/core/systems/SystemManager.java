package graphoney.core.systems;

import graphoney.utils.logging.Logger;

import java.util.*;

/**
 * Represents the system manager.
 * Launches and terminates all systems of engine.
 * It can also transfer data to systems.
 */
public class SystemManager {

    private static final SystemManager INSTANCE = new SystemManager();

    /**
     * Returns the single instance of the system manager.
     * @return the single instance of the system manager.
     */
    public static SystemManager getInstance() {
        return INSTANCE;
    }

    private SystemManager() {
    }

    private Map<String, System> activeSystems = new HashMap<>();
    private List<System> terminatingSystems = new LinkedList<>();

    /**
     * Runs systems passed as parameters.
     * @param systems systems that must be started.
     */
    public void runSystems(System... systems) {
        for (System system : systems) {
            this.activeSystems.put(system.getName(), system);
            system.start();
        }
    }

    /**
     * Terminates the specified system.
     * @param name name of the system that must be terminated.
     */
    public void terminateSystem(String name) {
        System system = activeSystems.get(name);
        system.terminate();
        terminatingSystems.add(system);
        activeSystems.remove(name);
    }

    /**
     * Terminates all running systems.
     */
    public void terminateAllSystems() {
        Logger.printInfo("Terminating all systems...");
        activeSystems.values().forEach(System::terminate);
        terminatingSystems.addAll(activeSystems.values());
        activeSystems.clear();
    }

    /**
     * Waits for shutdown of all systems that must be terminated.
     */
    public void waitForSystemsTermination() {
        while (!terminationFinished()) {
            terminatingSystems.get(0).waitForTermination();
        }
    }

    /**
     * Checks if termination of all terminated systems is finished.
     */
    private boolean terminationFinished() {
        terminatingSystems.removeIf(System::isTerminated);
        return terminatingSystems.isEmpty();
    }

    /**
     * Transfers necessary data to the specified system.
     * @param systemName name of the system.
     * @param data data to transfer.
     */
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
