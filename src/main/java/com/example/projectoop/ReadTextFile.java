package com.example.projectoop;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.SQLException;
public class ReadTextFile {
    public List<String> questions = new ArrayList<>();
    public List<String> answers = new ArrayList<>();
    File file = new File("C:\\Users\\admin\\Downloads\\test1.txt"); // Thay đổi đường dẫn tới file.txt
    public void readfile() {
        try {
            Scanner scanner = new Scanner(file);
//            List<String> questions = new ArrayList<>();
//            List<String> answers = new ArrayList<>();
            StringBuilder currentQuestion = new StringBuilder();
            StringBuilder currentAnswer = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.matches("\\d+\\. .*")) {
                    // Đây là câu hỏi mới
                    if (currentQuestion.length() > 0) {
                        questions.add(currentQuestion.toString());
                        answers.add(currentAnswer.toString());
                        currentQuestion = new StringBuilder();
                        currentAnswer = new StringBuilder();
                    }
                    currentQuestion.append(line).append("\n");
                } else if (line.startsWith("ANSWER:")) {
                    // Đây là đáp án
                    currentAnswer.append(line.substring(8)).append("\n");
                } else {
                    // Câu hỏi hoặc đáp án có nhiều dòng
                    if (currentQuestion.length() > 0) {
                        currentQuestion.append(line).append("\n");
                    } else if (currentAnswer.length() > 0) {
                        currentAnswer.append(line).append("\n");
                    }
                }
            }

            // Lưu câu hỏi và đáp án cuối cùng
            if (currentQuestion.length() > 0) {
                questions.add(currentQuestion.toString());
                answers.add(currentAnswer.toString());
            }

//            // In ra tất cả câu hỏi và đáp án
//            for (int i = 0; i < questions.size(); i++) {
//                System.out.println("Câu hỏi " + (i + 1) + ":");
//                System.out.println(questions.get(i));
//                System.out.println("Đáp án " + (i + 1) + ":");
//                System.out.println(answers.get(i));
//                System.out.println();
//            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Kết nối đến cơ sở dữ liệu
        String url = "jdbc:sqlsever://localhost:1433/pinocchio1412";
        String username = "sa ";
        String password = "pinocchio1412";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Chuẩn bị truy vấn INSERT
            String sql = "INSERT INTO your_table (QUESTION_NAME, ANSWER_TEXT) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Lặp qua danh sách câu hỏi và đáp án và thêm vào cơ sở dữ liệu
            for (int i = 0; i < questions.size(); i++) {
                String question = questions.get(i);
                String answer = answers.get(i);

                // Đặt giá trị cho tham số truy vấn
                statement.setString(1, question);
                statement.setString(2, answer);

                // Thực thi truy vấn
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
