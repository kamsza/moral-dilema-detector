package commonadapter.server.implementation;

import adapter.Road;
import adapter.RoadAttribute;
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

    @Override
    public void setEndAngle(float angle, com.zeroc.Ice.Current current) {
    }

    @Override
    public float getEndAngle(com.zeroc.Ice.Current current) {
        return 0;
    }

    @Override
    public void setRoadAttributes(RoadAttribute[] roadAttributes, com.zeroc.Ice.Current current) {

    }

    @Override
    public RoadAttribute[] getRoadAttributes(com.zeroc.Ice.Current current) {
        return new RoadAttribute[0];
    }

    @Override
    public void setAverageSpeed(int speed, com.zeroc.Ice.Current current) {

    }

    @Override
    public int getAverageSpeed(com.zeroc.Ice.Current current) {
        return 0;
    }

    @Override
    public void setSpeedLimit(int speed, com.zeroc.Ice.Current current) {

    }

    @Override
    public int getSpeedLimit(com.zeroc.Ice.Current current) {
        return 0;
    }

    @Override
    public String getId(com.zeroc.Ice.Current current) {
        return null;
    }
}
