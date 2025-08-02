package com.twitter.home_mitwitterer.product.for_you.response_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsTweetPreviewFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineservice.suggests.{thriftscala => st}
import com.twitter.search.earlybird.{thriftscala => eb}

object TweetPreviewResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[eb.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("TweetPreviewResponse")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIdFeature, IsTweetPreviewFeature, SuggestTypeFeature)

  def transform(
    input: eb.ThriftSearchResult
  ): FeatureMap = {
    FeatureMapBuilder()
      .add(IsTweetPreviewFeature, true)
      .add(SuggestTypeFeature, Some(st.SuggestType.TweetPreview))
      .add(AuthorIdFeature, input.metadata.map(_.fromUserId))
      .build()
  }
}
