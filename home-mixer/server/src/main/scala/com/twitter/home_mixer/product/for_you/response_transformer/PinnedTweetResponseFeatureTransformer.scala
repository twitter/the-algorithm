package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.product.for_you.candidate_source.PinnedTweetCandidate
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier

object PinnedTweetResponseFeatureTransformer
    extends CandidateFeatureTransformer[PinnedTweetCandidate] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("PinnedTweetResponse")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIdFeature, ServedTypeFeature)

  def transform(
    input: PinnedTweetCandidate
  ): FeatureMap = {
    FeatureMapBuilder()
      .add(AuthorIdFeature, input.userId)
      .add(ServedTypeFeature, hmt.ServedType.ForYouPinned)
      .build()
  }
}
