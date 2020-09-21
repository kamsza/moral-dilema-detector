package commonadapter.test.server.implementation;

import adapter.Pedestrian;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    public PedestrianImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
