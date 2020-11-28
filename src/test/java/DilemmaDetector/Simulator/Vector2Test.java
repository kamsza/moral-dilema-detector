package DilemmaDetector.Simulator;

import org.junit.Assert;
import org.junit.Test;

public class Vector2Test {
    @Test
    public void zeroVectorTest() {
        Assert.assertEquals(new Vector2(0,0), Vector2.zero());
    }

    @Test
    public void magnitudeTest(){
        Vector2 vector = new Vector2(-3, 4);
        Assert.assertEquals(5, vector.getMagnitude(), 0);
    }

    @Test
    public void normalizedVectorTest(){
        Vector2 vector = new Vector2(-3, 4);
        Vector2 normalizedVector = new Vector2(-3.0/5, 4.0/5);
        Assert.assertEquals(normalizedVector, vector.getNormalized());
    }

    @Test
    public void normalizedZeroVectorTest(){
        Vector2 zeroVector = Vector2.zero();
        Assert.assertEquals(zeroVector, zeroVector.getNormalized());
    }

    @Test
    public void addVectorsTest(){
        Vector2 vector1 = new Vector2(-100, 50);
        Vector2 vector2 = new Vector2(50, -100);
        Vector2 addedVectors = new Vector2(-50, -50);
        vector1.add(vector2);
        Assert.assertEquals(addedVectors, vector1);
    }

    @Test
    public void addZeroToVectorTest(){
        Vector2 vector = new Vector2(-100, 50);
        Vector2 addedVectors = new Vector2(-100, 50);
        vector.add(Vector2.zero());
        Assert.assertEquals(addedVectors, vector);
    }

    @Test
    public void subVectorsTest(){
        Vector2 vector1 = new Vector2(-100, 50);
        Vector2 vector2 = new Vector2(50, -100);
        Vector2 subVectors = new Vector2(-150, 150);
        vector1.sub(vector2);
        Assert.assertEquals(subVectors, vector1);
    }

    @Test
    public void subZeroFromVectorTest(){
        Vector2 vector = new Vector2(-100, 50);
        Vector2 subVectors = new Vector2(-100, 50);
        vector.sub(Vector2.zero());
        Assert.assertEquals(subVectors, vector);
    }

    @Test
    public void mulVectorTest(){
        Vector2 vector = new Vector2(-10, 5);
        double multiplier = -3.0;
        Vector2 mulVector = new Vector2(30, -15);
        vector.mul(multiplier);
        Assert.assertEquals(mulVector, vector);
    }

    @Test
    public void mulByZeroVectorTest(){
        Vector2 vector = new Vector2(-10, 5);
        double multiplier = 0;
        vector.mul(multiplier);
        Assert.assertEquals(Vector2.zero(), vector);
    }
}
