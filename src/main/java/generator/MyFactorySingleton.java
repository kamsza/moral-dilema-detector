package generator;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.MyFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class MyFactorySingleton {
    private static MyFactory factory;
    public static final String baseIRI = "http://webprotege.stanford.edu/";

    public static MyFactory getFactory() throws FileNotFoundException, OWLOntologyCreationException {
        String fileName = "traffic_ontology.owl";
        String directoryPath = System.getProperty("user.dir") + "\\src\\main\\resources\\";
        return getFactory(directoryPath + fileName);
    }

    public static MyFactory getFactory(String filepath) throws FileNotFoundException, OWLOntologyCreationException {
        if (factory == null) {
            File ontologyFile = new File(filepath);
            if (!ontologyFile.exists())
                throw new FileNotFoundException("File: " + ontologyFile.getAbsolutePath() + " not found");
            OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontologyFile);
            factory = new MyFactory(ontology);
        }
        return factory;
    }
}
