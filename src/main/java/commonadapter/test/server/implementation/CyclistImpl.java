package commonadapter.test.server.implementation;

import adapter.Cyclist;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class CyclistImpl extends EntityImpl implements Cyclist {

    public CyclistImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }
}
