import common.objects as obj
from common.strings import *


class CarlaSnapshot:
    def __init__(self, objects):
        self.__vehicles: list[obj.Vehicle] = objects[KEY_VEHICLE]
        self.__cyclists: list[obj.Cyclist] = objects[KEY_CYCLIST]
        self.__pedestrians: list[obj.Pedestrian] = objects[KEY_PEDESTRIAN]
        self.__lanes: list[obj.Lane] = objects[KEY_LANE]
        self.__roads: list[obj.Road] = objects[KEY_ROAD]
        self.__delimiters: list[obj.Delimiter] = objects[KEY_DELIMITER]
        self.__junctions: list[obj.Junction] = objects[KEY_JUNCTION]
        self.__road_attributes: list[obj.RoadAttributes] = objects[KEY_ROADATTRIBUTE]
        self.__road_points: list[obj.RoadPoint] = objects[KEY_ROADPOINT]
        self.__laneboundary: list[obj.LaneBoundary] = objects[KEY_LANEBOUNDARY]

    def get_vehicles(self) -> list[obj.Vehicle]:
        return self.__vehicles

    def get_cyclists(self) -> list[obj.Cyclist]:
        return self.__cyclists

    def get_pedestrians(self) -> list[obj.Pedestrian]:
        return self.__pedestrians

    def get_lanes(self) -> list[obj.Lane]:
        return self.__lanes

    def get_roads(self) -> list[obj.Road]:
        return self.__roads

    def get_delimiters(self) -> list[obj.Delimiter]:
        return self.__delimiters

    def get_junctions(self) -> list[obj.Junction]:
        return self.__junctions

    def get_road_attributes(self) -> list[obj.RoadAttributes]:
        return self.__road_attributes

    def get_road_points(self) -> list[obj.RoadPoint]:
        return self.__road_points

    def get_laneboundary(self) -> list[obj.LaneBoundary]:
        return self.__laneboundary
