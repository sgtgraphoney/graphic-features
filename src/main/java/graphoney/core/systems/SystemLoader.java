package graphoney.core.systems;

import graphoney.core.environment.EnvironmentManager;
import graphoney.core.environment.EnvironmentVariableException;
import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingLevel;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SystemLoader {

    private SystemClassLoader classLoader;

    public SystemLoader() throws InstantiationException {
        try {
            String classpath = (String) EnvironmentManager.getInstance().getVariable("SYSTEM_CLASSPATH");
            classLoader = new SystemClassLoader(classpath, ClassLoader.getSystemClassLoader());
        } catch (EnvironmentVariableException e) {
            throw new InstantiationException();
        }
    }

    public Map<String, System> loadSystems() {
        Logger.log(LoggingLevel.INFO, "Loading systems...");

        Map<String, System> systems = new HashMap<>();

        File directory = new File(classLoader.getSystemClasspath());
        String[] files = directory.list();

        if (files == null) {
            return systems;
        }

        for (String file : files) {
            String pattern = ".*\\.class$";

            if (file.matches(pattern)) {
                String classname = file.split(".class")[0];

                try {

                    Class<?> systemClass = classLoader.loadClass(classname);
                    if (isSystem(systemClass)) {
                        RegisterSystem annotation = systemClass.getDeclaredAnnotation(RegisterSystem.class);
                        System system = (System) systemClass.newInstance();
                        systems.put(annotation.name(), system);
                    }

                } catch (ReflectiveOperationException e) {
                    Logger.log(LoggingLevel.ERROR, "Could not instantiate system class " + classname + ".");
                }

            }
        }

        Logger.log(LoggingLevel.INFO, "All systems loaded.");
        return systems;
    }

    private boolean isSystem(Class<?> systemClass) {
        boolean implementsSystem = Arrays.stream(systemClass.getInterfaces()).anyMatch(x -> x.equals(System.class));
        boolean annotated = systemClass.isAnnotationPresent(RegisterSystem.class);
        return implementsSystem && annotated;
    }

}
