package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.exceptions.DuplicateStudentException;
import edu.ccrm.exceptions.StudentNotFoundException;
import edu.ccrm.exceptions.InvalidDataException;
import edu.ccrm.util.ValidationUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for student operations
 * Demonstrates Collections, Stream API, Lambda expressions
 */
public class StudentService {
    private final Map<String, Student> students;
    
    public StudentService() {
        this.students = new HashMap<>();
    }
    
    /**
     * Add a new student with validation
     */
    public void addStudent(Student student) throws DuplicateStudentException, InvalidDataException {
        // Validation
        if (student == null) {
            throw new InvalidDataException("Student cannot be null");
        }
        
        if (!ValidationUtils.isNotEmpty(student.getId())) {
            throw new InvalidDataException("Student ID is required");
        }
        
        if (!ValidationUtils.isValidEmail(student.getEmail())) {
            throw new InvalidDataException("Invalid email format");
        }
        
        if (students.containsKey(student.getId())) {
            throw new DuplicateStudentException("Student with ID " + student.getId() + " already exists");
        }
        
        // Check for duplicate registration number using Stream API
        boolean regNoExists = students.values().stream()
            .anyMatch(s -> s.getRegNo().equals(student.getRegNo()));
            
        if (regNoExists) {
            throw new DuplicateStudentException("Student with registration number " + student.getRegNo() + " already exists");
        }
        
        students.put(student.getId(), student);
    }
    
    /**
     * Find student by ID
     */
    public Student findStudentById(String id) {
        return students.get(id);
    }
    
    /**
     * Find student by registration number using Stream API
     */
    public Student findStudentByRegNo(String regNo) {
        return students.values().stream()
            .filter(student -> student.getRegNo().equals(regNo))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Get all students sorted by name using lambda expressions
     */
    public List<Student> getAllStudents() {
        return students.values().stream()
            .sorted((s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName()))
            .collect(Collectors.toList());
    }
    
    /**
     * Get active students only
     */
    public List<Student> getActiveStudents() {
        return students.values().stream()
            .filter(student -> student.getStatus() == Student.StudentStatus.ACTIVE)
            .sorted(Comparator.comparing(Student::getFullName))
            .collect(Collectors.toList());
    }
    
    /**
     * Search students by name (partial match)
     */
    public List<Student> searchStudentsByName(String namePattern) {
        String pattern = namePattern.toLowerCase();
        return students.values().stream()
            .filter(student -> student.getFullName().toLowerCase().contains(pattern))
            .sorted(Comparator.comparing(Student::getFullName))
            .collect(Collectors.toList());
    }
    
    /**
     * Update student information
     */
    public void updateStudent(Student student) throws StudentNotFoundException {
        if (!students.containsKey(student.getId())) {
            throw new StudentNotFoundException("Student with ID " + student.getId() + " not found");
        }
        students.put(student.getId(), student);
    }
    
    /**
     * Deactivate a student
     */
    public void deactivateStudent(String studentId) throws StudentNotFoundException {
        Student student = students.get(studentId);
        if (student == null) {
            throw new StudentNotFoundException("Student with ID " + studentId + " not found");
        }
        student.setStatus(Student.StudentStatus.INACTIVE);
    }
    
    /**
     * Get total student count
     */
    public int getTotalStudentCount() {
        return students.size();
    }
    
    /**
     * Get student count by status using Stream API grouping
     */
    public Map<Student.StudentStatus, Long> getStudentCountByStatus() {
        return students.values().stream()
            .collect(Collectors.groupingBy(
                Student::getStatus,
                Collectors.counting()
            ));
    }
}
