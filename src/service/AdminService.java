package service;

import model.Question;
import util.FileUtil;

import java.util.*;

public class AdminService {
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "1234";

    private List<Question> questionList = new ArrayList<>();

    public AdminService() {
        loadQuestions();
    }

    // ✅ Load existing questions
    private void loadQuestions() {
        List<String> lines = FileUtil.readLines("data/questions.txt");
        questionList.clear();
        for (int i = 0; i < lines.size(); i += 6) {
            String qText = lines.get(i);
            List<String> opts = new ArrayList<>();
            opts.add(lines.get(i+1));
            opts.add(lines.get(i+2));
            opts.add(lines.get(i+3));
            opts.add(lines.get(i+4));
            int correct = Integer.parseInt(lines.get(i+5));
            questionList.add(new Question(qText, opts, correct));
        }
    }

    // ✅ Admin login
    public void adminLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String user = sc.nextLine();
        System.out.print("Enter admin password: ");
        String pass = sc.nextLine();

        if (user.equals(ADMIN_USER) && pass.equals(ADMIN_PASS)) {
            System.out.println("\n✅ Welcome Admin!");
            showMenu();
        } else {
            System.out.println("❌ Invalid credentials!");
        }
    }

    // ✅ Admin menu
    private void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Question");
            System.out.println("2. Remove Question");
            System.out.println("3. View All Questions");
            System.out.println("4. View Student Results");
            System.out.println("5. Exit Admin");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addQuestion();
                case 2 -> removeQuestion();
                case 3 -> viewQuestions();
                case 4 -> viewResults();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ✅ Add question
    private void addQuestion() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter question text: ");
        String text = sc.nextLine();

        List<String> opts = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            System.out.print("Enter option " + i + ": ");
            opts.add(sc.nextLine());
        }

        System.out.print("Enter correct option (1-4): ");
        int correct = sc.nextInt() - 1;

        questionList.add(new Question(text, opts, correct));
        saveQuestions();
        System.out.println("✅ Question added!");
    }

    // ✅ Remove question
    private void removeQuestion() {
        Scanner sc = new Scanner(System.in);
        viewQuestions();
        if (questionList.isEmpty()) return;

        System.out.print("Enter question number to remove: ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < questionList.size()) {
            questionList.remove(index);
            saveQuestions();
            System.out.println("✅ Question removed!");
        } else {
            System.out.println("❌ Invalid index!");
        }
    }

    // ✅ View all questions
    private void viewQuestions() {
        if (questionList.isEmpty()) {
            System.out.println("⚠ No questions found.");
            return;
        }
        int i = 1;
        for (Question q : questionList) {
            System.out.println(i++ + ". " + q.getQuestionText());
        }
    }

    // ✅ View student results
    private void viewResults() {
        List<String> lines = FileUtil.readLines("data/results.txt");
        if (lines.isEmpty()) {
            System.out.println("⚠ No results found.");
            return;
        }

        System.out.println("\n=== Student Results ===");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2)
                System.out.println("👤 " + parts[0] + " → Score: " + parts[1]);
        }
    }

    // ✅ Save questions back to file
    private void saveQuestions() {
        List<String> lines = new ArrayList<>();
        for (Question q : questionList) {
            lines.add(q.getQuestionText());
            for (String opt : q.getOptions()) {
                lines.add(opt);
            }
            lines.add(String.valueOf(q.getCorrectOption()));
        }
        FileUtil.writeLines("data/questions.txt", lines);
    }
}
