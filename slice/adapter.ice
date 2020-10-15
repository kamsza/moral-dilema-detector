
#ifndef ICE
#define ICE

module adapter
{
  enum ItemType
  {
    SCENARIO,
    ROAD,
    LANE,
    JUNCTION,
    DELIMITER,
    VEHICLE,
    CYCLIST,
    PEDESTRIAN
  };

  enum RoadAttribute {
    FERRY,
    TUNNEL,
    BRIDGE,
    TOLL,
    CONTROLLEDACCES,
    SERVICEAREA,
    URBAN,
    MOTORWAY
  };

  enum BoundaryType {
    LONGDASHEDLINE,
    SHORTDASHEDLINE,
    DASHEDBLOCKS,
    DOUBLESOLIDLINE,
    SINGLESOLIDLINE,
    SOLIDLINEDASHEDLINE,
    DASHEDLINESOLIDLINE,
    BARRIERJERSEY,
    BARRIERSOUND
  };

  enum BoundaryColor {
    WHITE,
    YELLOW
  };

  enum BoundaryMaterial {
    PAINTING,
    CONCRETE
  };

  interface BaseItem
  {
    string getId();
  };

  interface LaneBoundary extends BaseItem
  {
    void setType(BoundaryType type);
    void setColor(BoundaryColor color);
    void setMaterial(BoundaryMaterial material);
  };

  sequence<RoadAttribute> RoadAttributes;

  sequence<LaneBoundary> LaneBoundaries;

  sequence<int> RoadIds;

  interface Scenario extends BaseItem
  {
    void addVehicle(string vehicleId);
    void addCyclist(string cyclistId);
    void addPedestrian(string pedestrianId);
    void addLane(string laneId);
    void addRoad(string roadId);
    void addRoadPoint(string roadPointId);
    void addJunction(string junctionId);
  };

  interface Road extends BaseItem
  {
    void setStartAngle(float angle);
    void setEndAngle(float angle);
    void setRoadAttributes(RoadAttributes roadAttributes);
    void setAverageSpeed(int speed);
    void setSpeedLimit(int speed);
  };

  interface Lane extends BaseItem
  {
    void setWidth(int width);
    void setCurbSideBoundary(LaneBoundaries laneBoundary);
    void setMiddleSideBoundary(LaneBoundaries laneBoundary);
  };

  interface RoadPoint extends BaseItem
  {
    void setLongitude(int x);
    void setLatitude(int y);
  };

  interface Junction extends RoadPoint
  {
    void setRoadIds(RoadIds roadIds);
  };

  interface Delimiter extends RoadPoint
  {

  };

  interface Entity extends BaseItem
  {
    void setLane(string laneId);
    void setDistance(float distance);
    void setAccelerationX(float accelerationX);
    void setAccelerationY(float accelerationY);
    void setLength(float length);
    void setWidth(float width);
    void setSpeedX(float speedX);
    void setSpeedY(float speedY);
  };

  interface Pedestrian extends Entity
  {

  };

  interface Vehicle extends Entity
  {

  };

  interface Cyclist extends Entity
  {

  };

  interface Manager
  {
    string create(ItemType type);
    void persist();
  };

};

#endif
