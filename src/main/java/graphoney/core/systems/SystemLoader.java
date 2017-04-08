package graphoney.core.systems;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SystemLoader {

    private static final String DEFAULT_SYSTEM_CLASS_PATH = "target/classes/";

    private SystemClassLoader classLoader;

    public SystemLoader() {
        classLoader = new SystemClassLoader(DEFAULT_SYSTEM_CLASS_PATH, ClassLoader.getSystemClassLoader());
    }

    public Map<String, System> loadSystems() {
        Map<String, System> systems = new HashMap<>();

        File directory = new File(classLoader.getSystemClassPath());
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
                    e.printStackTrace();
                }

            }
        }

        return systems;
    }

    private boolean isSystem(Class<?> systemClass) {
        boolean implementsSystem = Arrays.stream(systemClass.getInterfaces()).anyMatch(x -> x.equals(System.class));
        boolean annotated = systemClass.isAnnotationPresent(RegisterSystem.class);
        return implementsSystem && annotated;
    }

    public void setSystemClassPath(String path) throws IOException {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new IOException("Incorrect directory path.");
        }
        classLoader.setSystemClassPath(path);
    }

}
