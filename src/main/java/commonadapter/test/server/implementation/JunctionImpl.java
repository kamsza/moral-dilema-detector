package commonadapter.test.server.implementation;

import adapter.Junction;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class JunctionImpl extends RoadPointImpl implements Junction {

    public JunctionImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
