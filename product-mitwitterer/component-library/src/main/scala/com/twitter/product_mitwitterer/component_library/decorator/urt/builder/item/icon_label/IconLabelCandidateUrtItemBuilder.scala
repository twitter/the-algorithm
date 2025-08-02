package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.icon_label

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.icon_label.IconLabelCandidateUrtItemBuilder.IconLabelClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.LabelCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.icon.HorizonIcon
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.icon_label.IconLabelItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.richtetwittert.RichTetwittertEntity
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object IconLabelCandidateUrtItemBuilder {
  val IconLabelClientEventInfoElement: String = "iconlabel"
}

case class IconLabelCandidateUrtItemBuilder[-Query <: PipelineQuery, Candidate <: LabelCandidate](
  richTetwittertBuilder: BaseRichTetwittertBuilder[Query, Candidate],
  icon: Option[HorizonIcon] = None,
  entities: Option[List[RichTetwittertEntity]] = None,
  clientEventInfoBuilder: Option[BaseClientEventInfoBuilder[Query, Candidate]] = None,
  feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[Query, Candidate]] = None)
    etwittertends CandidateUrtEntryBuilder[Query, Candidate, IconLabelItem] {

  override def apply(
    query: Query,
    labelCandidate: Candidate,
    candidateFeatures: FeatureMap
  ): IconLabelItem =
    IconLabelItem(
      id = labelCandidate.id.toString,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder.flatMap(
        _.apply(query, labelCandidate, candidateFeatures, Some(IconLabelClientEventInfoElement))),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, labelCandidate, candidateFeatures)),
      tetwittert = richTetwittertBuilder(query, labelCandidate, candidateFeatures),
      icon = icon,
    )
}
