package DilemmaDetector.Simulator;

import java.util.Objects;

public class Vector2{
    public double x;
    public double y;
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 other){
        this.x = other.x;
        this.y = other.y;
    }

    public static Vector2 zero(){
        return new Vector2(0,0);
    }

    public double getMagnitude(){
        return Math.sqrt(x*x + y*y);
    }

    public Vector2 getNormalized(){
        double m = this.getMagnitude();
        if(m != 0)
            return new Vector2(x/m, y/m);
        else
            return Vector2.zero();
    }

    public Vector2 add(Vector2 other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 sub(Vector2 other){
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }


    public Vector2 mul(double other){
        this.x *= other;
        this.y *= other;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2){
            return ((Vector2)obj).x == this.x &&
                    ((Vector2)obj).y == this.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
