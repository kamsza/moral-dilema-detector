package generator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class GeneratorTest {


    public static void main(String[] args) throws FileNotFoundException, OWLOntologyCreationException, OWLOntologyStorageException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String fileName = "traffic_ontology.owl";
        String directoryPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";

        File ontologyFile = new File(directoryPath + fileName);
        if (!ontologyFile.exists())
            throw new FileNotFoundException("File: " + ontologyFile.getAbsolutePath() + " not found");

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);

        MyFactory factory = new MyFactory(ontology);
        String baseIRI = "http://webprotege.stanford.edu/";

        BaseScenarioGenerator generator = new AnimalOnRoadSG(factory, baseIRI);
        DecisionGenerator decisionGenerator = new DecisionGenerator(factory, baseIRI);
        Model model = generator.generate();
        decisionGenerator.generate(model);
        factory.saveOwlOntology();
    }
}
