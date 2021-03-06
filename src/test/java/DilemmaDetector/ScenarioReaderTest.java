package DilemmaDetector;

import org.junit.Assert;
import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import project.OWLFactory;

public class ScenarioReaderTest {
    @Test
    public void ScenarioReaderOnGetModelShouldCreateModelFromLoadedFactory(){
        GeneratedClassesMocks generatedClassesMocks = new GeneratedClassesMocks();
        OWLFactory factory = generatedClassesMocks.createFactoryMock();
        ScenarioReader scenarioReader = new ScenarioReader(factory);
        Assert.assertNotNull(scenarioReader.getModel(1));
    }

    @Test
    public void ScenarioReaderShouldCreateFactoryFromOntologyFile() throws OWLOntologyCreationException {
        ScenarioReader scenarioReader = new ScenarioReader();
        Assert.assertNotNull(scenarioReader.getFactory());
    }
}
