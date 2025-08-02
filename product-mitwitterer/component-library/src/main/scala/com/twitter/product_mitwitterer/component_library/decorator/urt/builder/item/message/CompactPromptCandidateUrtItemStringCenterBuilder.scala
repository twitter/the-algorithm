package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message.CompactPromptCandidateUrtItemStringCenterBuilder.CompactPromptClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.CompactPromptCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.CompactPromptMessageContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object CompactPromptCandidateUrtItemStringCenterBuilder {
  val CompactPromptClientEventInfoElement: String = "message"
}

case class CompactPromptCandidateUrtItemStringCenterBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, CompactPromptCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, CompactPromptCandidate]
  ] = None,
  headerTetwittertBuilder: BaseStr[Query, CompactPromptCandidate],
  bodyTetwittertBuilder: Option[BaseStr[Query, CompactPromptCandidate]] = None,
  headerRichTetwittertBuilder: Option[BaseRichTetwittertBuilder[Query, CompactPromptCandidate]] = None,
  bodyRichTetwittertBuilder: Option[BaseRichTetwittertBuilder[Query, CompactPromptCandidate]] = None)
    etwittertends CandidateUrtEntryBuilder[Query, CompactPromptCandidate, MessagePromptItem] {

  override def apply(
    query: Query,
    compactPromptCandidate: CompactPromptCandidate,
    candidateFeatures: FeatureMap
  ): MessagePromptItem =
    MessagePromptItem(
      id = compactPromptCandidate.id.toString,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query,
        compactPromptCandidate,
        candidateFeatures,
        Some(CompactPromptClientEventInfoElement)),
      feedbackActionInfo = feedbackActionInfoBuilder.flatMap(
        _.apply(query, compactPromptCandidate, candidateFeatures)),
      isPinned = None,
      content = CompactPromptMessageContent(
        headerTetwittert = headerTetwittertBuilder.apply(query, compactPromptCandidate, candidateFeatures),
        bodyTetwittert = bodyTetwittertBuilder.map(_.apply(query, compactPromptCandidate, candidateFeatures)),
        primaryButtonAction = None,
        secondaryButtonAction = None,
        action = None,
        headerRichTetwittert =
          headerRichTetwittertBuilder.map(_.apply(query, compactPromptCandidate, candidateFeatures)),
        bodyRichTetwittert =
          bodyRichTetwittertBuilder.map(_.apply(query, compactPromptCandidate, candidateFeatures))
      ),
      impressionCallbacks = None
    )
}
