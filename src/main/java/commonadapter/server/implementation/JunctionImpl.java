package commonadapter.server.implementation;

import adapter.Junction;
import project.MyFactory;

public class JunctionImpl extends RoadPointImpl implements Junction {

    private project.Junction junction;

    public JunctionImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        super.roadPoint = this.junction = owlFactory.createJunction(id);
    }

    @Override
    public void setRoadIds(int[] roadIds, com.zeroc.Ice.Current current) {

    }

    @Override
    public int[] getRoadsIds(com.zeroc.Ice.Current current) {
        return new int[0];
    }

    @Override
    public void setX(int x, com.zeroc.Ice.Current current) {

    }

    @Override
    public int getX(com.zeroc.Ice.Current current) {
        return 0;
    }

    @Override
    public void setY(int y, com.zeroc.Ice.Current current) {

    }

    @Override
    public int getY(com.zeroc.Ice.Current current) {
        return 0;
    }

    @Override
    public String getId(com.zeroc.Ice.Current current) {
        return null;
    }
}
