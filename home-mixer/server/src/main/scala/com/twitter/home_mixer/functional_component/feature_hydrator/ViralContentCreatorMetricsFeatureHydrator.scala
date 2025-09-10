package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ViralContentCreatorFeature
import com.twitter.home_mixer.module.ViralContentCreatorsConfig
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

/**
 *  Track metrics on how often we serve posts from viral content creators
 */
@Singleton
case class ViralContentCreatorMetricsFeatureHydrator @Inject() (
  viralContentCreatorsConfig: ViralContentCreatorsConfig)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("ViralContentCreatorMetrics")

  override val features: Set[Feature[_, _]] = Set(ViralContentCreatorFeature)

  private val ViralContentCreators = viralContentCreatorsConfig.creators

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    candidates.map { candidate =>
      val authorIdOpt = candidate.features.getOrElse(AuthorIdFeature, None)
      FeatureMap(ViralContentCreatorFeature, authorIdOpt.exists(ViralContentCreators.contains))
    }
  }
}
