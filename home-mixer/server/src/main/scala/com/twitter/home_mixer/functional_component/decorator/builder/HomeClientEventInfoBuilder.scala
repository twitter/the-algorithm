package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.home_mixer.model.HomeFeatures.EntityTokenFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventDetailsBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.ClientEventInfo
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Sets the [[ClientEventInfo]] with the `component` field set to the Suggest Type assigned to each candidate
 */
case class HomeClientEventInfoBuilder[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
  detailsBuilder: Option[BaseClientEventDetailsBuilder[Query, Candidate]] = None)
    extends BaseClientEventInfoBuilder[Query, Candidate] {

  override def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap,
    element: Option[String]
  ): Option[ClientEventInfo] = {
    val servedType = candidateFeatures.get(ServedTypeFeature)

    Some(
      ClientEventInfo(
        component = Some(servedType.originalName),
        element = element,
        details = detailsBuilder.flatMap(_.apply(query, candidate, candidateFeatures)),
        action = None,
        /**
         * A backend entity encoded by the Client Entities Encoding Library.
         * Placeholder string for now
         */
        entityToken = candidateFeatures.getOrElse(EntityTokenFeature, None)
      )
    )
  }
}
