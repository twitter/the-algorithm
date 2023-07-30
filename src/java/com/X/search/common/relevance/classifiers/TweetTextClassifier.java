package com.X.search.common.relevance.classifiers;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.X.finagle.mtls.authentication.ServiceIdentifier;
import java.util.List;

import com.X.common_internal.text.version.PenguinVersion;
import com.X.search.common.relevance.config.TweetProcessingConfig;
import com.X.search.common.relevance.entities.XMessage;

/**
 * Classifier that focuses on tweet text features and their corresponding
 * quality.
 */
public class TweetTextClassifier extends TweetClassifier {
  private TweetQualityFeatureExtractor featureExtractor = new TweetQualityFeatureExtractor();
  private TweetTrendsExtractor trendsExtractor = null;

  /**
   * Constructor. Requires a list of TweetQualityEvaluator objects.
   * @param evaluators list of TweetQualityEvaluator objects responsible for quality evaluation.
   * @param serviceIdentifier The identifier of the calling service.
   * @param supportedPenguinVersions A list of supported penguin versions.
   */
  public TweetTextClassifier(
      final Iterable<TweetEvaluator> evaluators,
      ServiceIdentifier serviceIdentifier,
      List<PenguinVersion> supportedPenguinVersions) {
    Preconditions.checkNotNull(evaluators);
    setQualityEvaluators(evaluators);
    TweetProcessingConfig.init();

    if (TweetProcessingConfig.getBool("extract_trends", false)) {
      trendsExtractor = new TweetTrendsExtractor(serviceIdentifier, supportedPenguinVersions);
    }
  }

  /**
   * Extract text features for the specified XMessage.
   *
   * @param tweet XMessage to extract features from.
   */
  @Override
  protected void extractFeatures(XMessage tweet) {
    extractFeatures(Lists.newArrayList(tweet));
  }

  /**
   * Extract text features for the specified list of XMessages.
   *
   * @param tweets list of XMessages to extract interesting features for
   */
  @Override
  protected void extractFeatures(Iterable<XMessage> tweets) {
    Preconditions.checkNotNull(tweets);
    for (XMessage tweet : tweets) {
      featureExtractor.extractTweetTextFeatures(tweet);
    }

    // Optionally try to annotate trends for all the tweets.
    if (TweetProcessingConfig.getBool("extract_trends", false) && trendsExtractor != null) {
      trendsExtractor.extractTrends(tweets);
    }
  }
}
