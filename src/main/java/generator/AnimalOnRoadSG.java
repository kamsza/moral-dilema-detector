package generator;

import project.Animal;
import project.Lane;
import project.MyFactory;

import java.lang.reflect.InvocationTargetException;

public class AnimalOnRoadSG extends BaseScenarioGenerator {

    public AnimalOnRoadSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        // model
        Model model = super.generate();

        // create objects
        Animal animal = subclassGenerator.generateAnimalSubclass(ObjectNamer.getName("animal"));

        // add to ontology
        model.getVehicle().addHas_in_the_front(animal);

        // add to model
        Lane lane  = model.getLanes().get(Model.Side.CENTER).get(0);
        model.getEntities().get(lane).add(animal);

        return model;
    }
}