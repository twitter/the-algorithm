package com.twitter.product_mitwitterer.component_library.premarshaller.cursor

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtOrderedCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtPassThroughCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtPlaceholderCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedBloomFilterCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.component_library.premarshaller.cursor.CursorSerializer.CursorThriftSerializer
import com.twitter.product_mitwitterer.component_library.{thriftscala => t}
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer.deserializeCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mitwitterer.core.pipeline.UrtPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MalformedCursor
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.search.common.util.bloomfilter.AdaptiveLongIntBloomFilterSerializer
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.operation.CursorTypeMarshaller

/**
 * Handles serialization and deserialization for all supported URT cursors
 */
object UrtCursorSerializer etwittertends PipelineCursorSerializer[UrtPipelineCursor] {

  val SerializedUrtPlaceholderCursor = CursorThriftSerializer.toString(
    t.ProductMitwittererRequestCursor.UrtPlaceholderCursor(t.UrtPlaceholderCursor()))

  val cursorTypeMarshaller = new CursorTypeMarshaller()

  override def serializeCursor(cursor: UrtPipelineCursor): String =
    cursor match {
      case UrtOrderedCursor(initialSortIndetwitter, id, cursorType, gapBoundaryId) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UrtOrderedCursor(
          t.UrtOrderedCursor(
            initialSortIndetwitter = initialSortIndetwitter,
            id = id,
            cursorType.map(cursorTypeMarshaller.apply),
            gapBoundaryId = gapBoundaryId))

        CursorThriftSerializer.toString(thriftCursor)
      case UrtUnorderedEtwittercludeIdsCursor(initialSortIndetwitter, etwittercludedIds) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UrtUnorderedEtwittercludeIdsCursor(
          t.UrtUnorderedEtwittercludeIdsCursor(
            initialSortIndetwitter = initialSortIndetwitter,
            etwittercludedIds = Some(etwittercludedIds)))

        CursorThriftSerializer.toString(thriftCursor)
      case UrtUnorderedBloomFilterCursor(initialSortIndetwitter, longIntBloomFilter) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UrtUnorderedBloomFilterCursor(
          t.UrtUnorderedBloomFilterCursor(
            initialSortIndetwitter = initialSortIndetwitter,
            serializedLongIntBloomFilter =
              AdaptiveLongIntBloomFilterSerializer.serialize(longIntBloomFilter)
          ))

        CursorThriftSerializer.toString(thriftCursor)
      case UrtPassThroughCursor(initialSortIndetwitter, cursorValue, cursorType) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UrtPassThroughCursor(
          t.UrtPassThroughCursor(
            initialSortIndetwitter = initialSortIndetwitter,
            cursorValue = cursorValue,
            cursorType = cursorType.map(cursorTypeMarshaller.apply)
          ))

        CursorThriftSerializer.toString(thriftCursor)
      case UrtPlaceholderCursor() =>
        SerializedUrtPlaceholderCursor
      case _ =>
        throw PipelineFailure(IllegalStateFailure, "Unknown cursor type")
    }

  def deserializeOrderedCursor(cursorString: String): Option[UrtOrderedCursor] = {
    deserializeUrtCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor.UrtOrderedCursor(
                t.UrtOrderedCursor(initialSortIndetwitter, id, cursorType, gapBoundaryId))) =>
          Some(
            UrtOrderedCursor(
              initialSortIndetwitter = initialSortIndetwitter,
              id = id,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall),
              gapBoundaryId))
      }
    )
  }

  def deserializeUnorderedEtwittercludeIdsCursor(
    cursorString: String
  ): Option[UrtUnorderedEtwittercludeIdsCursor] = {
    deserializeUrtCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor.UrtUnorderedEtwittercludeIdsCursor(
                t.UrtUnorderedEtwittercludeIdsCursor(initialSortIndetwitter, etwittercludedIdsOpt))) =>
          Some(
            UrtUnorderedEtwittercludeIdsCursor(
              initialSortIndetwitter = initialSortIndetwitter,
              etwittercludedIds = etwittercludedIdsOpt.getOrElse(Seq.empty)))
      }
    )
  }

  def deserializeUnorderedBloomFilterCursor(
    cursorString: String
  ): Option[UrtUnorderedBloomFilterCursor] = {
    deserializeUrtCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor.UrtUnorderedBloomFilterCursor(
                t.UrtUnorderedBloomFilterCursor(initialSortIndetwitter, serializedLongIntBloomFilter))) =>
          val longIntBloomFilter = AdaptiveLongIntBloomFilterSerializer
            .deserialize(serializedLongIntBloomFilter).getOrElse(
              throw PipelineFailure(
                MalformedCursor,
                s"Failed to deserialize UrtUnorderedBloomFilterCursor from cursor string: $cursorString")
            )

          Some(
            UrtUnorderedBloomFilterCursor(
              initialSortIndetwitter = initialSortIndetwitter,
              longIntBloomFilter = longIntBloomFilter))
      }
    )
  }

  def deserializePassThroughCursor(cursorString: String): Option[UrtPassThroughCursor] = {
    deserializeUrtCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor
                .UrtPassThroughCursor(
                  t.UrtPassThroughCursor(initialSortIndetwitter, cursorValue, cursorType))) =>
          Some(
            UrtPassThroughCursor(
              initialSortIndetwitter = initialSortIndetwitter,
              cursorValue = cursorValue,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall)))
      }
    )
  }

  private def deserializeUrtCursor[Cursor <: PipelineCursor](
    cursorString: String,
    deserializePf: PartialFunction[Option[t.ProductMitwittererRequestCursor], Option[Cursor]]
  ): Option[Cursor] = {
    deserializeCursor[t.ProductMitwittererRequestCursor, Cursor](
      cursorString,
      CursorThriftSerializer,
      deserializePf orElse {
        case Some(t.ProductMitwittererRequestCursor.UrtPlaceholderCursor(t.UrtPlaceholderCursor())) =>
          // Treat submitted placeholder cursor like an initial page load
          None
      },
    )
  }
}
