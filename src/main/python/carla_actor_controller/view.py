from kivy.app import App
from kivy.uix.button import Button
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label
from kivy.uix.scrollview import ScrollView
from kivy.uix.textinput import TextInput

from carla_actor_controller.controller import Controller
from carla_actor_controller.model import Model
from kivy.uix.screenmanager import ScreenManager, Screen


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
        self.cols = 2

        ti_blueprint = TextInput()

        ti_pos_x = TextInput()
        ti_pos_y = TextInput()
        ti_pos_z = TextInput()

        ti_rot_roll = TextInput()
        ti_rot_pitch = TextInput()
        ti_rot_yaw = TextInput()

        self.add_widget(Label(text="Blueprint"))
        self.add_widget(ti_blueprint)

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

        btn_create = Button(text="Create")
        btn_main_page = Button(text="Main page")
        btn_create.bind(on_press=lambda e: self.on_create(
            ti_blueprint.text,
            (ti_pos_x.text, ti_pos_y.text, ti_pos_z.text),
            (ti_rot_roll.text, ti_rot_pitch.text, ti_rot_yaw.text)
        ))
        btn_main_page.bind(on_press=lambda e: self.back_to_main())
        self.add_widget(btn_create)
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
    def navigate_change_world(self, event):
        self.__manager.clear_widgets()
        self.__manager.add_widget(ChangeWorldScreen(self.__controller, self.__manager))

    def navigate_create_actor(self, event):
        self.__manager.clear_widgets()
        self.__manager.add_widget(CreateActorScreen(self.__controller, self.__manager))

    def __init__(self, controller: Controller, manager: ScreenManager, **kwargs):
        super().__init__(**kwargs)
        self.__controller = controller
        self.__manager = manager
        self.cols = 1

        self.change_world_btn = Button(text="Change world")
        self.actor_modify_btn = Button(text="Move actor")
        self.create_actor_btn = Button(text="Create actor")
        self.print_actors_list_btn = Button(text="Print actors")

        self.change_world_btn.bind(on_press=self.navigate_change_world)
        self.create_actor_btn.bind(on_press=self.navigate_create_actor)
        self.print_actors_list_btn.bind(on_press=lambda e: print(controller.get_actors()))
        self.add_widget(self.change_world_btn)
        self.add_widget(self.actor_modify_btn)
        self.add_widget(self.create_actor_btn)
        self.add_widget(self.print_actors_list_btn)


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
