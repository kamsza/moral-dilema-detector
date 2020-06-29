package generator;

import project.Illegal_pedestrian_crossings;
import project.MyFactory;
import project.Pedestrian;

import java.lang.reflect.InvocationTargetException;

public class PedestrianIllegallyCrossingSG extends BaseScenarioGenerator {

    public PedestrianIllegallyCrossingSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    Pedestrian pedestrian;
    Illegal_pedestrian_crossings pedestrianLocation;

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Model model = super.generate();

        pedestrian = factory.createPedestrian(ObjectNamer.getName("pedestrian"));

        pedestrianLocation = factory.createIllegal_pedestrian_crossings(ObjectNamer.getName("illegal_pedestrian_crossing"));

        model.getScenario().addHas_pedestrian(pedestrian);

        pedestrian.addPedestrian_has_location(pedestrianLocation);

        return model;
    }

}
