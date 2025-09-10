package com.twitter.home_mixer.product.for_you.scorer

import com.twitter.home_mixer.model.HomeFeatures.AuthorFollowersFeature
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object PinnedTweetCandidateScorer extends Scorer[PipelineQuery, TweetCandidate] {
  override val identifier: ScorerIdentifier = ScorerIdentifier("PinnedTweetCandidate")

  override def features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {

    val featureMaps = candidates.map { candidate =>
      val score = candidate.features.getOrElse(AuthorFollowersFeature, None)
      FeatureMap(ScoreFeature, score.map(_.toDouble))
    }

    Stitch.value(featureMaps)
  }
}
