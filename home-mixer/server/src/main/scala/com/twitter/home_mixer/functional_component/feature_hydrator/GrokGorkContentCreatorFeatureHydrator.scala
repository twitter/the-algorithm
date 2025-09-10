package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.GrokContentCreatorFeature
import com.twitter.home_mixer.model.HomeFeatures.GorkContentCreatorFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch

/**
 * Used only for metrics tracking to measure how often we are serving these posts
 */
object GrokGorkContentCreatorFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("GrokGorkContentCreator")

  override val features: Set[Feature[_, _]] =
    Set(GrokContentCreatorFeature, GorkContentCreatorFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {

    candidates.map { candidate =>
      val authorIdOpt = candidate.features.getOrElse(AuthorIdFeature, None)
      FeatureMap(
        GrokContentCreatorFeature,
        authorIdOpt.contains(GrokCreatorId),
        GorkContentCreatorFeature,
        authorIdOpt.contains(GorkCreatorId),
      )
    }
  }

  private val GrokCreatorId = <removed_id> // @grok
  private val GorkCreatorId = <removed_id> // @gork
}
