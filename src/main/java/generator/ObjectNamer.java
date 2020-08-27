package generator;

import java.util.HashMap;
import java.util.Map;

class ObjectNamer {
    static Map<String, Integer> idMap = new HashMap<>();
    static int scenario_id = -1;

    /**
     * Allows to set the identifier of the first scenario
     */
    static void setInitScenarioId(int id) {
        if(scenario_id == -1)
            scenario_id = id - 1;
    }

    /**
     * If two variables created by factory will have the same name, they will be merged into one
     * so using this method to create variable names is highly advisable
     * @param name name of a variable
     * @return string with unique name, containing scenario id and unique identifier if needed
     */
    static String getName(String name) {
        if(name.equals("scenario"))
            scenario_id++;

        if(scenario_id != 0)
            name = scenario_id + "_" + name;

        int id = idMap.getOrDefault(name, 0);
        idMap.put(name, id + 1);

        if(id != 0)
            return name + "_" + id;

        return name;
    }
}