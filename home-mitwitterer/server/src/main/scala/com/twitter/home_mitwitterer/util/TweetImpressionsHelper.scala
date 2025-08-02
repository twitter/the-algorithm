package com.twitter.home_mitwitterer.util

import com.twitter.home_mitwitterer.model.HomeFeatures.TweetImpressionsFeature
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweets
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap

object TweetImpressionsHelper {
  def tweetImpressions(features: FeatureMap): Set[Long] = {
    val manhattanImpressions =
      features.getOrElse(TweetImpressionsFeature, Seq.empty).flatMap(_.tweetIds)
    val memcacheImpressions = features.getOrElse(ImpressedTweets, Seq.empty)

    (manhattanImpressions ++ memcacheImpressions).toSet
  }
}
