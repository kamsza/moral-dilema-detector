package generator;

import project.MyFactory;
import project.Pedestrian;
import project.Street_crossing;

import java.lang.reflect.InvocationTargetException;

public class PedestrianOnCrosswalkSG extends BaseScenarioGenerator {

    public PedestrianOnCrosswalkSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Model model = super.generate();

        Pedestrian pedestrian = factory.createPedestrian(ObjectNamer.getName("pedestrian"));

        Street_crossing pedestrianLocation = factory.createStreet_crossing(ObjectNamer.getName("street_crossing"));

        model.getScenario().addHas_pedestrian(pedestrian);

        pedestrian.addPedestrian_has_location(pedestrianLocation);

        return model;
    }
}
