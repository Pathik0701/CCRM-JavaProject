package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Instructor class demonstrating inheritance from Person
 */
public class Instructor extends Person {
    private String department;
    private List<String> assignedCourseIds;
    
    public Instructor(String id, String regNo, String fullName, String email) {
        super(id, regNo, fullName, email);
        this.assignedCourseIds = new ArrayList<>();
    }
    
    public Instructor(String id, String regNo, String fullName, String email, String department) {
        this(id, regNo, fullName, email);
        this.department = department;
    }
    
    // Getters and Setters
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public List<String> getAssignedCourseIds() { return new ArrayList<>(assignedCourseIds); }
    
    public void assignCourse(String courseId) {
        if (!assignedCourseIds.contains(courseId)) {
            assignedCourseIds.add(courseId);
        }
    }
    
    public void unassignCourse(String courseId) {
        assignedCourseIds.remove(courseId);
    }
    
    @Override
    public String getRole() {
        return "INSTRUCTOR";
    }
    
    @Override
    public String getDetailedProfile() {
        StringBuilder profile = new StringBuilder();
        profile.append("=== INSTRUCTOR PROFILE ===\n");
        profile.append("ID: ").append(id).append("\n");
        profile.append("Employee No: ").append(regNo).append("\n");
        profile.append("Name: ").append(fullName).append("\n");
        profile.append("Email: ").append(email).append("\n");
        profile.append("Department: ").append(department != null ? department : "Not Assigned").append("\n");
        profile.append("Assigned Courses: ").append(assignedCourseIds.size()).append("\n");
        return profile.toString();
    }
    
    @Override
    public String toString() {
        return String.format("[INSTRUCTOR] %s (%s) - %s [%s]", 
            fullName, regNo, email, department != null ? department : "No Dept");
    }
}
