package commonadapter.interfaces;

import project.Scenario;

public interface ScenarioBuilder {

    Scenario build();

    ScenarioBuilder addPedestrian(ScenarioPedestrian scenarioPedestrian);

    ScenarioBuilder addCyclist(ScenarioCyclist scenarioCyclist);

    ScenarioBuilder addVehicle(ScenarioVehicle scenarioVehicle);

    // possibly more addSomething-methods in the future

}
