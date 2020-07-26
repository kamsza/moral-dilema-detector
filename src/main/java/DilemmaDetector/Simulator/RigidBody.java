package DilemmaDetector.Simulator;

import java.util.Objects;

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


    public void update(double deltaTime) {
        updatePosition(deltaTime);
        updateSpeed(deltaTime);
    };

    private void updatePosition(double deltaTime) {
        //s = v0*t + at^2/2
        speed.mul(deltaTime).add(new Vector2(acceleration).mul(deltaTime * deltaTime).mul(0.5));
    };

    private void updateSpeed(double deltaTime) {
        speed.add(new Vector2(acceleration).mul(deltaTime));
    };

    @Override
    public int hashCode() {
        return Objects.hash(position, speed, acceleration);
    }
}
