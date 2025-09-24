package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Singleton pattern implementation for application configuration
 */
public class AppConfig {
    private static AppConfig instance;
    private static final Object lock = new Object();
    
    private final String dataDirectory;
    private final String exportDirectory;
    private final String backupDirectory;
    private final int maxCreditsPerSemester;
    
    private AppConfig() {
        this.dataDirectory = "test-data";
        this.exportDirectory = "exports";
        this.backupDirectory = "backups";
        this.maxCreditsPerSemester = 24;
        
        // Create directories if they don't exist
        createDirectories();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    private void createDirectories() {
        try {
            java.nio.file.Files.createDirectories(Paths.get(dataDirectory));
            java.nio.file.Files.createDirectories(Paths.get(exportDirectory));
            java.nio.file.Files.createDirectories(Paths.get(backupDirectory));
        } catch (Exception e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }
    
    // Getters
    public String getDataDirectory() { return dataDirectory; }
    public String getExportDirectory() { return exportDirectory; }
    public String getBackupDirectory() { return backupDirectory; }
    public int getMaxCreditsPerSemester() { return maxCreditsPerSemester; }
    public Path getDataPath() { return Paths.get(dataDirectory); }
    public Path getExportPath() { return Paths.get(exportDirectory); }
    public Path getBackupPath() { return Paths.get(backupDirectory); }
}
