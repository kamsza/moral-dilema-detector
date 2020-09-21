package commonadapter.test.server.implementation;

import adapter.Road;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class RoadImpl extends BaseItemImpl implements Road {


    public RoadImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
    }

    @Override
    public void setStartAngle(float angle, Current current) {

    }

    @Override
    public float getStartAngle(Current current) {
        return 0;
    }

}
