package generator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObjectNamerTest {
    @Test
    public void testScenarioName() {
        ObjectNamer.reset();
        assertEquals("scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("1_scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("2_scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("3_scenario", ObjectNamer.getName("scenario")) ;
        ObjectNamer.setInitScenarioId(21);
        assertEquals("4_scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("5_scenario", ObjectNamer.getName("scenario")) ;
    }

    @Test
    public void testScenarioNameWithInitValue() {
        ObjectNamer.reset();
        ObjectNamer.setInitScenarioId(21);
        assertEquals("21_scenario", ObjectNamer.getName("scenario")) ;
    }

    @Test
    public void testIndividualsNames() {
        ObjectNamer.reset();
        assertEquals("scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("a", ObjectNamer.getName("a")) ;
        assertEquals("a_1", ObjectNamer.getName("a")) ;
        assertEquals("b", ObjectNamer.getName("b")) ;

        assertEquals("1_scenario", ObjectNamer.getName("scenario")) ;
        assertEquals("1_a", ObjectNamer.getName("a")) ;
        assertEquals("1_a_1", ObjectNamer.getName("a")) ;
        assertEquals("1_b", ObjectNamer.getName("b")) ;
    }
}
