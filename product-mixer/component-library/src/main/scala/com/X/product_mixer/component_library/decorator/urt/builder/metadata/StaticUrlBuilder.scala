package com.X.product_mixer.component_library.decorator.urt.builder.metadata

import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Url
import com.X.product_mixer.core.model.marshalling.response.urt.metadata.UrlType
import com.X.product_mixer.core.pipeline.PipelineQuery

case class StaticUrlBuilder(url: String, urlType: UrlType)
    extends BaseUrlBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): Url = Url(url = url, urlType = urlType)
}
