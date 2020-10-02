
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
    JUNCTION
  };

  interface BaseItem
  {
    string getId();
    void setId(string id);
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

  interface Lane extends BaseItem
  {
    int getWidth();
    void setWidth(int width);
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
    float getStartAngle();
  };

  interface RoadPoint extends BaseItem
  {

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
