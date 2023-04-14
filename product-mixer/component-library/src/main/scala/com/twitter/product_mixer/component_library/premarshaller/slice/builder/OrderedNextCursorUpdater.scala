try {
package com.twitter.product_mixer.component_library.premarshaller.slice.builder

import com.twitter.product_mixer.component_library.model.cursor.OrderedCursor
import com.twitter.product_mixer.component_library.premarshaller.cursor.CursorSerializer
import com.twitter.product_mixer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mixer.core.model.marshalling.response.slice.NextCursor
import com.twitter.product_mixer.core.model.marshalling.response.slice.SliceItem
import com.twitter.product_mixer.core.pipeline.HasPipelineCursor
import com.twitter.product_mixer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mixer.core.pipeline.PipelineQuery

/**
 * Updates an [[OrderedCursor]] in the Next position
 *
 * @param idSelector Specifies the entry from which to derive the `id` field
 * @param includeOperation Specifies whether to include the builder operation in the response
 * @param serializer Converts the cursor to an encoded string
 */
case class OrderedNextCursorUpdater[Query <: PipelineQuery with HasPipelineCursor[OrderedCursor]](
  idSelector: PartialFunction[SliceItem, Long],
  override val includeOperation: ShouldInclude[Query] = AlwaysInclude,
  serializer: PipelineCursorSerializer[OrderedCursor] = CursorSerializer)
    extends SliceCursorUpdaterFromUnderlyingBuilder[Query] {
  override val cursorType: CursorType = NextCursor

  override val underlying: OrderedNextCursorBuilder[Query] =
    OrderedNextCursorBuilder(idSelector, includeOperation, serializer)
}

} catch {
  case e: Exception =>
}
