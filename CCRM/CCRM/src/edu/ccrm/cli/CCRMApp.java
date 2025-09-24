package edu.ccrm.cli;

import edu.ccrm.service.*;
import edu.ccrm.domain.*;
import edu.ccrm.io.FileOperationService;
import edu.ccrm.util.ValidationUtils;
import edu.ccrm.exceptions.*;

import java.util.Scanner;
import java.util.List;

/**
 * Main CLI Application for Campus Course & Records Manager
 * Demonstrates: Switch statements, loops, exception handling, design patterns
 */
public class CCRMApp {
    private final Scanner scanner;
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final TranscriptService transcriptService;
    private final ReportService reportService;
    private final FileOperationService fileService;
   
    
    public CCRMApp() {
        this.scanner = new Scanner(System.in);
       
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.enrollmentService = new EnrollmentService();
        this.transcriptService = new TranscriptService(enrollmentService, courseService);
        this.reportService = new ReportService();
        this.fileService = new FileOperationService();
        
        initializeSampleData();
    }
    
    public static void main(String[] args) {
        // Enable assertions (demonstrate assertion usage)
        
        
        System.out.println("=== Campus Course & Records Manager ===");
        System.out.println("Java Platform: " + getJavaPlatformInfo());
        System.out.println("JVM Version: " + System.getProperty("java.version"));
        
        CCRMApp app = new CCRMApp();
        app.run();
    }
    
