package DilemmaDetector.Simulator;
import generator.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.semanticweb.owlapi.model.*;
import project.Entity;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CollisionDetectorTest{
    private CollisionDetector collisionDetector;
    private Actor vehicleActor;

    @Before
    public void init() {
        RigidBody rigidBody = new RigidBody();
        rigidBody.setWidth(2.0);
        rigidBody.setLength(5.0);
        rigidBody.setPosition(new Vector2(0, 0));

        Entity entityMock = mock(Entity.class);
        OWLNamedIndividual owlIndividualMock = mock(OWLNamedIndividual.class);
        IRI iriMock = mock(IRI.class);
        when(entityMock.getOwlIndividual()).thenReturn(owlIndividualMock);
        when(owlIndividualMock.getIRI()).thenReturn(iriMock);
        when(iriMock.toString()).thenReturn("entity");
        vehicleActor = new Actor(entityMock, rigidBody);
        collisionDetector = new CollisionDetector(mock(Model.class), vehicleActor, null, null);
    }

    @Test
    public void shouldBeCollisionWithAllRigidBodies(){
        //rigidBody in front of mainVehicle
        RigidBody rigidBody1 = new RigidBody();
        rigidBody1.setWidth(2.0);
        rigidBody1.setLength(5.0);
        rigidBody1.setPosition(new Vector2(3, 0));

        //rigidBody in the back of main Vehicle
        RigidBody rigidBody2 = new RigidBody();
        rigidBody2.setWidth(3.0);
        rigidBody2.setLength(5.0);
        rigidBody2.setPosition(new Vector2(-3, 2));

        //rigidBody on right side of mainVehicle
        RigidBody rigidBody3 = new RigidBody();
        rigidBody3.setWidth(3.0);
        rigidBody3.setLength(1.0);
        rigidBody3.setPosition(new Vector2(0, -2));

        //rigidBody on front left side of mainVehicle
        RigidBody rigidBody4 = new RigidBody();
        rigidBody4.setWidth(3.0);
        rigidBody4.setLength(5.0);
        rigidBody4.setPosition(new Vector2(2, 2));

        //rigidBody on the back and left side
        RigidBody rigidBody5 = new RigidBody();
        rigidBody5.setWidth(3.0);
        rigidBody5.setLength(5.0);
        rigidBody5.setPosition(new Vector2(-3, 2));

        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody1, "rigidBody1"));
        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody2, "rigidBody2"));
        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody3, "rigidBody3"));
        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody4, "rigidBody4"));
        Assert.assertTrue(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody5, "rigidBody5"));
    }

    @Test
    public void shouldNotBeCollisionWithAnyRigidBodies(){
        //rigidBody in front of mainVehicle
        RigidBody rigidBody1 = new RigidBody();
        rigidBody1.setWidth(2.0);
        rigidBody1.setLength(5.0);
        rigidBody1.setPosition(new Vector2(6, 0));

        //rigidBody in the back of main Vehicle
        RigidBody rigidBody2 = new RigidBody();
        rigidBody2.setWidth(3.0);
        rigidBody2.setLength(5.0);
        rigidBody2.setPosition(new Vector2(-6, 2));

        //rigidBody on right side of mainVehicle
        RigidBody rigidBody3 = new RigidBody();
        rigidBody3.setWidth(3.0);
        rigidBody3.setLength(1.0);
        rigidBody3.setPosition(new Vector2(0, -4));

        //rigidBody on front left side of mainVehicle
        RigidBody rigidBody4 = new RigidBody();
        rigidBody4.setWidth(3.0);
        rigidBody4.setLength(5.0);
        rigidBody4.setPosition(new Vector2(6, 4));

        //rigidBody on the back and left side
        RigidBody rigidBody5 = new RigidBody();
        rigidBody5.setWidth(3.0);
        rigidBody5.setLength(5.0);
        rigidBody5.setPosition(new Vector2(-6, 4));

        Assert.assertFalse(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody1, "rigidBody1"));
        Assert.assertFalse(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody2, "rigidBody2"));
        Assert.assertFalse(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody3, "rigidBody3"));
        Assert.assertFalse(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody4, "rigidBody4"));
        Assert.assertFalse(collisionDetector.detectCollisionWithRigidBodyInMoment(rigidBody5, "rigidBody5"));
    }
}
