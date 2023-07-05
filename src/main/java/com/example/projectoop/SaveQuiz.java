package com.example.projectoop;

import java.util.ArrayList;
import java.util.List;

public class SaveQuiz {
    private static List<String> labelValues = new ArrayList<>();

    public static List<String> getLabelValues() {
        return labelValues;
    }

    public static void addLabelValue(String value) {
        labelValues.add(value);
    }

}
