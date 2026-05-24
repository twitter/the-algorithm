package com.twitter.product_mixer.component_library.decorator.urt.builder.item.commerce

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.commerce.CommerceProductCandidateUrtItemBuilder.CommerceProductClientEventInfoElement
import com.twitter.product_mixer.component_library.model.candidate.CommerceProductCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductItem
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object CommerceProductCandidateUrtItemBuilder {
  val CommerceProductClientEventInfoElement: String = "commerce-product"
}

case class CommerceProductCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, CommerceProductCandidate],
  feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[Query, CommerceProductCandidate]])
    extends CandidateUrtEntryBuilder[
      Query,
      CommerceProductCandidate,
      CommerceProductItem
    ] {

  override def apply(
    query: Query,
    candidate: CommerceProductCandidate,
    candidateFeatures: FeatureMap
  ): CommerceProductItem =
    CommerceProductItem(
      id = candidate.id,
      sortIndex = None,
      clientEventInfo = clientEventInfoBuilder(
        query,
        candidate,
        candidateFeatures,
        Some(CommerceProductClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures))
    )
}
