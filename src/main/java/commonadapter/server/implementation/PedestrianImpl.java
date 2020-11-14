package commonadapter.server.implementation;

import adapter.Pedestrian;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    private project.Pedestrian pedestrian;

    public PedestrianImpl(String id, project.Pedestrian ontoPedestrian, MyFactory owlFactory) {
        super(id, owlFactory);
        super.entity = this.pedestrian = ontoPedestrian;
    }


}
