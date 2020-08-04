package commonadapter.interfaces;


import java.util.Collection;

public interface Scenario {

    Collection<Pedestrian> getPedestrians();

    Collection<Cyclist> getCyclists();

    Collection<Vehicle> getVehicles();


    void addPedestrian(Pedestrian pedestrian);

    void addCyclist(Cyclist cyclist);

    void addVehicle(Vehicle vehicle);

    // possibly more addSomething-methods in the future
}
