package ru.appline.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CompassModel implements Serializable {
    private static final CompassModel instance = new CompassModel();
    private final Map<String, String> compassModel;

    public CompassModel() {
        compassModel = new HashMap<String, String>();
    }

    public static CompassModel getInstance() {
        return instance;
    }

    public void addSides(Map<String, String> data) {
        for (Map.Entry<String, String> pair: data.entrySet()) {
            compassModel.put(pair.getKey(),pair.getValue());
        }

    }

    public Map<String, String> getAllSides() {
        return compassModel;
    }
}
