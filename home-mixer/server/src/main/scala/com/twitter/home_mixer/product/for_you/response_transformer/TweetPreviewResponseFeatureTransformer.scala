package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.IsTweetPreviewFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.search.earlybird.{thriftscala => eb}

object TweetPreviewResponseFeatureTransformer
    extends CandidateFeatureTransformer[eb.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("TweetPreviewResponse")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIdFeature, IsTweetPreviewFeature, ServedTypeFeature)

  def transform(
    input: eb.ThriftSearchResult
  ): FeatureMap = {
    FeatureMapBuilder()
      .add(IsTweetPreviewFeature, true)
      .add(ServedTypeFeature, hmt.ServedType.ForYouTweetPreview)
      .add(AuthorIdFeature, input.metadata.map(_.fromUserId))
      .build()
  }
}
