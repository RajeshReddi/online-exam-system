import service.ExamService;
import service.AdminService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExamService examService = new ExamService();
        AdminService adminService = new AdminService();

        while (true) {
            System.out.println("\n=== Online Exam System ===");
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> examService.studentLogin();
                case 2 -> adminService.adminLogin();
                case 3 -> {
                    System.out.println("👋 Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("⚠ Invalid choice. Try again.");
            }
        }
    }
}
