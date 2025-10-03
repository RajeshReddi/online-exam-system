package service;

import model.*;
import util.FileUtil;
import java.util.*;

public class ExamService {
    private HashMap<String, Student> studentMap = new HashMap<>();
    private ArrayList<Question> questionList = new ArrayList<>();

    public ExamService() {
        loadStudents();
        loadQuestions();
    }

    // Load all students from file
    private void loadStudents() {
        List<String> lines = FileUtil.readLines("data/students.txt");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                studentMap.put(parts[0], new Student(parts[0], parts[1]));
            }
        }
    }

    // Load all questions from file
    private void loadQuestions() {
        List<String> lines = FileUtil.readLines("data/questions.txt");
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

    // Student login/register flow
    public void studentLogin() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String uname = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        if (studentMap.containsKey(uname)) {
            Student s = studentMap.get(uname);
            if (s.getPassword().equals(pass)) {
                System.out.println("✅ Login successful! Welcome, " + uname);
                takeExam(s);
            } else {
                System.out.println("❌ Incorrect password!");
            }
        } else {
            System.out.println("No account found. Do you want to register? (y/n)");
            char c = sc.next().charAt(0);
            if (c == 'y' || c == 'Y') {
                studentMap.put(uname, new Student(uname, pass));
                saveStudents();
                System.out.println("✅ Registration successful! You can now take the exam.");
                takeExam(studentMap.get(uname));
            }
        }
    }

    // Save students back to file
    private void saveStudents() {
        List<String> lines = new ArrayList<>();
        for (Student s : studentMap.values()) {
            lines.add(s.getUsername() + "," + s.getPassword());
        }
        FileUtil.writeLines("data/students.txt", lines);
    }

    // Conduct the exam
    public void takeExam(Student student) {
        Scanner sc = new Scanner(System.in);
        Collections.shuffle(questionList);
        int score = 0;

        for (int i = 0; i < 5 && i < questionList.size(); i++) {
            Question q = questionList.get(i);
            System.out.println("\nQ" + (i + 1) + ". " + q.getQuestionText());
            List<String> opts = q.getOptions();
            for (int j = 0; j < opts.size(); j++) {
                System.out.println((j + 1) + ". " + opts.get(j));
            }
            System.out.print("Your answer (1-4): ");
            int ans = sc.nextInt() - 1;
            if (ans == q.getCorrectOption()) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Wrong!");
            }
        }

        student.setScore(score);
        System.out.println("\n🎯 Exam completed. Your score: " + score);
        saveResult(student);
    }

    // Save exam result
    public void saveResult(Student student) {
        List<String> lines = FileUtil.readLines("data/results.txt");
        lines.add(student.getUsername() + "," + student.getScore());
        FileUtil.writeLines("data/results.txt", lines);
    }
}
