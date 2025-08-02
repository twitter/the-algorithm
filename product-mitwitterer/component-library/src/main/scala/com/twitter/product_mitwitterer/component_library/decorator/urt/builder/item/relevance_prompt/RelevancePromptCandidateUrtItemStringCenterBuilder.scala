package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.relevance_prompt

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.relevance_prompt.RelevancePromptCandidateUrtItemStringCenterBuilder.RelevancePromptClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.RelevancePromptCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt.RelevancePromptFollowUpFeedbackType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object RelevancePromptCandidateUrtItemStringCenterBuilder {
  val RelevancePromptClientEventInfoElement: String = "relevance_prompt"
}

case class RelevancePromptCandidateUrtItemStringCenterBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, RelevancePromptCandidate],
  titleTetwittertBuilder: BaseStr[Query, RelevancePromptCandidate],
  confirmationTetwittertBuilder: BaseStr[Query, RelevancePromptCandidate],
  isRelevantTetwittertBuilder: BaseStr[Query, RelevancePromptCandidate],
  notRelevantTetwittertBuilder: BaseStr[Query, RelevancePromptCandidate],
  displayType: RelevancePromptDisplayType,
  isRelevantCallback: Callback,
  notRelevantCallback: Callback,
  isRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType] = None,
  notRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType] = None,
  impressionCallbacks: Option[List[Callback]] = None)
    etwittertends CandidateUrtEntryBuilder[Query, RelevancePromptCandidate, PromptItem] {

  override def apply(
    query: Query,
    relevancePromptCandidate: RelevancePromptCandidate,
    candidateFeatures: FeatureMap
  ): PromptItem =
    PromptItem(
      id = relevancePromptCandidate.id,
      sortIndetwitter = None,
      clientEventInfo = clientEventInfoBuilder(
        query,
        relevancePromptCandidate,
        candidateFeatures,
        Some(RelevancePromptClientEventInfoElement)),
      feedbackActionInfo = None,
      content = RelevancePromptContent(
        title = titleTetwittertBuilder(query, relevancePromptCandidate, candidateFeatures),
        confirmation = confirmationTetwittertBuilder(query, relevancePromptCandidate, candidateFeatures),
        isRelevantTetwittert = isRelevantTetwittertBuilder(query, relevancePromptCandidate, candidateFeatures),
        notRelevantTetwittert =
          notRelevantTetwittertBuilder(query, relevancePromptCandidate, candidateFeatures),
        isRelevantCallback = isRelevantCallback,
        notRelevantCallback = notRelevantCallback,
        displayType = displayType,
        isRelevantFollowUp = isRelevantFollowUp,
        notRelevantFollowUp = notRelevantFollowUp,
      ),
      impressionCallbacks = impressionCallbacks
    )
}
