package commonadapter.server.logic.services;

import adapter.ItemType;
import commonadapter.server.logic.exceptions.OntologyItemCreationException;
import commonadapter.server.logic.exceptions.OntologyItemLoadingException;
import commonadapter.server.logic.logging.LogMessageType;
import commonadapter.server.logic.logging.Logger;
import commonadapter.server.logic.models.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import project.MyFactory;

import java.io.File;
import java.util.UUID;

public class OntologyService {

    public static final String IRI_PREFIX = "http://www.w3.org/2003/11/";

    private MyFactory owlFactory;

    private String ontologyFilePath;

    public OntologyService(String ontologyFilePath) {

        this.ontologyFilePath = ontologyFilePath;

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

        this.owlFactory = new MyFactory(ontology);
    }

    public BaseItemImpl createAndLoadItem(ItemType type) throws OntologyItemCreationException {

        BaseItemImpl item = null;

        String uuid = UUID.randomUUID().toString();
        String id = type.toString() + "-" + uuid;

        try {
            switch (type) {
                case SCENARIO:
                    item = new ScenarioImpl(id, owlFactory.createScenario(id), owlFactory);
                    break;
                case VEHICLE:
                    item = new VehicleImpl(id, owlFactory.createVehicle(id), owlFactory);
                    break;
                case PEDESTRIAN:
                    item = new PedestrianImpl(id, owlFactory.createPedestrian(id), owlFactory);
                    break;
                case CYCLIST:
                    item = new CyclistImpl(id, owlFactory.createCyclist(id), owlFactory);
                    break;
                case LANE:
                    item = new LaneImpl(id, owlFactory.createLane(id), owlFactory);
                    break;
                case ROAD:
                    item = new RoadImpl(id, owlFactory.createRoad(id), owlFactory);
                    break;
                case DELIMITER:
                    item = new DelimiterImpl(id, owlFactory.createDelimiter(id), owlFactory);
                    break;
                case JUNCTION:
                    item = new JunctionImpl(id, owlFactory.createJunction(id), owlFactory);
                    break;
                case LANEBOUNDARY:
                    item = new LaneBoundaryImpl(id, owlFactory.createLane_boundary(id), owlFactory);
                    break;
                case ROADATTRIBUTES:
                    item = new RoadAttributesImpl(id, owlFactory.createRoad_attributes(id), owlFactory);
                    break;
            }

        } catch (Exception ex) {

            throw new OntologyItemCreationException(ex.getMessage());
        }

        return item;
    }

    public BaseItemImpl loadItem(String id, ItemType type) throws OntologyItemLoadingException {

        BaseItemImpl item = null;

        try {
            switch (type) {
                case SCENARIO:
                    item = new ScenarioImpl(id, owlFactory.getScenario(IRI_PREFIX + id), owlFactory);
                    break;
                case VEHICLE:
                    item = new VehicleImpl(id, owlFactory.getVehicle(id), owlFactory);
                    break;
                case PEDESTRIAN:
                    item = new PedestrianImpl(id, owlFactory.getPedestrian(id), owlFactory);
                    break;
                case CYCLIST:
                    item = new CyclistImpl(id, owlFactory.getCyclist(id), owlFactory);
                    break;
                case LANE:
                    item = new LaneImpl(id, owlFactory.getLane(id), owlFactory);
                    break;
                case ROAD:
                    item = new RoadImpl(id, owlFactory.getRoad(id), owlFactory);
                    break;
                case DELIMITER:
                    item = new DelimiterImpl(id, owlFactory.getDelimiter(id), owlFactory);
                    break;
                case JUNCTION:
                    item = new JunctionImpl(id, owlFactory.getJunction(id), owlFactory);
                    break;
                case LANEBOUNDARY:
                    item = new LaneBoundaryImpl(id, owlFactory.getLane_boundary(id), owlFactory);
                    break;
                case ROADATTRIBUTES:
                    item = new RoadAttributesImpl(id, owlFactory.getRoad_attributes(id), owlFactory);
                    break;
            }

        } catch (Exception ex) {

            throw new OntologyItemLoadingException(ex.getMessage());
        }

        return item;
    }

    public void persist() {
        try {
            this.owlFactory.saveOwlOntology();
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
    }
}
