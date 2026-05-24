package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.home_mixer.product.for_you.candidate_source.StoryCandidate
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendDescriptionFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendDomainContextFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendNameFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendSocialContextImages
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendThumbnail
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendUrlFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.graphql.ApiImage
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.DeepLink
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Url

object StoriesModuleResponseFeatureTransformer extends CandidateFeatureTransformer[StoryCandidate] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("StoriesModuleResponse")

  override val features: Set[Feature[_, _]] = Set(
    TrendNameFeature,
    TrendDescriptionFeature,
    TrendUrlFeature,
    TrendDomainContextFeature,
    TrendSocialContextImages,
    TrendThumbnail
  )

  def transform(input: StoryCandidate): FeatureMap = {
    val url = input.id
    val thumbnail = input.thumbnail.map { img =>
      ApiImage(
        height = img.originalImgHeight,
        width = img.originalImgWidth,
        url = img.originalImgUrl
      )
    }

    FeatureMapBuilder()
      .add(TrendNameFeature, input.title)
      .add(TrendDescriptionFeature, None)
      .add(TrendUrlFeature, Url(DeepLink, url))
      .add(TrendDomainContextFeature, Some(input.context))
      .add(TrendSocialContextImages, Some(input.socialProof))
      .add(TrendThumbnail, thumbnail)
      .build()
  }
}
