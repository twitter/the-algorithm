package com.ExTwitter.product_mixer.component_library.pipeline.candidate.who_to_follow_module

import com.ExTwitter.hermit.{thriftscala => h}
import com.ExTwitter.account_recommendations_mixer.{thriftscala => t}
import com.ExTwitter.product_mixer.component_library.model.candidate.UserCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ContextTypeFeature extends Feature[UserCandidate, Option[t.ContextType]]

object WhoToFollowArmResponseFeatureTransformer
    extends CandidateFeatureTransformer[t.RecommendedUser] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("WhoToFollowArmResponse")

  override val features: Set[Feature[_, _]] =
    Set(
      AdImpressionFeature,
      ContextTypeFeature,
      HermitContextTypeFeature,
      SocialTextFeature,
      TrackingTokenFeature,
      ScoreFeature)

  override def transform(input: t.RecommendedUser): FeatureMap = FeatureMapBuilder()
    .add(AdImpressionFeature, input.adImpression)
    .add(ContextTypeFeature, input.contextType)
    .add(
      HermitContextTypeFeature,
      input.contextType.map(contextType => h.ContextType(contextType.value)))
    .add(SocialTextFeature, input.socialText)
    .add(TrackingTokenFeature, input.trackingToken)
    .add(ScoreFeature, input.mlPredictionScore)
    .build()
}
