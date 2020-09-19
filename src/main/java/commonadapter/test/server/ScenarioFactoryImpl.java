package commonadapter.test.server;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ServantLocator;


public class ScenarioFactoryImpl implements ScenarioFactory {

    @Override
    public void createScenario(String name, Current current) {
        current.adapter.add(new ScenarioImpl(), new Identity(name, "scenario"));
    }
}
