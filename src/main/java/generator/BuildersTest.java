package generator;

import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.Decision;
import project.MyFactory;
import project.Passenger;
import visualization.Visualization;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class BuildersTest {
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, OWLOntologyStorageException {
        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();
        MyFactory factory = MyFactorySingleton.getFactory();
        RandomSubclassGenerator subclassGenerator = new RandomSubclassGenerator(factory);


//        for(int i = 0; i < 100; i++) {
//            Passenger p = subclassGenerator.generatePassengerSubclass(ObjectNamer.getName("passenger"));
//            System.out.println(p.toString());
//        }

//        for(int i = 0; i < 40; i++) {
//            Model model = baseScenarioGenerator.generate();
//
//            Model newModel = new ScenarioFactory(model).carApproaching().getModel();
//
//            Visualization.getImage(newModel);
//            newModel.export();
//        }
        DecisionGenerator decisionGenerator= new DecisionGenerator();

        for(int i = 0; i < 1; i++) {
            Model model = baseScenarioGenerator.generate();


//             new ScenarioFactory(model)
//                    .animalOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .obstacleOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianOnCrossing(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianJaywalking(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1});
//
             new ModelBuilder(model)
                    .addAnimal(new int[]{5}, new double[]{1.0});
            decisionGenerator.generate(model);
            Visualization.getImage(model);
            System.out.println(model.getEntities());
            factory.saveOwlOntology();
//        }



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






    }}}

