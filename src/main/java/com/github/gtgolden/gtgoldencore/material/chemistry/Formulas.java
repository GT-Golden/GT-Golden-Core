package com.github.gtgolden.gtgoldencore.material.chemistry;

import java.util.HashMap;
import java.util.Map;

public class Formulas {
    private static final Map<String, String> symbols = new HashMap<>();

    public static void add(String formula, String name) {
        symbols.put(formula, name);
    }
    public static String name(String formula) {
        if (formula.startsWith("("))
            formula = formula.replaceAll("^\\(|\\)\\d*$", "");
        else if (formula.matches("[A-Z][a-z]?\\d*$"))
            formula = formula.replaceAll("\\d*$", "");
        return symbols.get(formula);
    }

    public static String get(String name) {
        return symbols.get(name);
    }
}
