import threading

from kivy.app import App
from kivy.uix.button import Button
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label
from kivy.uix.scrollview import ScrollView
from kivy.uix.textinput import TextInput
from kivy.uix.popup import Popup

from carla_actor_controller.controller import Controller
from carla_actor_controller.model import Model
from kivy.uix.screenmanager import ScreenManager, Screen

from carla_reader import CarlaReader

from scenario_describer.ScenarioGeneratror import Generator as ScenarioGenerator
from common.strings import DEFAULT_CARLA_VEHICLE, DEFAULT_CARLA_PEDESTRIAN, DEFAULT_CARLA_CYCLIST


class ScrollPanel(ScrollView):
    def __init__(self, elements=[], **kwargs):
        super().__init__(**kwargs)
        self.scroll_type = ['bars', 'content']
        self.__layout = GridLayout()
        for element in elements:
            self.__layout.add_widget(element)
        self.__layout.cols = 3
        self.__layout.rows = 3
        self.__layout.size_hint = (1, None)
        self.add_widget(self.__layout)

    def add_element(self, el):
        self.__layout.add_widget(el)


class CreateActorLayout(GridLayout):
    def on_create(self, blueprint, location_tuple, rotation_tuple):
        try:
            self.__controller.spawn_actor(
                float(location_tuple[0]),
                float(location_tuple[1]),
                float(location_tuple[2]),
                float(rotation_tuple[0]),
                float(rotation_tuple[1]),
                float(rotation_tuple[2]),
                blueprint
            )
        except RuntimeError:
            print("Actor creation has field possible in world collision check Transform")
            popup = Popup(title="Actor creation failed",
                          content=Label(text="Actor creation has field possible in world collision check Transform\n Press ESC to continue."),
                          auto_dismiss=True)
            popup.open()
        finally:
            self.back_to_main()

    def back_to_main(self):
        self.__manager.clear_widgets()
        self.__manager.add_widget(MainScreen(self.__controller, self.__manager))

    def __init__(self, controller: Controller, manager: ScreenManager, **kwargs):
        super().__init__(**kwargs)
        self.__controller = controller
        self.__manager = manager
        self.__person_blueprint = controller.get_pedestrian_blueprints()
        self.__vehicle_blueprint = controller.get_vehicle_blueprints()
        print("Persons")
        print(self.__person_blueprint)
        print("Vehicle")
        print(self.__vehicle_blueprint)
        print("Spawnpoints")
        print(str(controller.get_spawn_points()))
        self.cols = 2

        ti_pos_x = TextInput()
        ti_pos_y = TextInput()
        ti_pos_z = TextInput()

        ti_rot_roll = TextInput()
        ti_rot_pitch = TextInput()
        ti_rot_yaw = TextInput()

        self.add_widget(Label(text="Position X"))
        self.add_widget(ti_pos_x)

        self.add_widget(Label(text="Position Y"))
        self.add_widget(ti_pos_y)

        self.add_widget(Label(text="Position Z"))
        self.add_widget(ti_pos_z)

        self.add_widget(Label(text="Position Roll"))
        self.add_widget(ti_rot_roll)

        self.add_widget(Label(text="Position Pitch"))
        self.add_widget(ti_rot_pitch)

        self.add_widget(Label(text="Position Yaw"))
        self.add_widget(ti_rot_yaw)

        btn_create_vehicle = Button(text="Create vehicle")
        btn_create_vehicle.bind(on_press=lambda e: self.on_create(
            DEFAULT_CARLA_VEHICLE,
            (ti_pos_x.text, ti_pos_y.text, ti_pos_z.text),
            (ti_rot_roll.text, ti_rot_pitch.text, ti_rot_yaw.text)
        ))

        btn_create_cyclist = Button(text="Create cyclist")
        btn_create_cyclist.bind(on_press=lambda e: self.on_create(
            DEFAULT_CARLA_CYCLIST,
            (ti_pos_x.text, ti_pos_y.text, ti_pos_z.text),
            (ti_rot_roll.text, ti_rot_pitch.text, ti_rot_yaw.text)
        ))

        btn_create_pedestrian = Button(text="Create pedestrian")
        btn_create_pedestrian.bind(on_press=lambda e: self.on_create(
            DEFAULT_CARLA_PEDESTRIAN,
            (ti_pos_x.text, ti_pos_y.text, ti_pos_z.text),
            (ti_rot_roll.text, ti_rot_pitch.text, ti_rot_yaw.text)
        ))

        btn_main_page = Button(text="Main page")
        btn_main_page.bind(on_press=lambda e: self.back_to_main())

        self.add_widget(btn_create_vehicle)
        self.add_widget(btn_create_cyclist)
        self.add_widget(btn_create_pedestrian)
        self.add_widget(btn_main_page)


