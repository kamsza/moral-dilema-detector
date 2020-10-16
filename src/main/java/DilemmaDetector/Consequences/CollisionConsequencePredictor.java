package DilemmaDetector.Consequences;

import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.PhysicsUtils;
import DilemmaDetector.Simulator.RigidBody;
import generator.Model;
import project.Decision;
import project.Living_entity;
import project.MyFactory;
import project.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class CollisionConsequencePredictor {
    private IConsequenceContainer consequenceContainer;
    private MyFactory factory;
    private Model model;

    public CollisionConsequencePredictor(IConsequenceContainer consequenceContainer, MyFactory factory, Model model) {
        this.consequenceContainer = consequenceContainer;
        this.factory = factory;
        this.model = model;
    }

    public void createCollisionConsequences(Decision decision, Actor victimActor, Actor other) {
        List<Living_entity> individualVictims = getLivingEntitiesFromActor(victimActor);

        for (Living_entity victim : individualVictims) {
            ConsequenceType consequenceType = getHealthConsequenceType(
                    getCollisionSpeed(victimActor.getRigidBody(), other.getRigidBody()));
            if (consequenceType != ConsequenceType.NO_CONSEQUENCE) {
                System.out.println("ADD CONSEQUENCE");
                consequenceContainer.addHealthConsequence(decision, victim, consequenceType);
            }
        }
    }

    public void createCollisionConsequences(Decision decision, Actor victimActor) {
        List<Living_entity> individualVictims = getLivingEntitiesFromActor(victimActor);

        for (Living_entity victim : individualVictims) {
            ConsequenceType consequenceType = getHealthConsequenceType(
                    victimActor.getRigidBody().getSpeed().getMagnitude());
            if (consequenceType != ConsequenceType.NO_CONSEQUENCE) {
                System.out.println("ADD CONSEQ2");
                consequenceContainer.addHealthConsequence(decision, victim, consequenceType);
            }
        }
    }

    private double getCollisionSpeed(RigidBody victimRB, RigidBody otherRB) {
        return PhysicsUtils
                .GetRelativeSpeed(otherRB.getSpeed(), victimRB.getSpeed())
                .getMagnitude();
    }

    private ConsequenceType getHealthConsequenceType(double speedOfCollision) {
        double killProbability = fatalInjuryProbability(speedOfCollision);
        double severInjuryProbability = severInjuryProbability(speedOfCollision);
        double lightInjuryProbability = minorInjuryProbability(speedOfCollision);
        double maxProbability = killProbability;
        if (maxProbability < severInjuryProbability)
            maxProbability = severInjuryProbability;
        if (maxProbability < lightInjuryProbability)
            maxProbability = lightInjuryProbability;

        if (maxProbability == killProbability)
            return ConsequenceType.KILLED;
        if (maxProbability == severInjuryProbability)
            return ConsequenceType.SEVERELY_INJURED;
        if (maxProbability == lightInjuryProbability)
            return ConsequenceType.LIGHTLY_INJURED;

        return ConsequenceType.NO_CONSEQUENCE;
    }

    private List<Living_entity> getLivingEntitiesFromActor(Actor actor) {
        Vehicle vehicle = factory.getVehicle(actor.getEntity());
        Living_entity living_entity = factory.getLiving_entity(actor.getEntity());
        List<Living_entity> result = new ArrayList<>();

        if (vehicle != null) {
//            System.out.println("Get victims from vehicle");
            result.addAll(vehicle.getVehicle_has_passenger());
            result.addAll(vehicle.getVehicle_has_driver());
        } else if (living_entity != null) {
            result.add(living_entity);
        }

        return result;
    }

    private double minorInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 30) {
            probability = getProbabilityFromLinearFunction(0, 0, 30, 82.5, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(30, 82.5, 50, 75, speed);
        } else if (speed < 70) {
            probability = getProbabilityFromLinearFunction(50, 75, 70, 54.9, speed);
        } else if (speed < 90) {
            probability = getProbabilityFromLinearFunction(70, 54.9, 90, 32.3, speed);
        } else if (speed < 115) {
            probability = getProbabilityFromLinearFunction(90, 32.3, 115, 33.3, speed);
        } else {
            probability = getProbabilityFromLinearFunction(115, 33.3, speed, 33.3, speed);
        }
        return probability;
    }

    private double severInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 30) {
            probability = getProbabilityFromLinearFunction(0, 0, 30, 14.7, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(30, 14.7, 50, 21.9, speed);
        } else if (speed < 70) {
            probability = getProbabilityFromLinearFunction(50, 21.9, 70, 33.3, speed);
        } else if (speed < 90) {
            probability = getProbabilityFromLinearFunction(70, 33.3, 90, 30.6, speed);
        } else if (speed < 115) {
            probability = getProbabilityFromLinearFunction(90, 30.6, 115, 26.7, speed);
        } else {
            probability = getProbabilityFromLinearFunction(115, 26.7, speed, 26.7, speed);
        }
        return probability;
    }

    private double fatalInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 30) {
            probability = getProbabilityFromLinearFunction(0, 0, 30, 2.7, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(30, 2.7, 50, 3.1, speed);
        } else if (speed < 70) {
            probability = getProbabilityFromLinearFunction(50, 3.1, 70, 11.8, speed);
        } else if (speed < 90) {
            probability = getProbabilityFromLinearFunction(70, 11.8, 90, 37.1, speed);
        } else if (speed < 115) {
            probability = getProbabilityFromLinearFunction(90, 37.1, 115, 40.0, speed);
        } else {
            probability = getProbabilityFromLinearFunction(115, 40.0, speed, 40.0, speed);
        }
        return probability;
    }

    private double getProbabilityFromLinearFunction(double x1, double y1, double x2, double y2, double speed) {
        return (y2 - y1) / (x2 - x1) * (speed - x1) + y1;
    }
}
