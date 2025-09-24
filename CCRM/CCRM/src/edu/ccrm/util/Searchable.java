package edu.ccrm.util;

import java.util.List;
import java.util.function.Predicate;

/**
 * Generic interface for searchable collections
 * Demonstrates generics and functional interfaces
 */
public interface Searchable<T> {
    /**
     * Search by predicate
     */
    List<T> search(Predicate<T> criteria);
    
    /**
     * Search by string field
     */
    List<T> searchByField(String fieldValue, String fieldName);
    
    /**
     * Get all items
     */
    List<T> getAll();
    
    /**
     * Default method using lambda expressions
     */
    default List<T> searchByName(String name) {
        return search(item -> item.toString().toLowerCase().contains(name.toLowerCase()));
    }
}
