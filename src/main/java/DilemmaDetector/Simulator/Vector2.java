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
        return new Vector2(x/m, y/m);
    }

    public Vector2 add(Vector2 other){
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 sub(Vector2 other){
        Vector2 result = new Vector2(x, y);
        result.x -= other.x;
        this.y -= other.y;
        return result;
    }


    public Vector2 mul(double other){
        Vector2 result = new Vector2(x, y);
        result.x *= other;
        result.y *= other;
        return result;
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
}
