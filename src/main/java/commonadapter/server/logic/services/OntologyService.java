package commonadapter.server.logic.services;

import adapter.ItemType;
import commonadapter.server.logic.exceptions.OntologyItemLoadingException;
import commonadapter.logging.LogMessageType;
import commonadapter.logging.Logger;
import commonadapter.server.logic.models.*;
import javafx.util.Pair;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;

import javax.annotation.Nullable;

import static commonadapter.OntologyUtils.getPrefixedId;

import java.io.File;
import java.util.*;

public class OntologyService {


    private MyFactory myFactory;

    private String ontologyFilePath;

    private Map<String, Pair<BaseItemImpl, Boolean>> loadedItems;

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

        this.myFactory = new MyFactory(ontology);
    }

    public BaseItemImpl createAndLoadItem(ItemType type) {

        BaseItemImpl item = null;

        String uuid = UUID.randomUUID().toString();
        String id = type.toString() + "-" + uuid;


        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl(id, myFactory.createScenario(id), this);
                break;
            case VEHICLE:
                item = new VehicleImpl(id, myFactory.createVehicle(id), this);
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl(id, myFactory.createPedestrian(id), this);
                break;
            case CYCLIST:
                item = new CyclistImpl(id, myFactory.createCyclist(id), this);
                break;
            case LANE:
                item = new LaneImpl(id, myFactory.createLane(id), this);
                break;
            case ROAD:
                item = new RoadImpl(id, myFactory.createRoad(id), this);
                break;
            case DELIMITER:
                item = new DelimiterImpl(id, myFactory.createDelimiter(id), this);
                break;
            case JUNCTION:
                item = new JunctionImpl(id, myFactory.createJunction(id), this);
                break;
            case LANEBOUNDARY:
                item = new LaneBoundaryImpl(id, myFactory.createLane_boundary(id), this);
                break;
            case ROADATTRIBUTES:
                item = new RoadAttributesImpl(id, myFactory.createRoad_attributes(id), this);
                break;
        }

        loadedItems.put(item.getId(), new Pair<>(item, true));

        return item;
    }

    public BaseItemImpl loadItem(String id) {
        return loadItem(id, null);
    }

    public BaseItemImpl loadItem(String id, @Nullable ItemType type) {

        if (checkIfLoaded(id)) {
            return loadedItems.get(id).getKey();
        }

        if (type == null)
            throw new OntologyItemLoadingException("unable to load ontology item from file without declared type");


        BaseItemImpl item = null;
        String prefixedId = getPrefixedId(id);


        switch (type) {
            case SCENARIO:
                item = new ScenarioImpl(id, myFactory.getScenario(prefixedId), this);
                break;
            case VEHICLE:
                item = new VehicleImpl(id, myFactory.getVehicle(prefixedId), this);
                break;
            case PEDESTRIAN:
                item = new PedestrianImpl(id, myFactory.getPedestrian(prefixedId), this);
                break;
            case CYCLIST:
                item = new CyclistImpl(id, myFactory.getCyclist(prefixedId), this);
                break;
            case LANE:
                item = new LaneImpl(id, myFactory.getLane(prefixedId), this);
                break;
            case ROAD:
                item = new RoadImpl(id, myFactory.getRoad(prefixedId), this);
                break;
            case DELIMITER:
                item = new DelimiterImpl(id, myFactory.getDelimiter(prefixedId), this);
                break;
            case JUNCTION:
                item = new JunctionImpl(id, myFactory.getJunction(prefixedId), this);
                break;
            case LANEBOUNDARY:
                item = new LaneBoundaryImpl(id, myFactory.getLane_boundary(prefixedId), this);
                break;
            case ROADATTRIBUTES:
                item = new RoadAttributesImpl(id, myFactory.getRoad_attributes(prefixedId), this);
                break;
        }

        loadedItems.put(item.getId(), new Pair<>(item, false));

        return item;
    }

    public void persist() {

        try {

            this.myFactory.saveOwlOntology();

            StringBuffer sb = new StringBuffer();

            loadedItems.entrySet()
                    .forEach(entry -> sb
                            .append(entry.getKey())
                            .append(" RMI Object: ")
                            .append(entry.getValue().getValue())
                            .append("\n"));

            Logger.printLogMessage(sb.toString(), LogMessageType.INFO);

        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfLoaded(String itemId) {

        return loadedItems.containsKey(itemId);
    }
}
