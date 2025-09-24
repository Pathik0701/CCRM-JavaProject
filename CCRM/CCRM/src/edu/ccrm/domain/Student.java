package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Student class demonstrating inheritance from Person
 */
public class Student extends Person {
    private StudentStatus status;
    private final LocalDate enrollmentDate;
    private List<String> enrolledCourseIds;
    
    // Nested enum demonstrating nested classes
    public enum StudentStatus {
        ACTIVE, INACTIVE, SUSPENDED, GRADUATED
    }
    
    public Student(String id, String regNo, String fullName, String email) {
        super(id, regNo, fullName, email);
        this.status = StudentStatus.ACTIVE;
        this.enrollmentDate = LocalDate.now();
        this.enrolledCourseIds = new ArrayList<>();
    }
    
    // Getters and Setters
    public StudentStatus getStatus() { return status; }
    public void setStatus(StudentStatus status) { this.status = status; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public List<String> getEnrolledCourseIds() { return new ArrayList<>(enrolledCourseIds); }
    
    public void enrollInCourse(String courseId) {
        if (!enrolledCourseIds.contains(courseId)) {
            enrolledCourseIds.add(courseId);
        }
    }
    
    public void unenrollFromCourse(String courseId) {
        enrolledCourseIds.remove(courseId);
    }
    
    @Override
    public String getRole() {
        return "STUDENT";
    }
    
    @Override
    public String getDetailedProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("=== STUDENT PROFILE ===\n");
        profile.append("ID: ").append(id).append("\n");
        profile.append("Registration No: ").append(regNo).append("\n");
        profile.append("Name: ").append(fullName).append("\n");
        profile.append("Email: ").append(email).append("\n");
        profile.append("Status: ").append(status).append("\n");
        profile.append("Enrollment Date: ").append(enrollmentDate).append("\n");
        profile.append("Enrolled Courses: ").append(enrolledCourseIds.size()).append("\n");
        return profile.toString();
    }
    
    @Override
    public String toString() {
        return String.format("[STUDENT] %s (%s) - %s [%s]", 
            fullName, regNo, email, status);
    }
}