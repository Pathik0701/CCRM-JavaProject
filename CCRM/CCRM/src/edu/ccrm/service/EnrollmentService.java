package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.config.AppConfig;
import edu.ccrm.exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for enrollment operations
 */
public class EnrollmentService {
    private final List<Enrollment> enrollments;
    private final AppConfig config;
    
    public EnrollmentService() {
        this.enrollments = new ArrayList<>();
        this.config = AppConfig.getInstance();
    }
    
    /**
     * Enroll student in course with business rule validation
     */
    public void enrollStudent(Student student, Course course) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        // Check for duplicate enrollment
        boolean alreadyEnrolled = enrollments.stream()
            .anyMatch(e -> e.getStudentId().equals(student.getId()) && 
                e.getCourseCode().equals(course.getCode()));
                
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(
                "Student is already enrolled in course " + course.getCode());
        }
        
        // Check credit limit for current semester
        int currentCredits = getCurrentSemesterCredits(student.getId(), course.getSemester());
        if (currentCredits + course.getCredits() > config.getMaxCreditsPerSemester()) {
            throw new MaxCreditLimitExceededException(
                String.format("Enrolling in %s would exceed maximum credits per semester (%d)", 
                    course.getCode(), config.getMaxCreditsPerSemester()));
        }
        
        // Create enrollment
        Enrollment enrollment = new Enrollment(student.getId(), course.getCode());
        enrollments.add(enrollment);
        
        // Update student's enrolled courses
        student.enrollInCourse(course.getCode());
    }
    
    /**
     * Unenroll student from course
     */
    public void unenrollStudent(String studentId, String courseCode) 
            throws EnrollmentNotFoundException {
        
        boolean removed = enrollments.removeIf(e -> 
            e.getStudentId().equals(studentId) && 
            e.getCourseCode().equals(courseCode));
            
        if (!removed) {
            throw new EnrollmentNotFoundException(
                "No enrollment found for student " + studentId + " in course " + courseCode);
        }
    }
    
    /**
     * Record grade for student in course
     */
    public void recordGrade(String studentId, String courseCode, Grade grade, Double marks) 
            throws EnrollmentNotFoundException {
        
        Enrollment enrollment = findEnrollment(studentId, courseCode);
        if (enrollment == null) {
            throw new EnrollmentNotFoundException(
                "No enrollment found for student " + studentId + " in course " + courseCode);
        }
        
        enrollment.setGrade(grade, marks);
    }
    
    /**
     * Get student enrollments
     */
    public List<Enrollment> getStudentEnrollments(String studentId) {
        return enrollments.stream()
            .filter(e -> e.getStudentId().equals(studentId))
            .sorted(Comparator.comparing(Enrollment::getEnrollmentDate))
            .collect(Collectors.toList());
    }
    
    /**
     * Get course enrollments
     */
    public List<Enrollment> getCourseEnrollments(String courseCode) {
        return enrollments.stream()
            .filter(e -> e.getCourseCode().equals(courseCode))
            .sorted(Comparator.comparing(Enrollment::getEnrollmentDate))
            .collect(Collectors.toList());
    }
    
    /**
     * Calculate student GPA
     */
    public double calculateStudentGPA(String studentId) {
        List<Enrollment> studentEnrollments = getStudentEnrollments(studentId);
        
        double totalGradePoints = 0.0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : studentEnrollments) {
            if (enrollment.getGrade() != null) {
                // Note: In real implementation, you'd need course credits
                // For now, assume all courses are 3 credits
                int courseCredits = 3;
                totalGradePoints += enrollment.getGrade().getGradePoints() * courseCredits;
                totalCredits += courseCredits;
            }
        }
        
        return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
    }
    
    private Enrollment findEnrollment(String studentId, String courseCode) {
        return enrollments.stream()
            .filter(e -> e.getStudentId().equals(studentId) && 
                e.getCourseCode().equals(courseCode))
            .findFirst()
            .orElse(null);
    }
    
    private int getCurrentSemesterCredits(String studentId, Semester semester) {
        // Simplified implementation - in real app, you'd filter by current semester
        return getStudentEnrollments(studentId).size() * 3; // Assume 3 credits per course
    }
    
    /**
     * Get all enrollments
     */
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}