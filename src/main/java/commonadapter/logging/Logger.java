package commonadapter.logging;

public class Logger {

    public static void printLogMessage(String message, LogMessageType messageType) {

        String messageColor = ConsoleColors.WHITE;

        switch (messageType) {
            case INFO:
                messageColor = ConsoleColors.YELLOW;
                break;
            case ERROR:
                messageColor = ConsoleColors.RED;
        }

        System.out.println(messageColor + "[" + messageType + "] " + message + ConsoleColors.RESET);
    }
}
