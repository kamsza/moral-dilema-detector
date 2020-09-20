package DilemmaDetector.Simulator.Test;

import DilemmaDetector.Simulator.*;
import generator.ConsequenceGenerator;
import generator.DecisionGenerator;
import generator.Model;
import generator.SimplestPossibleScenarioGenerator;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.Decision;
import project.MyFactory;
import visualization.Visualization;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class ActorTest {

    public static final String baseIRI = "http://webprotege.stanford.edu/";


    public static Model getModelFromGenerator(MyFactory factory) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
//        BaseScenarioGenerator generator;
        SimplestPossibleScenarioGenerator generator = new SimplestPossibleScenarioGenerator(factory, baseIRI);

        Model model = generator.generate();
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        decisionGenerator.generate(model);
        return model;
    }


    public static void main(String [] args) throws OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));

        MyFactory factory = new MyFactory(ontology);
        Model scenarioModel = getModelFromGenerator(factory);
        Visualization.getImage(scenarioModel);

        RigidBody rigidBody = RigidBodyMapper.rigidBodyForMainVehicle(scenarioModel.getVehicle());

        for (int i = 0; i< 3; i++){
            System.out.println("\n\n\n DECISION " +i);
            int counter = 0;

            rigidBody.setToInitialValues();

            while (counter < 10){

                rigidBody.update(1);

                System.out.println("POSITION" + rigidBody.getPosition() + " PREV POS " + rigidBody.getPreviousPosition() +
                        " SPEED " + rigidBody.getSpeed() + " ACCEL " + rigidBody.getAcceleration());
                counter++;
            }
        }
    }
}
