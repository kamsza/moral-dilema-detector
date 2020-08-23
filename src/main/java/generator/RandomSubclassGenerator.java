package generator;

import org.protege.owl.codegeneration.CodeGenerationRuntimeException;
import org.protege.owl.codegeneration.impl.WrappedIndividualImpl;
import org.protege.owl.codegeneration.inference.CodeGenerationInference;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import project.*;
import project.impl.*;
import visualization.Visualization;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomSubclassGenerator {
    private final String iriPrefix = "http://webprotege.stanford.edu/";
    private MyFactory factory;
    private OWLOntology ontology;
    private OWLOntologyManager manager;
    private OWLReasoner reasoner;
    private OWLDataFactory dataFactory;
    private Random random;

    public RandomSubclassGenerator(MyFactory factory, OWLReasoner reasoner) {
        this.ontology = factory.getOwlOntology();
        this.factory = factory;
        this.reasoner = reasoner;
        manager = ontology.getOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        random = new Random();
    }

    public RandomSubclassGenerator(MyFactory factory) {
        this(factory, new StructuralReasonerFactory().createReasoner(factory.getOwlOntology()));
    }

    // default method for basic IRI
    public Vehicle generateVehicleSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateVehicleSubclass("vehicle");
    }

    // method for customized IRI
    public Vehicle generateVehicleSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultVehicle.class, Vocabulary.CLASS_VEHICLE, iriName);
    }

    public Weather generateWeatherSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateWeatherSubclass("weather");
    }

    public Weather generateWeatherSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultWeather.class, Vocabulary.CLASS_WEATHER, iriName);
    }

    public Animal generateAnimalSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateAnimalSubclass("animal");
    }

    public Animal generateAnimalSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultAnimal.class, Vocabulary.CLASS_ANIMAL, iriName);
    }

    public Time generateTimeSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateTimeSubclass("time");
    }

    public Time generateTimeSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultTime.class, Vocabulary.CLASS_TIME, iriName);
    }

    public Road_type generateRoadTypeSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateRoadTypeSubclass("road_type");
    }

    public Road_type generateRoadTypeSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultRoad_type.class, Vocabulary.CLASS_ROAD_TYPE, iriName);
    }

    public Surrounding generateSurroundingSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSurroundingSubclass("surrounding");
    }

    public Surrounding generateSurroundingSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultOn_the_side.class, Vocabulary.CLASS_ON_THE_SIDE, iriName);
    }

    public On_the_road generateSurroundingOnRoadSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSurroundingOnRoadSubclass("surrounding");
    }

    public On_the_road generateSurroundingOnRoadSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultOn_the_road.class, Vocabulary.CLASS_ON_THE_ROAD, iriName);
    }

    public On_the_lane generateSurroundingOnLaneSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSurroundingOnLaneSubclass("surrounding");
    }

    public On_the_lane generateSurroundingOnLaneSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultOn_the_lane.class, Vocabulary.CLASS_ON_THE_LANE, iriName);
    }

    public Passenger generatePassengerSubclass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generatePassengerSubclass("passenger");
    }

    public Passenger generatePassengerSubclass(String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return generateSubclass(DefaultPassenger.class, Vocabulary.CLASS_PASSENGER, iriName);
    }


    private <T extends WrappedIndividualImpl> T generateSubclass(Class<T> clazz, OWLClass type, String iriName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> classNames = getSubclassesNames(type);
        String randomSubclassName = classNames.get(random.nextInt(classNames.size()));

        List<String> availableMethods = Stream.of(factory.getClass().getDeclaredMethods())
                .filter(method -> method.getName().startsWith("create") &&
                        Set.of(method.getReturnType().getInterfaces()).contains(clazz.getInterfaces()[0]))
                .map(Method::getName)
                .collect(Collectors.toList());

        String methodName = "create" + randomSubclassName;
        if (!availableMethods.contains(methodName)) {
            methodName = availableMethods.get(0);
        }

        Method method = factory.getClass().getDeclaredMethod(methodName, String.class);
        // TODO change created instances name
        @SuppressWarnings("unchecked")
        T subclassInstance = (T) method.invoke(factory, iriName);
        return subclassInstance;
    }

    private List<String> getSubclassesNames(OWLClass baseClass) {
        Function<String, String> toCapitalLetter = s -> s.substring(0, 1).toUpperCase() + s.substring(1);
        Function<String, String> extractSimpleName = fullName -> fullName
                .substring(fullName.lastIndexOf("/") + 1, fullName.length() - 1);

        List<OWLClass> subClassList = new ArrayList<>(reasoner.getSubClasses(baseClass, true).getFlattened());
        return subClassList.stream()
                .map(OWLClass::toString)
                .map(extractSimpleName)
                .map(toCapitalLetter)
                .collect(Collectors.toList());
    }
}