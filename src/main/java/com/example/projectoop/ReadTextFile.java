package com.example.projectoop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadTextFile {
    public List<String> questions = new ArrayList<>();
    public List<List<String>> choices = new ArrayList<>();
    public List<String> correctChoices = new ArrayList<>();
    public File file = new File("C:\\Users\\admin\\Downloads\\test3.txt"); // file txt

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
}
