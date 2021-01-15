package gui.logic;

import generator.MyFactorySingleton;
import project.OWLFactory;

import java.lang.reflect.Field;

public class FactoryReflection {

    public static void changeFactorySingletonToNull(){
        Field privateFactoryField = null;
        try {
            privateFactoryField = MyFactorySingleton.class.
                    getDeclaredField("factory");
            privateFactoryField.setAccessible(true);
            privateFactoryField.set(MyFactorySingleton.class, null);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void changeFactorySingleton(OWLFactory factory){
        Field privateFactoryField = null;
        try {
            privateFactoryField = MyFactorySingleton.class.
                    getDeclaredField("factory");
            privateFactoryField.setAccessible(true);
            privateFactoryField.set(MyFactorySingleton.class, factory);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
