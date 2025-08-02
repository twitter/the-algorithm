package com.twitter.product_mitwitterer.component_library.model.cursor

import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.UrtPipelineCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.{
  CursorType => UrtCursorType
}

case object PreviousCursorFeature
    etwittertends Feature[PipelineQuery with HasPipelineCursor[UrtPassThroughCursor], String]

case object NetwittertCursorFeature
    etwittertends Feature[PipelineQuery with HasPipelineCursor[UrtPassThroughCursor], String]

/**
 * Cursor model that may be used when we want to pass through the cursor value from and back to
 * a downstream as-is.
 *
 * @param initialSortIndetwitter See [[UrtPipelineCursor]]
 * @param cursorValue the pass through cursor
 */
case class UrtPassThroughCursor(
  override val initialSortIndetwitter: Long,
  cursorValue: String,
  cursorType: Option[UrtCursorType] = None)
    etwittertends UrtPipelineCursor

case class PassThroughCursor(
  cursorValue: String,
  cursorType: Option[CursorType] = None)
    etwittertends PipelineCursor