class ChangeWorldLayout(GridLayout):
    def set_select(self, val):
        self.select = val

    def apply(self, event):
        self.__controller.set_world(self.select)

    def main_page(self, event):
        self.__manager.clear_widgets()
        self.__manager.add_widget(MainScreen(self.__controller, self.__manager))

    def __init__(self, controller: Controller, manager: ScreenManager, **kwargs):
        super().__init__(**kwargs)
        self.select = "Town02"
        self.__controller = controller
        self.__worlds = controller.get_words()
        self.__manager = manager

        self.main_page_btn = Button(text="Main Page")
        self.apply_btn = Button(text="Apply")
        self.cols = 1

        for world in self.__worlds:
            btn = Button(text=world)
            btn.bind(on_press=lambda e: self.set_select(e.text))
            self.add_widget(btn)

        self.add_widget(self.main_page_btn)
        self.main_page_btn.bind(on_press=self.main_page)
        self.add_widget(self.apply_btn)
        self.apply_btn.bind(on_press=self.apply)


class ChangeWorldScreen(Screen):
    def __init__(self, controller: Controller, manager: ScreenManager, **kw):
        super().__init__(**kw)
        self.add_widget(ChangeWorldLayout(controller, manager))


class CreateActorScreen(Screen):
    def __init__(self, controller: Controller, manager: ScreenManager, **kw):
        super().__init__(**kw)
        self.add_widget(CreateActorLayout(controller, manager))


class MainLayout(GridLayout):
    class OntologyThread(threading.Thread):
        def run(self):
            MainLayout.generate_ontology()

    def navigate_change_world(self, event):
        self.__manager.clear_widgets()
        self.__manager.add_widget(ChangeWorldScreen(self.__controller, self.__manager))

    def navigate_create_actor(self, event):
        self.__manager.clear_widgets()
        self.__manager.add_widget(CreateActorScreen(self.__controller, self.__manager))

    @staticmethod
    def generate_ontology():
        cr = CarlaReader.CarlaReader()
        ScenarioGenerator(cr.get_snapshot())

    def ontology_thread_start(self):
        self.OntologyThread().start()
        self.__controller.refresh_model()

    def generate_and_print_actors_summary(self):
        print("Number of actors is %d" % len(self.__controller.get_actors()))

    def __init__(self, controller: Controller, manager: ScreenManager, **kwargs):
        super().__init__(**kwargs)
        self.__controller = controller
        self.__manager = manager
        self.cols = 1
        self.ontology_thread = None

        self.change_world_btn = Button(text="Change world")
        self.create_actor_btn = Button(text="Create actor")
        self.generate_ontology_btn = Button(text="Generate ontology")
        self.print_raport_btn = Button(text="Print actors summary")

        self.change_world_btn.bind(on_press=self.navigate_change_world)
        self.create_actor_btn.bind(on_press=self.navigate_create_actor)
        self.generate_ontology_btn.bind(on_press=lambda e: self.ontology_thread_start())
        self.print_raport_btn.bind(on_press=lambda e: self.generate_and_print_actors_summary())

        self.add_widget(self.change_world_btn)
        self.add_widget(self.create_actor_btn)
        self.add_widget(self.generate_ontology_btn)
        self.add_widget(self.print_raport_btn)


class MainScreen(Screen):
    def __init__(self, controller: Controller, manager: ScreenManager, **kw):
        super().__init__(**kw)
        self.add_widget(MainLayout(controller, manager))


class WindowManager(ScreenManager):
    def __init__(self, controller, **kwargs):
        super().__init__(**kwargs)
        self.__controller = controller
        self.add_widget(MainScreen(controller, self))


class KivyView(App):
    def __init__(self, controller: Controller, **kwargs):
        super().__init__(**kwargs)
        self.__controller = controller

    def build(self):
        return WindowManager(self.__controller)


if __name__ == "__main__":
    KivyView(Controller(Model())).run()
