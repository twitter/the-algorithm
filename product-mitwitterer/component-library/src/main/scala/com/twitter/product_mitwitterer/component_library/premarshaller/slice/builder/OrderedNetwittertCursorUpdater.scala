package com.twitter.product_mitwitterer.component_library.premarshaller.slice.builder

import com.twitter.product_mitwitterer.component_library.model.cursor.OrderedCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.CursorSerializer
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.NetwittertCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

/**
 * Updates an [[OrderedCursor]] in the Netwittert position
 *
 * @param idSelector Specifies the entry from which to derive the `id` field
 * @param includeOperation Specifies whether to include the builder operation in the response
 * @param serializer Converts the cursor to an encoded string
 */
case class OrderedNetwittertCursorUpdater[Query <: PipelineQuery with HasPipelineCursor[OrderedCursor]](
  idSelector: PartialFunction[SliceItem, Long],
  override val includeOperation: ShouldInclude[Query] = AlwaysInclude,
  serializer: PipelineCursorSerializer[OrderedCursor] = CursorSerializer)
    etwittertends SliceCursorUpdaterFromUnderlyingBuilder[Query] {
  override val cursorType: CursorType = NetwittertCursor

  override val underlying: OrderedNetwittertCursorBuilder[Query] =
    OrderedNetwittertCursorBuilder(idSelector, includeOperation, serializer)
}
