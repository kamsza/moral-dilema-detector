package DilemmaDetector.Simulator;

import DilemmaDetector.GeneratedClassesMocks;
import generator.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.*;

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
    private Lane laneMock;

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
        laneMock = generatedClassesMocks.createLaneMock("lane1");
        TreeMap<Integer, Lane> centerLinesMapMock = new TreeMap<>();
        centerLinesMapMock.put(0, laneMock);
        laneMapMock.put(Model.Side.CENTER, centerLinesMapMock);
        when(modelMock.getLanes()).thenReturn(laneMapMock);
    }

    @Test
    public void MapperGenerateRigidBodyForMainVehicle(){
        Vehicle entity = createEntityMock("Entity_1", "Vehicle");
        RigidBody rigidBody = RigidBodyMapper.rigidBodyForMainVehicle(entity);
        Assert.assertEquals(PhysicsUtils.Kmph2ToMps2(mockAccX), rigidBody.getAcceleration().x, 0.01);
        Assert.assertEquals(PhysicsUtils.Kmph2ToMps2(mockAccY), rigidBody.getAcceleration().y, 0.01);
        Assert.assertEquals(mockSpeedX/3.6, rigidBody.getSpeed().x, 0.1);
        Assert.assertEquals(mockSpeedY/3.6, rigidBody.getSpeed().y, 0.1);
        Assert.assertEquals(mockWidth/100, rigidBody.getWidth(), 0.1);
        Assert.assertEquals(mockLength/100, rigidBody.getLength(), 0.1);
        Assert.assertEquals(Vector2.zero(), rigidBody.getPosition());
    }

    @Test
    public void MapperCreatesVehicleActor(){
        Map<Lane, ArrayList<Vehicle>> vehicleMapMock = new HashMap<>();
        Vehicle vehicle = createEntityMock("vehicle1", "Vehicle");
        ArrayList<Vehicle> arrayListVehicle = new ArrayList<>();
        arrayListVehicle.add(vehicle);
        vehicleMapMock.put(laneMock, arrayListVehicle);
        when(modelMock.getVehicles()).thenReturn(vehicleMapMock);

        List<Actor> actors = RigidBodyMapper.createActors(factoryWrapperMock, modelMock);

        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("vehicle1")));
        Assert.assertEquals(1, actors.size());
    }

    @Test
    public void MapperCreatesLivingEntityActor(){
        Map<Lane, ArrayList<Living_entity>> livingEntityMapMock = new HashMap<>();
        Living_entity livingEntity = createEntityMock("person_1", "Living_entity");
        ArrayList<Living_entity> arrayListLivingEntity = new ArrayList<>();
        arrayListLivingEntity.add(livingEntity);
        livingEntityMapMock.put(laneMock, arrayListLivingEntity);
        when(modelMock.getEntities()).thenReturn(livingEntityMapMock);

        List<Actor> actors = RigidBodyMapper.createActors(factoryWrapperMock, modelMock);

        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("person_1")));
        Assert.assertEquals(1, actors.size());
    }

    @Test
    public void MapperCreatesObjectActor(){
        Map<Lane, ArrayList<Non_living_entity>> entityMapMock = new HashMap<>();
        Non_living_entity entity = createEntityMock("stone_1", "Non_living_entity");
        ArrayList<Non_living_entity> arrayListEntity = new ArrayList<>();
        arrayListEntity.add(entity);
        entityMapMock.put(laneMock, arrayListEntity);
        when(modelMock.getObjects()).thenReturn(entityMapMock);

        List<Actor> actors = RigidBodyMapper.createActors(factoryWrapperMock, modelMock);

        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("stone_1")));
        Assert.assertEquals(1, actors.size());
    }

    @Test
    public void MapperCreatesSurroundingObjects(){
        Map<Model.Side, ArrayList<Surrounding>> surroundingMapMock = new HashMap<>();

        Surrounding surrounding = createEntityMock("surrounding_1", "Surrounding");
        ArrayList<Surrounding> surroundingList = new ArrayList<>();
        surroundingList.add(surrounding);
        surroundingMapMock.put(Model.Side.LEFT, surroundingList);

        Surrounding surrounding2 = createEntityMock("surrounding_2", "Surrounding");
        ArrayList<Surrounding> surroundingList2 = new ArrayList<>();
        surroundingList2.add(surrounding2);
        surroundingMapMock.put(Model.Side.RIGHT, surroundingList2);
        when(modelMock.getSurrounding()).thenReturn(surroundingMapMock);

        List<Actor> actors = RigidBodyMapper.createSurroundingActors(modelMock);

        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("surrounding_1")));
        Assert.assertTrue(actors.stream().anyMatch((a)->a.getEntityName().equals("surrounding_2")));
        Assert.assertEquals(2, actors.size());
    }
}
