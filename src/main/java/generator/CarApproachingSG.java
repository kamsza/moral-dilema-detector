package generator;

import project.Animal;
import project.Driver;
import project.Lane;
import project.MyFactory;
import project.Vehicle;

import java.lang.reflect.InvocationTargetException;

public class CarApproachingSG extends BaseScenarioGenerator {

    public CarApproachingSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // model
        Model model = super.generate();

        // create objects
        Vehicle vehicle = factory.createVehicle(ObjectNamer.getName("vehicle"));
        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        // add to ontology
        model.getScenario().addHas_vehicle(vehicle);
        model.getVehicle().addHas_at_the_back(vehicle);

        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());
        vehicle.addVehicle_has_speed_kmph(80);
//        if(model.getLanes().get(Model.Side.RIGHT).isEmpty())
//            vehicle.addHas_on_the_right(model.getSurrounding().get(Model.Side.RIGHT));
//        else
//            vehicle.addHas_on_the_right(model.getLanes().get(Model.Side.RIGHT).get(1));
//        if(model.getLanes().get(Model.Side.LEFT).isEmpty())
//            vehicle.addHas_on_the_right(model.getSurrounding().get(Model.Side.LEFT));
//        else
//            vehicle.addHas_on_the_right(model.getLanes().get(Model.Side.LEFT).get(1));
        vehicle.addHas_in_the_front(vehicle);

        // add to model
        Lane lane  = model.getLanes().get(Model.Side.CENTER).get(0);
        model.getVehicles().get(lane).add(vehicle);

        return model;
    }
}