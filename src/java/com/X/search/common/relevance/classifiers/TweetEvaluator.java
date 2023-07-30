package com.X.search.common.relevance.classifiers;

import com.google.common.base.Preconditions;

import com.X.search.common.relevance.entities.XMessage;

/**
 * Interface to perform quality evaluation for a single @XMessage
 * object or a group of them.
 *
 */
public abstract class TweetEvaluator {
  /**
   * Passed in XMessage is examined and any extractable
   * features are stored in TweetFeatures field of XMessage.
   *
   * @param tweet XMessage to perform classification on.
   */
  public abstract void evaluate(final XMessage tweet);

  /**
   * Classify a group of XMessages and store the features in their corresponding
   * TweetFeatures fields.
   *
   * This default implementation just iterates through the map and classifies each
   * individual tweet. Batching for better performance, if applicable, can be implemented by
   * concrete subclasses.
   *
   * @param tweets XMessages to perform classification on.
   */
   public void evaluate(final Iterable<XMessage> tweets) {
    Preconditions.checkNotNull(tweets);
    for (XMessage tweet: tweets) {
      evaluate(tweet);
    }
  }
}
