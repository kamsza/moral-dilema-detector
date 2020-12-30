package DilemmaDetector.Simulator;

import DilemmaDetector.GeneratedClassesMocks;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import project.Entity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActorTest {
    
    private Actor createMockActor(String name){
        GeneratedClassesMocks gcm = new GeneratedClassesMocks();
        Entity entityMock = gcm.createWrappedIndividualMock(name, "Entity");
        return new Actor(entityMock, mock(RigidBody.class), true);
    }

    @Test
    public void notEqualsTest() {
        Actor actor1 = createMockActor("Entity1");
        Actor actor2 = createMockActor("Entity2");

        Assert.assertNotEquals(actor1, actor2);
    }

    @Test
    public void equalsTest() {
        Actor actor1 = createMockActor("Entity");
        Actor actor2 = createMockActor("Entity");

        Assert.assertEquals(actor1, actor2);
    }
}
