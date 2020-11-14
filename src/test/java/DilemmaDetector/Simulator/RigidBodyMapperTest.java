package DilemmaDetector.Simulator;

import generator.BaseScenarioGenerator2;
import generator.Model;
import generator.ObjectNamer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.MyFactory;
import project.Vehicle;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RigidBodyMapperTest {

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
    public void rigidBodyForMainVehicleTest() throws OWLOntologyCreationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Vehicle mainVehicle = model.getVehicle();
        Assert.assertNotNull(model.getScenario());
        Assert.assertNotNull(mainVehicle);
        Actor mainVehicleActor = new Actor(model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));

        Assert.assertNotNull(mainVehicleActor);
        Assert.assertEquals(mainVehicleActor.getEntityName(), mainVehicle.getOwlIndividual().getIRI().toString());

        double accelX, accelY, speedX, speedY, width, length;
        accelX = PhysicsUtils.CmToMeters(RigidBodyMapper.getProperty(mainVehicle, "accelX"));
        accelY = PhysicsUtils.CmToMeters(RigidBodyMapper.getProperty(mainVehicle, "accelY"));
        speedX = PhysicsUtils.KmphToMeters(RigidBodyMapper.getProperty(mainVehicle, "speedX"));
        speedY = PhysicsUtils.KmphToMeters(RigidBodyMapper.getProperty(mainVehicle, "speedY"));
        width = PhysicsUtils.CmToMeters(RigidBodyMapper.getProperty(mainVehicle, "width"));
        length = PhysicsUtils.CmToMeters(RigidBodyMapper.getProperty(mainVehicle, "length"));

        Assert.assertEquals(new Vector2(accelX, accelY), mainVehicleActor.getRigidBody().getAcceleration());
        Assert.assertEquals(new Vector2(speedX, speedY), mainVehicleActor.getRigidBody().getSpeed());
        Assert.assertEquals(new Vector2(0,0), mainVehicleActor.getRigidBody().getPosition());
        Assert.assertEquals(new Vector2(0,0), mainVehicleActor.getRigidBody().getPreviousPosition());
        Assert.assertEquals(width, mainVehicleActor.getRigidBody().getWidth(), 0.01);
        Assert.assertEquals(length, mainVehicleActor.getRigidBody().getLength(), 0.01);
    }
}
