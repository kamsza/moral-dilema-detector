
#ifndef ICE
#define ICE

module adapter
{
  enum ItemType
  {
    SCENARIO,
    VEHICLE,
    CYCLIST,
    PEDESTRIAN,
    LANE,
    ROAD,
    DELIMITER,
    JUNCTION,
    LANEBOUNDARY,
    ROADATTRIBUTES
  };

  interface BaseItem
  {
    string getId();

  };

  interface Scenario extends BaseItem
  {
    void addVehicle(string vehicleId);
    void addCyclist(string cyclistId);
    void addPedestrian(string pedestrianId);
  };

  interface Lane extends BaseItem
  {
    void setWidth(int width);
    void setLeftSideBoundary(string boundaryId);
    void setRightSideBoundary(string boundaryId);
    void setRoad(string roadId);
    void setLaneNumber(int laneNumber);
  };

  interface LaneBoundary extends BaseItem
  {
    void setType(string type);
    void setColor(string color);
    void setMaterial(string material);
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

  interface Road extends BaseItem
  {
    void setStartAngle(float angle);
    void setEndAngle(float angle);
    void setStarts(string roadPointId);
    void setEnds(string roadPointId);
    void setRoadAttributes(string roadAttributesId);
    void setAverageSpeed(int averageSpeed);
    void setSpeedLimit(int speedLimit);
  };

  interface RoadAttributes extends BaseItem
  {
    void setMotorway(bool isMotorway);
    void setUrban(bool isUrban);
    void setServiceArea(bool isServiceArea);
    void setControlledAccess(bool isControlledAccess);
    void setToll(bool isToll);
    void setBridge(bool isBridge);
    void setTunnel(bool isTunnel);
    void setFerry(bool isFerry);
  };

  interface RoadPoint extends BaseItem
  {
    void setLatitude(string lat);
    void setLongitude(string lon);
  };

  interface Junction extends RoadPoint
  {

  };

  interface Delimiter extends RoadPoint
  {

  };

  interface Manager
  {
    string create(ItemType type);

    void persist();
  };

};

#endif
