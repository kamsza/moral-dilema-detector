package generator;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import project.*;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.*;

public class DecisionGeneratorTest {
    private Map<Model.Side, TreeMap<Integer, Lane>> getExampleLaneMap(int leftLanes, int rightLanes){
        Map<Model.Side, TreeMap<Integer, Lane>> lanesMap = new HashMap<>();
        TreeMap<Integer, Lane> leftTreeMap = new TreeMap<>();
        for(int i=1; i<=leftLanes; i++) {
            leftTreeMap.put(i, null);
        }
        TreeMap<Integer, Lane> rightTreeMap = new TreeMap<>();
        for(int i=1; i<=rightLanes; i++) {
            rightTreeMap.put(i, null);
        }
        TreeMap<Integer, Lane> centerTreeMap = new TreeMap<>();
        centerTreeMap.put(0, null);
        lanesMap.put(Model.Side.LEFT, leftTreeMap);
        lanesMap.put(Model.Side.RIGHT, rightTreeMap);
        lanesMap.put(Model.Side.CENTER, centerTreeMap);
        return lanesMap;
    }

    @Test
    public void generateActionByDecisionMapTest() {
        String baseIRI = "anything";
        MyFactory factoryMock = mock(MyFactory.class);
        Change_lane changeLaneMock = mock(Change_lane.class);
        Model modelMock = mock(Model.class, Mockito.RETURNS_DEEP_STUBS);
        Scenario scenarioMock = mock(Scenario.class);

        doNothing().when(changeLaneMock).addLane_change_by(anyInt());
        when(factoryMock.createDecision(anyString())).thenAnswer((Answer<Decision>) invocationOnMock -> mock(Decision.class));
        when(factoryMock.createFollow(anyString())).thenReturn(mock(Follow.class));
        when(factoryMock.createStop(anyString())).thenReturn(mock(Stop.class));
        when(factoryMock.createTurn_left(anyString())).thenReturn(mock(Turn_left.class));
        when(factoryMock.createTurn_right(anyString())).thenReturn(mock(Turn_right.class));
        when(factoryMock.createChange_lane(anyString())).thenReturn(changeLaneMock);
        doNothing().when(scenarioMock).addHas_decision(any());
        when(modelMock.getScenario()).thenReturn(scenarioMock);
        doNothing().when(modelMock).setActionByDecision(anyMap());

        Map<Model.Side, TreeMap<Integer, Lane>> lanesMap = getExampleLaneMap(3, 2);
        when(modelMock.getMainRoad().getLanes()).thenReturn(lanesMap);

        DecisionGenerator decisionGenerator = new DecisionGenerator(factoryMock, baseIRI);
        decisionGenerator.generate(modelMock);
        Map<Decision, Action> actionByDecision = decisionGenerator.getActionByDecision();

        List<Class> expectedActionClasses = List.of(Follow.class, Stop.class, Turn_right.class, Turn_left.class,
                Change_lane.class, Change_lane.class, Change_lane.class, Change_lane.class, Change_lane.class);
        // iterate over expectedActionClasses and remove 1 matching entry from map
        // that way we make sure we have the right amount of the right type of action
        for(Class actionClass : expectedActionClasses){
            for(Iterator<Map.Entry<Decision, Action>> it = actionByDecision.entrySet().iterator(); it.hasNext();){
                Map.Entry<Decision, Action> entry = it.next();
                if(actionClass.isInstance(entry.getValue())) {
                    it.remove();
                    break;
                }
            }
        }
        Assert.assertEquals(0, actionByDecision.size());
    }
}
