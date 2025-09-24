package edu.ccrm.domain;

import java.time.LocalDateTime;

/**
 * Abstract base class demonstrating inheritance and abstraction
 */
public abstract class Person {
    protected final String id;
    protected final String regNo;
    protected String fullName;
    protected String email;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    
    public Person(String id, String regNo, String fullName, String email) {
        this.id = id;
        this.regNo = regNo;
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters
    public String getId() { return id; }
    public String getRegNo() { return regNo; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    
    // Setters
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
        this.updatedAt = LocalDateTime.now();
    }
    
    public void setEmail(String email) { 
        this.email = email; 
        this.updatedAt = LocalDateTime.now();
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract String getRole();
    public abstract String getDetailedProfile();
    
    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", 
            getRole(), fullName, regNo, email);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return id.equals(person.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
