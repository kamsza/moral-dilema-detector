package commonadapter.server.implementation;

import adapter.Delimiter;
import project.MyFactory;

public class DelimiterImpl extends RoadPointImpl implements Delimiter {

    private project.Delimiter delimiter;

    public DelimiterImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        super.roadPoint = this.delimiter = owlFactory.createDelimiter(id);
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
