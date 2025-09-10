package com.twitter.home_mixer.functional_component.decorator.urt.builder

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.ServedIdFeature
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.relevance_prompt.RelevancePromptCandidateUrtItemStringCenterBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.StrStatic
import com.twitter.product_mixer.component_library.model.candidate.RelevancePromptCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.Compact
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt.PromptItem
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.timelines.common.{thriftscala => thriftCommon}
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.service.thriftscala.FeedbackEngagementType
import com.twitter.timelineservice.model.FeedbackInfo
import com.twitter.timelineservice.model.FeedbackMetadata
import com.twitter.timelineservice.suggests.thriftscala.SuggestType
import com.twitter.timelineservice.{thriftscala => thrift}

object RelevancePromptCandidateUrtItemBuilder {

  val ClientEventInfoComponent: String = "for_you_relevance_prompt"

  val ClientEventBuilder = ClientEventInfoBuilder[PipelineQuery, RelevancePromptCandidate](
    component = ClientEventInfoComponent
  )

  val ConfirmationStr = "Thank you for your feedback!"
}

case class RelevancePromptCandidateUrtItemBuilder(
  titleParam: FSParam[String],
  relevantPromptParam: FSParam[String],
  notRelevantPromptParam: FSParam[String],
  neutralPromptParam: FSParam[String],
) extends CandidateUrtEntryBuilder[
      PipelineQuery,
      RelevancePromptCandidate,
      PromptItem
    ] {

  import RelevancePromptCandidateUrtItemBuilder._

  override def apply(
    query: PipelineQuery,
    candidate: RelevancePromptCandidate,
    candidateFeatures: FeatureMap
  ): PromptItem = {

    val servedId: Long = query.features.flatMap(_.getOrElse(ServedIdFeature, None)).getOrElse(0L)

    // Use user 0 as a dummy entity ID until we have a proper session ID to track
    val feedbackMetadata = FeedbackMetadata(
      Some(FeedbackEngagementType.RelevancePrompt),
      entityIds = Seq(thriftCommon.FeedbackEntity.FeedbackId(servedId)),
      ttl = Some(30.days)
    )

    val positiveFeedbackUrl = FeedbackInfo.feedbackUrl(
      feedbackType = thrift.FeedbackType.Relevant,
      feedbackMetadata = feedbackMetadata,
      injectionType = Some(SuggestType.RelevancePrompt)
    )
    val negativeFeedbackUrl = FeedbackInfo.feedbackUrl(
      feedbackType = thrift.FeedbackType.NotRelevant,
      feedbackMetadata = feedbackMetadata,
      injectionType = Some(SuggestType.RelevancePrompt)
    )
    val neutralFeedbackUrl = FeedbackInfo.feedbackUrl(
      feedbackType = thrift.FeedbackType.Neutral,
      feedbackMetadata = feedbackMetadata,
      injectionType = Some(SuggestType.RelevancePrompt)
    )

    val relevancePromptCandidateUrtItemStringCenterBuilder =
      RelevancePromptCandidateUrtItemStringCenterBuilder(
        clientEventInfoBuilder = ClientEventBuilder,
        titleTextBuilder = StrStatic(query.params(titleParam)),
        confirmationTextBuilder = StrStatic(ConfirmationStr),
        isRelevantTextBuilder = StrStatic(query.params(relevantPromptParam)),
        notRelevantTextBuilder = StrStatic(query.params(notRelevantPromptParam)),
        displayType = Compact,
        isRelevantCallback = Callback(positiveFeedbackUrl),
        notRelevantCallback = Callback(negativeFeedbackUrl),
        neutralTextBuilder = Some(StrStatic(query.params(neutralPromptParam))),
        neutralCallback = Some(Callback(neutralFeedbackUrl))
      )

    relevancePromptCandidateUrtItemStringCenterBuilder(
      query = query,
      relevancePromptCandidate = candidate,
      candidateFeatures = candidateFeatures
    )
  }
}
