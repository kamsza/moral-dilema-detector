package commonadapter.server.implementation;

import adapter.Pedestrian;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    private project.Pedestrian pedestrian;

    public PedestrianImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        super.entity = this.pedestrian = owlFactory.createPedestrian(id);
    }


}
