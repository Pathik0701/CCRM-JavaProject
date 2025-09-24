package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.exceptions.CourseNotFoundException;
import edu.ccrm.exceptions.InvalidDataException;
import edu.ccrm.util.ValidationUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for course operations
 */
public class CourseService {
    private final Map<String, Course> courses;
    
    public CourseService() {
        this.courses = new HashMap<>();
    }
    
    /**
     * Add a new course with validation
     */
    public void addCourse(Course course) throws InvalidDataException {
        if (course == null) {
            throw new InvalidDataException("Course cannot be null");
        }
        
        if (!ValidationUtils.isValidCourseCode(course.getCode())) {
            throw new InvalidDataException("Invalid course code format");
        }
        
        if (courses.containsKey(course.getCode())) {
            throw new InvalidDataException("Course with code " + course.getCode() + " already exists");
        }
        
        courses.put(course.getCode(), course);
    }
    
    /**
     * Find course by code
     */
    public Course findCourseByCode(String code) {
        return courses.get(code);
    }
    
    /**
     * Get all courses sorted by code
     */
    public List<Course> getAllCourses() {
        return courses.values().stream()
            .sorted(Comparator.comparing(Course::getCode))
            .collect(Collectors.toList());
    }
    
    /**
     * Search courses by department using Stream API
     */
    public List<Course> searchByDepartment(String department) {
        return courses.values().stream()
            .filter(course -> course.getDepartment() != null && 
                course.getDepartment().equalsIgnoreCase(department))
            .sorted(Comparator.comparing(Course::getCode))
            .collect(Collectors.toList());
    }
    
    /**
     * Search courses by instructor name
     */
    public List<Course> searchByInstructor(String instructorName) {
        return courses.values().stream()
            .filter(course -> course.getInstructor() != null && 
                course.getInstructor().getFullName().toLowerCase()
                .contains(instructorName.toLowerCase()))
            .sorted(Comparator.comparing(Course::getCode))
            .collect(Collectors.toList());
    }
    
    /**
     * Search courses by semester
     */
    public List<Course> searchBySemester(Semester semester) {
        return courses.values().stream()
            .filter(course -> course.getSemester() == semester)
            .sorted(Comparator.comparing(Course::getCode))
            .collect(Collectors.toList());
    }
    
    /**
     * Update course information
     */
    public void updateCourse(Course course) throws CourseNotFoundException {
        if (!courses.containsKey(course.getCode())) {
            throw new CourseNotFoundException("Course with code " + course.getCode() + " not found");
        }
        courses.put(course.getCode(), course);
    }
    
    /**
     * Get courses by credit range using Stream API
     */
    public List<Course> getCoursesByCreditRange(int minCredits, int maxCredits) {
        return courses.values().stream()
            .filter(course -> course.getCredits() >= minCredits && 
                course.getCredits() <= maxCredits)
            .sorted(Comparator.comparing(Course::getCode))
            .collect(Collectors.toList());
    }
    
    /**
     * Get department statistics using Stream API grouping
     */
    public Map<String, Long> getDepartmentStatistics() {
        return courses.values().stream()
            .filter(course -> course.getDepartment() != null)
            .collect(Collectors.groupingBy(
                Course::getDepartment,
                Collectors.counting()
            ));
    }
}
