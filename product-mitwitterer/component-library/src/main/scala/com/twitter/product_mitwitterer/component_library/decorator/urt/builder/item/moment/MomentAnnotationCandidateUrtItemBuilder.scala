package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.moment

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.moment.MomentAnnotationCandidateUrtItemBuilder.MomentAnnotationItemClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.MomentAnnotationCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.moment.MomentAnnotationItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object MomentAnnotationCandidateUrtItemBuilder {
  val MomentAnnotationItemClientEventInfoElement = "metadata"
}

case class MomentAnnotationCandidateUrtItemBuilder[Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, MomentAnnotationCandidate],
  annotationTetwittertRichTetwittertBuilder: BaseRichTetwittertBuilder[Query, MomentAnnotationCandidate],
  annotationHeaderRichTetwittertBuilder: BaseRichTetwittertBuilder[Query, MomentAnnotationCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, MomentAnnotationCandidate]
  ] = None,
) etwittertends CandidateUrtEntryBuilder[Query, MomentAnnotationCandidate, MomentAnnotationItem] {

  override def apply(
    query: Query,
    candidate: MomentAnnotationCandidate,
    candidateFeatures: FeatureMap
  ): MomentAnnotationItem = MomentAnnotationItem(
    id = candidate.id,
    sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      candidate,
      candidateFeatures,
      Some(MomentAnnotationItemClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, candidate, candidateFeatures)),
    isPinned = None,
    tetwittert =
      candidate.tetwittert.map(_ => annotationTetwittertRichTetwittertBuilder(query, candidate, candidateFeatures)),
    header = candidate.header.map(_ =>
      annotationHeaderRichTetwittertBuilder(query, candidate, candidateFeatures)),
  )
}
