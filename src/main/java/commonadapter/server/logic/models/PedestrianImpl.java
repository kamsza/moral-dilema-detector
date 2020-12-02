package commonadapter.server.logic.models;

import adapter.Pedestrian;
import commonadapter.server.logic.models.EntityImpl;
import project.MyFactory;

public class PedestrianImpl extends EntityImpl implements Pedestrian {

    private project.Pedestrian pedestrian;

    public PedestrianImpl(String id, project.Pedestrian ontoPedestrian, MyFactory owlFactory) {
        super(id, owlFactory);
        super.entity = this.pedestrian = ontoPedestrian;
    }


}
