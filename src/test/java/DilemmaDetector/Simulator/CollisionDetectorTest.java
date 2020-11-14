package DilemmaDetector.Simulator;

import generator.BaseScenarioGenerator2;
import generator.Model;
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
import java.util.Collections;
import java.util.List;

public class CollisionDetectorTest {
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
    public void detectCollisionInMomentTest(){
        RigidBody rigidBody = new RigidBody();
        rigidBody.setWidth(1.0);
        rigidBody.setLength(1.0);
        rigidBody.setPosition(new Vector2(2, 0));

        Actor mainVehicle = new Actor(model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));
        List<Actor> actors = RigidBodyMapper.createActors(model);
        List<Actor> surroundingActors = RigidBodyMapper.createSurroundingActors(model);
        CollisionDetector collisionDetector = new CollisionDetector(model, mainVehicle, actors, surroundingActors);
        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody, "rigidBody"));
    }
}
