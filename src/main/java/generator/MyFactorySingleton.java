package generator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.OWLFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class MyFactorySingleton {
    private static OWLFactory factory;
    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static OWLFactory getFactory() throws FileNotFoundException, OWLOntologyCreationException {
        if (factory == null) {
            String fileName = "traffic_ontology.owl";
            String directoryPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
            File ontologyFile = new File(directoryPath + fileName);
            if (!ontologyFile.exists())
                throw new FileNotFoundException("File: " + ontologyFile.getAbsolutePath() + " not found");
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
            factory = new OWLFactory(ontology);
        }
        return factory;
    }
}
