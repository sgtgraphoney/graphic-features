package graphoney.core.systems;

import graphoney.core.communication.CommunicationManager;

import java.util.List;
import java.util.concurrent.Callable;

public interface System {

    void initialize();

    void terminate();

    List<Callable<Boolean>> getTasks();

    List<String> getSuppliersNames();
    
}
