import DilemmaDetector.Simulator.Vector2;
import generator.Model;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import project.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ScenarioReader {
    private OWLOntologyManager ontologyManager;
    private OWLOntology ontology;
    private MyFactory factory;
    public static final String name = "http://www.w3.org/2003/11/";

    public ScenarioReader() throws OWLOntologyCreationException {
        this.ontologyManager = OWLManager.createOWLOntologyManager();
        this.ontology = this.ontologyManager.loadOntologyFromOntologyDocument(new File("src/main/resources/traffic_ontology.owl"));
        this.factory = factory = new MyFactory(ontology);
    }


    public void getModel(int number ){
        Scenario scenario = factory.getScenario(name + String.valueOf(number) + "_scenario");
        Weather weather = factory.getWeather(name + String.valueOf(number) + "_weather" );
        Time time = factory.getTime(name + String.valueOf(number) + "_time");
        Road_type roadType = factory.getRoad_type(name + String.valueOf(number) + "_road_type");
        Vehicle vehicle = factory.getVehicle(name + String.valueOf(number) + "_vehicle_main");
        Driver driver = factory.getDriver(name + String.valueOf(number) + "_driver");
        System.out.println(driver.toString());
        ArrayList<Passenger> passengers = new ArrayList<>();
        Map<Model.Side, TreeMap<Integer, Lane>> lanes = new HashMap<>();


        Model model = new Model();
        model.setScenario(scenario);
        model.setWeather(weather);


    }

    public static void main(String[] args) throws OWLOntologyCreationException {
        ScenarioReader scenarioReader = new ScenarioReader();
        scenarioReader.getModel(188);
    }


}