    private static String getJavaPlatformInfo() {
        return "Java SE (Standard Edition) - Desktop application platform with full JDK features";
    }


/**
     * Initialize sample data for demonstration
     */
    private void initializeSampleData() {
        try {
            // Add sample students
            Student student1 = new Student("STU001", "2023001", "Alice Johnson", "alice.johnson@student.edu");
            Student student2 = new Student("STU002", "2023002", "Bob Smith", "bob.smith@student.edu");
            Student student3 = new Student("STU003", "2023003", "Carol Davis", "carol.davis@student.edu");
            
            studentService.addStudent(student1);
            studentService.addStudent(student2);
            studentService.addStudent(student3);
            
            // Add sample instructors and courses
            Instructor instructor1 = new Instructor("INST001", "I001", "Dr. John Wilson", "john.wilson@university.edu", "Computer Science");
            Instructor instructor2 = new Instructor("INST002", "I002", "Prof. Sarah Brown", "sarah.brown@university.edu", "Mathematics");
            
            Course course1 = new Course.Builder()
                .setCode("CS101")
                .setTitle("Introduction to Programming")
                .setCredits(3)
                .setInstructor(instructor1)
                .setDepartment("Computer Science")
                .setSemester(Semester.FALL)
                .build();
                
            Course course2 = new Course.Builder()
                .setCode("MATH201")
                .setTitle("Calculus II")
                .setCredits(4)
                .setInstructor(instructor2)
                .setDepartment("Mathematics")
                .setSemester(Semester.FALL)
                .build();
                
            courseService.addCourse(course1);
            courseService.addCourse(course2);
            
            // Add sample enrollments
            enrollmentService.enrollStudent(student1, course1);
            enrollmentService.enrollStudent(student1, course2);
            enrollmentService.enrollStudent(student2, course1);
            
            // Add sample grades
            enrollmentService.recordGrade("STU001", "CS101", Grade.A, 85.5);
            enrollmentService.recordGrade("STU001", "MATH201", Grade.B, 78.0);
            enrollmentService.recordGrade("STU002", "CS101", Grade.A, 92.0);
            
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
    
    public void run() {
        boolean running = true;
        
        // Demonstrate while loop and labeled break
        mainLoop: while (running) {
            displayMainMenu();
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                // Enhanced switch statement (Java 14+ style)
                switch (choice) {
                    case 1 -> handleStudentManagement();
                    case 2 -> handleCourseManagement();
                    case 3 -> handleEnrollmentManagement();
                    case 4 -> handleGradingManagement();
                    case 5 -> handleFileOperations();
                    case 6 -> handleReports();
                    case 7 -> handleBackupOperations();
                    case 8 -> {
                        System.out.println("Thank you for using CCRM!");
                        running = false;
                        break mainLoop; // Labeled break demonstration
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== CAMPUS COURSE & RECORDS MANAGER ===");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. Grading Management");
        System.out.println("5. File Operations (Import/Export)");
        System.out.println("6. Reports & Analytics");
        System.out.println("7. Backup Operations");
        System.out.println("8. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice: ");
    }

// Student Management Methods
    private void handleStudentManagement() {
        boolean back = false;
        
        do {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add Student");
            System.out.println("2. List All Students");
            System.out.println("3. Search Students");
            System.out.println("4. Update Student");
            System.out.println("5. View Student Profile & Transcript");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> listAllStudents();
                    case 3 -> searchStudents();
                    case 4 -> updateStudent();
                    case 5 -> viewStudentProfileAndTranscript();
                    case 6 -> back = true;
                    default -> System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (!back);
    }
    
    private void addStudent() {
        try {
            System.out.print("Enter student ID: ");
            String id = scanner.nextLine().trim();
            
            System.out.print("Enter registration number: ");
            String regNo = scanner.nextLine().trim();
            
            System.out.print("Enter full name: ");
            String fullName = scanner.nextLine().trim();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            
            // Assertion for non-null values
            assert id != null && !id.isEmpty() : "Student ID cannot be empty";
            
            Student student = new Student(id, regNo, fullName, email);
            studentService.addStudent(student);
            
            System.out.println(" Student added successfully!");
            
        } catch (DuplicateStudentException | InvalidDataException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Unexpected error: " + e.getMessage());
        }
    }
    
    private void listAllStudents() {
        List<Student> students = studentService.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println("\n=== STUDENT LIST ===");
        System.out.printf("%-10s %-15s %-25s %-30s %-10s%n", "ID", "Reg No", "Name", "Email", "Status");
        System.out.println("=".repeat(95));
        
        // Enhanced for loop demonstration
        for (Student student : students) {
            System.out.printf("%-10s %-15s %-25s %-30s %-10s%n",
                student.getId(),
                student.getRegNo(),
                student.getFullName().length() > 25 ? 
                    student.getFullName().substring(0, 22) + "..." : student.getFullName(),
                student.getEmail().length() > 30 ?
                    student.getEmail().substring(0, 27) + "..." : student.getEmail(),
                student.getStatus());
        }
        
        System.out.println("\nTotal students: " + students.size());
    }
    
    private void searchStudents() {
        System.out.print("Enter name to search: ");
        String searchTerm = scanner.nextLine().trim();
        
        if (searchTerm.isEmpty()) {
            System.out.println("Search term cannot be empty.");
            return;
        }
        
        List<Student> results = studentService.searchStudentsByName(searchTerm);
        
        if (results.isEmpty()) {
            System.out.println("No students found matching: " + searchTerm);
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            results.forEach(System.out::println); // Lambda expression
        }
    }
    
    private void updateStudent() {
        System.out.print("Enter student ID to update: ");
        String id = scanner.nextLine().trim();
        
        try {
            Student student = studentService.findStudentById(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            
            System.out.println("Current details: " + student);
            
            System.out.print("Enter new full name (press Enter to keep current): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                student.setFullName(newName);
            }
            
            System.out.print("Enter new email (press Enter to keep current): ");
            String newEmail = scanner.nextLine().trim();
            if (!newEmail.isEmpty()) {
                if (ValidationUtils.isValidEmail(newEmail)) {
                    student.setEmail(newEmail);
                } else {
                    throw new InvalidDataException("Invalid email format");
                }
            }
            
            studentService.updateStudent(student);
            System.out.println(" Student updated successfully!");
            
        } catch (Exception e) {
            System.out.println(" Error updating student: " + e.getMessage());
        }
    }
    
    private void viewStudentProfileAndTranscript() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();
        
        try {
            Student student = studentService.findStudentById(id);
            if (student == null) {
                System.out.println("Student not found.");
                return;
            }
            
            // Display profile
            System.out.println("\n" + student.getDetailedProfile());
            
            // Generate and display transcript using Builder pattern
            Transcript transcript = transcriptService.generateTranscript(student);
            System.out.println(transcript.toString());
            
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    // Course Management Methods
    private void handleCourseManagement() {
        System.out.println("\n=== COURSE MANAGEMENT ===");
        System.out.println("1. Add Course");
        System.out.println("2. List All Courses");
        System.out.println("3. Search Courses");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> listAllCourses();
                case 3 -> searchCourses();
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private void addCourse() {
        try {
            System.out.print("Enter course code (e.g., CS101): ");
            String code = scanner.nextLine().trim().toUpperCase();
            
            if (!ValidationUtils.isValidCourseCode(code)) {
                throw new InvalidDataException("Invalid course code format. Use format like CS101 or MATH1001");
            }
            
            System.out.print("Enter course title: ");
            String title = scanner.nextLine().trim();
            
            System.out.print("Enter credits (1-6): ");
            int credits = Integer.parseInt(scanner.nextLine());
            
            if (!ValidationUtils.isValidCreditRange(credits)) {
                throw new InvalidDataException("Credits must be between 1 and 6");
            }
            
            System.out.print("Enter instructor name: ");
            String instructorName = scanner.nextLine().trim();
            
            System.out.print("Enter department: ");
            String department = scanner.nextLine().trim();
            
            // Display semester options using enum values
            System.out.println("Select semester:");
            Semester[] semesters = Semester.values();
            for (int i = 0; i < semesters.length; i++) {
                System.out.printf("%d. %s%n", i + 1, semesters[i]);
            }
            
            int semChoice = Integer.parseInt(scanner.nextLine()) - 1;
            if (semChoice < 0 || semChoice >= semesters.length) {
                throw new InvalidDataException("Invalid semester selection");
            }
            
            // Create instructor
            Instructor instructor = new Instructor(
                "INST_" + System.currentTimeMillis(),
                "I" + System.currentTimeMillis(),
                instructorName,
                instructorName.toLowerCase().replace(" ", ".") + "@university.edu",
                department
            );
            
            // Builder pattern demonstration
            Course course = new Course.Builder()
                .setCode(code)
                .setTitle(title)
                .setCredits(credits)
                .setInstructor(instructor)
                .setDepartment(department)
                .setSemester(semesters[semChoice])
                .build();
                
            courseService.addCourse(course);
            System.out.println(" Course added successfully!");
            
        } catch (NumberFormatException e) {
            System.out.println(" Please enter valid numbers for numeric fields.");
        } catch (Exception e) {
            System.out.println(" Error adding course: " + e.getMessage());
        }
    }
    
    private void listAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        
        System.out.println("\n=== COURSE LIST ===");
        courses.forEach(System.out::println); // Method reference
        System.out.println("\nTotal courses: " + courses.size());
    }
    
    private void searchCourses() {
        System.out.println("Search by:");
        System.out.println("1. Department");
        System.out.println("2. Instructor");
        System.out.println("3. Semester");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            List<Course> results;
            
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter department: ");
                    String dept = scanner.nextLine().trim();
                    results = courseService.searchByDepartment(dept);
                }
                case 2 -> {
                    System.out.print("Enter instructor name: ");
                    String instructor = scanner.nextLine().trim();
                    results = courseService.searchByInstructor(instructor);
                }
                case 3 -> {
                    System.out.println("Select semester:");
                    Semester[] semesters = Semester.values();
                    for (int i = 0; i < semesters.length; i++) {
                        System.out.printf("%d. %s%n", i + 1, semesters[i]);
                    }
                    int semChoice = Integer.parseInt(scanner.nextLine()) - 1;
                    results = courseService.searchBySemester(semesters[semChoice]);
                }
                default -> {
                    System.out.println("Invalid choice.");
                    return;
                }
            }
            
            if (results.isEmpty()) {
                System.out.println("No courses found matching criteria.");
            } else {
                System.out.println("\n=== SEARCH RESULTS ===");
                results.forEach(System.out::println);
            }
            
        } catch (Exception e) {
            System.out.println(" Error searching courses: " + e.getMessage());
        }
    }
    
    // Enrollment Management Methods
    private void handleEnrollmentManagement() {
        System.out.println("\n=== ENROLLMENT MANAGEMENT ===");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. View Student Enrollments");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> viewStudentEnrollments();
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private void enrollStudent() {
        try {
            System.out.print("Enter student ID: ");
            String studentId = scanner.nextLine().trim();
            
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();
            
            Student student = studentService.findStudentById(studentId);
            Course course = courseService.findCourseByCode(courseCode);
            
            if (student == null) {
                System.out.println(" Student not found.");
                return;
            }
            
            if (course == null) {
                System.out.println(" Course not found.");
                return;
            }
            
            enrollmentService.enrollStudent(student, course);
            System.out.println(" Student enrolled successfully!");
            System.out.println("Student: " + student.getFullName());
            System.out.println("Course: " + course.getTitle());
            
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println(" Enrollment failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    private void viewStudentEnrollments() {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine().trim();
        
        try {
            Student student = studentService.findStudentById(studentId);
            if (student == null) {
                System.out.println(" Student not found.");
                return;
            }
            
            List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
            
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
            } else {
                System.out.println("\n=== STUDENT ENROLLMENTS ===");
                System.out.println("Student: " + student.getFullName());
                System.out.println("=".repeat(60));
                
                for (Enrollment enrollment : enrollments) {
                    Course course = courseService.findCourseByCode(enrollment.getCourseCode());
                    System.out.printf("%-10s | %-30s | %s%n",
                        enrollment.getCourseCode(),
                        course != null ? course.getTitle() : "Unknown Course",
                        enrollment.getGrade() != null ? 
                            "Grade: " + enrollment.getGrade() + " (" + enrollment.getMarks() + ")" :
                            "Not Graded"
                    );
                }
            }
            
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    // Grading Management Methods
    private void handleGradingManagement() {
        System.out.println("\n=== GRADING MANAGEMENT ===");
        System.out.println("1. Record Grade");
        System.out.println("2. View Course Grades");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> recordGrade();
                case 2 -> viewCourseGrades();
                default -> System.out.println("Invalid choice.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
    private void recordGrade() {
        try {
            System.out.print("Enter student ID: ");
            String studentId = scanner.nextLine().trim();
            
            System.out.print("Enter course code: ");
            String courseCode = scanner.nextLine().trim().toUpperCase();
            
            System.out.print("Enter marks (0-100): ");
            double marks = Double.parseDouble(scanner.nextLine());
            
            if (!ValidationUtils.isValidMarks(marks)) {
                throw new InvalidDataException("Marks must be between 0 and 100");
            }
            
            Grade grade = Grade.fromMarks(marks);
            enrollmentService.recordGrade(studentId, courseCode, grade, marks);
            
            System.out.println(" Grade recorded successfully!");
            System.out.println("Marks: " + marks);
            System.out.println("Grade: " + grade + " (" + grade.getDescription() + ")");
            System.out.println("Grade Points: " + grade.getGradePoints());
            
        } catch (EnrollmentNotFoundException | InvalidDataException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(" Please enter valid marks.");
        }
    }
    
    private void viewCourseGrades() {
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        
        try {
            Course course = courseService.findCourseByCode(courseCode);
            if (course == null) {
                System.out.println(" Course not found.");
                return;
            }
            
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseCode);
            
            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this course.");
            } else {
                System.out.println("\n=== COURSE GRADES ===");
                System.out.println("Course: " + course.getTitle() + " (" + courseCode + ")");
                System.out.println("=".repeat(80));
                System.out.printf("%-15s %-25s %-10s %-10s %-15s%n", 
                    "Student ID", "Student Name", "Grade", "Marks", "Grade Points");
                System.out.println("-".repeat(80));
                
                for (Enrollment enrollment : enrollments) {
                    Student student = studentService.findStudentById(enrollment.getStudentId());
                    String studentName = student != null ? student.getFullName() : "Unknown";
                    
                    if (enrollment.getGrade() != null) {
                        System.out.printf("%-15s %-25s %-10s %-10.2f %-15.1f%n",
                            enrollment.getStudentId(),
                            studentName.length() > 25 ? studentName.substring(0, 22) + "..." : studentName,
                            enrollment.getGrade(),
                            enrollment.getMarks(),
                            enrollment.getGrade().getGradePoints()
                        );
                    } else {
                        System.out.printf("%-15s %-25s %-10s %-10s %-15s%n",
                            enrollment.getStudentId(),
                            studentName.length() > 25 ? studentName.substring(0, 22) + "..." : studentName,
                            "N/A", "N/A", "N/A"
                        );
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }
    
    // File Operations
    private void handleFileOperations() {
        System.out.println("\n=== FILE OPERATIONS ===");
        System.out.println("1. Import Students from CSV");
        System.out.println("2. Import Courses from CSV");
        System.out.println("3. Export Students to CSV");
        System.out.println("4. Export Courses to CSV");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter CSV file path (default: test-data/students.csv): ");
                    String path = scanner.nextLine().trim();
                    if (path.isEmpty()) path = "test-data/students.csv";
                    fileService.importStudents(path, studentService);
                    System.out.println(" Students imported successfully!");
                }
                case 2 -> {
                    System.out.print("Enter CSV file path (default: test-data/courses.csv): ");
                    String path = scanner.nextLine().trim();
                    if (path.isEmpty()) path = "test-data/courses.csv";
                    fileService.importCourses(path, courseService);
                    System.out.println(" Courses imported successfully!");
                }
                case 3 -> {
                    fileService.exportStudents(studentService.getAllStudents(), "exports/students_export.csv");
                    System.out.println(" Students exported to exports/students_export.csv");
                }
                case 4 -> {
                    fileService.exportCourses(courseService.getAllCourses(), "exports/courses_export.csv");
                    System.out.println(" Courses exported to exports/courses_export.csv");
                }
                default -> System.out.println("Invalid choice.");
            }
            
        } catch (Exception e) {
            System.out.println(" File operation failed: " + e.getMessage());
        }
    }
    
    // Reports
    private void handleReports() {
        System.out.println("\n=== REPORTS & ANALYTICS ===");
        System.out.println("1. Top Students by GPA");
        System.out.println("2. GPA Distribution");
        System.out.println("3. Course Enrollment Statistics");
        System.out.println("4. Department Summary");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> reportService.showTopStudentsByGPA(studentService.getAllStudents(), enrollmentService);
                case 2 -> reportService.showGPADistribution(studentService.getAllStudents(), enrollmentService);
                case 3 -> reportService.showCourseEnrollmentStats(courseService.getAllCourses(), enrollmentService);
                case 4 -> reportService.showDepartmentSummary(courseService.getAllCourses());
                default -> System.out.println("Invalid choice.");
            }
            
        } catch (Exception e) {
            System.out.println(" Error generating report: " + e.getMessage());
        }
    }
    
    // Backup Operations
    private void handleBackupOperations() {
        System.out.println("\n=== BACKUP OPERATIONS ===");
        System.out.println("1. Create Backup");
        System.out.println("2. Show Backup Directory Size (Recursive)");
        System.out.println("3. List Backup Files");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1 -> {
                    fileService.createBackup(studentService, courseService, enrollmentService);
                    System.out.println(" Backup created successfully!");
                }
                case 2 -> {
                    long size = fileService.calculateBackupDirectorySize();
                    System.out.println("Total backup directory size: " + fileService.formatFileSize(size));
                }
                case 3 -> fileService.listBackupFiles();
                default -> System.out.println("Invalid choice.");
            }
            
        } catch (Exception e) {
            System.out.println(" Backup operation failed: " + e.getMessage());
        }
    }
}