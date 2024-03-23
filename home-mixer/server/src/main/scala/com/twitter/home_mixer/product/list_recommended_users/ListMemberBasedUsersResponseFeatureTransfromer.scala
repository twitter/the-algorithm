package com.ExTwitter.home_mixer.product.list_recommended_users

import com.ExTwitter.hermit.candidate.{thriftscala => t}
import com.ExTwitter.home_mixer.product.list_recommended_users.model.ListRecommendedUsersFeatures.ScoreFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object ListMemberBasedUsersResponseFeatureTransfromer
    extends CandidateFeatureTransformer[t.Candidate] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("ListMemberBasedUsers")

  override val features: Set[Feature[_, _]] = Set(ScoreFeature)

  override def transform(candidate: t.Candidate): FeatureMap = FeatureMapBuilder()
    .add(ScoreFeature, candidate.score)
    .build()
}
