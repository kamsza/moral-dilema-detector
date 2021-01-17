package generator;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProbRandTest {
    @Test
    public void testProbEquals1() {
        assertEquals(1, ProbRand.randInt(new int[]{1}, new double[]{1.0}));
    }

    @Test
    public void testProbEquals0() {
        assertEquals(0, ProbRand.randInt(new int[]{1}, new double[]{0.0}));
    }

//    @Test
//    public void testRandomElement() {
//        assertTrue(IntStream.of(1,2,3).anyMatch(x -> x == ProbRand.randInt(new int[]{1,2,3}, new double[]{0.6, 0.3, 0.1})));
//    }
}
