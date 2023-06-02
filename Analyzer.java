import static java.lang.System.exit;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;

/*
 * Implement the four methods as described in the specification.
 *
 * Do not change the method signatures!
 * Contact the instructor if you feel that a change is necessary.
 */

public class Analyzer {

    public Analyzer() {
    }

    public static Set<Sentence> readFile(String filename) {

        // throw IllegalArgumentException if:
        // The input filename is null
        if (filename == null) {
            throw new IllegalArgumentException("Filename cannot be null");
        }

        // implement this method in Part 1
        Set<Sentence> sentences = new HashSet<Sentence>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                try {
                    int score = Integer.parseInt(tokens[0]);

                    // Ignore (do not throw an exception) for:
                    // Line starts with an int outside range [-2, 2]}
                    if (!(score < -2 || score > 2)) {
                        // Line starts with a number but is not followed by any text
                        if (!(tokens.length < 2)) {
                            String sentence = line.substring(line.indexOf(" ") + 1);
                            sentences.add(new Sentence(score, sentence));
                        }
                    }
                } catch (NumberFormatException e) {
                    // Ignore (do not throw an exception) for:
                    // Line starts with a non-int number (ex. 1.7)
                    // Line that does not start with a number at all
                    System.out.println("Error: " + "Invalid score value");
                }
            }
            reader.close();
        } catch (IOException e) {
            // throw IllegalArgumentException if:
            // File cannot be opened for reading (ex. the file does not exist)
            throw new IllegalArgumentException(e);
        }
        return sentences;
    }

    public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {
        // If there are no sentences in the set, return an empty map
        if (sentences == null || sentences.isEmpty()) {
            return Map.of();
        }
        Map<String, Double> wordScores = new HashMap<String, Double>();
        Map<String, Double> wordCounts = new HashMap<String, Double>();
        // For each sentence in the set, calculate the sentiment for each word
        for (Sentence s : sentences) {
            // Check that the sentence/score is valid, if not move on to the next sentence
            if (s == null || s.getScore() < -2 || s.getScore() > 2 || s.getText() == null || s.getText().isEmpty()) {
                continue;
            }
            // Convert the sentence to lowercase
            String sentence = s.getText().toLowerCase();
            // Split the sentence into words. For each word:
            String[] tokens = sentence.split(" ");
            for (String token : tokens) {
                // The current token is null or empty
                if (token == null || token.isEmpty() || !(Character.isLetter(token.charAt(0)))) {
                    continue;
                }
                // The current word is not in the dictionary yet, so add it
                if (!wordScores.containsKey(token)) {
                    wordScores.put(token, (double) s.getScore());
                    wordCounts.put(token, 1.0);
                } else {
                    // The current word is already in the dictionary, update its sentiment and count
                    wordScores.compute(token, (k, v) -> v + (double) s.getScore());
                    wordCounts.compute(token, (k, v) -> v + 1.0);
                }
            }
        }
        // Calculate the weighted average sentiment score for each word
        wordScores.replaceAll((k, v) -> v / wordCounts.get(k));
        return wordScores;
    }
    public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
        // Return 0 if any of the necessary input data is missing
        if (wordScores == null || wordScores.isEmpty() || sentence == null || sentence.isEmpty()) {
            return 0.0;
        }
        // Convert the sentence to lower case
        sentence = sentence.toLowerCase();
        double scoreSum = 0.0;
        int numWords = 0;
        for (String token : sentence.split(" ")) {
            if (token != null && !(token.isEmpty()) && Character.isLetter(token.charAt(0))) {
                // Check if this token is in the wordScores map
                Double score = wordScores.get(token);
                if (score == null) {
                    // This token is not in the map, so treat its score as 0
                    score = 0.0;
                }
                scoreSum += score;
                numWords++;
            }
            else
            {
                return 0.0;
            }
        }
        if(numWords > 0)
        {
            return scoreSum / numWords;
        }
        else
        {
            return 0.0;
        }
    }
    public static void main(String[] args) {
        // If the name of the input file is not provided as a runtime argument, the program should display
        //“no input file” and should end.
        if (args == null || args.length == 0) {
            System.out.println("No input file provided");
            System.exit(0);
        }
        // Read the name of the input file as a runtime argument
        String filename = args[0];
        // 1. Pass the name of the input file to the readFile method to get the Set of Sentence
        // objects
        try {
            Set<Sentence> sentences = readFile(filename);
            // 2. Pass the Set of Sentence objects to the calculateWordScores method to get the Map of
            // words and their scores
            Map<String, Double> wordScores = calculateWordScores(sentences);
            String inputSentence;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // 3. Prompt the user to enter a sentence
                System.out.println("Please enter a sentence, or type \"quit\" to exit: ");
                inputSentence = scanner.nextLine();
                if (inputSentence.equals("quit")) {
                    scanner.close();
                    exit(0);
                } else {
                    // 4. Pass the Map and the sentence that the user entered to the calculateSentenceScore
                    // method, and print out the result
                    System.out.println(calculateSentenceScore(wordScores, inputSentence));
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Bad input file");
            exit(0);
        }
    }
}
