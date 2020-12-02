//package visualization;
//
//import generator.BaseScenarioGenerator;
//import generator.Model;
//import generator.ScenarioFactory;
//import org.semanticweb.owlapi.model.OWLOntologyCreationException;
//import org.semanticweb.owlapi.model.OWLOntologyStorageException;
//import project.MyFactory;
//
//import java.io.FileNotFoundException;
//import java.lang.reflect.InvocationTargetException;
//
//public class VisualizationTest {
//
//    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, OWLOntologyStorageException {
//        BaseScenarioGenerator baseScenarioGenerator = new BaseScenarioGenerator();
////        MyFactory factory = new MyFactory();
//        for (int i = 0; i < 200; i++) {
//            Model baseModel = baseScenarioGenerator.generate();
//
//            ScenarioFactory scenarioFactory = new ScenarioFactory(baseModel);
//            Model model1 = scenarioFactory
//                    .animalOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .obstacleOnRoad(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianOnCrossing(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .pedestrianJaywalking(new int[]{1, 2, 3}, new double[]{0.3, 0.1, 0.1})
//                    .getModel();
//           // model1.export();
//            Visualization.getImage(model1);
//        }
//    }
//}