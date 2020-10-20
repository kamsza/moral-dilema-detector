package generator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;
import visualization.Visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class BuildersTest {
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, OWLOntologyStorageException {
        BaseScenarioGenerator2 baseScenarioGenerator = new BaseScenarioGenerator2();
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);

        for(int i = 0; i < 1; i++) {
            Model model = baseScenarioGenerator.generate();


             model = new ScenarioFactory(model)
                     .carApproaching().getModel();
//                    .animalOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1}).getModel();
//                    .obstacleOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianOnCrossing(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianJaywalking(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1});
////
//             new ModelBuilder(model).
//                    .addVehicles(new int[]{1}, new double[]{1.0});

            try {
                factory.saveOwlOntology();
            } catch (OWLOntologyStorageException ignored) {

            }
            System.out.println(model.getScenario().getOwlIndividual().toString());
            Visualization.getImage(model);


        }



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
