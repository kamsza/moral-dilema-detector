package commonadapter.server.implementation;

import adapter.*;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BaseFactoryImpl implements BaseFactory {

    // TODO: export paths to config
    private String ontologyFilePath = "src\\main\\resources\\traffic_ontology.owl";
    private String resultFilePath = "src\\main\\resources\\waymo\\ontology_with_scenario.owl";

    private Map<String, BaseItemImpl> items;

    private MyFactory owlFactory;

    public BaseFactoryImpl() {

        items = new HashMap<>();

        try {
            prepareOntology();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    private void prepareOntology() throws IOException, OWLOntologyCreationException {

        File original = new File(ontologyFilePath);
        File copied = new File(resultFilePath);

        com.google.common.io.Files.copy(original, copied);

        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(copied);

        this.owlFactory = new MyFactory(ontology);
    }

    @Override
    public String create(ItemType type, Current current) {

        BaseItemImpl item = null;

        String uuid = UUID.randomUUID().toString();
        String id = type.toString() + "/" + uuid;

        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl(id, owlFactory);
                break;
            case VEHICLE:
                item = new VehicleImpl(id, owlFactory);
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl(id, owlFactory);
                break;
            case CYCLIST:
                item = new CyclistImpl(id, owlFactory);
                break;
            case LANE:
                item = new LaneImpl(id, owlFactory);
                break;
            case ROAD:
                item = new RoadImpl(id, owlFactory);
                break;
            case ROADPOINT:
                item = new RoadPointImpl(id, owlFactory);
                break;
            case JUNCTION:
                item = new JunctionImpl(id, owlFactory);
                break;
        }

        items.put(id, item);

        current.adapter.add(item, new Identity(uuid, type.toString()));

        return id;
    }

    @Override
    public void persist(Current current) {
        try {
            this.owlFactory.saveOwlOntology();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
}
