package com.X.home_mixer.product.list_recommended_users

import com.X.hermit.candidate.{thriftscala => t}
import com.X.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.ScoreFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier

object ListMemberBasedUsersResponseFeatureTransfromer
    extends CandidateFeatureTransformer[t.Candidate] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("ListMemberBasedUsers")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def transform(candidate: t.Candidate): FeatureMap = FeatureMapBuilder()
    .add(ScoreFeature, candidate.score)
    .build()
}
