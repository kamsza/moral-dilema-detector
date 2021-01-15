import carla_reader.CarlaConnector as Connector
import carla_reader.Providers as Providers
from common.strings import *
from carla_reader.Providers import EntitiesProvider
from common.carla_snapshot import CarlaSnapshot


class CarlaReader:
    def __init__(self):
        self.__client = Connector.CarlaConnector().get_client()

        # self.__waypoints = self.__client.get_world().get_map().generate_waypoints(10)

        self.__world = self.__client.get_world()
        self.__entity_provider = EntitiesProvider(self.__world)
        #self.__waypoints = self.__map.generate_waypoints(10)

        provided_vals = []
        for provider in Providers.get_providers(self.__world):
            objs = provider.provide()
            print(objs)
            provided_vals.append(objs)

        self.__map_objects = [x[1] for x in provided_vals if x[0] is ATOM_MAP][0]
        self.__entity_objects = [x[1] for x in provided_vals if x[0] is ATOM_ENTITY][0]

        self.__initial_snapshot = CarlaSnapshot(self.__map_objects | self.__entity_objects)
        self.__current_snapshot = self.__initial_snapshot

    def update_actors(self):
        self.__current_snapshot = CarlaSnapshot(self.__entity_provider.provide()[1] | self.__map_objects)

    def get_snapshot(self):
        return self.__current_snapshot
