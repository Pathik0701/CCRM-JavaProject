package edu.ccrm.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Transcript class with Builder pattern
 */
public class Transcript {
    private final Student student;
    private final List<TranscriptEntry> entries;
    private final LocalDateTime generatedAt;
    private final double overallGPA;
    private final int totalCredits;
    
    private Transcript(Builder builder) {
        this.student = builder.student;
        this.entries = new ArrayList<>(builder.entries);
        this.generatedAt = LocalDateTime.now();
        this.overallGPA = builder.calculateGPA();
        this.totalCredits = builder.calculateTotalCredits();
    }
    
    // Getters
    public Student getStudent() { return student; }
    public List<TranscriptEntry> getEntries() { return new ArrayList<>(entries); }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public double getOverallGPA() { return overallGPA; }
    public int getTotalCredits() { return totalCredits; }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TRANSCRIPT ===\n");
        sb.append("Student: ").append(student.getFullName()).append("\n");
        sb.append("Registration No: ").append(student.getRegNo()).append("\n");
        sb.append("Generated: ").append(generatedAt).append("\n");
        sb.append("Overall GPA: ").append(String.format("%.2f", overallGPA)).append("\n");
        sb.append("Total Credits: ").append(totalCredits).append("\n\n");
        
        sb.append("Course Details:\n");
        sb.append("================\n");
        for (TranscriptEntry entry : entries) {
            sb.append(entry.toString()).append("\n");
        }
        
        return sb.toString();
    }
    
    // Static nested class for building Transcript
    public static class Builder {
        private Student student;
        private List<TranscriptEntry> entries = new ArrayList<>();
        
        public Builder setStudent(Student student) {
            this.student = student;
            return this;
        }
        
        public Builder addEntry(TranscriptEntry entry) {
            this.entries.add(entry);
            return this;
        }
        
        public Builder addEntries(List<TranscriptEntry> entries) {
            this.entries.addAll(entries);
            return this;
        }
        
        private double calculateGPA() {
            if (entries.isEmpty()) return 0.0;
            
            double totalGradePoints = 0.0;
            int totalCredits = 0;
            
            for (TranscriptEntry entry : entries) {
                if (entry.getGrade() != null) {
                    totalGradePoints += entry.getGrade().getGradePoints() * entry.getCredits();
                    totalCredits += entry.getCredits();
                }
            }
            
            return totalCredits > 0 ? totalGradePoints / totalCredits : 0.0;
        }
        
        private int calculateTotalCredits() {
            return entries.stream().mapToInt(TranscriptEntry::getCredits).sum();
        }
        
        public Transcript build() {
            if (student == null) {
                throw new IllegalArgumentException("Student is required");
            }
            return new Transcript(this);
        }
    }
    
    // Inner class for transcript entries
    public static class TranscriptEntry {
        private final String courseCode;
        private final String courseTitle;
        private final int credits;
        private final Grade grade;
        private final Double marks;
        
        public TranscriptEntry(String courseCode, String courseTitle, int credits, Grade grade, Double marks) {
            this.courseCode = courseCode;
            this.courseTitle = courseTitle;
            this.credits = credits;
            this.grade = grade;
            this.marks = marks;
        }
        
        // Getters
        public String getCourseCode() { return courseCode; }
        public String getCourseTitle() { return courseTitle; }
        public int getCredits() { return credits; }
        public Grade getGrade() { return grade; }
        public Double getMarks() { return marks; }
        
        @Override
        public String toString() {
            String gradeInfo = grade != null ? 
                String.format("%s (%.2f) - %.1f points", grade.name(), marks, grade.getGradePoints()) 
                : "Not Graded";
            return String.format("%-10s | %-30s | %d credits | %s", 
                courseCode, courseTitle, credits, gradeInfo);
        }
    }
}
