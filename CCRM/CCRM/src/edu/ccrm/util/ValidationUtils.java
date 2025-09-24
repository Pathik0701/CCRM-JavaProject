package edu.ccrm.util;

import java.util.regex.Pattern;

/**
 * Utility class for validation operations
 * Demonstrates static methods and regular expressions
 */
public class ValidationUtils {
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    
    private static final Pattern COURSE_CODE_PATTERN = 
        Pattern.compile("^[A-Z]{2,4}\\d{3,4}$");
    
    // Private constructor to prevent instantiation
    private ValidationUtils() {}
    
    /**
     * Validates email format using regex
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validates course code format (e.g., CS101, MATH1001)
     * @param courseCode Course code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidCourseCode(String courseCode) {
        return courseCode != null && COURSE_CODE_PATTERN.matcher(courseCode).matches();
    }
    
    /**
     * Validates if string is not null or empty
     * @param value String to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Validates credit range
     * @param credits Credits to validate
     * @return true if valid (1-6 credits), false otherwise
     */
    public static boolean isValidCreditRange(int credits) {
        return credits >= 1 && credits <= 6;
    }
    
    /**
     * Validates marks range
     * @param marks Marks to validate
     * @return true if valid (0-100), false otherwise
     */
    public static boolean isValidMarks(double marks) {
        return marks >= 0.0 && marks <= 100.0;
    }
}