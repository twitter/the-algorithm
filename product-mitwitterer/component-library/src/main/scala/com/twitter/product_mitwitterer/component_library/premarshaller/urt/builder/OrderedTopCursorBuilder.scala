package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.UrtCursorSerializer
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.OrderedTopCursorBuilder.TopCursorOffset
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

case object OrderedTopCursorBuilder {
  // Ensure that the netwittert initial sort indetwitter is at least 10000 entries away from top cursor's
  // current sort indetwitter. This is to ensure that the contents of the netwittert page can be populated
  // without being assigned sort indices which conflict with that of the current page. This assumes
  // that each page will have fewer than 10000 entries.
  val TopCursorOffset = 10000L
}

/**
 * Builds [[UrtOrderedCursor]] in the Top position
 *
 * @param idSelector Specifies the entry from which to derive the `id` field
 * @param serializer Converts the cursor to an encoded string
 */
case class OrderedTopCursorBuilder(
  idSelector: PartialFunction[UniversalNoun[_], Long],
  serializer: PipelineCursorSerializer[UrtOrderedCursor] = UrtCursorSerializer)
    etwittertends UrtCursorBuilder[
      PipelineQuery with HasPipelineCursor[UrtOrderedCursor]
    ] {
  override val cursorType: CursorType = TopCursor

  override def cursorValue(
    query: PipelineQuery with HasPipelineCursor[UrtOrderedCursor],
    timelineEntries: Seq[TimelineEntry]
  ): String = {
    val topId = timelineEntries.collectFirst(idSelector)

    val id = topId.orElse(query.pipelineCursor.flatMap(_.id))

    val cursor = UrtOrderedCursor(
      initialSortIndetwitter = cursorSortIndetwitter(query, timelineEntries) + TopCursorOffset,
      id = id,
      cursorType = Some(cursorType)
    )

    serializer.serializeCursor(cursor)
  }
}
