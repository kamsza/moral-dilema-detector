# import sys
# import Ice
# import carla
#
# from adapter_ice import ScenarioPrx, ManagerPrx, ItemType
#
# with Ice.initialize(sys.argv) as communicator:
#     base = communicator.stringToProxy("factory/factory1:tcp -h localhost -p 10000")
#
#     managerPrx = ManagerPrx.checkedCast(base)
#     if managerPrx is None : raise ConnectionError("Invalid proxy")
#
#     while True:
#         command = input()
#         if command == 'create':
#             scenarioId = managerPrx.create(ItemType.SCENARIO)
#             laneId = managerPrx.create(ItemType.LANE)
#             vehicleId = managerPrx.create(ItemType.VEHICLE)
#
#             basePrx = communicator.stringToProxy(scenarioId + ":tcp -h localhost -p 10000")
#             scenarioPrx = ScenarioPrx.checkedCast(basePrx)
from carla_actor_controller.controller import Controller
from carla_actor_controller.model import Model
from carla_actor_controller.view import KivyView
from carla_reader import CarlaReader

from scenario_describer.ScenarioGeneratror import Generator as ScenarioGenerator

if __name__ == '__main__':
    KivyView(Controller(Model())).run()
