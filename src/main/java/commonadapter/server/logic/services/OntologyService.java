package commonadapter.server.logic.services;

import adapter.ItemType;
import commonadapter.server.logic.exceptions.OntologyItemCreationException;
import commonadapter.server.logic.exceptions.OntologyItemLoadingException;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;
import commonadapter.server.logic.models.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.OWLFactory;

import javax.annotation.Nullable;

import static commonadapter.OntologyUtils.getPrefixedId;

import java.io.File;
import java.util.*;

public class OntologyService {


    private OWLFactory owlFactory;

    private String ontologyFilePath;

    private Map<String, BaseItemImpl> loadedItems;

    public OntologyService(String ontologyFilePath) {

        this.ontologyFilePath = ontologyFilePath;
        this.loadedItems = new HashMap<>();

        try {
            prepareOntology();
        } catch (OWLOntologyCreationException ex) {
            Logger.printLogMessage("unable to load ontology from file", LogMessageType.ERROR);
        }

    }

    private void prepareOntology() throws OWLOntologyCreationException {

        File ontologyFile = new File(ontologyFilePath);

        OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = ontologyManager.loadOntologyFromOntologyDocument(ontologyFile);

        this.owlFactory = new OWLFactory(ontology);
    }

    public BaseItemImpl createAndLoadItem(ItemType type) {

        BaseItemImpl item = null;

        String uuid = UUID.randomUUID().toString();
        String id = type.toString() + "-" + uuid;


        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl(id, owlFactory.createScenario(id), this);
                break;
            case VEHICLE:
                item = new VehicleImpl(id, owlFactory.createVehicle(id), this);
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl(id, owlFactory.createPedestrian(id), this);
                break;
            case CYCLIST:
                item = new CyclistImpl(id, owlFactory.createCyclist(id), this);
                break;
            case LANE:
                item = new LaneImpl(id, owlFactory.createLane(id), this);
                break;
            case ROAD:
                item = new RoadImpl(id, owlFactory.createRoad(id), this);
                break;
            case DELIMITER:
                item = new DelimiterImpl(id, owlFactory.createDelimiter(id), this);
                break;
            case JUNCTION:
                item = new JunctionImpl(id, owlFactory.createJunction(id), this);
                break;
            case LANEBOUNDARY:
                item = new LaneBoundaryImpl(id, owlFactory.createLane_boundary(id), this);
                break;
            case ROADATTRIBUTES:
                item = new RoadAttributesImpl(id, owlFactory.createRoad_attributes(id), this);
                break;
        }

        loadedItems.put(item.getId(), item);

        return item;
    }

    public BaseItemImpl loadItem(String id) {
        return loadItem(id, null);
    }

    public BaseItemImpl loadItem(String id, @Nullable ItemType type) {

        if (checkIfLoaded(id)) {
            return loadedItems.get(id);
        }

        if (type == null)
            throw new OntologyItemLoadingException("unable to load ontology item from file without declared type");


        BaseItemImpl item = null;
        String prefixedId = getPrefixedId(id);


        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl(id, owlFactory.getScenario(prefixedId), this);
                break;
            case VEHICLE:
                item = new VehicleImpl(id, owlFactory.getVehicle(prefixedId), this);
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl(id, owlFactory.getPedestrian(prefixedId), this);
                break;
            case CYCLIST:
                item = new CyclistImpl(id, owlFactory.getCyclist(prefixedId), this);
                break;
            case LANE:
                item = new LaneImpl(id, owlFactory.getLane(prefixedId), this);
                break;
            case ROAD:
                item = new RoadImpl(id, owlFactory.getRoad(prefixedId), this);
                break;
            case DELIMITER:
                item = new DelimiterImpl(id, owlFactory.getDelimiter(prefixedId), this);
                break;
            case JUNCTION:
                item = new JunctionImpl(id, owlFactory.getJunction(prefixedId), this);
                break;
            case LANEBOUNDARY:
                item = new LaneBoundaryImpl(id, owlFactory.getLane_boundary(prefixedId), this);
                break;
            case ROADATTRIBUTES:
                item = new RoadAttributesImpl(id, owlFactory.getRoad_attributes(prefixedId), this);
                break;
        }

        loadedItems.put(item.getId(), item);

        return item;
    }

    public void persist() {

        try {

            this.owlFactory.saveOwlOntology();

            StringBuffer sb = new StringBuffer();

            loadedItems.keySet().forEach(id -> {
                sb.append(id);
                sb.append("\n");
            });

            Logger.printLogMessage(sb.toString(), LogMessageType.INFO);

        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfLoaded(String itemId) {

        return loadedItems.containsKey(itemId);
    }
}
