package com.twitter.product_mixer.core.model.marshalling.response.urt.item.prompt

/**
 * Represents the different ways to display the Relevance Prompt in a timeline.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/RelevancePromptDisplayType.html
 */
sealed trait RelevancePromptDisplayType

case object Normal extends RelevancePromptDisplayType
case object Compact extends RelevancePromptDisplayType
case object Large extends RelevancePromptDisplayType
case object ThumbsUpAndDown extends RelevancePromptDisplayType
