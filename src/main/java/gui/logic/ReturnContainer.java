package gui.logic;

import DilemmaDetector.Consequences.IConsequenceContainer;
import DilemmaDetector.Simulator.Actor;
import project.Decision;

import java.util.Map;
import java.util.Set;

public class ReturnContainer {
    private Map<Decision, Set<Actor>> collidedEntities;
    private String pictureName;
    private IConsequenceContainer consequenceContainer;

    public IConsequenceContainer getConsequenceContainer() {
        return consequenceContainer;
    }

    public ReturnContainer(Map<Decision, Set<Actor>> collidedEntities, String pictureName,
                           IConsequenceContainer consequenceContainer) {
        this.collidedEntities = collidedEntities;
        this.pictureName = pictureName;
        this.consequenceContainer = consequenceContainer;
    }

    public Map<Decision, Set<Actor>> getCollidedEntities() {
        return collidedEntities;
    }

    public String getPictureName() {
        return pictureName;
    }
}
