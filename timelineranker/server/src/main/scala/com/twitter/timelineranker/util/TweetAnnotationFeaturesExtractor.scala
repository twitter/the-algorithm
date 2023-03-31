package com.twitter.timelineranker.util

import com.twitter.tweetypie.{thriftscala => tweetypie}
import com.twitter.timelineranker.recap.model.ContentFeatures

object TweetAnnotationFeaturesExtractor {
  def addAnnotationFeaturesFromTweet(
    inputFeatures: ContentFeatures,
    tweet: tweetypie.Tweet,
    hydrateSemanticCoreFeatures: Boolean
  ): ContentFeatures = {
    if (hydrateSemanticCoreFeatures) {
      val annotations = tweet.escherbirdEntityAnnotations.map(_.entityAnnotations)
      inputFeatures.copy(semanticCoreAnnotations = annotations)
    } else {
      inputFeatures
    }
  }
}
