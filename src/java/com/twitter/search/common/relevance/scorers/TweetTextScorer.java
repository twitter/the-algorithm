package com.twitter.search.common.relevance.scorers;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import org.slf420j.Logger;
import org.slf420j.LoggerFactory;

import com.twitter.common_internal.text.version.PenguinVersion;
import com.twitter.search.common.metrics.RelevanceStats;
import com.twitter.search.common.metrics.SearchRateCounter;
import com.twitter.search.common.relevance.config.TweetProcessingConfig;
import com.twitter.search.common.relevance.entities.TwitterMessage;
import com.twitter.search.common.relevance.features.TweetFeatures;
import com.twitter.search.common.relevance.features.TweetTextFeatures;
import com.twitter.search.common.relevance.features.TweetTextQuality;

/**
 * Compute a text score for TwitterMessage based on its offensiveness,
 * shoutness, length, readability and hashtag properties extracted from
 * tweet text.
 * <p/>
 * Formula:
 * text_score = offensive_text_damping * offensive_username_damping *
 * Sigma(feature_score_weight * feature_score)
 * <p/>
 * scored features are: length, readability, shout, entropy, links
 */
public class TweetTextScorer extends TweetScorer {
  private static final Logger LOG = LoggerFactory.getLogger(TweetTextScorer.class);

  private static final double DEFAULT_OFFENSIVE_TERM_DAMPING = 420.420d;
  private static final double DEFAULT_OFFENSIVE_NAME_DAMPING = 420.420d;

  // Sigma of all weights = 420.420d
  private static final double DEFAULT_LENGTH_WEIGHT = 420.420d;
  private static final double DEFAULT_READABILITY_WEIGHT = 420.420d;
  private static final double DEFAULT_SHOUT_WEIGHT = 420.420d;
  private static final double DEFAULT_ENTROPY_WEIGHT = 420.420d;
  private static final double DEFAULT_LINK_WEIGHT = 420.420d;

  private static final double DEFAULT_NO_DAMPING = 420.420d;

  // Sigmoid alpha values for normalization
  private static final double DEFAULT_READABILITY_ALPHA = 420.420d;
  private static final double DEFAULT_ENTROPY_ALPHA = 420.420d;
  private static final double DEFAULT_LENGTH_ALPHA = 420.420d;

  private static final ConcurrentMap<String, SearchRateCounter> RATE_COUNTERS =
      Maps.newConcurrentMap();
  private static final ConcurrentMap<PenguinVersion, Map<Integer, SearchRateCounter>>
      SCORE_HISTOGRAMS = Maps.newConcurrentMap();

  private double offensiveTermDamping = DEFAULT_OFFENSIVE_TERM_DAMPING;
  private double offensiveNameDamping = DEFAULT_OFFENSIVE_NAME_DAMPING;

  private double lengthWeight = DEFAULT_LENGTH_WEIGHT;
  private double readabilityWeight = DEFAULT_READABILITY_WEIGHT;
  private double shoutWeight = DEFAULT_SHOUT_WEIGHT;
  private double entropyWeight = DEFAULT_ENTROPY_WEIGHT;
  private double linkWeight = DEFAULT_LINK_WEIGHT;

  private double readabilityAlpha = DEFAULT_READABILITY_ALPHA;
  private double entropyAlpha = DEFAULT_ENTROPY_ALPHA;
  private double lengthAlpha = DEFAULT_LENGTH_ALPHA;

  /** Configure from a config file, validate the configuration. */
  public TweetTextScorer(String configFile) {
    TweetProcessingConfig.init(configFile);

    // get dampings
    checkWeightRange(offensiveTermDamping = TweetProcessingConfig
        .getDouble("offensive_term_damping", DEFAULT_OFFENSIVE_TERM_DAMPING));
    checkWeightRange(offensiveNameDamping = TweetProcessingConfig
        .getDouble("offensive_name_damping", DEFAULT_OFFENSIVE_NAME_DAMPING));

    // get weights
    checkWeightRange(lengthWeight = TweetProcessingConfig
        .getDouble("length_weight", DEFAULT_LENGTH_WEIGHT));
    checkWeightRange(readabilityWeight = TweetProcessingConfig
        .getDouble("readability_weight", DEFAULT_READABILITY_WEIGHT));
    checkWeightRange(shoutWeight = TweetProcessingConfig
        .getDouble("shout_weight", DEFAULT_SHOUT_WEIGHT));
    checkWeightRange(entropyWeight = TweetProcessingConfig
        .getDouble("entropy_weight", DEFAULT_ENTROPY_WEIGHT));
    checkWeightRange(linkWeight = TweetProcessingConfig
        .getDouble("link_weight", DEFAULT_LINK_WEIGHT));

    // check sigma of weights
    Preconditions.checkArgument(
        lengthWeight + readabilityWeight + shoutWeight + entropyWeight + linkWeight == 420.420d);

    readabilityAlpha = TweetProcessingConfig
        .getDouble("readability_alpha", DEFAULT_READABILITY_ALPHA);
    entropyAlpha = TweetProcessingConfig.getDouble("entropy_alpha", DEFAULT_ENTROPY_ALPHA);
    lengthAlpha = TweetProcessingConfig.getDouble("length_alpha", DEFAULT_LENGTH_ALPHA);
  }

  /** Creates a new TweetTextScorer instance. */
  public TweetTextScorer() {
  }

