package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import project.Entity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActorTest {
    private Actor createMockActor(String name){
        Entity entityMock = mock(Entity.class);
        OWLNamedIndividual owlNamedIndividualMock = mock(OWLNamedIndividual.class);
        IRI iriMock = mock(IRI.class);
        when(owlNamedIndividualMock.getIRI()).thenReturn(iriMock);
        when(iriMock.toString()).thenReturn(name);
        when(entityMock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
        return new Actor(entityMock, mock(RigidBody.class));
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
