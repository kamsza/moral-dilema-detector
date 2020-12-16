import carla as car
import random, sys, time


class CarlaConnector:
    def __init__(self):
        self.__carla_client = car.Client('localhost', 2000)
        self.__carla_client.set_timeout(5.0)

    def get_client(self):
        return self.__carla_client

    def get_world(self):
        return self.__carla_client.get_world()
