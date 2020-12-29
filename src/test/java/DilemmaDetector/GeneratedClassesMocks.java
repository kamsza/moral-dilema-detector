package DilemmaDetector;

import org.protege.owl.codegeneration.WrappedIndividual;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import project.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class GeneratedClassesMocks {
    public static final String IRI_PREFIX = "http://www.w3.org/2003/11/";
    private MyFactory factory = mock(MyFactory.class);

    private OWLNamedIndividual getOwlNamedIndividualMock(String name) {
        OWLNamedIndividual owlNamedIndividualMock = mock(OWLNamedIndividual.class);
        IRI iriMock = mock(IRI.class);
        when(owlNamedIndividualMock.getIRI()).thenReturn(iriMock);
        when(iriMock.toString()).thenReturn(name);
        return owlNamedIndividualMock;
    }

    public <T extends WrappedIndividual> T createWrappedIndividualMock(String name, String className) {
        try {
            T mock = (T) mock(Class.forName("project." + className));
            OWLNamedIndividual owlNamedIndividualMock = getOwlNamedIndividualMock(name.toLowerCase());
            when(mock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
            when(mock.toString()).thenReturn(name);
            return mock;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public Lane createLaneMock(String name) {
        Lane mock = mock(Lane.class);
        OWLNamedIndividual owlNamedIndividualMock = getOwlNamedIndividualMock(name);
        when(mock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
        when(mock.toString()).thenReturn(name);
        return mock;
    }

    public Scenario createScenarioMock(String name) {
        Scenario mock = mock(Scenario.class);
        OWLNamedIndividual owlNamedIndividualMock = getOwlNamedIndividualMock(name);
        when(mock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
        when(mock.toString()).thenReturn(name);
        return mock;
    }

    public Road_type createRoadTypeMock(String name) {
        Road_type mock = mock(Road_type.class);
        OWLNamedIndividual owlNamedIndividualMock = getOwlNamedIndividualMock(name);
        when(mock.getOwlIndividual()).thenReturn(owlNamedIndividualMock);
        when(mock.toString()).thenReturn(name);
        when(factory.getRoad_type(IRI_PREFIX + name.toLowerCase()))
                .thenReturn(mock);

        Collection<Integer> lanesCount = Collections.singletonList(3);
        doReturn(lanesCount).when(mock).getLanes_count();
        lanesCount = Collections.singletonList(1);
        doReturn(lanesCount).when(mock).getLeft_lanes_count();
        return mock;
    }

    public MyFactory createFactoryMock() {
        String className;
        String name;
        WrappedIndividual m;
        className = "scenario";
        Scenario scenarioMock = createScenarioMock("1_" + className);
        ///////////////////
        Lane laneMock = createLaneMock("1_lane_0");
        ///////////////////
        className = "Sunny";
        m = createWrappedIndividualMock("1_weather", className);
        when(factory.getSunny(IRI_PREFIX + "1_weather"))
                .thenReturn((Sunny) m);
        ///////////////////
        className = "Time";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getTime(IRI_PREFIX + "1_" + className.toLowerCase()))
                .thenReturn((Time) m);
        ///////////////////
        className = "Road_type";
        createRoadTypeMock("1_" + className);
        ///////////////////
        m = createWrappedIndividualMock("1_vehicle_main", "Vehicle");
        when(factory.getVehicle(IRI_PREFIX + "1_vehicle_main"))
                .thenReturn((Vehicle) m);
        ///////////////////
        className = "Driver";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getDriver(IRI_PREFIX + "1_" + className.toLowerCase()))
                .thenReturn((Driver) m);
        ///////////////////
        Collection<? extends Vehicle> vehicles = new ArrayList<Vehicle>();
        doReturn(vehicles).when(laneMock).getLane_has_vehicle();
        className = "Truck";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getTruck("1_" + className.toLowerCase()))
                .thenReturn((Truck) m);
        ((ArrayList<Vehicle>)vehicles).add((Vehicle)m);
        className = "Bicycle";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getBicycle("1_" + className.toLowerCase()))
                .thenReturn((Bicycle) m);
        ((ArrayList<Vehicle>)vehicles).add((Vehicle)m);
        className = "Motorcycle";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getMotorcycle("1_" + className.toLowerCase()))
                .thenReturn((Motorcycle) m);
        ((ArrayList<Vehicle>)vehicles).add((Vehicle)m);
        className = "Car";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getCar("1_" + className.toLowerCase()))
                .thenReturn((Car) m);
        ((ArrayList<Vehicle>)vehicles).add((Vehicle)m);

        doReturn(vehicles).when(laneMock).getLane_has_vehicle();

        Collection<Surrounding> surroundings = new ArrayList<>();

        className = "Bushes";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getBushes("1_" + className.toLowerCase()))
                .thenReturn((Bushes) m);
        surroundings.add((Surrounding) m);
        className = "Cycling_path";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getCycling_path("1_" + className.toLowerCase()))
                .thenReturn((Cycling_path) m);
        surroundings.add((Surrounding) m);
        className = "Ditch";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getDitch("1_" + className.toLowerCase()))
                .thenReturn((Ditch) m);
        surroundings.add((Surrounding) m);
        className = "Edge";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getEdge("1_" + className.toLowerCase()))
                .thenReturn((Edge) m);
        surroundings.add((Surrounding) m);
        className = "Field";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getField("1_" + className.toLowerCase()))
                .thenReturn((Field) m);
        surroundings.add((Surrounding) m);
        className = "Forest";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getForest("1_" + className.toLowerCase()))
                .thenReturn((Forest) m);
        surroundings.add((Surrounding) m);
        className = "Noise_barrier";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getNoise_barrier("1_" + className.toLowerCase()))
                .thenReturn((Noise_barrier) m);
        surroundings.add((Surrounding) m);
        className = "Pavement";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getPavement("1_" + className.toLowerCase()))
                .thenReturn((Pavement) m);
        surroundings.add((Surrounding) m);
        className = "Railway";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getRailway("1_" + className.toLowerCase()))
                .thenReturn((Railway) m);
        surroundings.add((Surrounding) m);
        className = "Road_sign_post";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getRoad_sign_post("1_" + className.toLowerCase()))
                .thenReturn((Road_sign_post) m);
        surroundings.add((Surrounding) m);
        className = "Rock";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getRock("1_" + className.toLowerCase()))
                .thenReturn((Rock) m);
        surroundings.add((Surrounding) m);
        className = "Security_side_barrier";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getSecurity_side_barrier("1_" + className.toLowerCase()))
                .thenReturn((Security_side_barrier) m);
        surroundings.add((Surrounding) m);
        className = "Street_lamp";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getStreet_lamp("1_" + className.toLowerCase()))
                .thenReturn((Street_lamp) m);
        surroundings.add((Surrounding) m);
        className = "Tree";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getTree("1_" + className.toLowerCase()))
                .thenReturn((Tree) m);
        surroundings.add((Surrounding) m);
        className = "Wall";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getWall("1_" + className.toLowerCase()))
                .thenReturn((Wall) m);
        surroundings.add((Surrounding) m);

        doReturn(surroundings).when(scenarioMock).getHas_surrounding_left();

        Collection<Living_entity> livingEntities = new ArrayList<>();

        className = "Person";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getPerson("1_" + className.toLowerCase()))
                .thenReturn((Person) m);
        livingEntities.add((Living_entity)m);
        className = "Stock";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getStock("1_" + className.toLowerCase()))
                .thenReturn((Stock) m);
        livingEntities.add((Living_entity)m);
        className = "Pet";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getPet("1_" + className.toLowerCase()))
                .thenReturn((Pet) m);
        livingEntities.add((Living_entity)m);
        className = "Wild";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getWild("1_" + className.toLowerCase()))
                .thenReturn((Wild) m);
        livingEntities.add((Living_entity)m);

        doReturn(livingEntities).when(laneMock).getLane_has_pedestrian();

        Collection<Non_living_entity> nonLivingEntities = new ArrayList<>();

        className = "Concrete_barrier";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getConcrete_barrier("1_" + className.toLowerCase()))
                .thenReturn((Concrete_barrier) m);
        nonLivingEntities.add((Non_living_entity)m);
        className = "Plastic_barrier";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getPlastic_barrier("1_" + className.toLowerCase()))
                .thenReturn((Plastic_barrier) m);
        nonLivingEntities.add((Non_living_entity)m);
        className = "Rock";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getRock("1_" + className.toLowerCase()))
                .thenReturn((Rock) m);
        nonLivingEntities.add((Non_living_entity)m);
        className = "Street_tidy";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getStreet_tidy("1_" + className.toLowerCase()))
                .thenReturn((Street_tidy) m);
        nonLivingEntities.add((Non_living_entity)m);
        className = "Pedestrian_crossing";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getPedestrian_crossing("1_" + className.toLowerCase()))
                .thenReturn((Pedestrian_crossing) m);
        nonLivingEntities.add((Non_living_entity)m);
        className = "Speed_bump";
        m = createWrappedIndividualMock("1_" + className, className);
        when(factory.getSpeed_bump("1_" + className.toLowerCase()))
                .thenReturn((Speed_bump) m);

        doReturn(nonLivingEntities).when(laneMock).getLane_has_object();
        doReturn(laneMock).when(factory).getLane(IRI_PREFIX + "1_lane_0");
        when(factory.getScenario(IRI_PREFIX + "1_scenario"))
                .thenReturn(scenarioMock);

        Lane laneMockLeft = createLaneMock("1_lane_left_1");
        doReturn(laneMockLeft).when(factory).getLane(IRI_PREFIX + "1_lane_left_1");
        Lane laneMockRight = createLaneMock("1_lane_right_1");
        doReturn(laneMockRight).when(factory).getLane(IRI_PREFIX + "1_lane_right_1");
        return factory;
    }

}
