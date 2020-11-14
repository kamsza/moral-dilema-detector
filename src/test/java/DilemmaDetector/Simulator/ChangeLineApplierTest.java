package DilemmaDetector.Simulator;

import generator.BaseScenarioGenerator2;
import generator.Model;
import javafx.collections.SetChangeListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.MyFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class ChangeLineApplierTest {
    private static final double MOVING_TIME = 5.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.01;

    public static final String baseIRI = "http://webprotege.stanford.edu/";
    private Model model;

    @Before
    public void init() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, OWLOntologyCreationException {
        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));
        MyFactory factory = new MyFactory(ontology);
        BaseScenarioGenerator2 baseScenarioGenerator2 = new BaseScenarioGenerator2(factory, baseIRI);
        this.model = baseScenarioGenerator2.generate();
    }


    @Test
    public void changeLine(){
        ChangeLaneActionApplier changeLaneActionApplier = new ChangeLaneActionApplier();
        int startLaneNumber = 0;
        int endLaneNumber = 3;
        int laneWidth = 3;
        double currentTime = 0.0;
        Actor mainVehicleActor = new Actor( model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            System.out.println(
                    "Pos: " + mainVehicleActor.getRigidBody().getPosition() +
                            " | PrevPos: " + mainVehicleActor.getRigidBody().getPreviousPosition() +
                            " | Speed: " + mainVehicleActor.getRigidBody().getSpeed() +
                            " = " + mainVehicleActor.getRigidBody().getSpeed().getMagnitude() +
                            " | Acc: " + mainVehicleActor.getRigidBody().getAcceleration());

            changeLaneActionApplier.CarChangeLanes(mainVehicleActor.getRigidBody(), model.getWeather(), startLaneNumber, endLaneNumber , laneWidth);
            mainVehicleActor.getRigidBody().update(TIME_PART);
        }

        Assert.assertEquals(mainVehicleActor.getRigidBody().getPosition().y, endLaneNumber*laneWidth, 0.1);
    }
}

