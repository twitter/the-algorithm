package com.X.home_mixer.product.for_you.response_transformer

import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.IsTweetPreviewFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineservice.suggests.{thriftscala => st}
import com.X.search.earlybird.{thriftscala => eb}

object TweetPreviewResponseFeatureTransformer
    extends CandidateFeatureTransformer[eb.ThriftSearchResult] {

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
