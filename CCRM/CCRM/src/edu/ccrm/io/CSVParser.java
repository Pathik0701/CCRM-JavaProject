package edu.ccrm.io;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Simple CSV parser utility
 * Demonstrates string operations and collections
 */
public class CSVParser {
    private static final String DEFAULT_DELIMITER = ",";
    
    private CSVParser() {} // Utility class
    
    /**
     * Parse CSV line into fields
     */
    public static List<String> parseLine(String line) {
        return parseLine(line, DEFAULT_DELIMITER);
    }
    
    /**
     * Parse CSV line with custom delimiter
     */
    public static List<String> parseLine(String line, String delimiter) {
        if (line == null || line.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return Arrays.stream(line.split(delimiter))
            .map(String::trim)
            .map(field -> field.replaceAll("^\"|\"$", "")) // Remove quotes
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * Convert fields to CSV line
     */
    public static String toCsvLine(List<String> fields) {
        return String.join(DEFAULT_DELIMITER, 
            fields.stream()
                .map(field -> field.contains(",") ? "\"" + field + "\"" : field)
                .toArray(String[]::new));
    }
}
