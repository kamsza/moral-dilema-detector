package commonadapter.server.implementation;

import adapter.Pedestrian;
import project.MyFactory;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    public PedestrianImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
