import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CalculateSentenceScoreTest {
   /*==============================================================================================
    Requirements:
    - Return 0 if any data needed to calculate the score is missing.
    - Words not in map yet should have score 0
    - Input sentence gets converted to lower case
    - Average score is calculated correctly.
    ==============================================================================================*/

    // - Return 0 if any data needed to calculate the score is missing.
    @Test
    public void missingDataReturns0() {
        Assert.assertEquals(0.0, Analyzer.calculateSentenceScore(null, null), 0.0);
    }

    @Test
    public void missingScoresReturns0() {
        Assert.assertEquals(0.0, Analyzer.calculateSentenceScore(null, "hi"), 0.0);
    }

    @Test
    public void emptySentenceReturns0() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("test", -2.0);
        Assert.assertEquals(0.0, Analyzer.calculateSentenceScore(wordScores, ""), 0.0);
    }

    @Test
    public void nullSentenceReturns0() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("test", -2.0);
        Assert.assertEquals(0.0, Analyzer.calculateSentenceScore(wordScores, null), 0.0);
    }

    @Test
    public void singleWord() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("test", -2.0);
        Assert.assertEquals(-2.0, Analyzer.calculateSentenceScore(wordScores, "test"), 0.0);
    }

    // - Average score is calculated correctly.
    @Test
    public void twoWordsAverage() {
        Map<String, Double> wordScores = new HashMap<>();
        wordScores.put("tests", -2.0);
        wordScores.put("great!", 2.0);
        Assert.assertEquals(0.0, Analyzer.calculateSentenceScore(wordScores, "tests are great!"), 0.0);
    }

    // - Words not in map yet should have score 0
    @Test
    public void wordsNotInMapScore0() {
        Map<String, Double> wordScores = new HashMap<>();
        // score will be: (2 + 1 + 0 + 2) / 4
        wordScores.put("dogs", 2.0);
        wordScores.put("are", 1.0);
        wordScores.put("cute!", 2.0);
        Assert.assertEquals(1.25, Analyzer.calculateSentenceScore(wordScores, "dogs are so cute!"), 0.0);
    }

    // - Input sentence gets converted to lower case
    @Test
    public void sentenceConvertedToLowerCase() {
        Map<String, Double> wordScores = new HashMap<>();
        // score will be: (0 + 2 + 1 + 0 + 0) / 5 = 0.6
        wordScores.put("cats", 2.0);
        wordScores.put("are", 1.0);
        wordScores.put("the", 0.0);
        Assert.assertEquals(0.6, Analyzer.calculateSentenceScore(wordScores, "BUT CATS ARE THE BEST"), 0.0);
    }
}
