package commonadapter.test.server;

import com.zeroc.Ice.Current;
import commonadapter.communication.generated.adapter.ScenarioBuilder;

public class ScenarioBuilderImpl implements ScenarioBuilder {
    @Override
    public int tmp(int a, Current current) {
        return 2 * a;
    }
}
