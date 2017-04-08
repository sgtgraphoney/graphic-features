package graphoney.core.environment;

import graphoney.utils.logging.Logger;
import graphoney.utils.logging.LoggingLevel;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentManager {

    private static final EnvironmentManager INSTANCE = new EnvironmentManager();

    public static EnvironmentManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, Object> variables = new HashMap<>();

    private EnvironmentManager() {
        setVariable("SYSTEM_CLASSPATH", "taarget/classes/");
        setVariable("ABRAKADABRA", "LOL");
    }

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object getVariable(String name) throws EnvironmentVariableException {
        Object var = variables.get(name);
        if (var == null) {
            throw new EnvironmentVariableException("There is no variable with name '" + name + "'.");
        }
        return var;
    }

}
