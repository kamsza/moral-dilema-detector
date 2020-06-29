package generator;

import project.Animal;
import project.MyFactory;

import java.lang.reflect.InvocationTargetException;

public class AnimalOnRoadSG extends BaseScenarioGenerator {

    public AnimalOnRoadSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Model model = super.generate();

        Animal animal = subclassGenerator.generateAnimalSubclass(ObjectNamer.getName("animal"));

        model.getVehicle().addHas_in_the_front(animal);

        return model;
    }
}