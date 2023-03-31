package com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Callback

/**
 * Represents different types of URT Prompts supported such as the Relevance Prompt.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/PromptContent.html
 */
sealed trait PromptContent

/**
 * Relevance Prompt is a Yes-No style prompt that can be used for collecting feedback from a User
 * about a part of their timeline.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/RelevancePrompt.html
 */
case class RelevancePromptContent(
  title: String,
  confirmation: String,
  isRelevantText: String,
  notRelevantText: String,
  isRelevantCallback: Callback,
  notRelevantCallback: Callback,
  displayType: RelevancePromptDisplayType,
  isRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType],
  notRelevantFollowUp: Option[RelevancePromptFollowUpFeedbackType])
    extends PromptContent
