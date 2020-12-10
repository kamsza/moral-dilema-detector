package generatorGUI;

import javax.swing.*;
import java.util.Arrays;

interface ValueHandler {
    default void showExceptionWindow(String message) {
        JOptionPane.showMessageDialog(new JFrame(),
                "The following problem was encountered while running the generator:\n" + message,
                "Generator error",
                JOptionPane.ERROR_MESSAGE);
    }

    default int getJSpinnerValue(JSpinner jSpinner) {
        try {
            return Integer.parseInt(jSpinner.getValue().toString());
        } catch(NumberFormatException ex) {
            showExceptionWindow("Wrong format of ...");
            throw new IllegalArgumentException();
        }
    }

    default void checkValidity(int[] objectNumbers, double[] probabilities) throws IllegalArgumentException {
        if(objectNumbers.length != probabilities.length) {
            showExceptionWindow("Not equal number of max object and pobabilities set\n" +
                    "max objects: " + objectNumbers.length + "   probabilities: " + probabilities.length);
            throw new IllegalArgumentException();
        }
    }
}
