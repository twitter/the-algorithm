package com.twitter.search.common.relevance.classifiers;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import com.twitter.finagle.mtls.authentication.ServiceIdentifier;
import java.util.List;

import com.twitter.common_internal.tetwittert.version.PenguinVersion;
import com.twitter.search.common.relevance.config.TweetProcessingConfig;
import com.twitter.search.common.relevance.entities.TwitterMessage;

/**
 * Classifier that focuses on tweet tetwittert features and their corresponding
 * quality.
 */
public class TweetTetwittertClassifier etwittertends TweetClassifier {
  private TweetQualityFeatureEtwittertractor featureEtwittertractor = new TweetQualityFeatureEtwittertractor();
  private TweetTrendsEtwittertractor trendsEtwittertractor = null;

  /**
   * Constructor. Requires a list of TweetQualityEvaluator objects.
   * @param evaluators list of TweetQualityEvaluator objects responsible for quality evaluation.
   * @param serviceIdentifier The identifier of the calling service.
   * @param supportedPenguinVersions A list of supported penguin versions.
   */
  public TweetTetwittertClassifier(
      final Iterable<TweetEvaluator> evaluators,
      ServiceIdentifier serviceIdentifier,
      List<PenguinVersion> supportedPenguinVersions) {
    Preconditions.checkNotNull(evaluators);
    setQualityEvaluators(evaluators);
    TweetProcessingConfig.init();

    if (TweetProcessingConfig.getBool("etwittertract_trends", false)) {
      trendsEtwittertractor = new TweetTrendsEtwittertractor(serviceIdentifier, supportedPenguinVersions);
    }
  }

  /**
   * Etwittertract tetwittert features for the specified TwitterMessage.
   *
   * @param tweet TwitterMessage to etwittertract features from.
   */
  @Override
  protected void etwittertractFeatures(TwitterMessage tweet) {
    etwittertractFeatures(Lists.newArrayList(tweet));
  }

  /**
   * Etwittertract tetwittert features for the specified list of TwitterMessages.
   *
   * @param tweets list of TwitterMessages to etwittertract interesting features for
   */
  @Override
  protected void etwittertractFeatures(Iterable<TwitterMessage> tweets) {
    Preconditions.checkNotNull(tweets);
    for (TwitterMessage tweet : tweets) {
      featureEtwittertractor.etwittertractTweetTetwittertFeatures(tweet);
    }

    // Optionally try to annotate trends for all the tweets.
    if (TweetProcessingConfig.getBool("etwittertract_trends", false) && trendsEtwittertractor != null) {
      trendsEtwittertractor.etwittertractTrends(tweets);
    }
  }
}
