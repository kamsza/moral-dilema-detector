package generator;

import project.Lane;
import project.MyFactory;
import project.Surrounding;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class ObstacleOnRoadSG extends BaseScenarioGenerator {

    public ObstacleOnRoadSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // model
        Model model = super.generate();

        // create objects
        Surrounding obstacle = getObstacle();

        // add to ontology
        model.getVehicle().addHas_in_the_front(obstacle);

        // add to model
        Lane lane  = model.getLanes().get(Model.Side.CENTER).get(0);
        model.getEntities().get(lane).add(obstacle);

        return model;
    }

    private Surrounding getObstacle() {
        Random rand = new Random();
        Surrounding obstacle;
        if(rand.nextInt(2) == 0)
            obstacle = factory.createRock(ObjectNamer.getName("obstacle"));
        else
            obstacle = factory.createTree(ObjectNamer.getName("obstacle"));
        return obstacle;
    }

}
