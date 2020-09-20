
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
    ROADPOINT,
    JUNCTION
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
    void addLane(string laneId);
    void addRoad(string roadId);
    void addRoadPoint(string roadPointId);
    void addJunction(string junctionId);

    void persist();
  };

  interface Lane extends BaseItem
  {
    int getWidth();
    void setWidth(int width);
  };

  interface Entity extends BaseItem
  {
    void setLane(string laneId);
    string getLaneId();
    float getDistance();
    void setDistance(float distance);
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

  interface BaseFactory
  {
    string create(ItemType type);
  };


};

#endif
