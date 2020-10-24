package commonadapter.server.implementation;

import adapter.Delimiter;
import project.MyFactory;

public class DelimiterImpl extends RoadPointImpl implements Delimiter {

    private project.Delimiter delimiter;

    public DelimiterImpl(String id, MyFactory owlFactory) {
        super(id, owlFactory);
        super.roadPoint = this.delimiter = owlFactory.createDelimiter(id);
    }
}
