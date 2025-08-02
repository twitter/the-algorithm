package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message.InlinePromptCandidateUrtItemStringCenterBuilder.InlinePromptClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.InlinePromptCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.richtetwittert.BaseRichTetwittertBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.social_contetwittert.BaseSocialContetwittertBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.InlinePromptMessageContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessagePromptItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object InlinePromptCandidateUrtItemStringCenterBuilder {
  val InlinePromptClientEventInfoElement: String = "message"
}

case class InlinePromptCandidateUrtItemStringCenterBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, InlinePromptCandidate],
  feedbackActionInfoBuilder: Option[
    BaseFeedbackActionInfoBuilder[Query, InlinePromptCandidate]
  ] = None,
  headerTetwittertBuilder: BaseStr[Query, InlinePromptCandidate],
  bodyTetwittertBuilder: Option[BaseStr[Query, InlinePromptCandidate]] = None,
  headerRichTetwittertBuilder: Option[BaseRichTetwittertBuilder[Query, InlinePromptCandidate]] = None,
  bodyRichTetwittertBuilder: Option[BaseRichTetwittertBuilder[Query, InlinePromptCandidate]] = None,
  primaryMessageTetwittertActionBuilder: Option[
    MessageTetwittertActionBuilder[Query, InlinePromptCandidate]
  ] = None,
  secondaryMessageTetwittertActionBuilder: Option[
    MessageTetwittertActionBuilder[Query, InlinePromptCandidate]
  ] = None,
  socialContetwittertBuilder: Option[BaseSocialContetwittertBuilder[Query, InlinePromptCandidate]] = None,
  userFacePileBuilder: Option[
    UserFacePileBuilder
  ] = None)
    etwittertends CandidateUrtEntryBuilder[Query, InlinePromptCandidate, MessagePromptItem] {

  override def apply(
    query: Query,
    inlinePromptCandidate: InlinePromptCandidate,
    candidateFeatures: FeatureMap
  ): MessagePromptItem =
    MessagePromptItem(
      id = inlinePromptCandidate.id,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query,
        inlinePromptCandidate,
        candidateFeatures,
        Some(InlinePromptClientEventInfoElement)),
      feedbackActionInfo =
        feedbackActionInfoBuilder.flatMap(_.apply(query, inlinePromptCandidate, candidateFeatures)),
      isPinned = None,
      content = InlinePromptMessageContent(
        headerTetwittert = headerTetwittertBuilder.apply(query, inlinePromptCandidate, candidateFeatures),
        bodyTetwittert = bodyTetwittertBuilder.map(_.apply(query, inlinePromptCandidate, candidateFeatures)),
        primaryButtonAction = primaryMessageTetwittertActionBuilder.map(
          _.apply(query, inlinePromptCandidate, candidateFeatures)),
        secondaryButtonAction = secondaryMessageTetwittertActionBuilder.map(
          _.apply(query, inlinePromptCandidate, candidateFeatures)),
        headerRichTetwittert =
          headerRichTetwittertBuilder.map(_.apply(query, inlinePromptCandidate, candidateFeatures)),
        bodyRichTetwittert =
          bodyRichTetwittertBuilder.map(_.apply(query, inlinePromptCandidate, candidateFeatures)),
        socialContetwittert =
          socialContetwittertBuilder.flatMap(_.apply(query, inlinePromptCandidate, candidateFeatures)),
        userFacepile = userFacePileBuilder.map(_.apply())
      ),
      impressionCallbacks = None
    )
}
