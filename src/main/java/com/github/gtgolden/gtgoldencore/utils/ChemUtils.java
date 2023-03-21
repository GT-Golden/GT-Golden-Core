package com.github.gtgolden.gtgoldencore.utils;

import com.github.gtgolden.gtgoldencore.material.MaterialStack;
import com.github.gtgolden.gtgoldencore.material.Materials;
import com.github.gtgolden.gtgoldencore.material.chemistry.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemUtils {
    public static List<MaterialStack> parse(String formula) {
        // https://stackoverflow.com/questions/23602175/regex-for-parsing-chemical-formulas
        System.out.println("\nParce: "+formula+": "+ Materials.name(formula));

        final String regex = "[A-Z][a-z]?\\d*|\\([^()]*(?:\\(.*\\))?[^()]*\\)\\d+";

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(formula);

        final List<MaterialStack> components = new ArrayList<>();

        while (matcher.find()) {
            String chemFormula = matcher.group(0);

            components.add(new MaterialStack(
                    Materials.name(chemFormula),
                    Integer.max(1, Integer.parseInt(chemFormula.replaceAll("\\(.*\\)|[A-Z][a-z]?", "0"))))
            );
        }

        return components;
    }
}
