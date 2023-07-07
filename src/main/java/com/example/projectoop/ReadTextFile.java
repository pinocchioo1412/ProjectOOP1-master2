package com.example.projectoop;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ReadTextFile {
    public List<String> questions = new ArrayList<>();
    public List<List<String>> choices = new ArrayList<>();
    public List<String> correctChoices = new ArrayList<>();
    public File file = new File("");

    public void readFile() {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder currentQuestion = new StringBuilder();
            List<String> currentChoices = new ArrayList<>();
            boolean readingQuestion = false;
            boolean readingChoices = false;
            boolean readingAnswer = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("ANSWER:")) {
                    readingAnswer = true;
                    correctChoices.add(line.substring(8).trim());
                } else if (line.matches("\\d+\\. .*")) {
                    if (currentQuestion.length() > 0) {
                        questions.add(currentQuestion.toString().trim());
                        choices.add(currentChoices);
                        currentQuestion = new StringBuilder();
                        currentChoices = new ArrayList<>();
                    }
                    currentQuestion.append(line).append("\n");
                    readingQuestion = true;
                    readingChoices = false;
                } else if (readingQuestion && line.matches("[A-D]\\. .*")) {
                    currentChoices.add(line.substring(3).trim());
                    readingChoices = true;
                } else if (readingChoices && line.matches("[A-D]\\. .*")) {
                    currentChoices.add(line.substring(3).trim());
                } else if (!line.isEmpty()) {
                    if (readingAnswer) {
                        correctChoices.add(line.trim());
                    } else {
                        currentQuestion.append(line).append("\n");
                    }
                }
            }

            if (currentQuestion.length() > 0) {
                questions.add(currentQuestion.toString().trim());
                choices.add(currentChoices);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void printQuestions() {
        for (int i = 0; i < questions.size(); i++) {
            System.out.println("Question " + (i + 1) + ":");
            System.out.println(questions.get(i));
            System.out.println("Choices:");
            List<String> currentChoices = choices.get(i);
            for (int j = 0; j < currentChoices.size(); j++) {
                System.out.println((char) ('A' + j) + ". " + currentChoices.get(j));
            }
            System.out.println("Correct Answer(s):");
            System.out.println(correctChoices.get(i));
            System.out.println();
        }
    }
    public void pushToDatabase() {
        // Thông tin kết nối cơ sở dữ liệu
        String url = "jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "abc" + ";encrypt=true;trustServerCertificate=true";
        String username = "oop";
        String password = "123";

        //  chèn dữ liệu vào bảng QUESTION
        String insertQuestionSQL = "INSERT INTO QUESTION (QUESTION_ID, QUESTION_NAME) VALUES (?, ?)";

        //   chèn dữ liệu vào bảng ANSWER
        String insertAnswerSQL = "INSERT INTO ANSWER (ANSWER_ID, ANSWER_TEXT, ANSWER_GRADE, QUESTION_ID) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Chuẩn bị câu truy vấn chèn dữ liệu vào bảng QUESTION
            PreparedStatement questionStatement = connection.prepareStatement(insertQuestionSQL);

            // Chèn dữ liệu vào bảng QUESTION
            for (int i = 0; i < questions.size(); i++) {
                String question = questions.get(i);
                questionStatement.setInt(1, i + 1);
                questionStatement.setString(2, question);
                questionStatement.executeUpdate();
            }

            // Chuẩn bị câu truy vấn chèn dữ liệu vào bảng ANSWER
            PreparedStatement answerStatement = connection.prepareStatement(insertAnswerSQL);

            // Chèn dữ liệu vào bảng ANSWER
            int answerId = 15; // Giá trị ban đầu cho ANSWER_ID
            for (int i = 0; i < choices.size(); i++) {
                List<String> choiceList = choices.get(i);
                String correctChoice = correctChoices.get(i);

                for (String choice : choiceList) {
                    answerStatement.setInt(1, answerId);
                    answerStatement.setString(2, choice);

                    if (choice.equals(correctChoice)) {
                        answerStatement.setInt(3, 100); // Đúng
                    } else {
                        answerStatement.setInt(3, 0); // Sai
                    }

                    answerStatement.setInt(4, i + 1); // QUESTION_ID là số thứ tự câu hỏi

                    answerStatement.executeUpdate();
                    answerId++;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
