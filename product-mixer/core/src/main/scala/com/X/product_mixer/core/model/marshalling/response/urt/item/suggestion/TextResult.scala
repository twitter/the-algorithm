package com.X.product_mixer.core.model.marshalling.response.urt.item.suggestion

import com.X.product_mixer.core.model.marshalling.response.urt.item.highlight.HighlightedSection

/**
 * Represents text with hit-highlights used for returning search query suggestions.
 *
 * URT API Reference: https://docbird.X.biz/unified_rich_timelines_urt/gen/com/X/timelines/render/thriftscala/TextResult.html
 */
case class TextResult(
  text: String,
  hitHighlights: Option[Seq[HighlightedSection]],
  score: Option[Double],
  querySource: Option[String])
