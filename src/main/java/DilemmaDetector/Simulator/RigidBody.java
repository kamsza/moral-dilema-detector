package DilemmaDetector.Simulator;

public class RigidBody {
    private Vector2 position = null;
    private Vector2 speed = null;
    private Vector2 acceleration = null;

    public void addAcceleration(Vector2 acceleration){
        this.acceleration.add(acceleration);
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void update(){};
    private void updatePosition(){};
    private void updateSpeed(){};

}
