package generator;

import project.Driver;
import project.Lane;
import project.MyFactory;
import project.Vehicle;

import java.lang.reflect.InvocationTargetException;

public class CarOvertakingSG extends BaseScenarioGenerator {

    public CarOvertakingSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    //TODO
    /*
    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // model
        Model model = super.generate();

        // create objects
        Vehicle vehicle = factory.createVehicle(ObjectNamer.getName("vehicle"));
        Driver driver = factory.createDriver(ObjectNamer.getName("driver"));

        // add to ontology
        model.getVehicle().addHas_on_the_left(vehicle);

        vehicle.addLength(500F);
        vehicle.addDistance(new RandomDistanceGenerator().getRandomDistance());
        vehicle.addVehicle_has_driver(driver);
        vehicle.addVehicle_has_location(model.getRoadType());
        vehicle.addVehicle_has_speed_kmph(80);
        vehicle.addHas_on_the_right(model.getVehicle());
//        if(model.getLanes().get(Model.Side.LEFT).size() == 2)
//            vehicle.addHas_on_the_left(model.getLanes().get(Model.Side.LEFT).get(2));
//        else
//            vehicle.addHas_on_the_left(model.getSurrounding().get(Model.Side.LEFT));

        // add to model
        Lane lane = model.getLanes().get(Model.Side.LEFT).get(1);
        model.getVehicles().get(lane).add(vehicle);

        return model;
    }
     */
}