package DilemmaDetector.Simulator;

public class Vector2{
    public double x;
    public double y;
    public Vector2(double x, double y){
        this.x = x;
        this.y = y;
    }

    static public Vector2 zero = new Vector2(0,0);

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
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }


    public Vector2 mul(double other){
        this.x *= other;
        this.y *= other;
        return this;
    }
}
