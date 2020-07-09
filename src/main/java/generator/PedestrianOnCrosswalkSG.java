package generator;

import project.Lane;
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
        // model
        Model model = super.generate();

        // create objects
        Pedestrian pedestrian = factory.createPedestrian(ObjectNamer.getName("pedestrian"));
        Street_crossing pedestrianLocation = factory.createStreet_crossing(ObjectNamer.getName("street_crossing"));

        // add to ontology
        pedestrian.addPedestrian_has_location(pedestrianLocation);
        model.getScenario().addHas_pedestrian(pedestrian);

        // add to model
        Lane lane  = model.getLanes().get(Model.Side.CENTER).get(0);
        model.getEntities().get(lane).add(pedestrian);

        return model;
    }
}
