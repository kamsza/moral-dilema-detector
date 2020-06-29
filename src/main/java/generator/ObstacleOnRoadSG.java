package generator;

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
        Model model = super.generate();

        Surrounding obstacle = getObstacle();

        model.getVehicle().addHas_in_the_front(obstacle);

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
