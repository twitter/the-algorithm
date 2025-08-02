package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.prompt

/**
 * Represents the different ways to display the Relevance Prompt in a timeline.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/RelevancePromptDisplayType.html
 */
sealed trait RelevancePromptDisplayType

case object Normal etwittertends RelevancePromptDisplayType
case object Compact etwittertends RelevancePromptDisplayType
case object Large etwittertends RelevancePromptDisplayType
case object ThumbsUpAndDown etwittertends RelevancePromptDisplayType
