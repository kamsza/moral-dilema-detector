package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Test;

public class Vector2Test {
    @Test
    public void zeroVectorTest() {
        Assert.assertEquals(Vector2.zero(), new Vector2(0,0));
    }

    @Test
    public void magnitudeTest(){
        Vector2 vector = new Vector2(-3, 4);
        Assert.assertEquals(vector.getMagnitude(), 5, 0);
    }

    @Test
    public void normalizedVectorTest(){
        Vector2 vector = new Vector2(-3, 4);
        Vector2 normalizedVector = new Vector2(-3.0/5, 4.0/5);
        Assert.assertEquals(vector.getNormalized(), normalizedVector);
    }

    @Test
    public void normalizedZeroVectorTest(){
        Vector2 zeroVector = Vector2.zero();
        Assert.assertEquals(zeroVector.getNormalized(), zeroVector);
    }

    @Test
    public void addVectorsTest(){
        Vector2 vector1 = new Vector2(-100, 50);
        Vector2 vector2 = new Vector2(50, -100);
        Vector2 addedVectors = new Vector2(-50, -50);
        vector1.add(vector2);
        Assert.assertEquals(vector1, addedVectors);
    }

    @Test
    public void subVectorsTest(){
        Vector2 vector1 = new Vector2(-100, 50);
        Vector2 vector2 = new Vector2(50, -100);
        Vector2 subVectors = new Vector2(-150, 150);
        vector1.sub(vector2);
        Assert.assertEquals(vector1, subVectors);
    }

    @Test
    public void mulVectorsTest(){
        Vector2 vector = new Vector2(-10, 5);
        double multiplier = -3.0;
        Vector2 mulVector = new Vector2(30, -15);
        vector.mul(multiplier);
        Assert.assertEquals(vector, mulVector);
    }
}
