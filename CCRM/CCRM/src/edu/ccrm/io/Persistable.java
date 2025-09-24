package edu.ccrm.io;

import java.io.IOException;

/**
 * Interface for objects that can be persisted
 * Demonstrates interface definition and default methods
 */
public interface Persistable {
    /**
     * Save object to persistent storage
     */
    void save() throws IOException;
    
    /**
     * Load object from persistent storage
     */
    void load() throws IOException;
    
    /**
     * Get unique identifier
     */
    String getId();
    
    /**
     * Default method - can be overridden but provides default implementation
     */
    default boolean isValid() {
        return getId() != null && !getId().trim().isEmpty();
    }
    
    /**
     * Static method in interface (Java 8 feature)
     */
    static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
    }
}