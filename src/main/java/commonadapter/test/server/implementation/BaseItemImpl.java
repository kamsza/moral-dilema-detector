package commonadapter.test.server.implementation;

import adapter.BaseItem;
import com.zeroc.Ice.Current;

public abstract class BaseItemImpl implements BaseItem {
    @Override
    public String getId(Current current) {
        return current.id.category + "/" + current.id.name;
    }
}
