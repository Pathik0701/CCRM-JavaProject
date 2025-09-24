package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Course class demonstrating Builder pattern and nested static class
 */
public class Course {
    private final String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private String department;
    private Semester semester;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Private constructor - only Builder can create instances
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.department = builder.department;
        this.semester = builder.semester;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public String getDepartment() { return department; }
    public Semester getSemester() { return semester; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Setters (except for code which is final)
    public void setTitle(String title) { 
        this.title = title; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setCredits(int credits) { 
        this.credits = credits; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setInstructor(Instructor instructor) { 
        this.instructor = instructor; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setDepartment(String department) { 
        this.department = department; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setSemester(Semester semester) { 
        this.semester = semester; 
        this.updatedAt = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return String.format("[%s] %s (%d credits) - %s | %s | %s", 
            code, title, credits, 
            instructor != null ? instructor.getFullName() : "No Instructor",
            department != null ? department : "No Department",
            semester != null ? semester : "No Semester");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return code.equals(course.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    
    // ========================================================================
    // Static Nested Class - Builder Pattern Implementation
    // ========================================================================
    public static class Builder {
        private String code;
        private String title;
        private int credits;
        private Instructor instructor;
        private String department;
        private Semester semester;
        
        public Builder setCode(String code) {
            this.code = code;
            return this;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setCredits(int credits) {
            this.credits = credits;
            return this;
        }
        
        public Builder setInstructor(Instructor instructor) {
            this.instructor = instructor;
            return this;
        }
        
        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }
        
        public Builder setSemester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Course build() {
            // Validation
            if (code == null || code.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code is required");
            }
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Course title is required");
            }
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be positive");
            }
            
            return new Course(this);
        }
    }
}