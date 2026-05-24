package com.twitter.home_mixer.util

import com.twitter.home_mixer.model.HomeFeatures.TweetImpressionsFeature
import com.twitter.product_mixer.component_library.feature_hydrator.query.impressed_tweets.ImpressedTweets
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap

object TweetImpressionsHelper {
  def tweetImpressions(features: FeatureMap): Set[Long] = {
    val manhattanImpressions =
      features.getOrElse(TweetImpressionsFeature, Seq.empty).flatMap(_.tweetIds)
    val memcacheImpressions = features.getOrElse(ImpressedTweets, Seq.empty)

    (manhattanImpressions ++ memcacheImpressions).toSet
  }
}
