package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Enrollment class representing student-course relationship
 */
public class Enrollment {
    private final String enrollmentId;
    private final String studentId;
    private final String courseCode;
    private final LocalDateTime enrollmentDate;
    private Grade grade;
    private Double marks;
    
    public Enrollment(String studentId, String courseCode) {
        this.enrollmentId = generateEnrollmentId(studentId, courseCode);
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrollmentDate = LocalDateTime.now();
    }
    
    private String generateEnrollmentId(String studentId, String courseCode) {
        return "ENR_" + studentId + "_" + courseCode + "_" + System.currentTimeMillis();
    }
    
    // Getters
    public String getEnrollmentId() { return enrollmentId; }
    public String getStudentId() { return studentId; }
    public String getCourseCode() { return courseCode; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
    public Grade getGrade() { return grade; }
    public Double getMarks() { return marks; }
    
    // Setters for grade and marks
    public void setGrade(Grade grade, Double marks) {
        this.grade = grade;
        this.marks = marks;
    }
    
    @Override
    public String toString() {
        String gradeInfo = grade != null ? 
            String.format(" | Grade: %s (%.2f)", grade, marks) : " | Not Graded";
        return String.format("Enrollment[%s] Student: %s, Course: %s%s", 
            enrollmentId, studentId, courseCode, gradeInfo);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return enrollmentId.equals(that.enrollmentId);
    }
    
    @Override
    public int hashCode() {
        return enrollmentId.hashCode();
    }
}