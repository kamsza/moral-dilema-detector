from time import sleep

from carla import Client, Transform as cTransform, Location as cLocation, Rotation as cRotation, Actor as cActor\
    , ActorBlueprint as cBlueprint
host = 'localhost'
port = 2000
client = Client(host, port)
client.set_timeout(10.0)
world = client.get_world()
wid = world.id


def refresh_cache():
    global spawn_points
    spawn_points = [convert_carla_transform(sp) for sp in world.get_map().get_spawn_points()]
    blueprints = world.get_blueprint_library()
    vehicles = [x.id for x in blueprints.filter('vehicle')]
    pedestrians = [x.id for x in blueprints.filter('walker')]
    blueprints_ids = [x for x in vehicles+pedestrians]
    actor_list = [x for x in world.get_actors() if x.type_id in blueprints_ids]
    return {
        'ACTORS': [convert_carla_actor(actor) for actor in actor_list],
        'BLUEPRINTS': blueprints,
        'VEHICLES': vehicles,
        'PEDESTRIANS': pedestrians,
        'SPAWNPOINTS': spawn_points
    }


def find_blueprint(actor: cActor):
    return actor.type_id


class Location:
    def __init__(self, x, y, z):
        self.__x = x
        self.__y = y
        self.__z = z

    def get_x(self):
        return self.__x

    def get_y(self):
        return self.__y

    def get_z(self):
        return self.__z


class Rotation:
    def __init__(self, x, y, z):
        self.__x = x
        self.__y = y
        self.__z = z

    def get_x(self):
        return self.__x

    def get_y(self):
        return self.__y

    def get_z(self):
        return self.__z


class Transform:
    def __init__(self, rotation: Rotation, location: Location):
        self.__rotation = rotation
        self.__location = location

    def get_rotation(self) -> Rotation:
        return self.__rotation

    def get_location(self) -> Location:
        return self.__location

    def to_cTransform(self):
        rot = self.__rotation
        loc = self.__location
        cRot = cRotation(roll=rot.get_x(), pitch=rot.get_y(), yaw=rot.get_z())
        cLoc = cLocation(x=loc.get_x(), y=loc.get_y(), z=loc.get_z())
        return cTransform(rotation=cRot, location=cLoc)


class Actor:
    def __init__(self, blueprint, transform: Transform, id_=-1):
        self.__blueprint = blueprint
        self.__transform = transform
        self.__id = id_

    def get_transform(self) -> Transform:
        return self.__transform

    def get_blueprint(self):
        return self.__blueprint

    def get_id(self):
        return self.__id

    def set_id(self, id_):
        self.__id = id_

    def __eq__(self, other):
        return other.get_id == self.__id


def convert_carla_transform(transform: cTransform):
    l = transform.location
    r = transform.rotation
    location = Location(l.x, l.y, l.z)
    rotation = Rotation(r.roll, r.pitch, r.yaw)
    return Transform(rotation, location)


spawn_points = [convert_carla_transform(sp) for sp in world.get_map().get_spawn_points()]


def convert_carla_actor(actor: cActor):
    transform = convert_carla_transform(actor.get_transform())
    blueprint = find_blueprint(actor)
    id_ = actor.id
    return Actor(blueprint, transform, id_)


def get_worlds():
    return [
        'Town01',
        'Town02',
        'Town03',
        'Town04',
        'Town05',
        'Town06',
        'Town07',
    ]


class Model:
    def __init__(self):
        self.__cache = refresh_cache()

    def get_actors(self):
        self.ref()
        return self.__cache['ACTORS']

    def get_blueprints(self):
        return self.__cache['VEHICLES']+self.__cache['PEDESTRIANS']

    def get_vehicles_blueprints(self):
        return self.__cache['VEHICLES']

    def get_pedestrian_blueprints(self):
        return self.__cache['PEDESTRIANS']

    def get_spawn_points(self):
        return self.__cache['SPAWNPOINTS']

    def load_world(self, world_name):
        client.load_world(world_name)
        self.ref()

    def get_actor_ids(self):
        return [x.get_id() for x in self.get_actors()]

    def get_actor_by_id(self, id_):
        for actor in self.get_actors():
            if actor.get_id() == id_:
                return actor
        return None

    def __get_blueprint_from_string(self, bp):
        return world.get_blueprint_library().find(bp)

    def spawn_actor(self, actor: Actor):
        id_ = world.spawn_actor(self.__get_blueprint_from_string(actor.get_blueprint()),
                                actor.get_transform().to_cTransform())
        actor.set_id(id_)
        self.ref()

    def move_actor(self, actor_id, transform: Transform):
        trans = transform.to_cTransform()
        cActor = world.get_actors().find(actor_id)
        if cActor is not None:
            cActor.set_transform(trans)
        self.ref()

    def destroy_actor(self, actor_id):
        actor = world.get_actors().find(actor_id)
        if actor is not None:
            actor.destory()
        self.ref()

    def get_worlds(self):
        return [
            'Town01',
            'Town02',
            'Town03',
            'Town04',
            'Town05',
            'Town06',
            'Town07',
        ]

    def ref(self):
        sleep(0.5)
        self.__cache = refresh_cache()


model = Model()

if __name__ == '__main__':
    print([x for x in model.get_actors()].__len__())
    # print(model.get_blueprints())
    print(model.get_vehicles_blueprints())
    print(model.get_pedestrian_blueprints())
    print(model.get_spawn_points())
    model.load_world(get_worlds()[4])
