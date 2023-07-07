package com.example.projectoop;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadTextFile {
    public  String DB_URL ="jdbc:sqlserver://" +"localhost" + ":1433;DatabaseName=" + "pinocchio1412" + ";encrypt=true;trustServerCertificate=true";
    public  String DB_USER = "sa";
    public  String DB_PASSWORD = "pinocchio1412";

    public List<String> questions = new ArrayList<>();
    public List<String> answers = new ArrayList<>();
    public File file = new File("");
//    public File file = new File("C:\\Users\\admin\\Downloads\\test1.txt"); // file txt
    public void readfile() {
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder currentQuestion = new StringBuilder();
            StringBuilder currentAnswer = new StringBuilder();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.matches("\\d+\\. .*")) {
                    if (currentQuestion.length() > 0) {
                        questions.add(currentQuestion.toString());
                        answers.add(currentAnswer.toString());
                        currentQuestion = new StringBuilder();
                        currentAnswer = new StringBuilder();
                    }
                    currentQuestion.append(line).append("\n");
                } else if (line.startsWith("ANSWER:")) {
                    currentAnswer.append(line.substring(8)).append("\n");
                } else {
                    if (currentQuestion.length() > 0) {
                        currentQuestion.append(line).append("\n");
                    } else if (currentAnswer.length() > 0) {
                        currentAnswer.append(line).append("\n");
                    }
                }
            }

            if (currentQuestion.length() > 0) {
                questions.add(currentQuestion.toString());
                answers.add(currentAnswer.toString());
            }

            // Print all questions and answers
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
    }

    public void pushToDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            for (int i = 0; i < questions.size(); i++) {
                String questionText = questions.get(i).substring(3); //
                String answerText = answers.get(i);

                // tính điểm
                int answerGrade = 0;
                String firstChar = answerText.trim().substring(0, 1);
                if (firstChar.equalsIgnoreCase(answers.get(i).substring(8, 9))) {
                    answerGrade = 100;
                }

                // chèn câu hỏi vào bảng question
                String insertQuestionQuery = "INSERT INTO QUESTION (QUESTION_ID, QUESTION_TEXT) VALUES (?, ?)";
                PreparedStatement questionStatement = connection.prepareStatement(insertQuestionQuery);
                questionStatement.setInt(100, i + 100); // cho QUESTION_ID tăng từ 100
                questionStatement.setString(2, questionText);
                questionStatement.executeUpdate();

                // chèn đáp án vào bảng question
                String insertAnswerQuery = "INSERT INTO ANSWER (ANSWER_ID, ANSWER_TEXT, ANSWER_GRADE) VALUES (?, ?, ?)";
                PreparedStatement answerStatement = connection.prepareStatement(insertAnswerQuery);
                answerStatement.setInt(100, i + 100); // cho ANSWER_ID tăng từ 100
                answerStatement.setString(2, answerText);
                answerStatement.setInt(3, answerGrade);
                answerStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}