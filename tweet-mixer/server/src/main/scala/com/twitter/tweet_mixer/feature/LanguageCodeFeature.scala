package com.twitter.tweet_mixer.feature

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure

object LanguageCodeFeature extends FeatureWithDefaultOnFailure[TweetCandidate, Option[String]] {
  override val defaultValue: Option[String] = None
}
