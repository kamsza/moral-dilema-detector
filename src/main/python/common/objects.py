class BaseItem:
    def __init__(self, identifier):
        self.__identifier = identifier

    def get_id(self):
        return self.__identifier

    def set_id(self, identifier):
        self.__identifier = identifier


class Entity(BaseItem):
    def __init__(self,
                 identifier,
                 lane,
                 distance,
                 acceleration,
                 length,
                 width,
                 speed):
        super().__init__(identifier)
        self.__lane = lane
        self.__distance = distance
        self.__acceleration = acceleration
        self.__length = length
        self.__width = width
        self.__speed = speed

    def get_lane_id(self):
        return self.__lane.get_id()

    def get_distance(self):
        return self.__distance

    def get_acceleration_x(self):
        return self.__acceleration.get_x()

    def get_acceleration_y(self):
        return self.__acceleration.get_y()

    def get_length(self):
        return self.__length

    def get_width(self):
        return self.__width

    def get_speed_x(self):
        return self.__speed.get_x()

    def get_speed_y(self):
        return self.__speed.get_y()


class Lane(BaseItem):
    def __init__(self, identifier, width, left_boundary, right_boundary, road):
        super().__init__(identifier)
        self.__width = width
        self.__left_boundary = left_boundary
        self.__right_boundary = right_boundary
        self.__road = road

    def get_width(self):
        return self.__width

    def set_width(self, width):
        self.__width = width

    def get_left_side_boundary(self):
        return self.__left_boundary

    def get_right_side_boundary(self):
        return self.__right_boundary

    def get_road(self):
        return self.__road


class LaneBoundary(BaseItem):
    def __init__(self, _type, color, material, identifier):
        super().__init__(identifier)
        self.__type = str(_type)
        self.__color = color
        self.__material = material

    def get_type(self):
        return self.__type

    def get_color(self):
        return self.__color

    def get_material(self):
        return self.__material


class RoadAttributes(BaseItem):
    def __init__(self, identifier, motorway, urban, service_area, controlled_access, toll, bridge, tunnel, ferry):
        super().__init__(identifier)
        self.__motorway = motorway
        self.__urban = urban
        self.__service_area = service_area
        self.__controlled_access = controlled_access
        self.__toll = toll
        self.__bridge = bridge
        self.__tunnel = tunnel
        self.__ferry = ferry

    def is_motorway(self):
        return self.__motorway

    def is_urban(self):
        return self.__urban

    def is_service_area(self):
        return self.__service_area

    def is_controlled_access(self):
        return self.__controlled_access

    def is_toll(self):
        return self.__toll

    def is_bridge(self):
        return self.__bridge

    def is_tunnel(self):
        return self.__tunnel

    def is_ferry(self):
        return self.__ferry


class Vehicle(Entity):
    pass


class Pedestrian(Entity):
    pass


class Cyclist(Entity):
    pass


class Road(BaseItem):
    def __init__(self, start_angle, end_angle, start_point, end_point, attributes, identifier):
        super().__init__(identifier)
        self.__start_angle = start_angle
        self.__end_angle = end_angle
        self.__start_point = start_point
        self.__end_point = end_point
        self.__attributes = attributes

    def get_start_angle(self):
        return self.__start_angle

    def get_end_angle(self):
        return self.__end_angle

    def get_start_point(self):
        return self.__start_point

    def get_end_point(self):
        return self.__end_point

    def get_attributes(self):
        return self.__attributes


class RoadPoint(BaseItem):
    def __init__(self, identifier, latitude, longitude):
        super().__init__(identifier)
        self.__latitude = latitude
        self.__longitude = longitude

    def get_latitude(self):
        return self.__latitude

    def get_longitude(self):
        return self.__longitude


class Junction(RoadPoint):
    pass


class Delimiter(RoadPoint):
    pass


class Acceleration:
    def __init__(self, acX, acY):
        self.__acX = acX
        self.__acY = acY

    def get_x(self):
        return self.__acX

    def get_y(self):
        return self.__acY

    def as_tuple(self):
        return self.__acX, self.__acY


class Speed:
    def __init__(self, spX, spY):
        self.__spX = spX
        self.__spY = spY

    def get_x(self):
        return self.__spX

    def get_y(self):
        return self.__spY

    def as_tuple(self):
        return self.__spX, self.__spY
