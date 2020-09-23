package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import visualization.Visualization;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class BuildersTest {
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();

        for(int i = 0; i < 50; i++) {
            Model baseModel = baseScenarioGenerator.generate();
//        Visualization.getImage(baseModel);


            ScenarioFactory scenarioFactory = new ScenarioFactory(baseModel);
            Model model1 = scenarioFactory
                    .animalOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .obstacleOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .pedestrianOnCrossing(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .pedestrianJaywalking(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
                    .getModel();
            Visualization.getImage(model1);
        }

//        Model model2 = new ModelBuilder(model1)
//                .addVehicles(new int[]{5}, new double[]{1.0})
//                .getModel();
//        Visualization.getImage(model2);

//        Model model1 = scenarioFactory
//                .animalOnRoad()
//                .pedestrianOnCrossing()
//                .obstacleOnRoad()
//                .getModel();
//        Visualization.getImage(model1);

//        Model model2 = new ModelBuilder(model1)
//                .addAnimals(3)
//                .addCar()
//                .getModel();
//        Visualization.getImage(model2);






    }
}
