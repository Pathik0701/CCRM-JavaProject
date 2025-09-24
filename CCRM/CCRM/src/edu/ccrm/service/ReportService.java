package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for generating reports using Stream API
 */
public class ReportService {
    
    /**
     * Show top students by GPA using Stream API
     */
    public void showTopStudentsByGPA(List<Student> students, EnrollmentService enrollmentService) {
        System.out.println("\n=== TOP STUDENTS BY GPA ===");
        
        Map<Student, Double> studentGPAs = students.stream()
            .collect(Collectors.toMap(
                student -> student,
                student -> enrollmentService.calculateStudentGPA(student.getId())
            ));
        
        studentGPAs.entrySet().stream()
            .filter(entry -> entry.getValue() > 0.0)
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
            .limit(10)
            .forEach(entry -> {
                System.out.printf("%-20s %-15s GPA: %.2f%n", 
                    entry.getKey().getFullName(),
                    entry.getKey().getRegNo(),
                    entry.getValue());
            });
    }
    
    /**
     * Show GPA distribution using Stream API grouping
     */
    public void showGPADistribution(List<Student> students, EnrollmentService enrollmentService) {
        System.out.println("\n=== GPA DISTRIBUTION ===");
        
        Map<String, Long> distribution = students.stream()
            .map(student -> enrollmentService.calculateStudentGPA(student.getId()))
            .filter(gpa -> gpa > 0.0)
            .collect(Collectors.groupingBy(
                this::getGPARange,
                Collectors.counting()
            ));
        
        distribution.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByKey())
            .forEach(entry -> {
                System.out.printf("%s: %d students%n", entry.getKey(), entry.getValue());
            });
    }
    
    private String getGPARange(Double gpa) {
        if (gpa >= 9.0) return "9.0 - 10.0 (Excellent)";
        if (gpa >= 8.0) return "8.0 - 8.9 (Very Good)";
        if (gpa >= 7.0) return "7.0 - 7.9 (Good)";
        if (gpa >= 6.0) return "6.0 - 6.9 (Average)";
        return "Below 6.0 (Poor)";
    }
    
    /**
     * Show course enrollment statistics
     */
    public void showCourseEnrollmentStats(List<Course> courses, EnrollmentService enrollmentService) {
        System.out.println("\n=== COURSE ENROLLMENT STATISTICS ===");
        System.out.printf("%-10s %-30s %-15s %-10s%n", "Code", "Title", "Department", "Enrolled");
        System.out.println("=".repeat(70));
        
        courses.stream()
            .sorted(Comparator.comparing(Course::getCode))
            .forEach(course -> {
                int enrollmentCount = enrollmentService.getCourseEnrollments(course.getCode()).size();
                System.out.printf("%-10s %-30s %-15s %-10d%n",
                    course.getCode(),
                    course.getTitle().length() > 30 ? 
                        course.getTitle().substring(0, 27) + "..." : course.getTitle(),
                    course.getDepartment() != null ? course.getDepartment() : "N/A",
                    enrollmentCount);
            });
    }
    
    /**
     * Show department summary using Stream API
     */
    public void showDepartmentSummary(List<Course> courses) {
        System.out.println("\n=== DEPARTMENT SUMMARY ===");
        
        Map<String, List<Course>> departmentCourses = courses.stream()
            .filter(course -> course.getDepartment() != null)
            .collect(Collectors.groupingBy(Course::getDepartment));
        
        departmentCourses.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> {
                String dept = entry.getKey();
                List<Course> deptCourses = entry.getValue();
                int totalCredits = deptCourses.stream().mapToInt(Course::getCredits).sum();
                
                System.out.printf("Department: %s%n", dept);
                System.out.printf("  Courses: %d%n", deptCourses.size());
                System.out.printf("  Total Credits: %d%n", totalCredits);
                System.out.println();
            });
    }
}
