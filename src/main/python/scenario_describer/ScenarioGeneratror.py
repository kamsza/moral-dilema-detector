import Ice

from scenario_describer.ScenarioDescriberConnector import ScenarioDescriberConnector
from common.carla_snapshot import CarlaSnapshot
from scenario_describer.scenario_builder import ScenarioBuilder
proxy = ":tcp -h localhost -p 10000"


def getInternetAddress(id_):
    return "adapter/" + id_ + proxy


class Generator:
    def __init__(self, snapshot):
        should_destroy = True
        try:
            self.__connector = ScenarioDescriberConnector(proxy)
            self.__scenario_builder = ScenarioBuilder(proxy, self.__connector, snapshot)
            self.ids, self.proxies_by_ids = self.__scenario_builder.get_ids(), self.__scenario_builder.get_proxies()
        except Ice.ConnectionRefusedException:
            print("Connection was not possible, check Ice server")
            should_destroy = False
        finally:
            if should_destroy:
                self.__connector.get_communicator().destroy()
