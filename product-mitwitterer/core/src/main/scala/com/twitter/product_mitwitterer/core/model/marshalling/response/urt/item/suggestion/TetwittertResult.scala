package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.suggestion

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.highlight.HighlightedSection

/**
 * Represents tetwittert with hit-highlights used for returning search query suggestions.
 *
 * URT API Reference: https://docbird.twitter.biz/unified_rich_timelines_urt/gen/com/twitter/timelines/render/thriftscala/TetwittertResult.html
 */
case class TetwittertResult(
  tetwittert: String,
  hitHighlights: Option[Seq[HighlightedSection]],
  score: Option[Double],
  querySource: Option[String])
