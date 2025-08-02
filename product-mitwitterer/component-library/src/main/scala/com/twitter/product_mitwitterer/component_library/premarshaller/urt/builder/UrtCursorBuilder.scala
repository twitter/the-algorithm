package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtCursorBuilder.DefaultSortIndetwitter
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtCursorBuilder.NetwittertPageTopCursorEntryOffset
import com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder.UrtCursorBuilder.UrtEntryOffset
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.BottomCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorOperation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.GapCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.TopCursor
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.UrtPipelineCursor
import com.twitter.product_mitwitterer.core.util.SortIndetwitterBuilder

object UrtCursorBuilder {
  val NetwittertPageTopCursorEntryOffset = 1L
  val UrtEntryOffset = 1L
  val DefaultSortIndetwitter = (query: PipelineQuery) => SortIndetwitterBuilder.timeToId(query.queryTime)
}

trait UrtCursorBuilder[-Query <: PipelineQuery] {

  val includeOperation: IncludeInstruction[Query] = AlwaysInclude

  def cursorType: CursorType
  def cursorValue(query: Query, entries: Seq[TimelineEntry]): String

  /**
   * Identifier of an *etwitteristing* timeline cursor that this new cursor would replace, if this cursor
   * is returned in a `ReplaceEntry` timeline instruction.
   *
   * Note:
   *   - This id is used to populate the `entryIdToReplace` field on the URT TimelineEntry
   *     generated. More details at [[CursorOperation.entryIdToReplace]].
   *   - As a convention, we use the sortIndetwitter of the cursor for its id/entryId fields. So the
   *     `idToReplace` should represent the sortIndetwitter of the etwitteristing cursor to be replaced.
   */
  def idToReplace(query: Query): Option[Long] = None

  def cursorSortIndetwitter(query: Query, entries: Seq[TimelineEntry]): Long =
    (query, cursorType) match {
      case (query: PipelineQuery with HasPipelineCursor[_], TopCursor) =>
        topCursorSortIndetwitter(query, entries)
      case (query: PipelineQuery with HasPipelineCursor[_], BottomCursor | GapCursor) =>
        bottomCursorSortIndetwitter(query, entries)
      case _ =>
        throw new UnsupportedOperationEtwitterception(
          "Automatic sort indetwitter support limited to top and bottom cursors")
    }

  def build(query: Query, entries: Seq[TimelineEntry]): Option[CursorOperation] = {
    if (includeOperation(query, entries)) {
      val sortIndetwitter = cursorSortIndetwitter(query, entries)

      val cursorOperation = CursorOperation(
        id = sortIndetwitter,
        sortIndetwitter = Some(sortIndetwitter),
        value = cursorValue(query, entries),
        cursorType = cursorType,
        displayTreatment = None,
        idToReplace = idToReplace(query),
      )

      Some(cursorOperation)
    } else None
  }

  /**
   * Build the top cursor sort indetwitter which handles the following cases:
   * 1. When there is at least one non-cursor entry, use the first entry's sort indetwitter + UrtEntryOffset
   * 2. When there are no non-cursor entries, and initialSortIndetwitter is not set which indicates that
   *    it is the first page, use DefaultSortIndetwitter + UrtEntryOffset
   * 3. When there are no non-cursor entries, and initialSortIndetwitter is set which indicates that it is
   *    not the first page, use the query.initialSortIndetwitter from the passed-in cursor + UrtEntryOffset
   */
  protected def topCursorSortIndetwitter(
    query: PipelineQuery with HasPipelineCursor[_],
    entries: Seq[TimelineEntry]
  ): Long = {
    val nonCursorEntries = entries.filter {
      case _: CursorOperation => false
      case _: CursorItem => false
      case _ => true
    }

    lazy val initialSortIndetwitter =
      UrtPipelineCursor.getCursorInitialSortIndetwitter(query).getOrElse(DefaultSortIndetwitter(query))

    nonCursorEntries.headOption.flatMap(_.sortIndetwitter).getOrElse(initialSortIndetwitter) + UrtEntryOffset
  }

  /**
   * Specifies the point at which the netwittert page's entries' sort indices will start counting.
   *
   * Note that in the case of URT, the netwittert page's entries' does not include the top cursor. As
   * such, the value of initialSortIndetwitter passed back in the cursor is typically the bottom cursor's
   * sort indetwitter - 2. Subtracting 2 leaves room for the netwittert page's top cursor, which will have a
   * sort indetwitter of top entry + 1.
   */
  protected def netwittertBottomInitialSortIndetwitter(
    query: PipelineQuery with HasPipelineCursor[_],
    entries: Seq[TimelineEntry]
  ): Long = {
    bottomCursorSortIndetwitter(query, entries) - NetwittertPageTopCursorEntryOffset - UrtEntryOffset
  }

  /**
   * Build the bottom cursor sort indetwitter which handles the following cases:
   * 1. When there is at least one non-cursor entry, use the last entry's sort indetwitter - UrtEntryOffset
   * 2. When there are no non-cursor entries, and initialSortIndetwitter is not set which indicates that
   *    it is the first page, use DefaultSortIndetwitter
   * 3. When there are no non-cursor entries, and initialSortIndetwitter is set which indicates that it is
   *    not the first page, use the query.initialSortIndetwitter from the passed-in cursor
   */
  protected def bottomCursorSortIndetwitter(
    query: PipelineQuery with HasPipelineCursor[_],
    entries: Seq[TimelineEntry]
  ): Long = {
    val nonCursorEntries = entries.filter {
      case _: CursorOperation => false
      case _: CursorItem => false
      case _ => true
    }

    lazy val initialSortIndetwitter =
      UrtPipelineCursor.getCursorInitialSortIndetwitter(query).getOrElse(DefaultSortIndetwitter(query))

    nonCursorEntries.lastOption
      .flatMap(_.sortIndetwitter).map(_ - UrtEntryOffset).getOrElse(initialSortIndetwitter)
  }
}
