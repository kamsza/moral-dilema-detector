package commonadapter.server.implementation;

import adapter.Vehicle;
import project.MyFactory;

public class VehicleImpl extends EntityImpl implements Vehicle {


    public VehicleImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
