package generator;

import project.Illegal_pedestrian_crossings;
import project.Lane;
import project.MyFactory;
import project.Pedestrian;

import java.lang.reflect.InvocationTargetException;

public class PedestrianIllegallyCrossingSG extends BaseScenarioGenerator {

    public PedestrianIllegallyCrossingSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // model
        Model model = super.generate();

        // create objects
        Pedestrian pedestrian = factory.createPedestrian(ObjectNamer.getName("pedestrian"));
        Illegal_pedestrian_crossings pedestrianLocation = factory.createIllegal_pedestrian_crossings(ObjectNamer.getName("illegal_pedestrian_crossing"));

        // add to ontology
        pedestrian.addPedestrian_has_location(pedestrianLocation);
        model.getScenario().addHas_pedestrian(pedestrian);

        // add to model
        Lane lane  = model.getLanes().get(Model.Side.CENTER).get(0);
        model.getEntities().get(lane).add(pedestrian);

        return model;
    }

}
