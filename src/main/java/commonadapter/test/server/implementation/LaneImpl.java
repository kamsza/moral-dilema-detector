package commonadapter.test.server.implementation;

import adapter.Lane;
import com.zeroc.Ice.Current;

public class LaneImpl extends BaseItemImpl implements Lane {

    @Override
    public int getWidth(Current current) {
        return 0;
    }

    @Override
    public void setWidth(int width, Current current) {

    }
}
