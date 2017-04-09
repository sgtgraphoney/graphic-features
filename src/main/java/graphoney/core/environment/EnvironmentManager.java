package graphoney.core.environment;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentManager {

    private static final EnvironmentManager INSTANCE = new EnvironmentManager();

    public static EnvironmentManager getInstance() {
        return INSTANCE;
    }

    private final Map<String, Object> variables = new HashMap<>();

    private EnvironmentManager() {
        setVariable("SYSTEM_CLASSPATH", "target/classes/");
        setVariable("THREADS_NUMBER", 4);
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

    public String listVariables() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            stringBuilder.append(entry.getKey());
            stringBuilder.append("='");
            stringBuilder.append(entry.getValue());
            stringBuilder.append("'\n");
        }
        return stringBuilder.toString();
    }

}
