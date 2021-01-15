from abc import ABC
from common.strings import *
import random
from carla_reader.Creators import create_entity, MapCreator


def get_providers(world):
    return [MapProvider(world), EntitiesProvider(world)]


class AbstractProvider(ABC):
    main_vehicle_bp = None
    transform = None

    wasInit = False

    lanes = None

    def __init__(self, world):
        self.__world = world
        if not AbstractProvider.wasInit:
            blueprint_library = world.get_blueprint_library()
            main_vehicle_bp = random.choice(blueprint_library.filter(DEFAULT_CARLA_VEHICLE))
            AbstractProvider.transform = random.choice(world.get_map().get_spawn_points())
            try:
                world.spawn_actor(main_vehicle_bp, AbstractProvider.transform)
            except RuntimeError:
                print("Collision detected")
                self.__init__(self.__world)
        AbstractProvider.wasInit = True

    def provide(self):
        pass

    def keys(self):
        pass


class MapProvider(AbstractProvider):
    map_precision = 0.02
    max_distance = 50

    def __init__(self, world):
        super().__init__(world)
        self.__map = world.get_map()
        self.__waypoints = self.__map.generate_waypoints(self.map_precision)
        main_way_point = self.__map.get_waypoint(AbstractProvider.transform.location)
        self.__map_creator = MapCreator(self.__map, main_way_point, self.max_distance)

    def provide(self):
        return (ATOM_MAP, {
            KEY_ROAD: self.__get_roads(),
            KEY_ROADATTRIBUTE: self.__get_roadattributes(),
            KEY_JUNCTION: self.__get_junctions(),
            KEY_DELIMITER: self.__get_delimiters(),
            KEY_LANE: self.__get_lanes(),
            KEY_LANEBOUNDARY: self.__map_creator.boundaries,
            KEY_ROADPOINT: self.__map_creator.road_points
        })

    def keys(self):
        return [
            KEY_ROAD,
            KEY_ROADATTRIBUTE,
            KEY_JUNCTION,
            KEY_DELIMITER,
            KEY_LANE,
            KEY_LANEBOUNDARY,
            KEY_ROADPOINT
        ]

    def __get_roads(self):
        return self.__map_creator.roads

    def __get_roadattributes(self):
        return self.__map_creator.road_attributes

    def __get_junctions(self):
        return self.__map_creator.road_points

    def __get_delimiters(self):
        return self.__map_creator.road_points

    def __get_lanes(self):
        lanes = self.__map_creator.lanes
        AbstractProvider.lanes = lanes
        return lanes


class EntitiesProvider(AbstractProvider):
    def __init__(self, world):
        super().__init__(world)
        self.__actors = world.get_actors()

    def provide(self):
        return (ATOM_ENTITY, {
            KEY_VEHICLE: self.__get_vehicles(),
            KEY_PEDESTRIAN: self.__get_pedestrians(),
            KEY_CYCLIST: self.__get_pedestrians()
        })

    def keys(self):
        return [
            KEY_VEHICLE,
            KEY_CYCLIST,
            KEY_PEDESTRIAN
        ]

    def __get_vehicles(self):
        vehicles = self.__actors.filter(DEFAULT_CARLA_VEHICLE)
        return [create_entity(x, KEY_VEHICLE, AbstractProvider.transform.location, AbstractProvider.lanes)
                for x in vehicles if self.__is_valid_vehicle(x)]

    def __get_cyclists(self):
        cyclists = self.__actors.filter(DEFAULT_CARLA_CYCLIST)
        return [create_entity(x, KEY_CYCLIST, AbstractProvider.transform.location, AbstractProvider.lanes)
                for x in cyclists if self.__is_valid_cyclist(x)]

    def __get_pedestrians(self):
        pedestrians = self.__actors.filter(DEFAULT_CARLA_PEDESTRIAN)
        return [create_entity(x, KEY_PEDESTRIAN, AbstractProvider.transform.location, AbstractProvider.lanes)
                for x in pedestrians if self.__is_valid_pedestrian(x)]

    @staticmethod
    def __is_valid_vehicle(e):
        return True

    @staticmethod
    def __is_valid_pedestrian(e):
        return True

    @staticmethod
    def __is_valid_cyclist(e):
        return True
