package com.X.product_mixer.component_library.decorator.urt.builder.item.X_list

import com.X.product_mixer.component_library.decorator.urt.builder.item.X_list.XListCandidateUrtItemBuilder.ListClientEventInfoElement
import com.X.product_mixer.component_library.model.candidate.XListCandidate
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.X.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.item.X_list.XListItem
import com.X.product_mixer.core.pipeline.PipelineQuery

object XListCandidateUrtItemBuilder {
  val ListClientEventInfoElement: String = "list"
}

case class XListCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, XListCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, XListCandidate]
  ] = None,
  displayType: Option[XListDisplayType] = None)
    extends CandidateUrtEntryBuilder[Query, XListCandidate, XListItem] {

  override def apply(
    query: Query,
    XListCandidate: XListCandidate,
    candidateFeatures: FeatureMap
  ): XListItem = XListItem(
    id = XListCandidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      XListCandidate,
      candidateFeatures,
      Some(ListClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, XListCandidate, candidateFeatures)),
    displayType = displayType
  )
}
