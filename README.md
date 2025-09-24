# CCRM-JavaProject
# Campus Course & Records Manager (CCRM)

A comprehensive console-based Java SE application for managing campus courses, students, enrollments, and academic records. This project demonstrates advanced Java programming concepts including OOP principles, design patterns, file I/O with NIO.2, Stream API, and modern Java features.

## 🚀 Project Overview

CCRM is an educational management system that allows institutes to:
- **Manage Students**: Create, update, search, and manage student profiles
- **Manage Courses**: Add courses, assign instructors, and organize by departments
- **Handle Enrollments**: Enroll/unenroll students with business rule validation
- **Grade Management**: Record grades, calculate GPA, and generate transcripts
- **File Operations**: Import/export data via CSV files
- **Generate Reports**: Analytics and statistics using Stream API
- **Backup System**: Create timestamped backups with recursive directory operations

## 🎯 Features

### Core Functionality
- ✅ **Student Management** - CRUD operations with profile management
- ✅ **Course Management** - Course creation with Builder pattern
- ✅ **Enrollment System** - Business rule validation (credit limits, duplicates)
- ✅ **Grading System** - Grade recording with automatic GPA calculation
- ✅ **Transcript Generation** - Professional transcript formatting
- ✅ **Search & Filter** - Advanced filtering using Stream API
- ✅ **Import/Export** - CSV file operations using NIO.2
- ✅ **Backup & Recovery** - Timestamped backups with file size calculation
- ✅ **Reports & Analytics** - GPA distribution, enrollment statistics

### Technical Features
- 🔧 **Menu-driven CLI** with professional user interface
- 🔧 **Exception handling** with custom exception hierarchy
- 🔧 **Data validation** using regex patterns
- 🔧 **Memory-efficient** operations with proper resource management
- 🔧 **Cross-platform** compatibility (Windows, macOS, Linux)

## 🛠️ Technical Requirements

### Prerequisites
- **JDK 11 or higher** (OpenJDK/Oracle JDK)
- **IDE**: Eclipse, IntelliJ IDEA, or VS Code with Java extensions
- **OS**: Windows, macOS, or Linux

### Installation & Setup

1. **Install JDK**:
   ```bash
   # Verify installation
   java -version
   javac -version
   ```

2. **Clone the repository**:
   ```bash
   git clone  https://github.com/Pathik0701/CCRM-JavaProject.git
   cd CCRM
   ```

3. **Compile the project**:
   ```bash
   # Create bin directory if not exists
   mkdir bin
   
   # Compile all Java files
   javac -d bin -cp src src/edu/ccrm/cli/CCRMApp.java
   ```

4. **Run the application**:
   ```bash
   # Run with assertions enabled
   java -ea -cp bin edu.ccrm.cli.CCRMApp
   ```

## 📁 Project Structure

```
CCRM/
├── src/
│   └── edu/
│       └── ccrm/
│           ├── cli/                 # Command-line interface
│           │   └── CCRMApp.java
│           ├── config/              # Configuration (Singleton pattern)
│           │   └── AppConfig.java
│           ├── domain/              # Domain entities
│           │   ├── Course.java      # (Builder pattern)
│           │   ├── Enrollment.java
│           │   ├── Grade.java       # (Enum with methods)
│           │   ├── Instructor.java
│           │   ├── Person.java      # (Abstract class)
│           │   ├── Semester.java    # (Enum with constructor)
│           │   ├── Student.java
│           │   └── Transcript.java  # (Builder pattern)
│           ├── exceptions/          # Custom exception hierarchy
│           │   ├── CCRMException.java
│           │   ├── CourseNotFoundException.java
│           │   ├── DuplicateEnrollmentException.java
│           │   ├── DuplicateStudentException.java
│           │   ├── EnrollmentNotFoundException.java
│           │   ├── InvalidDataException.java
│           │   ├── MaxCreditLimitExceededException.java
│           │   └── StudentNotFoundException.java
│           ├── io/                  # File operations (NIO.2)
│           │   ├── CSVParser.java
│           │   ├── FileOperationService.java
│           │   └── Persistable.java # (Interface)
│           ├── service/             # Business logic
│           │   ├── CourseService.java
│           │   ├── EnrollmentService.java
│           │   ├── ReportService.java
│           │   ├── StudentService.java
│           │   └── TranscriptService.java
│           └── util/                # Utilities
│               ├── Searchable.java  # (Generic interface)
│               └── ValidationUtils.java
├── test-data/                       # Sample CSV files
│   ├── courses.csv
│   └── students.csv
├── exports/                         # Generated export files
├── backups/                         # Backup directories
└── README.md
```

## 🧠 Java Concepts Demonstrated

### Object-Oriented Programming
- **Encapsulation**: Private fields with controlled access via getters/setters
- **Inheritance**: `Person` → `Student`/`Instructor` with method overriding
- **Abstraction**: Abstract `Person` class and `Persistable`/`Searchable` interfaces
- **Polymorphism**: Method overriding, interface implementations, runtime binding

### Design Patterns
- **Singleton Pattern**: `AppConfig` class with thread-safe double-checked locking
- **Builder Pattern**: `Course.Builder` and `Transcript.Builder` classes

### Advanced Java Features
- **Enums with Logic**: `Grade` and `Semester` enums with constructors and methods
- **Exception Handling**: Custom exception hierarchy with try-catch-finally blocks
- **Collections Framework**: `HashMap`, `ArrayList` with advanced operations
- **Stream API**: Filtering, mapping, grouping, and collecting operations
- **Lambda Expressions**: Method references and functional programming
- **File I/O (NIO.2)**: Path API, Files operations, directory walking
- **Date/Time API**: `LocalDate` and `LocalDateTime` usage
- **Nested Classes**: Static nested classes (Builder) and inner classes

