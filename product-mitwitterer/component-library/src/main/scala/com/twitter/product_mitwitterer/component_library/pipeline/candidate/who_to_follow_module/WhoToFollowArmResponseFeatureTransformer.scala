package com.twitter.product_mitwitterer.component_library.pipeline.candidate.who_to_follow_module

import com.twitter.hermit.{thriftscala => h}
import com.twitter.account_recommendations_mitwitterer.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

object ContetwittertTypeFeature etwittertends Feature[UserCandidate, Option[t.ContetwittertType]]

object WhoToFollowArmResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[t.RecommendedUser] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("WhoToFollowArmResponse")

  override val features: Set[Feature[_, _]] =
    Set(
      AdImpressionFeature,
      ContetwittertTypeFeature,
      HermitContetwittertTypeFeature,
      SocialTetwittertFeature,
      TrackingTokenFeature,
      ScoreFeature)

  override def transform(input: t.RecommendedUser): FeatureMap = FeatureMapBuilder()
    .add(AdImpressionFeature, input.adImpression)
    .add(ContetwittertTypeFeature, input.contetwittertType)
    .add(
      HermitContetwittertTypeFeature,
      input.contetwittertType.map(contetwittertType => h.ContetwittertType(contetwittertType.value)))
    .add(SocialTetwittertFeature, input.socialTetwittert)
    .add(TrackingTokenFeature, input.trackingToken)
    .add(ScoreFeature, input.mlPredictionScore)
    .build()
}
