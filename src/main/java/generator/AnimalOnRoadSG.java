package generator;

import project.Animal;
import project.MyFactory;

public class AnimalOnRoadSG extends BaseScenarioGenerator {

    public AnimalOnRoadSG(MyFactory factory, String baseIRI) {
        super(factory, baseIRI);
    }

    @Override
    public Model generate() {
        Model model = super.generate();

        Animal animal = factory.createAnimalSubclass(ObjectNamer.getName("animal"));

        model.getVehicle().addHas_in_the_front(animal);

        return model;
    }
}