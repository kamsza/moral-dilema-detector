from common.carla_snapshot import CarlaSnapshot
import Ice
import adapter_ice
from common.strings import *
import scenario_describer.ScenarioGeneratror as sg
import common.objects as obj


def set_entity_fields(proxy, entity: obj.Entity):
    proxy.setLane(entity.get_lane_id())
    proxy.setDistance(entity.get_distance())
    proxy.setAccelerationX(entity.get_acceleration_x())
    proxy.setAccelerationY(entity.get_acceleration_y())
    #proxy.setLength(entity.get_length())
    #proxy.setWidth(entity.get_width())
    proxy.setSpeedX(entity.get_speed_x())
    proxy.setSpeedX(entity.get_speed_y())


def set_road_fields(proxy, road: obj.Road):
    proxy.setStartAngle(road.get_start_angle())
    proxy.setEndAngle(road.get_end_angle())
    proxy.setStarts(road.get_start_point().get_id())
    proxy.setEnds(road.get_end_point().get_id())
    proxy.setRoadAttributes(road.get_attributes().get_id())


def set_lane_fields(proxy, lane: obj.Lane):
    proxy.setWidth(lane.get_width())
    proxy.setLeftSideBoundary(lane.get_left_side_boundary().get_id())
    proxy.setRightSideBoundary(lane.get_right_side_boundary().get_id())
    proxy.setRoad(lane.get_road().get_id())


