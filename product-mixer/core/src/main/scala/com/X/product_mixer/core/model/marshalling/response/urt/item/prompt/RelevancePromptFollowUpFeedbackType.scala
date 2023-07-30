package com.X.product_mixer.core.model.marshalling.response.urt.item.prompt

import com.X.product_mixer.core.model.marshalling.response.urt.metadata.Callback

/**
 * Different kinds of follow-ups after a positive-negative feedback on a prompt button.
 *
 * URT API Reference: https://docbird.X.biz/unified_rich_timelines_urt/gen/com/X/timelines/render/thriftscala/RelevancePromptFollowUpFeedbackType.html
 */
sealed trait RelevancePromptFollowUpFeedbackType

case class RelevancePromptFollowUpTextInput(
  context: String,
  textFieldPlaceholder: String,
  sendTextCallback: Callback)
    extends RelevancePromptFollowUpFeedbackType
