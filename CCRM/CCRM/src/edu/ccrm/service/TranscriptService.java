package edu.ccrm.service;

import edu.ccrm.domain.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for transcript generation
 */
public class TranscriptService {
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    
    public TranscriptService() {
        this.enrollmentService = new EnrollmentService();
        this.courseService = new CourseService();
    }
    
    public TranscriptService(EnrollmentService enrollmentService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
    }
    
    /**
     * Generate transcript for student using Builder pattern
     */
    public Transcript generateTranscript(Student student) {
        List<Enrollment> studentEnrollments = enrollmentService.getStudentEnrollments(student.getId());
        
        List<Transcript.TranscriptEntry> entries = studentEnrollments.stream()
            .map(this::createTranscriptEntry)
            .filter(entry -> entry != null)
            .collect(Collectors.toList());
        
        return new Transcript.Builder()
            .setStudent(student)
            .addEntries(entries)
            .build();
    }
    
    private Transcript.TranscriptEntry createTranscriptEntry(Enrollment enrollment) {
        Course course = courseService.findCourseByCode(enrollment.getCourseCode());
        if (course == null) {
            return null;
        }
        
        return new Transcript.TranscriptEntry(
            course.getCode(),
            course.getTitle(),
            course.getCredits(),
            enrollment.getGrade(),
            enrollment.getMarks()
        );
    }
}
