import math
from typing import List

import common.objects as obj
import carla
from common.strings import *


def __carla_velocity_to_speed(velocity):
    return obj.Speed(velocity.x, velocity.y)


def __carla_acceleration_to_acceleration(acceleration):
    return obj.Acceleration(acceleration.x, acceleration.y)


def create_entity(data, entity_type, main_vehicle_location, lanes=[]):
    speed = __carla_velocity_to_speed(data.get_velocity())
    acceleration = __carla_acceleration_to_acceleration(data.get_acceleration())
    identifier = str(data.id)
    distance = data.get_location().distance(main_vehicle_location)

    # problem z lane distance acceleration
    if entity_type is KEY_CYCLIST:
        return obj.Cyclist(identifier, get_entity_lane(data, lanes), distance, acceleration, 10, 10, speed)
    elif entity_type is KEY_VEHICLE:
        return obj.Vehicle(identifier, get_entity_lane(data, lanes), distance, acceleration, 10, 10, speed)
    elif entity_type is KEY_PEDESTRIAN:
        return obj.Pedestrian(identifier, get_entity_lane(data, lanes), distance, acceleration, 10, 10, speed)
    else:
        pass  # rzucić wyjątkiem


def get_entity_lane(data, lanes):
    lanes_list = list(lanes)
    return lanes_list[0]


