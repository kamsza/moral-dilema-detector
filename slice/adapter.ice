
#ifndef ICE
#define ICE

module adapter
{
  interface Scenario
  {
    string getName();
  };

  interface Entity
  {

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

  interface Lane
  {

  };

  interface Road
  {

  };

  interface RoadPoint
  {

  };

  interface Junction extends RoadPoint
  {

  };

  interface ScenarioFactory
  {
    void createScenario(string name);
  };

};

#endif