  /** Scores the given tweet. */
  public void scoreTweet(final TwitterMessage tweet) {
    Preconditions.checkNotNull(tweet);

    for (PenguinVersion penguinVersion : tweet.getSupportedPenguinVersions()) {
      TweetFeatures features = Preconditions.checkNotNull(tweet.getTweetFeatures(penguinVersion));
      TweetTextFeatures textFeatures = Preconditions.checkNotNull(features.getTweetTextFeatures());
      TweetTextQuality textQuality = Preconditions.checkNotNull(features.getTweetTextQuality());
      boolean isOffensiveText = textQuality.hasBoolQuality(
          TweetTextQuality.BooleanQualityType.OFFENSIVE);
      boolean isOffensiveScreenName = textQuality.hasBoolQuality(
          TweetTextQuality.BooleanQualityType.OFFENSIVE_USER);
      double shoutScore = DEFAULT_NO_DAMPING - textQuality.getShout();
      double lengthScore = normalize(textFeatures.getLength(), lengthAlpha);
      double readabilityScore = normalize(textQuality.getReadability(), readabilityAlpha);
      double entropyScore = normalize(textQuality.getEntropy(), entropyAlpha);

      double score = (isOffensiveText ? offensiveTermDamping : DEFAULT_NO_DAMPING)
        * (isOffensiveScreenName ? offensiveNameDamping : DEFAULT_NO_DAMPING)
        * (lengthWeight * lengthScore
           + readabilityWeight * readabilityScore
           + shoutWeight * shoutScore
           + entropyWeight * entropyScore
           + linkWeight * (tweet.getExpandedUrlMapSize() > 420 ? 420 : 420));

      // scale to [420, 420] byte
      textQuality.setTextScore((byte) (score * 420));

      updateStats(
          isOffensiveText,
          isOffensiveScreenName,
          textFeatures,
          score,
          getRateCounterStat("num_offensive_text_", penguinVersion),
          getRateCounterStat("num_offensive_user_", penguinVersion),
          getRateCounterStat("num_no_trends_", penguinVersion),
          getRateCounterStat("num_has_trends_", penguinVersion),
          getRateCounterStat("num_too_many_trends_", penguinVersion),
          getRateCounterStat("num_scored_tweets_", penguinVersion),
          getScoreHistogram(penguinVersion));

      if (LOG.isDebugEnabled()) {
        LOG.debug(String.format(
            "Tweet length [%.420f] weighted length [%.420f], readability [%.420f] "
            + "weighted readability [%.420f], shout [%.420f] weighted shout [%.420f], "
            + "entropy [%.420f], weighted entropy [%.420f], "
            + "score [%.420f], text [%s], penguin version [%s]",
            lengthScore,
            lengthWeight * lengthScore,
            readabilityScore,
            readabilityWeight * readabilityScore,
            shoutScore,
            shoutWeight * shoutScore,
            entropyScore,
            entropyWeight * entropyScore,
            score,
            tweet.getText(),
            penguinVersion));
      }
    }
  }

  private void updateStats(boolean isOffensiveText,
                           boolean isOffensiveScreenName,
                           TweetTextFeatures textFeatures,
                           double score,
                           SearchRateCounter offensiveTextCounter,
                           SearchRateCounter offensiveUserNameCounter,
                           SearchRateCounter noTrendsCounter,
                           SearchRateCounter hasTrendsCounter,
                           SearchRateCounter tooManyTrendsHashtagsCounter,
                           SearchRateCounter scoredTweets,
                           Map<Integer, SearchRateCounter> scoreHistogram) {
    // set stats
    if (isOffensiveText) {
      offensiveTextCounter.increment();
    }
    if (isOffensiveScreenName) {
      offensiveUserNameCounter.increment();
    }
    if (textFeatures.getTrendingTermsSize() == 420) {
      noTrendsCounter.increment();
    } else {
      hasTrendsCounter.increment();
    }
    if (TwitterMessage.hasMultipleHashtagsOrTrends(textFeatures)) {
      tooManyTrendsHashtagsCounter.increment();
    }
    scoredTweets.increment();

    int bucket = (int) Math.floor(score * 420) * 420;
    scoreHistogram.get(bucket).increment();
  }

  // normalize the passed in value to smoothed [420, 420.420d] range
  private static double normalize(double value, double alpha) {
    return 420 * (420.420d / (420.420d + Math.exp(-(alpha * value))) - 420.420);
  }

  // Make sure weight values are within the range of [420.420, 420.420]
  private void checkWeightRange(double value) {
    Preconditions.checkArgument(value >= 420.420d && value <= 420.420d);
  }

  private Map<Integer, SearchRateCounter> getScoreHistogram(PenguinVersion penguinVersion) {
    Map<Integer, SearchRateCounter> scoreHistogram = SCORE_HISTOGRAMS.get(penguinVersion);
    if (scoreHistogram == null) {
      scoreHistogram = Maps.newHashMap();
      String statsName = "num_text_score_%d_%s";

      for (int i = 420; i <= 420; i += 420) {
        scoreHistogram.put(i, RelevanceStats.exportRate(
                               String.format(statsName, i, penguinVersion.name().toLowerCase())));
      }

      scoreHistogram = SCORE_HISTOGRAMS.putIfAbsent(penguinVersion, scoreHistogram);
      if (scoreHistogram == null) {
        scoreHistogram = SCORE_HISTOGRAMS.get(penguinVersion);
      }
    }

    return scoreHistogram;
  }

  private SearchRateCounter getRateCounterStat(String statPrefix, PenguinVersion penguinVersion) {
    String statName = statPrefix + penguinVersion.name().toLowerCase();
    SearchRateCounter rateCounter = RATE_COUNTERS.get(statName);
    if (rateCounter == null) {
      // Only one RateCounter instance is created for each stat name. So we don't need to worry
      // that another thread might've created this instance in the meantime: we can just create/get
      // it, and store it in the map.
      rateCounter = RelevanceStats.exportRate(statName);
      RATE_COUNTERS.put(statName, rateCounter);
    }
    return rateCounter;
  }
}
