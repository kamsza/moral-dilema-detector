import math
import unittest
from unittest.mock import MagicMock

import carla

from carla_reader import Creators
from carla_reader.Creators import MapCreator, create_entity

from common import objects
from common.objects import Lane, Cyclist, Vehicle, Pedestrian
from common.strings import KEY_CYCLIST, KEY_VEHICLE, KEY_PEDESTRIAN


class MyTestCase(unittest.TestCase):
    def test_carla_labe_boundary_test(self):
        # Given
        # Lane with two boundaries
        map_creator = MapCreator
        map_creator.boundary_id = MagicMock(return_value=1)
        way_point = carla.Waypoint
        bl = carla.LaneMarking
        bl.type = MagicMock(return_value="BOTTSDOTS")
        rl = carla.LaneMarking
        rl.type = MagicMock(return_value="BOTTSDOTS")
        way_point.left_lane_marking = MagicMock(return_value=bl)
        way_point.right_lane_marking = MagicMock(return_value=rl)

        # When boundary creation is performed
        left_b = map_creator.create_lane_boundary(self=map_creator, way_point=way_point, right=False)
        right_b = map_creator.create_lane_boundary(self=map_creator, way_point=way_point, right=True)

        # Correct values are set
        self.assertEqual(left_b.get_material(), "UNKNOWN")
        self.assertEqual(left_b.get_type(), "OTHER")
        self.assertEqual(right_b.get_type(), "OTHER")
        self.assertEqual(right_b.get_material(), "UNKNOWN")

    def test_angle1(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=0, y=2, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 90 deg
        self.assertLessEqual(math.fabs(math.pi/2-res), 10e-7)

    def test_angle2(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=0, y=0, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 0 deg
        self.assertLessEqual(math.fabs(res), 10e-7)

    def test_angle3(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=2, y=2, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 45 deg
        self.assertLessEqual(math.fabs(math.pi/4 - res), 10e-7)

    def test_angle4(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=2, y=-2, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 135 deg
        print(res)
        self.assertLessEqual(math.fabs(3*math.pi/4 - res), 10e-7)

    def test_angle5(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=-2, y=-2, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 225 deg
        print(res)
        self.assertLessEqual(math.fabs(5*math.pi/4 - res), 10e-7)

    def test_angle5(self):
        # Given
        # Map creator and location
        map_creator = MapCreator
        loc = carla.Location(x=-2, y=2, z=0)

        # When
        # angle is calculated
        res = map_creator.get_angle_of_location(map_creator, loc)

        # It is 315 deg
        print(res)
        self.assertLessEqual(math.fabs(7*math.pi/4 - res), 10e-7)

    def test_road_attributes(self):
        # Given waypoint with value that implicits controlled access and urban
        map_creator = MapCreator
        way_point = carla.Waypoint
        way_point.lane_type = carla.LaneType.RoadWorks
        # When road attributes are created
        res = map_creator.create_road_attributes(map_creator, way_point)
        # Then road is controlled access, is not service area and is urban
        self.assertEqual(res.is_controlled_access(), True)
        self.assertEqual(res.is_service_area(), False)
        self.assertEqual(res.is_urban(), True)

    def test_way_point_to_road_point(self):
        # Given map creator and waypoint with positive latitude and longitude that is junction
        map_creator = MapCreator
        way_point = carla.Waypoint
        way_point.is_junction = True
        transform = carla.Location
        map = carla.Map
        geoLoc = carla.GeoLocation(latitude=1, longitude=2, altitude=0)
        map.transform_to_geolocation = MagicMock(return_value=geoLoc)
        map_creator.map = map
        transform.location = carla.Location(x=0, y=0, z=0)
        way_point.transform = transform

        # When way-point is converted to geolocation
        res = map_creator.way_point_to_road_point(map_creator, way_point)

        # Then roadpoint is junction and geolocation is NW
        self.assertEqual(res.__class__, objects.Junction)
        self.assertEqual(res.get_latitude()[-1], 'N')
        self.assertEqual(res.get_longitude()[-1], 'W')

    def test_way_point_to_road_point(self):
        # Given map creator and waypoint with negative latitude and longitude that is junction
        map_creator = MapCreator
        way_point = carla.Waypoint
        way_point.is_junction = False
        transform = carla.Location
        map = carla.Map
        geoLoc = carla.GeoLocation(latitude=-1, longitude=-2, altitude=0)
        map.transform_to_geolocation = MagicMock(return_value=geoLoc)
        map_creator.map = map
        transform.location = carla.Location(x=0, y=0, z=0)
        way_point.transform = transform

        # When way-point is converted to geolocation
        res = map_creator.way_point_to_road_point(map_creator, way_point)

        # Then roadpoint is base RoadPoint and geolocation is SE
        self.assertEqual(res.__class__, objects.RoadPoint)
        self.assertEqual(res.get_latitude()[-1], 'S')
        self.assertEqual(res.get_longitude()[-1], 'E')

    def test_create_entity1(self):
        # Given carla actor that is cyclist
        data = carla.Actor
        entity_type = KEY_CYCLIST
        velocity = carla.Vector3D(x=1, y=2, z=0)
        acceleration = carla.Vector3D(x=3, y=4, z=0)
        data.get_velocity = MagicMock(return_value=velocity)
        data.get_acceleration = MagicMock(return_value=acceleration)
        data.id = "ID_1"
        data.get_location = MagicMock(return_value=carla.Location(x=3, y=4, z=0))
        main_vehicle_location = carla.Location(x=0, y=0, z=0)
        lane = Lane
        lane.get_id = "LANE_ID"
        lanes = [lane]

        # When entity is created
        entity = create_entity(data, entity_type, main_vehicle_location, lanes)

        # Then entity class is Cyclist object and fields contain same values as in carla object
        self.assertEqual(entity.__class__, Cyclist)
        self.assertEqual(entity.get_speed_x(), data.get_velocity().x)
        self.assertEqual(entity.get_speed_y(), data.get_velocity().y)
        self.assertEqual(entity.get_acceleration_x(), data.get_acceleration().x)
        self.assertEqual(entity.get_acceleration_y(), data.get_acceleration().y)
        self.assertEqual(entity.get_distance(), 5)

    def test_create_entity2(self):
        # Given carla actor that is Vehicle
        data = carla.Actor
        entity_type = KEY_VEHICLE
        velocity = carla.Vector3D(x=1, y=2, z=0)
        acceleration = carla.Vector3D(x=3, y=4, z=0)
        data.get_velocity = MagicMock(return_value=velocity)
        data.get_acceleration = MagicMock(return_value=acceleration)
        data.id = "ID_1"
        data.get_location = MagicMock(return_value=carla.Location(x=3, y=4, z=0))
        main_vehicle_location = carla.Location(x=0, y=0, z=0)
        lane = Lane
        lane.get_id = "LANE_ID"
        lanes = [lane]

        # When entity is created
        entity = create_entity(data, entity_type, main_vehicle_location, lanes)

        # Then entity class is Vehicle object and fields contain same values as in carla object
        self.assertEqual(entity.__class__, Vehicle)
        self.assertEqual(entity.get_speed_x(), data.get_velocity().x)
        self.assertEqual(entity.get_speed_y(), data.get_velocity().y)
        self.assertEqual(entity.get_acceleration_x(), data.get_acceleration().x)
        self.assertEqual(entity.get_acceleration_y(), data.get_acceleration().y)
        self.assertEqual(entity.get_distance(), 5)

    def test_create_entity1(self):
        # Given carla actor that is Pedestrian
        data = carla.Actor
        entity_type = KEY_PEDESTRIAN
        velocity = carla.Vector3D(x=1, y=2, z=0)
        acceleration = carla.Vector3D(x=3, y=4, z=0)
        data.get_velocity = MagicMock(return_value=velocity)
        data.get_acceleration = MagicMock(return_value=acceleration)
        data.id = "ID_1"
        data.get_location = MagicMock(return_value=carla.Location(x=3, y=4, z=0))
        main_vehicle_location = carla.Location(x=0, y=0, z=0)
        lane = Lane
        lane.get_id = "LANE_ID"
        lanes = [lane]

        # When entity is created
        entity = create_entity(data, entity_type, main_vehicle_location, lanes)

        # Then entity class is Pedestrian object and fields contain same values as in carla object
        self.assertEqual(entity.__class__, Pedestrian)
        self.assertEqual(entity.get_speed_x(), data.get_velocity().x)
        self.assertEqual(entity.get_speed_y(), data.get_velocity().y)
        self.assertEqual(entity.get_acceleration_x(), data.get_acceleration().x)
        self.assertEqual(entity.get_acceleration_y(), data.get_acceleration().y)
        self.assertEqual(entity.get_distance(), 5)


if __name__ == '__main__':
    unittest.main()
