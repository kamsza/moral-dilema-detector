import sys
import Ice

import adapter_ice


class ScenarioDescriberConnector:
    # string_proxy = ":tcp -h localhost -p 10000"

    def __init__(self, string_proxy):
        self.__string_proxy = string_proxy
        communicator = Ice.initialize(sys.argv)
        self.__base = communicator.stringToProxy("adapter/manager" + string_proxy)

        self.__managerPrx = adapter_ice.ManagerPrx.checkedCast(self.__base)

        if self.__managerPrx is None: raise ConnectionError("Invalid proxy")
        self.__communicator = communicator

    def get_manager(self):
        return self.__managerPrx

    def get_base(self):
        return self.__base

    def get_communicator(self):
        return self.__communicator
