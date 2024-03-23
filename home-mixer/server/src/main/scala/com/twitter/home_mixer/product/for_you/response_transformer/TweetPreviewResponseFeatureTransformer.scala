package com.ExTwitter.home_mixer.product.for_you.response_transformer

import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsTweetPreviewFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}
import com.ExTwitter.search.earlybird.{thriftscala => eb}

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
