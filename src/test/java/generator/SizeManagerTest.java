package generator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import org.junit.Test;
import project.Car;

public class SizeManagerTest {
    SizeManager sizeManager = new SizeManager();

    @Test
    public void testForNonExistingObjectName() {
        assertEquals(sizeManager.getLength("xX"), 0F, 0);
        assertEquals(sizeManager.getWidth("xX"), 0F,0);
    }

    @Test
    public void testForExistingObjectName() {
        assertTrue(sizeManager.getLength("vehicle") > 0);
        assertThat(sizeManager.getLength("vehicle"), instanceOf(Float.class));

        assertTrue(sizeManager.getWidth("vehicle") > 0);
        assertThat(sizeManager.getWidth("vehicle"), instanceOf(Float.class));
    }

}
