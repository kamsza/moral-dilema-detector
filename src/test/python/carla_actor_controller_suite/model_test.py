import unittest
from unittest import mock
from unittest.mock import patch, MagicMock

import carla
from carla_actor_controller.model import convert_carla_transform, convert_carla_actor


class MyTestCase(unittest.TestCase):
    def transform_conversion_test(self):
        # given
        # carla transform with rotation and specification
        cLoc = carla.Location(x=5, y=6, z=7)
        cRot = carla.Rotation(roll=1, pitch=2, yaw=3)
        cTrans = carla.Transform(cLoc, cRot)
        # when
        # user converts carla transform to transform and then converts back to carla type
        trans = convert_carla_transform(cTrans)
        cTrans2 = trans.to_cTransform()
        # then
        # system data represents same data as carla
        cLocVals = (cLoc.x, cLoc.y, cLoc.z)
        cRotVals = (cRot.roll, cRot.pitch, cRot.yaw)
        rot = trans.get_rotation()
        rotVals = (rot.get_x(), rot.get_y(), rot.get_z())
        loc = trans.get_location()
        locVals = (loc.get_x(), loc.get_y(), loc.get_z())
        self.assertEqual(cLocVals, locVals)
        self.assertEqual(cRotVals, rotVals)
        # and both carla transforms should equal
        self.assertEqual(cTrans, cTrans2)

    def actor_convertion_test(self):
        # given
        # carla actor
        carlaActor = carla.Actor
        cLoc = carla.Location(x=5, y=6, z=7)
        cRot = carla.Rotation(roll=1, pitch=2, yaw=3)
        cTrans = carla.Transform(cLoc, cRot)
        blueprint = "ala.ma.kota"
        carlaActor.get_transform = MagicMock(return_value=cTrans)

        # when
        # user converts to system type
        actor = convert_carla_actor(carlaActor)

        # then
        # data match their equivalents
        cLocVals = (cLoc.x, cLoc.y, cLoc.z)
        cRotVals = (cRot.roll, cRot.pitch, cRot.yaw)
        trans = actor.get_transform()
        rot = trans.get_rotation()
        loc = trans.get_location()
        locVals = (loc.get_x(), loc.get_y(), loc.get_z())
        rotVals = (rot.get_x(), rot.get_y(), rot.get_z())
        self.assertEqual(carlaActor.id, actor.get_id())
        self.assertEqual(carlaActor.type_id, actor.get_blueprint())
        self.assertEqual(cLocVals, locVals)
        self.assertEqual(cRotVals, rotVals)

    def test_transform(self):
        self.transform_conversion_test()

    def test_actor(self):
        self.actor_convertion_test()


if __name__ == '__main__':
    unittest.main()