### Modern Java Syntax
- **Switch Expressions** (Java 14+)
- **Enhanced For Loops**
- **Try-with-resources**
- **Multi-catch Exception Handling**
- **Method References**
- **Functional Interfaces**

## 📊 Usage Examples

### Basic Operations
```bash
# Start the application
java -ea -cp bin edu.ccrm.cli.CCRMApp

# Follow the menu-driven interface:
=== CAMPUS COURSE & RECORDS MANAGER ===
1. Student Management
2. Course Management  
3. Enrollment Management
4. Grading Management
5. File Operations (Import/Export)
6. Reports & Analytics
7. Backup Operations
8. Exit
```

### Sample Data Files

**students.csv**:
```csv
ID,RegNo,FullName,Email
STU001,2023001,Alice Johnson,alice.johnson@student.edu
STU002,2023002,Bob Smith,bob.smith@student.edu
```

**courses.csv**:
```csv
Code,Title,Credits,Instructor,Department,Semester
CS101,Introduction to Programming,3,Dr. John Wilson,Computer Science,FALL
MATH201,Calculus II,4,Dr. Sarah Brown,Mathematics,FALL
```

## 🔧 Configuration

The application uses the `AppConfig` singleton for configuration:

- **Data Directory**: `test-data/` (for CSV imports)
- **Export Directory**: `exports/` (for CSV exports)
- **Backup Directory**: `backups/` (for backup operations)
- **Max Credits per Semester**: 24 credits

## 📈 Key Features Demo

### 1. Student Management
- Add new students with validation
- Search students by name (partial matching)
- Update student information
- View detailed student profiles with transcripts

### 2. Course Management
- Create courses using Builder pattern
- Search by department, instructor, or semester
- Manage course assignments and credits

### 3. Enrollment System
- Enroll students with business rule validation
- Prevent duplicate enrollments
- Enforce credit limits per semester
- View enrollment history

### 4. Grading & Transcripts
- Record grades with automatic grade calculation
- Generate professional transcripts
- Calculate GPA using weighted averages
- View course-wise grade reports

### 5. Reports & Analytics
- Top students by GPA ranking
- GPA distribution analysis
- Course enrollment statistics
- Department-wise summaries

### 6. File Operations
- Import/Export CSV files using NIO.2
- Create timestamped backups
- Recursive directory size calculation
- File listing with depth indication

## 🏗️ Development Setup

### IDE Configuration

#### VS Code
1. Install "Extension Pack for Java"
2. Open project folder
3. Configure JDK path in settings
4. Use integrated terminal for compilation

#### Eclipse
1. Create new Java project
2. Import source files maintaining package structure
3. Configure build path
4. Run from main class

### Build Commands
```bash
# Compile all classes
javac -d bin -cp src $(find src -name "*.java")

# Run with assertions
java -ea -cp bin edu.ccrm.cli.CCRMApp

# Run specific tests (if implemented)
java -cp bin edu.ccrm.test.TestRunner
```

## 📋 Testing Checklist

- [ ] **Student Operations**: Add, list, search, update students
- [ ] **Course Operations**: Create courses with different semesters
- [ ] **Enrollment**: Test business rules (duplicates, credit limits)
- [ ] **Grading**: Record grades and verify GPA calculations
- [ ] **File Import**: Import sample CSV files
- [ ] **File Export**: Export data and verify CSV format
- [ ] **Backup System**: Create backup and check directory structure
- [ ] **Reports**: Generate all report types
- [ ] **Error Handling**: Test invalid inputs and edge cases

## 🔍 Code Quality Features

- **Input Validation**: Regex patterns for email and course codes
- **Error Handling**: Comprehensive exception management
- **Memory Management**: Proper resource cleanup
- **Code Documentation**: Javadoc comments throughout
- **Clean Architecture**: Separation of concerns with layered design
- **SOLID Principles**: Single responsibility, dependency injection

## 🏆 Academic Requirements Coverage

### Java Evolution Timeline
- **1995**: Java 1.0 - Initial release
- **1998**: Java 1.2 - Collections Framework
- **2004**: Java 5 - Generics, enums, annotations
- **2011**: Java 7 - Try-with-resources, diamond operator
- **2014**: Java 8 - Lambda expressions, Stream API
- **2017**: Java 9 - Modules, reactive streams
- **2018**: Java 11 - HTTP Client API, String methods (LTS)
- **2021**: Java 17 - Records, sealed classes (LTS)

### Java Platform Comparison

| Feature | Java ME | Java SE | Java EE |
|---------|---------|---------|---------|
| **Target** | Mobile/Embedded | Desktop Applications | Enterprise Applications |
| **Components** | CDC, CLDC profiles | Full JDK/JRE | Servlets, JSP, EJB |
| **Memory** | Limited (KB-MB) | Standard (MB-GB) | High (GB+) |
| **Use Cases** | IoT devices | Desktop apps, tools | Web apps, microservices |

### Java Architecture
- **JDK (Java Development Kit)**: Complete development environment
- **JRE (Java Runtime Environment)**: Runtime for executing applications
- **JVM (Java Virtual Machine)**: Platform-independent execution engine

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature-name`
3. Commit changes: `git commit -m 'Add feature'`
4. Push to branch: `git push origin feature-name`
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Java SE documentation and best practices
- Object-oriented design principles
- Modern Java programming patterns
- Educational requirements for comprehensive Java demonstration

## 📞 Contact

**Developer**: Pathik Chauhan   
**LinkedIn**: www.linkedin.com/in/pathik-chauhan07  
**GitHub**: https://github.com/Pathik0701
