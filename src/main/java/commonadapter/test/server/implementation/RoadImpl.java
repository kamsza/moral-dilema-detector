package commonadapter.test.server.implementation;

import adapter.Road;
import com.zeroc.Ice.Current;

public class RoadImpl extends BaseItemImpl implements Road {
    @Override
    public void setStartAngle(float angle, Current current) {

    }

    @Override
    public float getStartAngle(Current current) {
        return 0;
    }

}
