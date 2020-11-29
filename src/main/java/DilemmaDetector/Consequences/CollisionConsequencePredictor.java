package DilemmaDetector.Consequences;

import DilemmaDetector.Simulator.Actor;
import DilemmaDetector.Simulator.FactoryWrapper;
import DilemmaDetector.Simulator.PhysicsUtils;
import DilemmaDetector.Simulator.RigidBody;
import project.Decision;
import project.Living_entity;

import java.util.List;

public class CollisionConsequencePredictor {
    private IConsequenceContainer consequenceContainer;
    private FactoryWrapper factoryWrapper;

    public CollisionConsequencePredictor(IConsequenceContainer consequenceContainer) {
        this.consequenceContainer = consequenceContainer;
        try{
            this.factoryWrapper = new FactoryWrapper();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public CollisionConsequencePredictor(IConsequenceContainer consequenceContainer, FactoryWrapper factoryWrapper) {
        this.consequenceContainer = consequenceContainer;
        this.factoryWrapper = factoryWrapper;
    }

    public void createCollisionConsequences(Decision decision, Actor victimActor, Actor other) {
        List<Living_entity> individualVictims = factoryWrapper.getLivingEntitiesFromActor(victimActor);
        double speed = getCollisionSpeed(victimActor.getRigidBody(), other.getRigidBody());
        double materialConsequenceValue = getMaterialConsequence(victimActor, speed);
        for (Living_entity victim : individualVictims) {
            ConsequenceType consequenceType;
            if (factoryWrapper.isPedestrian(victimActor)) {
                consequenceType = getHealthConsequenceTypeForPedestrian(
                        getCollisionSpeed(victimActor.getRigidBody(), other.getRigidBody()));
            } else {
                consequenceType = getHealthConsequenceType(
                        getCollisionSpeed(victimActor.getRigidBody(), other.getRigidBody()));
            }
            if (consequenceType != ConsequenceType.NO_CONSEQUENCE) {
                consequenceContainer.addHealthConsequence(decision, victim, consequenceType);
            }
        }
        consequenceContainer.addMaterialConsequence(decision, victimActor.getEntityName(), materialConsequenceValue);
    }

    public void createCollisionConsequences(Decision decision, Actor victimActor) {
        List<Living_entity> individualVictims = factoryWrapper.getLivingEntitiesFromActor(victimActor);

        double speed = victimActor.getRigidBody().getSpeed().getMagnitude();
        double materialConsequenceValue = getMaterialConsequence(victimActor, speed);

        for (Living_entity victim : individualVictims) {
            ConsequenceType consequenceType = getHealthConsequenceType(speed);
            if (consequenceType != ConsequenceType.NO_CONSEQUENCE) {
                consequenceContainer.addHealthConsequence(decision, victim, consequenceType);
            }
        }
        consequenceContainer.addMaterialConsequence(decision, victimActor.getEntityName(), materialConsequenceValue);
    }

    private double getCollisionSpeed(RigidBody victimRB, RigidBody otherRB) {
        return PhysicsUtils
                .GetRelativeSpeed(otherRB.getSpeed(), victimRB.getSpeed())
                .getMagnitude();
    }

    private int getMaterialConsequence(Actor victimActor, double speed){
        int criticalSpeed = 115; //speed when collision has fatal consequences
        double victimValue =  victimActor.getValueInDollars();
        if (speed >= criticalSpeed) {
            return (int) victimValue;
        }
        else{
            return (int) Math.round(victimValue * (speed / criticalSpeed));
        }
    }

    private ConsequenceType getHealthConsequenceTypeForPedestrian(double speedOfCollision) {
        double killProbability = pedestrianFatalInjuryProbability(speedOfCollision);
        double severInjuryProbability = pedestrianSeverInjuryProbability(speedOfCollision);
        double lightInjuryProbability = pedestrianMinorInjuryProbability(speedOfCollision);
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

    private double pedestrianFatalInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 35) {
            probability = getProbabilityFromLinearFunction(0, 0, 35, 0.1, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(35, 0.1, 50, 0.4, speed);
        } else if (speed < 65) {
            probability = getProbabilityFromLinearFunction(50, 0.4, 65, 0.9, speed);
        } else {
            probability = getProbabilityFromLinearFunction(65, 0.9, 80, 1, speed);
        }
        return probability;
    }

    private double pedestrianMinorInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 35) {
            probability = getProbabilityFromLinearFunction(0, 0, 35, 0.9, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(35, 0.9, 50, 0.6, speed);
        } else if (speed < 65) {
            probability = getProbabilityFromLinearFunction(50, 0.6, 65, 0.1, speed);
        } else {
            probability = getProbabilityFromLinearFunction(65, 0.6, 80, 0, speed);
        }
        return probability;
    }

    private double pedestrianSeverInjuryProbability(double speed) {
        speed = PhysicsUtils.MetersToKmph(speed);
        double probability;
        if (speed < 35) {
            probability = getProbabilityFromLinearFunction(0, 0, 35, 0.2, speed);
        } else if (speed < 50) {
            probability = getProbabilityFromLinearFunction(35, 0.2, 50, 0.7, speed);
        } else if (speed < 65) {
            probability = getProbabilityFromLinearFunction(50, 0.7, 65, 0.4, speed);
        } else {
            probability = getProbabilityFromLinearFunction(65, 0.4, 80, 0.1, speed);
        }
        return probability;
    }

    private double getProbabilityFromLinearFunction(double x1, double y1, double x2, double y2, double speed) {
        return (y2 - y1) / (x2 - x1) * (speed - x1) + y1;
    }
}
