package commonadapter.server.implementation;

import adapter.Junction;
import project.MyFactory;

public class JunctionImpl extends RoadPointImpl implements Junction {

    public JunctionImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
