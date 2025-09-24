package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File operations service using NIO.2
 * Demonstrates Path API, Files class, and Stream operations
 */
public class FileOperationService {
    private final AppConfig config;
    
    public FileOperationService() {
        this.config = AppConfig.getInstance();
    }
    
    /**
     * Import students from CSV file using NIO.2
     */
    public void importStudents(String filename, StudentService studentService) throws IOException {
        Path filePath = Paths.get(filename);
        
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
            List<String> dataLines = lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
                
            for (String line : dataLines) {
                try {
                    List<String> fields = CSVParser.parseLine(line);
                    if (fields.size() >= 4) {
                        Student student = new Student(
                            fields.get(0), // ID
                            fields.get(1), // RegNo
                            fields.get(2), // FullName
                            fields.get(3)  // Email
                        );
                        studentService.addStudent(student);
                    }
                } catch (Exception e) {
                    System.err.println("Error importing student from line: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Import courses from CSV file
     */
    public void importCourses(String filename, CourseService courseService) throws IOException {
        Path filePath = Paths.get(filename);
        
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        
        try (Stream<String> lines = Files.lines(filePath)) {
            List<String> dataLines = lines
                .skip(1) // Skip header
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
                
            for (String line : dataLines) {
                try {
                    List<String> fields = CSVParser.parseLine(line);
                    if (fields.size() >= 6) {
                        // Create instructor
                        Instructor instructor = new Instructor(
                            "INST_" + System.currentTimeMillis(),
                            "I" + System.currentTimeMillis(),
                            fields.get(3), // Instructor name
                            fields.get(3).toLowerCase().replace(" ", ".") + "@university.edu"
                        );
                        
                        // Parse semester
                        Semester semester = Semester.valueOf(fields.get(5).toUpperCase());
                        
                        Course course = new Course.Builder()
                            .setCode(fields.get(0))
                            .setTitle(fields.get(1))
                            .setCredits(Integer.parseInt(fields.get(2)))
                            .setInstructor(instructor)
                            .setDepartment(fields.get(4))
                            .setSemester(semester)
                            .build();
                            
                        courseService.addCourse(course);
                    }
                } catch (Exception e) {
                    System.err.println("Error importing course from line: " + line);
                    System.err.println("Error: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Export students to CSV file using NIO.2
     */
    public void exportStudents(List<Student> students, String filename) throws IOException {
        Path filePath = Paths.get(filename);
        Files.createDirectories(filePath.getParent());
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            // Write header
            writer.write("ID,RegNo,FullName,Email,Status,EnrollmentDate");
            writer.newLine();
            
            // Write data
            for (Student student : students) {
                String line = String.join(",",
                    student.getId(),
                    student.getRegNo(),
                    "\"" + student.getFullName() + "\"",
                    student.getEmail(),
                    student.getStatus().toString(),
                    student.getEnrollmentDate().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    /**
     * Export courses to CSV file
     */
    public void exportCourses(List<Course> courses, String filename) throws IOException {
        Path filePath = Paths.get(filename);
        Files.createDirectories(filePath.getParent());
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            // Write header
            writer.write("Code,Title,Credits,Instructor,Department,Semester");
            writer.newLine();
            
            // Write data
            for (Course course : courses) {
                String line = String.join(",",
                    course.getCode(),
                    "\"" + course.getTitle() + "\"",
                    String.valueOf(course.getCredits()),
                    course.getInstructor() != null ? 
                        "\"" + course.getInstructor().getFullName() + "\"" : "\"\"",
                    course.getDepartment() != null ? course.getDepartment() : "",
                    course.getSemester() != null ? course.getSemester().toString() : ""
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    /**
     * Create backup with timestamp using NIO.2
     */
    public void createBackup(StudentService studentService, CourseService courseService, 
                           EnrollmentService enrollmentService) throws IOException {
        
        // Create timestamped backup directory
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path backupDir = config.getBackupPath().resolve("backup_" + timestamp);
        Files.createDirectories(backupDir);
        
        // Export all data to backup directory
        exportStudents(studentService.getAllStudents(), 
            backupDir.resolve("students.csv").toString());
        exportCourses(courseService.getAllCourses(), 
            backupDir.resolve("courses.csv").toString());
        
        // Export enrollments
        exportEnrollments(enrollmentService.getAllEnrollments(), 
            backupDir.resolve("enrollments.csv").toString());
        
        System.out.println("Backup created in: " + backupDir.toAbsolutePath());
    }
    
    /**
     * Export enrollments to CSV
     */
    private void exportEnrollments(List<Enrollment> enrollments, String filename) throws IOException {
        Path filePath = Paths.get(filename);
        
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            // Write header
            writer.write("EnrollmentId,StudentId,CourseCode,EnrollmentDate,Grade,Marks");
            writer.newLine();
            
            // Write data
            for (Enrollment enrollment : enrollments) {
                String line = String.join(",",
                    enrollment.getEnrollmentId(),
                    enrollment.getStudentId(),
                    enrollment.getCourseCode(),
                    enrollment.getEnrollmentDate().toString(),
                    enrollment.getGrade() != null ? enrollment.getGrade().toString() : "",
                    enrollment.getMarks() != null ? enrollment.getMarks().toString() : ""
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }
    
    /**
     * Recursively calculate backup directory size
     * Demonstrates recursion and NIO.2 file walking
     */
    public long calculateBackupDirectorySize() {
        try {
            Path backupPath = config.getBackupPath();
            if (!Files.exists(backupPath)) {
                return 0;
            }
            
            return Files.walk(backupPath)
                .filter(Files::isRegularFile)
                .mapToLong(this::getFileSize)
                .sum();
                
        } catch (IOException e) {
            System.err.println("Error calculating backup size: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Recursively list backup files by depth
     */
    public void listBackupFiles() {
        try {
            Path backupPath = config.getBackupPath();
            if (!Files.exists(backupPath)) {
                System.out.println("No backup directory found.");
                return;
            }
            
            System.out.println("\n=== BACKUP FILES ===");
            Files.walk(backupPath)
                .sorted()
                .forEach(path -> {
                    int depth = path.getNameCount() - backupPath.getNameCount();
                    String indent = "  ".repeat(depth);
                    String name = path.getFileName().toString();
                    
                    if (Files.isDirectory(path)) {
                        System.out.println(indent + "[DIR] " + name);
                    } else {
                        long size = getFileSize(path);
                        System.out.println(indent + "[FILE] " + name + " (" + formatFileSize(size) + ")");
                    }
                });
                
        } catch (IOException e) {
            System.err.println("Error listing backup files: " + e.getMessage());
        }
    }
    
    private long getFileSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0;
        }
    }
    
    /**
     * Format file size in human readable format
     */
    public String formatFileSize(long size) {
        if (size < 1024) return size + " B";
        if (size < 1024 * 1024) return String.format("%.1f KB", size / 1024.0);
        if (size < 1024 * 1024 * 1024) return String.format("%.1f MB", size / (1024.0 * 1024));
        return String.format("%.1f GB", size / (1024.0 * 1024 * 1024));
    }
}
