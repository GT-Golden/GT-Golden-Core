package com.github.gtgolden.gtgoldencore.material;

import javax.annotation.RegEx;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChemUtils {
    public static void parse(String formula) {
        // https://stackoverflow.com/questions/23602175/regex-for-parsing-chemical-formulas
        final String regex = "[A-Z][a-z]?\\d*|\\([^()]*(?:\\(.*\\))?[^()]*\\)\\d+";

        final Matcher matcher = Pattern.compile(regex, Pattern.MULTILINE).matcher(formula);

        while (matcher.find()) {
            System.out.println("Full match: " + matcher.group(0));

            for (int i = 1; i <= matcher.groupCount(); i++) {
                System.out.println("Group " + i + ": " + matcher.group(i));
            }
        }
    }
}
