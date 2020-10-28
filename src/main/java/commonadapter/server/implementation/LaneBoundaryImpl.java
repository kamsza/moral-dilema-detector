package commonadapter.server.implementation;

import adapter.LaneBoundary;
import com.zeroc.Ice.Current;
import project.MyFactory;

public class LaneBoundaryImpl extends BaseItemImpl implements LaneBoundary {

    private project.Lane_boundary laneBoundary;

    public LaneBoundaryImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        this.laneBoundary = owlFactory.createLane_boundary(id);
    }

    @Override
    public void setType(String type, Current current) {

        this.laneBoundary.addType(type);
    }

    @Override
    public void setColor(String color, Current current) {

        this.laneBoundary.addColor(color);
    }

    @Override
    public void setMaterial(String material, Current current) {

        this.laneBoundary.addMaterial(material);
    }
}
