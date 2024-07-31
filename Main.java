import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Main {
    private static String[][] students;
    private static int maxStudents;
    private static int currentStudents = 0;
    private static Scanner scanner = new Scanner(System.in);
    Student student = new Student();

    public static void main(String[] args) {
        System.out.println("\n\u001B[7m\u001B[1m ==== STUDENT ACTIVITY MANAGEMENT SYSTEM ==== \u001B[0m");
        displayMenu();
    }

    private static void displayMenu() {
        int choice = 0;
        do {
            System.out.println("\n\u001B[34m\u001B[1mMain Menu\u001B[0m");
            System.out.println("\u001B[1m1. Check available seats");
            System.out.println("2. Register student (with ID)");
            System.out.println("3. Delete student");
            System.out.println("4. Find student (with student ID)");
            System.out.println("5. Store student details into a file");
            System.out.println("6. Load student details from a file");
            System.out.println("7. View the list of students based on their names");
            System.out.println("8. Show more Option");
            System.out.println("0. Exit\u001B[0m");
            System.out.print("Enter your choice: ");




            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        findStudent();
                        break;
                    case 5:
                        storeStudentDetails();
                        break;
                    case 6:
                        loadStudentDetails();
                        break;
                    case 7:
                        viewStudentList();
                        break;
                    case 8:
                        moreOptions();
                        break;
                    case 0:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice‼ Please try again.");
                }
            } catch (InputMismatchException e) {//Check if the user input is invalid format.
                System.out.println("Invalid input‼ Please enter a valid number.");
                scanner.nextLine();
            }
        }while (choice != 0);

        scanner.close();
    }



    //    Set the 2D array by giving the number of rows.
    public static void setStudentArray() {
        System.out.print("Enter the maximum amount of Intake: ");
        while (!scanner.hasNextInt()) { //if an invalid input
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        maxStudents = scanner.nextInt();
        scanner.nextLine();
        students = new String[maxStudents][6];
        System.out.println("Student array set for " + maxStudents + " students.");
    }

    // Check how many seats are available.
    private static void checkAvailableSeats() {
        if (maxStudents == 0 && currentStudents == 0) {// check if the array is set or not.
            System.out.print("No Data available on system! " +
                    "\n-Press N to create new list. \n-Press L to load a data file from directory.\n\nEnter choice: ");
            String choice2 = scanner.next().toUpperCase();
            scanner.nextLine();
            if (choice2.equals("N")) {
                setStudentArray();
            } else if (choice2.equals("L")) {
                loadStudentDetails();
            }

        }
        int availableSeats = maxStudents - currentStudents;
        System.out.println("Available seats: " + availableSeats);
    }

    //register new student.
    public static void registerStudent() {
        if (maxStudents == 0) {
            setStudentArray();
        }
        if (currentStudents >= maxStudents) { //if reach the limit.
            System.out.println("Sorry. Maximum number of students reached.");
            return;
        }

        System.out.print("Enter student ID (e.g., w1234567): ");
        String id = scanner.nextLine();

        if (!id.matches("w\\d{7}")) {// Check if the entered ID in right format.
            System.out.println("ERROR: Invalid ID format. It should be 'w' followed by 7 digits.");
            return;
        }

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][0] != null && students[i][0].equals(id)) {
                System.out.println("ERROR: Student with this ID already exists.");
                return;
            }
        }

        students[currentStudents][0] = id;
        currentStudents++;
        System.out.println("Student registered successfully.");
    }

    //Delete a student from system
    public static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        String id = scanner.nextLine();

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][0].equals(id)) {
                // Shift up remaining students
                for (int j = i; j < currentStudents - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[currentStudents - 1] = new String[6]; // Clear last row and reset the array.
                currentStudents--;
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("ERROR: Student not found.");
    }

    public static void findStudent() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][0].equals(id)) {
                System.out.println("ID: " + students[i][0]);
//
                //check if it is null
                System.out.print("Name: ");
                if (students[i][1] != null) {
                    System.out.println(students[i][1]);
                } else {
                    System.out.println("Not added");
                }


                System.out.print("Module 1: ");
                if (students[i][2] != null) {
                    System.out.println(students[i][2]);
                } else {
                    System.out.println("Not added");
                }


                System.out.print("Module 2: ");
                if (students[i][3] != null) {
                    System.out.println(students[i][3]);
                } else {
                    System.out.println("Not added");
                }


                System.out.print("Module 3: ");
                if (students[i][4] != null) {
                    System.out.println(students[i][4]);
                } else {
                    System.out.println("Not added");
                }


                System.out.print("Grade: ");
                if (students[i][5] != null) {
                    System.out.println(students[i][5]);
                } else {
                    System.out.println("Not calculated");
                }
                return;
            }
        }
        System.out.println("ERROR: Student not found.");
    }

    //Store the data to a text file.
    public static void storeStudentDetails() {
        System.out.print("Enter file name to store student details: ");//ask user to enter the name.
        String fileName = scanner.nextLine();
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName + ".txt"))) {
            for (int i = 0; i < currentStudents; i++) {
                writer.println(String.join(",", students[i]));
            }
            System.out.println("Student details stored successfully.");
        } catch (IOException e) {
            System.out.println("ERROR: Failed to store student details. " + e.getMessage());
        }
    }

    //Load data from exisiting file.
    public static void loadStudentDetails() {
        if (maxStudents == 0 && currentStudents == 0) {
            setStudentArray();//if the array haven't set yet.
        }

        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));//check the file in directory

        if (files == null || files.length == 0) {
            System.out.println("No text files found in the directory.");
            return;
        }

        System.out.println("\nText files available in the current directory:");
        for (int i = 0; i < files.length; i++) {
            System.out.println((i + 1) + ". " + files[i].getName());
        }
        System.out.print("\nEnter the number of the file you want to load: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        File selectedFile;
        if (choice > 0 && choice <= files.length) {
            selectedFile = files[choice - 1];
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            String line;
//            currentStudents = 0;
            students = new String[maxStudents][6]; // set the students array

            while ((line = reader.readLine()) != null && currentStudents < maxStudents) {
                String[] parts = line.split(",");//split data by ","
                if (parts.length != 6) {//if the data doesn't natch to the array
                    System.out.println("WARNING: Skipping invalid lines/data: " + line);
                    continue;
                }

                // Check student ID
                if (!parts[0].matches("w\\d{7}")) {
                    System.out.println("WARNING: Skipping line with invalid student ID: " + parts[0]);
                    continue;
                }

                // Check for duplicate ID
                boolean isDuplicate = false; //set a temp. variable
                for (int i = 0; i < currentStudents; i++) {//If an id is already exist
                    if (students[i][0] != null && students[i][0].equals(parts[0])) {
                        System.out.println("WARNING: Skipping duplicate student ID: " + parts[0]);
                        isDuplicate = true;
                        break;
                    }
                }
                if (isDuplicate) continue;

                // Validate marks
                for (int i = 2; i <= 4; i++) {
                    if (!parts[i].matches("\\d{1,3}")) {
                        System.out.println("Warning: Invalid mark for student " + parts[0] + ". Setting to N/A");
                        parts[i] = "0";
                    } else {
                        int mark = Integer.parseInt(parts[i]);
                        if (mark < 0 || mark > 100) {
                            System.out.println("Warning: Mark out of range for student " + parts[0] + ". Setting to N/A");
                            parts[i] = "0";
                        }
                    }
                }

                // Add student data to the array
                System.arraycopy(parts, 0, students[currentStudents], 0, 6);
                currentStudents++;
            }

            if (currentStudents >= maxStudents && reader.readLine() != null) {
                System.out.println("Warning: Some students were not loaded as the maximum limit was reached.");
            }

            System.out.println("Student details loaded successfully. Total students loaded: " + currentStudents);
        } catch (IOException e) {
            System.out.println("Error: Failed to load student details. " + e.getMessage());
        }
    }


    //View data in system
    public static void viewStudentList() {
        if (currentStudents == 0) {
            System.out.println("No students to display.");
            return;
        }

        // Using the Bubble sort algorithm to sort students by Name
        for (int i = 0; i < currentStudents - 1; i++) {
            for (int j = 0; j < currentStudents - i - 1; j++) {
                // Check if names are not null before comparing
                if (students[j][1] != null && students[j + 1][1] != null) {
                    if (students[j][1].compareToIgnoreCase(students[j + 1][1]) > 0) {
                        // Swap
                        String[] temp = students[j];
                        students[j] = students[j + 1];
                        students[j + 1] = temp;
                    }
                } else if (students[j][1] == null && students[j + 1][1] != null) {
                    // Move null student names to the end
                    String[] temp = students[j];
                    students[j] = students[j + 1];
                    students[j + 1] = temp;
                }
            }
        }

        // Display sorted list to the console
        System.out.println("\n=== Sorted Student List ===");
        System.out.printf("%-18s | %-20s\n", "\u001B[1mUoW ID\u001B[0m", "\u001B[1mStudent Name\u001B[0m");
        for (int i = 0; i < currentStudents; i++) {
            if (students[i][1] != null) {
                System.out.printf("%-10s | %-20s\n", students[i][0], students[i][1]);
            } else {
                System.out.printf("%-10s | %-20s\n", students[i][0], "Name not added");
            }
        }
    }

    //More Options //TASK 2
    public static void moreOptions() {
        Student student = new Student();
        String choice;
        while (true) {
            System.out.println("\n\u001B[34m\u001B[1m More Options \u001B[0m");
            System.out.println("\u001B[1ma. Add student name");
            System.out.println("b. Add module marks");
            System.out.println("c. Generate summary report");
            System.out.println("d. Generate complete report");
            System.out.println("0. Back to main menu\u001B[0m");
            System.out.print("\nEnter your choice: ");
            choice = scanner.next();
            scanner.nextLine();

            switch (choice) {
                case "a":
                    addStudentName();
                    break;
                case "b":
                    addModuleMarks();
                    break;
                case "c":
                    generateSummaryReport();
                    break;
                case "d":
                    generateCompleteReport();
                    break;
                case "0":
                    System.out.println("Returning to main menu...");
                    displayMenu();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    //    //Add name to Student IDs.
    public static void addStudentName() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        Student student = new Student();
        student.setId(id);

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][0].equals(student.getId())) {
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                if (name.trim().isEmpty()) {
                    System.out.println("ERROR: Name cannot be empty.");
                    return;
                }
                student.setName(name);
                students[i][1] = student.getName();
                System.out.println("Name added successfully.");
                return;
            }
        }
        System.out.println("ERROR: Student with this ID does not exist.");
    }

    public static void addModuleMarks() {
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][0].equals(id)) {
                if (students[i][1] == null) {
                    System.out.println("ERROR: Student name must be added before marks.");
                    return;
                }

                for (int j = 2; j <= 4; j++) {
                    System.out.print("Enter Module " + (j - 1) + " mark: ");
                    String mark = scanner.nextLine();
                    if (!mark.matches("\\d{1,3}")) {//Check if mark within range.
                        System.out.println("ERROR: Invalid mark. Please enter a number between 0 and 100.");
                        return;
                    }
                    int markInt = Integer.parseInt(mark);//change the type
                    if (markInt < 0 || markInt > 100) {
                        System.out.println("ERROR: Mark must be between 0 and 100.");
                        return;
                    }
                    students[i][j] = mark;
                }

                calculateAndThenStoreGrade(i);//calculate grade
                System.out.println("Marks added successfully.");
                return;
            }
        }
        System.out.println("ERROR: Student not found.");
    }


    public static void generateSummaryReport() {
        System.out.println("\n\u001B[1m==== SUMMARY OF REPORT ====\u001B[0m");
        System.out.println("Total student registrations: " + currentStudents);
        int module1Count = 0, module2Count = 0, module3Count = 0;

        for (int i = 0; i < currentStudents; i++) {
            if (students[i][2] != null && Integer.parseInt(students[i][2]) >= 40) module1Count++;
            if (students[i][3] != null && Integer.parseInt(students[i][3]) >= 40) module2Count++;
            if (students[i][4] != null && Integer.parseInt(students[i][4]) >= 40) module3Count++;
        }

        System.out.println("Students who scored 40 or more marks:");
        System.out.println("\u001B[1mModule 1\u001B[0m: " + module1Count);
        System.out.println("\u001B[1mModule 2\u001B[0m: " + module2Count);
        System.out.println("\u001B[1mModule 3\u001B[0m: " + module3Count);
    }


    public static void generateCompleteReport() {
        System.out.println("\n\u001B[1m           === THE COMPLETE REPORT ===        \u001B[0m");
        System.out.printf(" | %-20s | %-27s | %-4s | %-5s | %-10s | %-10s | %-10s | %-11s |\n",
                "\u001B[1mUoW ID\u001B[0m","\u001B[1mStudent Name\u001B[0m","\u001B[1mModule 1\u001B[0m","\u001B[1mModule 2\u001B[0m","\u001B[1mModule 3\u001B[0m","\u001B[1mTotal\u001B[0m","\u001B[1mAverage\u001B[0m","\u001B[1mGrade\u001B[0m");
        for (int i = 0; i < currentStudents; i++) {
            if (students[i][1] != null) {
                int total = 0;
                for (int j = 2; j <= 4; j++) {
                    total += (students[i][j] != null) ? Integer.parseInt(students[i][j]) : 0;
                }
                double average = total / 3.0;

                System.out.printf(" | %-12s | %-19s | %-8s | %-8s | %-8s | %-5s | %-7s | %-5s |\n",
                        students[i][0],                           // ID
                        students[i][1],                           // Name
                        students[i][2] != null ? students[i][2] : "N/A",  // Module 1
                        students[i][3] != null ? students[i][3] : "N/A",  // Module 2
                        students[i][4] != null ? students[i][4] : "N/A",  // Module 3
                        total,                                            // Total
                        average,                                          // Average
                        calculateGrade(average));                         // Grade
            }
        }
    }
//Calculate the grade
    private static void calculateAndThenStoreGrade(int studentIndex) {
        int sum = 0;
        for (int j = 2; j <= 4; j++) {
            sum += Integer.parseInt(students[studentIndex][j]);//change the type
        }
        int avg = sum / 3;
        students[studentIndex][5] = calculateGrade(avg);
    }

    private static String calculateGrade(double avg) {
        if (avg >= 80) return "DISTINCTION";
        if (avg >= 70) return "MERIT";
        if (avg >= 40) return "PASS";
        return "FAIL";
    }
}
