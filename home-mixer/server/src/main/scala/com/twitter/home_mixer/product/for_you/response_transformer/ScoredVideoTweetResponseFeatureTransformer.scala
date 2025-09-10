package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoAspectRatioFeature
import com.twitter.home_mixer.model.HomeFeatures.VideoDisplayTypeFeature
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredVideoTweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.Carousel
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.CompactCarousel

object ScoredVideoTweetResponseFeatureTransformer
    extends CandidateFeatureTransformer[ScoredVideoTweetCandidate] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredVideoTweetResponse")

  override val features: Set[Feature[_, _]] =
    Set(AuthorIdFeature, ServedTypeFeature, VideoAspectRatioFeature, VideoDisplayTypeFeature)

  def transform(
    input: ScoredVideoTweetCandidate
  ): FeatureMap = {
    val displayType = input.aspectRatio.map { ratio =>
      if (ratio > 1.0) Carousel else CompactCarousel
    }
    FeatureMapBuilder()
      .add(AuthorIdFeature, Some(input.authorId))
      .add(ServedTypeFeature, input.servedType)
      .add(VideoAspectRatioFeature, input.aspectRatio.map(_.toFloat))
      .add(VideoDisplayTypeFeature, displayType)
      .build()
  }
}
