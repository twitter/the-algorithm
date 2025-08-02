package com.twitter.product_mitwitterer.component_library.premarshaller.cursor

import com.twitter.product_mitwitterer.component_library.model.cursor.OrderedCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.PassThroughCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UnorderedBloomFilterCursor
import com.twitter.product_mitwitterer.component_library.model.cursor.UnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.component_library.{thriftscala => t}
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MalformedCursor
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.scrooge.BinaryThriftStructSerializer
import com.twitter.scrooge.ThriftStructCodec
import com.twitter.search.common.util.bloomfilter.AdaptiveLongIntBloomFilterSerializer
import com.twitter.util.Base64UrlSafeStringEncoder
import com.twitter.util.StringEncoder
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.slice.CursorTypeMarshaller

/**
 * Handles serialization and deserialization for all supported generic cursors. Note that generic
 * cursors may be used for Slices or any bespoke marshalling format.
 */
object CursorSerializer etwittertends PipelineCursorSerializer[PipelineCursor] {

  private[cursor] val CursorThriftSerializer: BinaryThriftStructSerializer[
    t.ProductMitwittererRequestCursor
  ] =
    new BinaryThriftStructSerializer[t.ProductMitwittererRequestCursor] {
      override def codec: ThriftStructCodec[t.ProductMitwittererRequestCursor] =
        t.ProductMitwittererRequestCursor
      override def encoder: StringEncoder = Base64UrlSafeStringEncoder
    }

  override def serializeCursor(cursor: PipelineCursor): String =
    cursor match {
      case OrderedCursor(id, cursorType, gapBoundaryId) =>
        val cursorTypeMarshaller = new CursorTypeMarshaller()
        val thriftCursor = t.ProductMitwittererRequestCursor.OrderedCursor(
          t.OrderedCursor(
            id = id,
            cursorType = cursorType.map(cursorTypeMarshaller.apply),
            gapBoundaryId))

        CursorThriftSerializer.toString(thriftCursor)
      case UnorderedEtwittercludeIdsCursor(etwittercludedIds) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UnorderedEtwittercludeIdsCursor(
          t.UnorderedEtwittercludeIdsCursor(etwittercludedIds = Some(etwittercludedIds)))

        CursorThriftSerializer.toString(thriftCursor)
      case UnorderedBloomFilterCursor(longIntBloomFilter) =>
        val thriftCursor = t.ProductMitwittererRequestCursor.UnorderedBloomFilterCursor(
          t.UnorderedBloomFilterCursor(
            serializedLongIntBloomFilter =
              AdaptiveLongIntBloomFilterSerializer.serialize(longIntBloomFilter)
          ))

        CursorThriftSerializer.toString(thriftCursor)
      case PassThroughCursor(cursorValue, cursorType) =>
        val cursorTypeMarshaller = new CursorTypeMarshaller()
        val thriftCursor = t.ProductMitwittererRequestCursor.PassThroughCursor(
          t.PassThroughCursor(
            cursorValue = cursorValue,
            cursorType = cursorType.map(cursorTypeMarshaller.apply)
          ))

        CursorThriftSerializer.toString(thriftCursor)
      case _ =>
        throw PipelineFailure(IllegalStateFailure, "Unknown cursor type")
    }

  def deserializeOrderedCursor(cursorString: String): Option[OrderedCursor] =
    deserializeCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor
                .OrderedCursor(t.OrderedCursor(id, cursorType, gapBoundaryId))) =>
          val cursorTypeMarshaller = new CursorTypeMarshaller()
          Some(
            OrderedCursor(
              id = id,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall),
              gapBoundaryId))
      }
    )

  def deserializeUnorderedEtwittercludeIdsCursor(
    cursorString: String
  ): Option[UnorderedEtwittercludeIdsCursor] = {
    deserializeCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor
                .UnorderedEtwittercludeIdsCursor(t.UnorderedEtwittercludeIdsCursor(etwittercludedIdsOpt))) =>
          Some(UnorderedEtwittercludeIdsCursor(etwittercludedIds = etwittercludedIdsOpt.getOrElse(Seq.empty)))
      }
    )
  }

  def deserializeUnorderedBloomFilterCursor(
    cursorString: String
  ): Option[UnorderedBloomFilterCursor] =
    deserializeCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor.UnorderedBloomFilterCursor(
                t.UnorderedBloomFilterCursor(serializedLongIntBloomFilter))) =>
          val bloomFilter = AdaptiveLongIntBloomFilterSerializer
            .deserialize(serializedLongIntBloomFilter).getOrElse(
              throw PipelineFailure(
                MalformedCursor,
                s"Failed to deserialize UnorderedBloomFilterCursor from cursor string: $cursorString")
            )

          Some(UnorderedBloomFilterCursor(longIntBloomFilter = bloomFilter))
      }
    )

  def deserializePassThroughCursor(cursorString: String): Option[PassThroughCursor] =
    deserializeCursor(
      cursorString,
      {
        case Some(
              t.ProductMitwittererRequestCursor
                .PassThroughCursor(t.PassThroughCursor(cursorValue, cursorType))) =>
          val cursorTypeMarshaller = new CursorTypeMarshaller()
          Some(
            PassThroughCursor(
              cursorValue = cursorValue,
              cursorType = cursorType.map(cursorTypeMarshaller.unmarshall)))
      }
    )

  // Note that the "A" type of the PartialFunction cannot be inferred due to the thrift type not
  // being present on the PipelineCursorSerializer trait. By using this private def with the
  // deserializePf type declared, it can be inferred.
  private def deserializeCursor[Cursor <: PipelineCursor](
    cursorString: String,
    deserializePf: PartialFunction[Option[t.ProductMitwittererRequestCursor], Option[Cursor]]
  ): Option[Cursor] =
    PipelineCursorSerializer.deserializeCursor(
      cursorString,
      CursorThriftSerializer,
      deserializePf
    )
}
