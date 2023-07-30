package com.X.product_mixer.component_library.decorator.urt.builder.item.commerce

import com.X.product_mixer.component_library.decorator.urt.builder.item.commerce.CommerceProductGroupCandidateUrtItemBuilder.CommerceProductGroupClientEventInfoElement
import com.X.product_mixer.component_library.model.candidate.CommerceProductGroupCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.item.commerce.CommerceProductGroupItem
import com.X.product_mixer.core.pipeline.PipelineQuery

object CommerceProductGroupCandidateUrtItemBuilder {
  val CommerceProductGroupClientEventInfoElement: String = "commerce-product-group"
}

case class CommerceProductGroupCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, CommerceProductGroupCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, CommerceProductGroupCandidate]
  ]) extends CandidateUrtEntryBuilder[
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
      sortIndex = None,
      clientEventInfo = clientEventInfoBuilder(
        query,
        candidate,
        candidateFeatures,
        Some(CommerceProductGroupClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures))
    )
}
