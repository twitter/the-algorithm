package com.twitter.product_mixer.component_library.decorator.urt.builder.item.relevance_prompt

import com.twitter.product_mixer.component_library.decorator.urt.builder.item.relevance_prompt.RelevancePromptCandidateUrtItemStringCenterBuilder.RelevancePromptClientEventInfoElement
import com.twitter.product_mixer.component_library.model.candidate.RelevancePromptCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.metadata.BaseStr
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptContent
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.RelevancePromptFollowUpFeedbackType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object RelevancePromptCandidateUrtItemStringCenterBuilder {
  val RelevancePromptClientEventInfoElement: String = "relevance_prompt"
}

case class RelevancePromptCandidateUrtItemStringCenterBuilder[-Query <: PipelineQuery](
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, RelevancePromptCandidate],
  titleTextBuilder: BaseStr[Query, RelevancePromptCandidate],
  confirmationTextBuilder: BaseStr[Query, RelevancePromptCandidate],
  isRelevantTextBuilder: BaseStr[Query, RelevancePromptCandidate],
  notRelevantTextBuilder: BaseStr[Query, RelevancePromptCandidate],
  displayType: RelevancePromptDisplayType,
  isRelevantCallback: Callback,
  notRelevantCallback: Callback,
  isRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType] = None,
  notRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType] = None,
  impressionCallbacks: Option[List[Callback]] = None)
    extends CandidateUrtEntryBuilder[Query, RelevancePromptCandidate, PromptItem] {

  override def apply(
    query: Query,
    relevancePromptCandidate: RelevancePromptCandidate,
    candidateFeatures: FeatureMap
  ): PromptItem =
    PromptItem(
      id = relevancePromptCandidate.id,
      sortIndex = None,
      clientEventInfo = clientEventInfoBuilder(
        query,
        relevancePromptCandidate,
        candidateFeatures,
        Some(RelevancePromptClientEventInfoElement)),
      feedbackActionInfo = None,
      content = RelevancePromptContent(
        title = titleTextBuilder(query, relevancePromptCandidate, candidateFeatures),
        confirmation = confirmationTextBuilder(query, relevancePromptCandidate, candidateFeatures),
        isRelevantText = isRelevantTextBuilder(query, relevancePromptCandidate, candidateFeatures),
        notRelevantText =
          notRelevantTextBuilder(query, relevancePromptCandidate, candidateFeatures),
        isRelevantCallback = isRelevantCallback,
        notRelevantCallback = notRelevantCallback,
        displayType = displayType,
        isRelevantFollowUp = isRelevantFollowUp,
        notRelevantFollowUp = notRelevantFollowUp,
      ),
      impressionCallbacks = impressionCallbacks
    )
}
