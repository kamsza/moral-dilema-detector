from carla_actor_controller.model import Actor, Location, Rotation, Model, Transform


class Controller:
    def __init__(self, model: Model):
        self.__model = model

    def refresh_model(self):
        self.__model.ref()

    def get_blueprints(self):
        return self.__model.get_blueprints()

    def get_vehicle_blueprints(self):
        return self.__model.get_vehicles_blueprints()

    def get_pedestrian_blueprints(self):
        return self.__model.get_pedestrian_blueprints()

    def get_words(self):
        return self.__model.get_worlds()

    def get_actor_by_id(self, id_):
        return self.__model.get_actor_by_id(id_)

    def move_actor(self, actor_id, trans):
        self.__model.move_actor(actor_id, trans)

    def move_actor_spawnpoint(self, actor_id, trans: Transform):
        spawnpoints = self.__model.get_spawn_points()
        if trans in spawnpoints:
            self.move_actor(actor_id, trans)
        else:
            raise TransformNotSpawnPointException()

    def destroy_actor(self, actor_id):
        self.__model.destroy_actor(actor_id)

    def spawn_actor(self, trans: Transform, blueprint):
        actor = Actor(blueprint, trans)
        self.__model.spawn_actor(actor)
        print("Actor spawned")

    def spawn_actor(self, xl, yl, zl, xr, yr, zr, blueprint):
        rot = Rotation(xr, yr, zr)
        loc = Location(xl, yl, zl)
        trans = Transform(rot, loc)
        actor = Actor(blueprint, trans)
        self.__model.spawn_actor(actor)
        print("Actor spawned")

    def set_world(self, world):
        self.__model.load_world(world)

    def get_actors(self):
        return self.__model.get_actors()

    def get_spawn_points(self):
        return self.__model.get_spawn_points()


class TransformNotSpawnPointException(Exception):
    pass
