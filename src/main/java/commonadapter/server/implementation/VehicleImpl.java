package commonadapter.server.implementation;

import adapter.Vehicle;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class VehicleImpl extends EntityImpl implements Vehicle {

    private project.Vehicle vehicle;

    public VehicleImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        super.entity = this.vehicle = owlFactory.createVehicle(id);
    }


}
