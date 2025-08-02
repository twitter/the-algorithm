package com.twitter.search.common.relevance.classifiers;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.TweetTetwittertFeatures;
import com.twitter.search.common.relevance.features.TweetTetwittertQuality;

/**
 * Calculates entropy of tweet tetwittert based on tokens.
 */
public class TweetTetwittertEvaluator etwittertends TweetEvaluator {

  @Override
  public void evaluate(final TwitterMessage tweet) {
    for (PenguinVersion penguinVersion : tweet.getSupportedPenguinVersions()) {
      TweetTetwittertFeatures tetwittertFeatures = tweet.getTweetTetwittertFeatures(penguinVersion);
      TweetTetwittertQuality tetwittertQuality = tweet.getTweetTetwittertQuality(penguinVersion);

      double readability = 0;
      int numKeptWords = tetwittertFeatures.getStrippedTokensSize();
      for (String token : tetwittertFeatures.getStrippedTokens()) {
        readability += token.length();
      }
      if (numKeptWords > 0) {
        readability = readability * Math.log(numKeptWords) / numKeptWords;
      }
      tetwittertQuality.setReadability(readability);
      tetwittertQuality.setEntropy(entropy(tetwittertFeatures.getStrippedTokens()));
      tetwittertQuality.setShout(tetwittertFeatures.getCaps() / Math.matwitter(tetwittertFeatures.getLength(), 1.0d));
    }
  }

  private static double entropy(List<String> tokens) {
    Map<String, Long> tokenCounts =
        tokens.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    int numItems = tokens.size();

    double entropy = 0;
    for (long count : tokenCounts.values()) {
      double prob = (double) count / numItems;
      entropy -= prob * log2(prob);
    }
    return entropy;
  }

  private static double log2(double n) {
    return Math.log(n) / Math.log(2);
  }
}
