package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.component_library.model.cursor.UrtUnorderedEtwittercludeIdsCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.BottomCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorType
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineCursorSerializer
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.timelines.configapi.Param

trait BaseUnorderedEtwittercludeIdsBottomCursorBuilder
    etwittertends UrtCursorBuilder[
      PipelineQuery with HasPipelineCursor[UrtUnorderedEtwittercludeIdsCursor]
    ] {

  def etwittercludedIdsMatwitterLengthParam: Param[Int]

  def etwittercludeEntriesCollector(entries: Seq[TimelineEntry]): Seq[Long]

  def serializer: PipelineCursorSerializer[UrtUnorderedEtwittercludeIdsCursor]

  override val cursorType: CursorType = BottomCursor

  override def cursorValue(
    query: PipelineQuery with HasPipelineCursor[UrtUnorderedEtwittercludeIdsCursor],
    entries: Seq[TimelineEntry]
  ): String = {
    val etwittercludedIdsMatwitterLength = query.params(etwittercludedIdsMatwitterLengthParam)
    assert(etwittercludedIdsMatwitterLength > 0, "Etwittercluded IDs matwitter length must be greater than zero")

    val newEntryIds = etwittercludeEntriesCollector(entries)
    assert(
      newEntryIds.length < etwittercludedIdsMatwitterLength,
      "New entry IDs length must be smaller than etwittercluded IDs matwitter length")

    val etwittercludedIds = query.pipelineCursor
      .map(_.etwittercludedIds ++ newEntryIds)
      .getOrElse(newEntryIds)
      .takeRight(etwittercludedIdsMatwitterLength)

    val cursor = UrtUnorderedEtwittercludeIdsCursor(
      initialSortIndetwitter = netwittertBottomInitialSortIndetwitter(query, entries),
      etwittercludedIds = etwittercludedIds
    )

    serializer.serializeCursor(cursor)
  }
}
