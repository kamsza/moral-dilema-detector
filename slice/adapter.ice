
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

  enum LaneBoundary {
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

  sequence<RoadAttribute> RoadAttributes;

  sequence<int> RoadIds;

  interface BaseItem
  {
    string getId();
  };

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
    float getStartAngle();
    void setEndAngle(float angle);
    float getEndAngle();
    void setRoadAttributes(RoadAttributes roadAttributes);
    RoadAttributes getRoadAttributes();
    void setAverageSpeed(int speed);
    int getAverageSpeed();
    void setSpeedLimit(int speed);
    int getSpeedLimit();
  };

  interface Lane extends BaseItem
  {
    int getWidth();
    void setWidth(int width);
    void setOpenToCurbSide(bool isOpen);
    bool getOpenToCurbSide();
    void setOpenToMiddleSide(bool isOpen);
    bool getOpenToMiddleSide();
    void setLaneBoundary(LaneBoundary laneBoundary);
    LaneBoundary getLaneBoundary();
  };

  interface RoadPoint extends BaseItem
  {
    void setX(int x);
    int getX();
    void setY(int y);
    int getY();
  };

  interface Junction extends RoadPoint
  {
    void setRoadIds(RoadIds roadIds);
    RoadIds getRoadsIds();
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
