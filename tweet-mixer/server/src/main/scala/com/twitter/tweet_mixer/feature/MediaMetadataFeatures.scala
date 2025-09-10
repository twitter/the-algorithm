package com.twitter.tweet_mixer.feature

import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object MediaClusterIdFeature extends Feature[TweetCandidate, Option[Long]]

object ImpressedMediaIds extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
  override val defaultValue: Seq[Long] = Seq.empty
}
