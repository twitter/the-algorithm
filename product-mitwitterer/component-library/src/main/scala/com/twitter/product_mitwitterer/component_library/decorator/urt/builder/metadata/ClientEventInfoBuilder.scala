package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.metadata

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder

/**
 * Sets the [[ClientEventInfo]] with the `component` field set to [[component]]
 * @see  [[http://go/client-events]]
 */
case class ClientEventInfoBuilder[-Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
  component: String,
  detailsBuilder: Option[BaseClientEventDetailsBuilder[Query, Candidate]] = None)
    etwittertends BaseClientEventInfoBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap,
    element: Option[String]
  ): Option[ClientEventInfo] =
    Some(
      ClientEventInfo(
        component = Some(component),
        element = element,
        details = detailsBuilder.flatMap(_.apply(query, candidate, candidateFeatures)),
        action = None,
        entityToken = None)
    )
}

/**
 * In rare cases you might not want to send client event info. For
 * etwitterample, this might be set already on the client for some legacy
 * timelines.
 */
object EmptyClientEventInfoBuilder
    etwittertends BaseClientEventInfoBuilder[PipelineQuery, UniversalNoun[Any]] {
  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap,
    element: Option[String]
  ): Option[ClientEventInfo] = None
}
