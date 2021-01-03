package DilemmaDetector.Simulator;

import DilemmaDetector.GeneratedClassesMocks;
import com.google.common.collect.Lists;
import generator.Model;
import org.junit.Assert;
import org.junit.Before;
import org.kie.internal.runtime.beliefs.Mode;
import project.*;
import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class RigidBodyMapperTest {
    GeneratedClassesMocks generatedClassesMocks = new GeneratedClassesMocks();
    private final float mockAccX = 100;
    private final float mockAccY = 200;
    private final float mockSpeedX = 300;
    private final float mockSpeedY = 400;
    private final float mockWidth = 500;
    private final float mockLength = 600;
    private final float mockDist = 700;
    private final float mockDistToRoad = 800;
    private final float mockValue = 900;

    private FactoryWrapper factoryWrapperMock;
    private Model modelMock;

    public <T extends Entity> T createEntityMock(String name, String className){
        T entity = generatedClassesMocks.createWrappedIndividualMock(name, className);
        Collection<Float> values;

        when(entity.hasAccelerationX()).thenReturn(true);
        values = Collections.singletonList(mockAccX);
        doReturn(values).when(entity).getAccelerationX();

        when(entity.hasAccelerationY()).thenReturn(true);
        values = Collections.singletonList(mockAccY);
        doReturn(values).when(entity).getAccelerationY();

        when(entity.hasSpeedX()).thenReturn(true);
        values = Collections.singletonList(mockSpeedX);
        doReturn(values).when(entity).getSpeedX();

        when(entity.hasSpeedY()).thenReturn(true);
        values = Collections.singletonList(mockSpeedY);
        doReturn(values).when(entity).getSpeedY();

        when(entity.hasWidth()).thenReturn(true);
        values = Collections.singletonList(mockWidth);
        doReturn(values).when(entity).getWidth();

        when(entity.hasLength()).thenReturn(true);
        values = Collections.singletonList(mockLength);
        doReturn(values).when(entity).getLength();

        when(entity.hasDistance()).thenReturn(true);
        values = Collections.singletonList(mockDist);
        doReturn(values).when(entity).getDistance();

        when(entity.hasDistanceToRoad()).thenReturn(true);
        values = Collections.singletonList(mockDistToRoad);
        doReturn(values).when(entity).getDistanceToRoad();

        when(entity.hasValueInDollars()).thenReturn(true);
        values = Collections.singletonList(mockValue);
        doReturn(values).when(entity).getValueInDollars();
        return entity;
    }

    @Before
    public void Init(){
        factoryWrapperMock = mock(FactoryWrapper.class);
        modelMock = mock(Model.class);

        Map<Model.Side, TreeMap<Integer, Lane>> laneMapMock = new HashMap<>();
        Lane lane0 = generatedClassesMocks.createLaneMock("lane1");
        TreeMap<Integer, Lane> centerLinesMapMock = new TreeMap<>();
        centerLinesMapMock.put(0, lane0);
        laneMapMock.put(Model.Side.CENTER, centerLinesMapMock);
        when(modelMock.getLanes()).thenReturn(laneMapMock);

        Map<Lane, ArrayList<Vehicle>> vehicleMapMock = new HashMap<>();
        Vehicle vehicle = createEntityMock("vehicle1", "Vehicle");
        ArrayList<Vehicle> arrayListVehicle = new ArrayList<>();
        arrayListVehicle.add(vehicle);
        vehicleMapMock.put(lane0, arrayListVehicle);
        when(modelMock.getVehicles()).thenReturn(vehicleMapMock);

        Map<Lane, ArrayList<Living_entity>> livingEntityMapMock = new HashMap<>();
        Living_entity livingEntity = createEntityMock("person_1", "Living_entity");
        ArrayList<Living_entity> arrayListLivingEntity = new ArrayList<>();
        arrayListLivingEntity.add(livingEntity);
        livingEntityMapMock.put(lane0, arrayListLivingEntity);
        when(modelMock.getEntities()).thenReturn(livingEntityMapMock);

        Map<Lane, ArrayList<Non_living_entity>> entityMapMock = new HashMap<>();
        Non_living_entity entity = createEntityMock("stone_1", "Non_living_entity");
        ArrayList<Non_living_entity> arrayListEntity = new ArrayList<>();
        arrayListEntity.add(entity);
        entityMapMock.put(lane0, arrayListEntity);
        when(modelMock.getObjects()).thenReturn(entityMapMock);

    }

    @Test
    public void MapperGenerateRigidBodyForMainVehicle(){
        Vehicle entity = createEntityMock("Entity_1", "Vehicle");
        RigidBody rigidBody = RigidBodyMapper.rigidBodyForMainVehicle(entity);
        Assert.assertEquals(mockAccX*3.6*3600, rigidBody.getAcceleration().x, 0.01);
        Assert.assertEquals(mockAccY*3.6*3600, rigidBody.getAcceleration().y, 0.01);
        Assert.assertEquals(mockSpeedX/3.6, rigidBody.getSpeed().x, 0.1);
        Assert.assertEquals(mockSpeedY/3.6, rigidBody.getSpeed().y, 0.1);
        Assert.assertEquals(mockWidth/100, rigidBody.getWidth(), 0.1);
        Assert.assertEquals(mockLength/100, rigidBody.getLength(), 0.1);
        Assert.assertEquals(Vector2.zero(), rigidBody.getPosition());
    }

    @Test
    public void MapperCreatesActors(){
        List<Actor> actors = RigidBodyMapper.createActors(factoryWrapperMock, modelMock);
        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("vehicle1")));
        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("person_1")));
        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("stone_1")));

        Assert.assertEquals(3, actors.size());
    }
}
