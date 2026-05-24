package com.twitter.product_mixer.core.model.marshalling.response.urt.item.suggestion

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.highlight.HighlightedSection

/**
 * Represents text with hit-highlights used for returning search query suggestions.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/TextResult.html
 */
case class TextResult(
  text: String,
  hitHighlights: Option[Seq[HighlightedSection]],
  score: Option[Double],
  querySource: Option[String])
