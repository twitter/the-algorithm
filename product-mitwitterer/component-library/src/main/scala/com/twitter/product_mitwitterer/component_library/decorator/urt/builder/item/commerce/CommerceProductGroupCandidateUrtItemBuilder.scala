package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.commerce

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.commerce.CommerceProductGroupCandidateUrtItemBuilder.CommerceProductGroupClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.CommerceProductGroupCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.commerce.CommerceProductGroupItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object CommerceProductGroupCandidateUrtItemBuilder {
  val CommerceProductGroupClientEventInfoElement: String = "commerce-product-group"
}

case class CommerceProductGroupCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, CommerceProductGroupCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, CommerceProductGroupCandidate]
  ]) etwittertends CandidateUrtEntryBuilder[
      Query,
      CommerceProductGroupCandidate,
      CommerceProductGroupItem
    ] {

  override def apply(
    query: Query,
    candidate: CommerceProductGroupCandidate,
    candidateFeatures: FeatureMap
  ): CommerceProductGroupItem =
    CommerceProductGroupItem(
      id = candidate.id,
      sortIndetwitter = None,
      clientEventInfo = clientEventInfoBuilder(
        query,
        candidate,
        candidateFeatures,
        Some(CommerceProductGroupClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures))
    )
}
