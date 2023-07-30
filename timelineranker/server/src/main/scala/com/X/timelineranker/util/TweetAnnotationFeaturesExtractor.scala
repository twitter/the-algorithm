package com.X.timelineranker.util

import com.X.tweetypie.{thriftscala => tweetypie}
import com.X.timelineranker.recap.model.ContentFeatures

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