class ScenarioBuilder:
    __ids = {
        KEY_SCENARIO: [],
        KEY_VEHICLE: [],
        KEY_CYCLIST: [],
        KEY_PEDESTRIAN: [],
        KEY_LANE: [],
        KEY_ROAD: [],
        KEY_DELIMITER: [],
        KEY_JUNCTION: [],
        KEY_LANEBOUNDARY: [],
        KEY_ROADATTRIBUTE: [],
        KEY_ROADPOINT: []
    }

    __proxies_by_ids = {}

    def __init__(self, proxy, connector, snapshot: CarlaSnapshot):
        self.__communicator = connector.get_communicator()
        self.__managerPrx = connector.get_manager()
        # self.__proxy = proxy

        self.scPrx = self.__create_scenario()
        self.__create_delimiter(snapshot.get_delimiters())
        self.__create_junction(snapshot.get_junctions())
        self.__create_lane_boundary(snapshot.get_laneboundary())
        self.__create_road_attributes(snapshot.get_road_attributes())
        #self.__create_road_point(snapshot.get_road_points())
        self.__create_road(snapshot.get_roads())
        self.__create_lane(snapshot.get_lanes())

        self.__create_vehicles(snapshot.get_vehicles())
        self.__create_cyclist(snapshot.get_cyclists())
        self.__create_pedestrian(snapshot.get_pedestrians())
        self.__managerPrx.persist()
        print("ontology persisted")

    def __create_scenario(self):
        scenario = self.__managerPrx.create(adapter_ice.ItemType.SCENARIO)
        basePrx = self.__communicator.stringToProxy(sg.getInternetAddress(scenario))
        scenarioPrx = adapter_ice.ScenarioPrx.checkedCast(basePrx)
        self.__ids[KEY_SCENARIO].append(scenario)
        self.__proxies_by_ids[scenario] = scenarioPrx
        return scenarioPrx

    def __create_vehicles(self, vehicles: list[obj.Vehicle]):
        self.__create_entity(vehicles, adapter_ice.ItemType.VEHICLE, adapter_ice.VehiclePrx, KEY_VEHICLE)

    def __create_cyclist(self, cyclists: list[obj.Cyclist]):
        self.__create_entity(cyclists, adapter_ice.ItemType.CYCLIST, adapter_ice.CyclistPrx, KEY_CYCLIST)

    def __create_pedestrian(self, pedestrians: list[obj.Pedestrian]):
        self.__create_entity(pedestrians, adapter_ice.ItemType.PEDESTRIAN, adapter_ice.PedestrianPrx, KEY_PEDESTRIAN)

    def __create_lane(self, lanes: list[obj.Lane]):
        for lane in lanes:
            lane_id = self.__managerPrx.create(adapter_ice.ItemType.LANE)
            lane.set_id(lane_id)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(lane_id))
            extended_proxy = adapter_ice.LanePrx.checkedCast(base)

            set_lane_fields(extended_proxy, lane)

            self.__ids[KEY_LANE].append(lane_id)
            self.__proxies_by_ids[lane_id] = extended_proxy

    def __create_road(self, roads: list[obj.Road]):
        for road in roads:
            road_id = self.__managerPrx.create(adapter_ice.ItemType.ROAD)
            road.set_id(road_id)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(road_id))
            extended_proxy = adapter_ice.RoadPrx.checkedCast(base)

            set_road_fields(extended_proxy, road)

            self.__ids[KEY_ROAD].append(road_id)
            self.__proxies_by_ids[road_id] = extended_proxy

    def __create_delimiter(self, delimiters: list[obj.Delimiter]):
        self.__create_road_point(delimiters, adapter_ice.ItemType.DELIMITER, adapter_ice.DelimiterPrx, KEY_DELIMITER)

    def __create_junction(self, junctions: list[obj.Junction]):
        self.__create_road_point(junctions, adapter_ice.ItemType.JUNCTION, adapter_ice.JunctionPrx, KEY_JUNCTION)

    def __create_entity(self, entities: list[obj.Entity], item_type, proxy_object,
                        ids_key):
        for entity in entities:
            entity_id = self.__managerPrx.create(item_type)
            entity.set_id(entity_id)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(entity_id))
            extended_proxy = proxy_object.checkedCast(base)

            set_entity_fields(extended_proxy, entity)

            self.__ids[ids_key].append(entity_id)
            self.__proxies_by_ids[entity_id] = extended_proxy
            if ids_key == KEY_VEHICLE:
                self.scPrx.addVehicle(entity_id)
            elif ids_key == KEY_PEDESTRIAN:
                self.scPrx.addPedestrian(entity_id)
            elif ids_key == KEY_CYCLIST:
                self.scPrx.addCyclist(entity_id)
            else:
                pass

    def __create_road_point(self, roadpoints: list[obj.RoadPoint], item_type,
                            proxy_object, ids_key):
        for roadpoint in roadpoints:
            rp_id = self.__managerPrx.create(item_type)
            roadpoint.set_id(rp_id)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(rp_id))
            extended_proxy = proxy_object.checkedCast(base)

            extended_proxy.setLatitude(roadpoint.get_latitude())
            extended_proxy.setLongitude(roadpoint.get_longitude())

            self.__ids[ids_key].append(rp_id)
            self.__proxies_by_ids[rp_id] = extended_proxy

    def __create_road_attributes(self, attributes: list[obj.RoadAttributes]):
        for attribute in attributes:
            at_id = self.__managerPrx.create(adapter_ice.ItemType.ROADATTRIBUTES)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(at_id))
            extended_proxy: adapter_ice.RoadAttributesPrx = adapter_ice.RoadAttributesPrx.checkedCast(base)
            attribute.set_id(at_id)

            extended_proxy.setMotorway(attribute.is_motorway())
            extended_proxy.setMotorway(attribute.is_urban())
            extended_proxy.setMotorway(attribute.is_service_area())
            extended_proxy.setMotorway(attribute.is_controlled_access())
            extended_proxy.setMotorway(attribute.is_toll())
            extended_proxy.setMotorway(attribute.is_bridge())
            extended_proxy.setMotorway(attribute.is_tunnel())
            extended_proxy.setMotorway(attribute.is_ferry())

            self.__ids[KEY_ROADATTRIBUTE].append(at_id)
            self.__proxies_by_ids[at_id] = extended_proxy

    def __create_lane_boundary(self, boundaries: list[obj.LaneBoundary]):
        for boundary in boundaries:
            bd_id = self.__managerPrx.create(adapter_ice.ItemType.LANEBOUNDARY)
            boundary.set_id(bd_id)
            base = self.__communicator.stringToProxy(sg.getInternetAddress(bd_id))
            extended_proxy: adapter_ice.LaneBoundaryPrx = adapter_ice.LaneBoundaryPrx.checkedCast(base)

            extended_proxy.setType(boundary.get_type())
            extended_proxy.setColor(boundary.get_color())
            extended_proxy.setMaterial(boundary.get_material())

            self.__ids[KEY_LANEBOUNDARY].append(bd_id)
            self.__proxies_by_ids[bd_id] = extended_proxy

    def get_ids(self):
        return self.__ids

    def get_proxies(self):
        return self.__proxies_by_ids
