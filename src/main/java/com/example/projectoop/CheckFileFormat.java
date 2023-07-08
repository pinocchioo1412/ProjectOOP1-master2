package com.example.projectoop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CheckFileFormat {


    static int checkAikenFormat(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int questionCount = 0;
            boolean isInQuestionBlock = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Bỏ qua các dòng trống
                if (line.isEmpty()) {
                    continue;
                }

                // Kiểm tra dòng bắt đầu bằng số nguyên và kết thúc bằng ":"
                if (line.matches("^\\d+.*:")) {
                    questionCount++;
                    isInQuestionBlock = true;
                } else if (isInQuestionBlock && (line.startsWith("ANSWER:") || line.startsWith("A."))) {
                    isInQuestionBlock = false;
                }
            }

            // Định dạng Aiken yêu cầu ít nhất một dòng câu hỏi
            if (questionCount > 0) {
                return questionCount;
            } else {
                return -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Xử lý ngoại lệ theo yêu cầu của bạn
        }
    }
}