package commonadapter.test.server;

import adapter.Scenario;
import com.zeroc.Ice.Current;

public class ScenarioImpl implements Scenario {

    @Override
    public String getName(Current current) {


        return "witam 0_0";
    }
}
