//package commonadapter.test.server;
//
//import adapter.Scenario;
//import com.zeroc.Ice.Current;
//import com.zeroc.Ice.Object;
//import com.zeroc.Ice.ServantLocator;
//import com.zeroc.Ice.UserException;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ScenarioLocator implements ServantLocator {
//
//    private Map<String, Scenario> scenarios;
//
//    public ScenarioLocator() {
//        scenarios = new HashMap<>();
//        scenarios.put("s1", new ScenarioImpl());
//    }
//
//    @Override
//    public LocateResult locate(Current current) throws UserException {
//
//        String name = current.id.name;
//        String category = current.id.category;
//
//        return new ServantLocator.LocateResult(scenarios.get(name), null);
//    }
//
//    @Override
//    public void finished(Current current, Object object, java.lang.Object o) throws UserException {
//
//    }
//
//    @Override
//    public void deactivate(String s) {
//
//    }
//
//
//}
