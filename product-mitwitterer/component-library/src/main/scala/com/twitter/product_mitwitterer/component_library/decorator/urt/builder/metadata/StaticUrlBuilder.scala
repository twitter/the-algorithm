package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseUrlBuilder
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Url
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.UrlType
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case class StaticUrlBuilder(url: String, urlType: UrlType)
    etwittertends BaseUrlBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): Url = Url(url = url, urlType = urlType)
}
