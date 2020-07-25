package DilemmaDetector.Simulator;

public class RigidBody {
    private Vector2 position = null;
    private Vector2 speed = null;
    private Vector2 acceleration = null;

    private Double width = 0.0;
    private Double length = 0.0;


    public void addAcceleration(Vector2 acceleration) {
        this.acceleration.add(acceleration);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setSpeed(Vector2 speed) {
        this.speed = speed;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public void setLength(Double length) {
        this.length = length;
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

    public Double getWidth() {
        return width;
    }

    public Double getLength() {
        return length;
    }


    public void update() {};

    private void updatePosition() { };

    private void updateSpeed() {};

}
