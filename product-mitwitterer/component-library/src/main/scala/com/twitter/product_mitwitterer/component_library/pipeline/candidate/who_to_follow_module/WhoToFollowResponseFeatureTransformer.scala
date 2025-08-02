package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.adserver.{thriftscala => ad}
import com.twitter.hermit.{thriftscala => h}
import com.twitter.peoplediscovery.api.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

object AdImpressionFeature etwittertends Feature[UserCandidate, Option[ad.AdImpression]]
object HermitContetwittertTypeFeature etwittertends Feature[UserCandidate, Option[h.ContetwittertType]]
object SocialTetwittertFeature etwittertends Feature[UserCandidate, Option[String]]
object TrackingTokenFeature etwittertends Feature[UserCandidate, Option[String]]
object ScoreFeature etwittertends Feature[UserCandidate, Option[Double]]

object WhoToFollowResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[t.RecommendedUser] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("WhoToFollowResponse")

  override val features: Set[Feature[_, _]] =
    Set(
      AdImpressionFeature,
      HermitContetwittertTypeFeature,
      SocialTetwittertFeature,
      TrackingTokenFeature,
      ScoreFeature)

  override def transform(input: t.RecommendedUser): FeatureMap = FeatureMapBuilder()
    .add(AdImpressionFeature, input.adImpression)
    .add(HermitContetwittertTypeFeature, input.reason.flatMap(_.contetwittertType))
    .add(SocialTetwittertFeature, input.socialTetwittert)
    .add(TrackingTokenFeature, input.trackingToken)
    .add(ScoreFeature, input.mlPredictionScore)
    .build()
}
