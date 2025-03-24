package dev.jhndrncrz.quizzit.utilities.logging;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class AppLog {
    private final List<String> logs;
    private static AppLog singleton;

    public enum Type {
        SUCCESS,
        INFO,
        WARNING,
        ERROR
    }

    private AppLog() {
        this.logs = new ArrayList<>();
    }

    public static AppLog getInstance() {
        if (singleton == null) {
            singleton = new AppLog();
        }

        return singleton;
    }


    public void addLog(String message, Type type) {
        String logType = switch (type) {
            case SUCCESS -> "SUCCESS";
            case INFO -> "INFO";
            case WARNING -> "WARNING";
            case ERROR -> "ERROR";
            default -> "";
        };

        this.logs.add(String.format("[%s] %s: %s", logType, message, new Date().toString()));
    }

    public void printLogs() {
        for (String log : this.logs) {
            System.out.println(log);
        }
    }
}