class MapCreator:
    lanes = set()
    roads = set()
    boundaries = set()
    road_attributes = set()
    road_points = set()

    def __init__(self, _map, start_waypoint, distance):
        self.map = _map
        self.start_waypoint = start_waypoint
        self.distance = distance
        self.create_road(start_waypoint)

    def create_road_points(self, data, rp_type):
        pass

    road_id = 0

    def create_road(self, swp):
        print("generating road")
        w_points_main = self.start_waypoint.next_until_lane_end(self.distance)

        right_lanes_points = []
        print("generating right lanes")
        right = self.start_waypoint.get_right_lane()
        while right is not None:
            print("next right lane")
            right_lanes_points.append(right.next_until_lane_end(self.distance))
            right = right.get_right_lane()

        left_lanes_points = []
        print("generating left lanes")
        left = self.start_waypoint.get_left_lane()

        opposite_way = False
        while left is not None:
            if not opposite_way \
                    and left.get_left_lane() is not None \
                    and left.transform.rotation != left.get_left_lane().transform.rotation:
                print(left.transform.rotation)
                print(left.get_left_lane().transform.rotation)
                opposite_way = True

            print("next left lane")
            print(left)
            left_lanes_points.append(left.next_until_lane_end(self.distance))

            if opposite_way: left = left.get_right_lane()
            else: left = left.get_left_lane()

        attributes = self.create_road_attributes(swp)

        self.road_id += 1
        road_id_str = "ROAD_" + str(self.road_id)

        start_point = self.way_point_to_road_point(w_points_main[0])
        end_point = self.way_point_to_road_point(w_points_main[-1])
        road = obj.Road(
            self.get_angle_of_location(w_points_main[0].transform.location),
            self.get_angle_of_location(w_points_main[-1].transform.location),
            start_point,
            end_point,
            attributes,
            road_id_str
        )

        self.road_points.add(start_point)
        self.road_points.add(end_point)

        self.road_id += 1
        road_id_str = "ROAD_" + str(self.road_id)
        lanes = [self.create_lane(w_points_main, road)] \
                + [self.create_lane(x, road) for x in right_lanes_points] \
                + [self.create_lane(x, road) for x in left_lanes_points]

        self.roads.add(road)
        return road

    boundary_id = 0

    def create_lane_boundary(self, way_point, right):
        def open_drive_to_nds_boundary_type(odt):
            open_drive_typ = repr(odt)
            if open_drive_typ == "Broken":
                return "LONG_DASHED_LINE"
            elif open_drive_typ =="Solid":
                return "SINGLE_SOLID_LINE"
            elif open_drive_typ =="SolidSolid":
                return "DOUBLE_SOLID_LINE"
            elif open_drive_typ =="BrokenSolid":
                return "DASHED_LINE_SOLID_LINE"
            elif open_drive_typ =="SolidBroken":
                return "SOLID_LINE_DASHED_LINE"
            elif open_drive_typ =="BrokenBroken":
                return "DOUBLE_DASHED_LINE"
            elif open_drive_typ =="Crub":
                return "CRUB"
            elif open_drive_typ == "Grass":
                return "CURB_TRAVERSABLE"
            elif open_drive_typ == "NONE":
                return "NO_MARKING"
            else:
                return "OTHER"
        print("generating boundary")
        if right:
            #right_material_opt = way_point.get_right_lane()
            right_material = "UNKNOWN"
            self.boundary_id += 1
            return obj.LaneBoundary(
                open_drive_to_nds_boundary_type(way_point.right_lane_marking.type),
                str(way_point.right_lane_marking.color),
                right_material,
                "BOUNDARY_" + str(self.boundary_id)
            )
        else:
            #left_material_opt = way_point.get_right_lane()
            left_material = "UNKNOWN"
            self.boundary_id += 1
            return obj.LaneBoundary(
                open_drive_to_nds_boundary_type(way_point.left_lane_marking.type),
                str(way_point.left_lane_marking.color),
                left_material,
                "BOUNDARY_" + str(self.boundary_id)
            )

    atr_id = 0

    def create_road_attributes(self, main_way_point):
        print("generating atrs")
        self.atr_id += 1
        lane_type = main_way_point.lane_type
        controlled_access = False
        if lane_type is carla.LaneType.Restricted or lane_type is carla.LaneType.RoadWorks:
            controlled_access = True
        urban = False
        if lane_type in [
            carla.LaneType.Driving,
            carla.LaneType.Entry,
            carla.LaneType.RoadWorks,
            carla.LaneType.OffRamp,
            carla.LaneType.OnRamp,
            carla.LaneType.Exit,
            carla.LaneType.Bidirectional,
            carla.LaneType.NONE
        ]:
            urban = True

        atr = obj.RoadAttributes(
            "LATR_" + str(self.atr_id),
            False,
            urban,
            lane_type is carla.LaneType.Shoulder,
            controlled_access,
            False,
            False,
            False,
            False
        )
        self.road_attributes.add(atr)
        return atr

    lane_id = 0

    def create_lane(self, way_points, _road):
        print("generating lane")
        self.lane_id += 1
        lane_id_str = "LANE_" + str(self.lane_id)
        sample_wp = way_points[0]
        left_boundary = self.create_lane_boundary(sample_wp, False)
        right_boundary = self.create_lane_boundary(sample_wp, True)
        lane = obj.Lane(lane_id_str, sample_wp.lane_width, left_boundary, right_boundary, _road)
        self.lanes.add(lane)
        self.boundaries.add(left_boundary)
        self.boundaries.add(right_boundary)
        return lane

    def add_waypoints_as_roadpoints(self, wps):
        for wp in wps:
            rp = self.way_point_to_road_point(wp)
            self.road_points.add(rp)

    def way_point_to_road_point(self, wp):
        def latitude_convert_to_string(lat):
            if lat > 0:
                return str(lat) + 'N'
            else:
                return str(-lat) + 'S'

        def longitude_convert_to_string(long):
            if long > 0:
                return str(long) + 'W'
            else:
                return str(-long) + 'E'

        location = self.map.transform_to_geolocation(wp.transform.location)
        if wp.is_junction:
            return obj.Junction("",
                                latitude_convert_to_string(location.latitude),
                                longitude_convert_to_string(location.longitude)
                                )
        else:
            return obj.RoadPoint("",
                                 latitude_convert_to_string(location.latitude),
                                 longitude_convert_to_string(location.longitude)
                                 )

    def get_angle_of_location(self, location):
        # g_loc = self.__map.transform_to_geolocation(location)
        x = location.x
        y = location.y
        if x == 0 and y == 0: return 0
        if x < 0 and y >= 0: return math.acos(x/math.sqrt(x**2 + y**2)) + math.pi
        elif y >= 0: return math.acos(x/math.sqrt(x**2 + y**2))
        else: return math.asin(x/math.sqrt(x**2 + y**2)) + math.pi/2
