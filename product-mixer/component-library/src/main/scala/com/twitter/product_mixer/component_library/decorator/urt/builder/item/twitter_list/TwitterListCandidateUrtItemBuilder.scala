package com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.ExTwitter_list

import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.ExTwitter_list.ExTwitterListCandidateUrtItemBuilder.ListClientEventInfoElement
import com.ExTwitter.product_mixer.component_library.model.candidate.ExTwitterListCandidate
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.ExTwitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.ExTwitter_list.ExTwitterListDisplayType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.item.ExTwitter_list.ExTwitterListItem
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery

object ExTwitterListCandidateUrtItemBuilder {
  val ListClientEventInfoElement: String = "list"
}

case class ExTwitterListCandidateUrtItemBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, ExTwitterListCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, ExTwitterListCandidate]
  ] = None,
  displayType: Option[ExTwitterListDisplayType] = None)
    extends CandidateUrtEntryBuilder[Query, ExTwitterListCandidate, ExTwitterListItem] {

  override def apply(
    query: Query,
    ExTwitterListCandidate: ExTwitterListCandidate,
    candidateFeatures: FeatureMap
  ): ExTwitterListItem = ExTwitterListItem(
    id = ExTwitterListCandidate.id,
    sortIndex = None, // Sort indexes are automatically set in the domain marshaller phase
    clientEventInfo = clientEventInfoBuilder(
      query,
      ExTwitterListCandidate,
      candidateFeatures,
      Some(ListClientEventInfoElement)),
    feedbackActionInfo =
      feedbackActionInfoBuilder.flatMap(_.apply(query, ExTwitterListCandidate, candidateFeatures)),
    displayType = displayType
  )
}
