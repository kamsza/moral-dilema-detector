package DilemmaDetector.Simulator;

import generator.Model;
import project.*;
import project.impl.DefaultDecision;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SimulatorEngine {

    //3 seconds lasts each period when we check moves of all entities in model
    private static final double MOVING_TIME = 3.0;
    //each TIME_PART we check if there is a collision between main vehicle and some different entity
    private static final double TIME_PART = 0.1;

    private Model model;

    private List<Actor> actors;

    private Actor mainVehicle;
    private CollisionDetector collisionDetector;

    public SimulatorEngine(Model model) {
        this.model = model;
        this.mainVehicle = new Actor(model.getVehicle(), RigidBodyMapper.rigidBodyForMainVehicle(model.getVehicle()));
        this.actors = RigidBodyMapper.createActors(model);
        collisionDetector = new CollisionDetector(mainVehicle, this.actors);
    }


    public void simulate(Decision decision)
    {
        double currentTime = 0;
        while (currentTime < MOVING_TIME) {
            currentTime += TIME_PART;
            // TODO wstrzykiwanie decyzji
            //  BasicActionsApplier.CarTurning(mainVehicle, Sunny.class, false);
            mainVehicle.getRigidBody().update(TIME_PART);
            for (Actor actor : actors) {
                actor.getRigidBody().update(TIME_PART);

            }

            if (!collisionDetector.detectCollisionInMoment().isEmpty()) {
                System.out.println("Collision in decision " + decision.toString());
                return;
            }
        }
    }

}
