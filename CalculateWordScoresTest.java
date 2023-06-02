import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalculateWordScoresTest {
    /*==============================================================================================
    Requirements:
    - Ignore any token that does not start with a letter
    - Tokens that start with a letter should not be ignored
    - Ignore any sentence with a score out of range [-2, 2]
    - Convert all strings to lower case
    - Return an empty Map if set of sentences is null
    - Return an empty Map if set of sentences is empty
    - Ignore any sentences in the set that are null
    - Ignore any sentences with null text
    - Ignore any sentences with empty text
    - Calculates word score properly (1 occurrence)
    - Calculates word score properly (multi-occurrence)
    - Map has correct word/score pairs
    - Map has correct number of keys
    ==============================================================================================*/

    // - Ignore any token that does not start with a letter
    @Test
    public void tokenStartsWithNonLetter() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(-2, "?weirdo");
        sentences.add(s1);

        Assert.assertEquals(Map.of(), Analyzer.calculateWordScores(sentences));
    }

    @Test
    public void multipleTokensStartWithNonLetter() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(-2, "I am ??? like , so totally confused LOL :D");
        sentences.add(s1);

        Assert.assertEquals(Map.of(
                "i", -2.0,
                "am", -2.0,
                "like", -2.0,
                "so", -2.0,
                "totally", -2.0,
                "confused", -2.0,
                "lol", -2.0
        ), Analyzer.calculateWordScores(sentences));
    }

    // - Tokens that start with a letter should not be ignored
    @Test
    public void tokenStartsWithLetter() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(2, "Anassa Kata!");
        sentences.add(s1);

        Assert.assertEquals(
                Map.of("anassa", 2.0,
                        "kata!", 2.0),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Ignore any sentence with a score out of range [-2, 2]
    @Test
    public void scoreOutOfBounds() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(5, "This is the most amazing sentence ever!");
        sentences.add(s1);

        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Convert all strings to lower case
    @Test
    public void allStringsToLowerCase() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(2, "VIKI IS THE BEST!");
        sentences.add(s1);

        Assert.assertEquals(
                Map.of("viki", 2.0,
                        "is", 2.0,
                        "the", 2.0,
                        "best!", 2.0
                ),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Return an empty Map if set of sentences is null
    @Test
    public void nullSetEmptyMap() {
        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(null)
        );
    }

    // - Return an empty Map if set of sentences is empty
    @Test
    public void emptySetEmptyMap() {
        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(new HashSet<Sentence>() {
                })
        );
    }

    // - Ignore any sentences in the set that are null
    @Test
    public void nullSentenceEmptyMap() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        sentences.add(null);

        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Ignore any sentences with null text
    @Test
    public void nullTextEmptyMap() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(1, null);
        sentences.add(s1);

        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Ignore any sentences with empty text
    @Test
    public void emptyTextEmptyMap() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(1, "");
        sentences.add(s1);

        Assert.assertEquals(
                Map.of(),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Calculates word score properly (1 occurrence)
    @Test
    public void singleOccurrence() {
        // Add the test sentence
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(2, "I love cake because cake is great!");
        sentences.add(s1);

        Assert.assertEquals(
                Map.of("i", 2.0,
                        "love", 2.0,
                        "cake", 2.0,
                        "because", 2.0,
                        "is", 2.0,
                        "great!", 2.0),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Calculates word score properly (multi-occurrence)
    @Test
    public void multipleOccurrences() {
        // Add the test sentences
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        // cake score should be: (2 + 2 + 1) / 3 = 1.6666666666666667
        Sentence s1 = new Sentence(2, "I love cake because cake is great!");
        Sentence s2 = new Sentence(1, "Cake is just ok");
        sentences.add(s1);
        sentences.add(s2);

        Assert.assertEquals(
                Map.of("i", 2.0,
                        "love", 2.0,
                        "cake", 1.6666666666666667,
                        "because", 2.0,
                        "is", 1.5,
                        "great!", 2.0,
                        "just", 1.0,
                        "ok", 1.0),
                Analyzer.calculateWordScores(sentences)
        );
    }
    // - Map has correct key-value pairs
    @Test
    public void correctMapPairs() {
        // Add the test sentences
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(-2, "Stinky");
        Sentence s2 = new Sentence(-1, "Meh");
        Sentence s3 = new Sentence(0, "Ok");
        Sentence s4 = new Sentence(1, "Good");
        Sentence s5 = new Sentence(2, "Perfect!");
        sentences.add(s1);
        sentences.add(s2);
        sentences.add(s3);
        sentences.add(s4);
        sentences.add(s5);

        Assert.assertEquals(
                Map.of("stinky", -2.0,
                        "meh", -1.0,
                        "ok", 0.0,
                        "good", 1.0,
                        "perfect!", 2.0),
                Analyzer.calculateWordScores(sentences)
        );
    }

    // - Map has correct number of keys
    @Test
    public void correctNumMapKeys() {
        // Add the test sentences
        Set<Sentence> sentences = new HashSet<Sentence>() {
        };
        Sentence s1 = new Sentence(-2, "Stinky");
        Sentence s2 = new Sentence(-1, "Meh");
        Sentence s3 = new Sentence(0, "Ok");
        Sentence s4 = new Sentence(1, "Good");
        Sentence s5 = new Sentence(2, "Perfect!");
        sentences.add(s1);
        sentences.add(s2);
        sentences.add(s3);
        sentences.add(s4);
        sentences.add(s5);

        Assert.assertEquals(
                5,
                Analyzer.calculateWordScores(sentences).keySet().size()
        );
    }
}
