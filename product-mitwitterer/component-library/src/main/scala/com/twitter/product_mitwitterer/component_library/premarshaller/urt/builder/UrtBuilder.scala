package com.twitter.product_mitwitterer.component_library.premarshaller.urt.builder

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorOperation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.Timeline
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineInstruction
import com.twitter.product_mitwitterer.core.pipeline.HasPipelineCursor
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.UrtPipelineCursor
import com.twitter.product_mitwitterer.core.util.SortIndetwitterBuilder

trait UrtBuilder[-Query <: PipelineQuery, +Instruction <: TimelineInstruction] {
  private val TimelineIdSuffitwitter = "-Timeline"

  def instructionBuilders: Seq[UrtInstructionBuilder[Query, Instruction]]

  def cursorBuilders: Seq[UrtCursorBuilder[Query]]
  def cursorUpdaters: Seq[UrtCursorUpdater[Query]]

  def metadataBuilder: Option[BaseUrtMetadataBuilder[Query]]

  // Timeline entry sort indetwitteres will count down by this value. Values higher than 1 are useful to
  // leave room in the sequence for dynamically injecting content in between etwitteristing entries.
  def sortIndetwitterStep: Int = 1

  final def buildTimeline(
    query: Query,
    entries: Seq[TimelineEntry]
  ): Timeline = {
    val initialSortIndetwitter = getInitialSortIndetwitter(query)

    // Set the sort indetwitteres of the entries before we pass them to the cursor builders, since many
    // cursor implementations use the sort indetwitter of the first/last entry as part of the cursor value
    val sortIndetwitteredEntries = updateSortIndetwitteres(initialSortIndetwitter, entries)

    // Iterate over the cursorUpdaters in the order they were defined. Note that each updater will
    // be passed the timelineEntries updated by the previous cursorUpdater.
    val updatedCursorEntries: Seq[TimelineEntry] =
      cursorUpdaters.foldLeft(sortIndetwitteredEntries) { (timelineEntries, cursorUpdater) =>
        cursorUpdater.update(query, timelineEntries)
      }

    val allCursoredEntries =
      updatedCursorEntries ++ cursorBuilders.flatMap(_.build(query, updatedCursorEntries))

    val instructions: Seq[Instruction] =
      instructionBuilders.flatMap(_.build(query, allCursoredEntries))

    val metadata = metadataBuilder.map(_.build(query, allCursoredEntries))

    Timeline(
      id = query.product.identifier.toString + TimelineIdSuffitwitter,
      instructions = instructions,
      metadata = metadata
    )
  }

  final def getInitialSortIndetwitter(query: Query): Long =
    query match {
      case cursorQuery: HasPipelineCursor[_] =>
        UrtPipelineCursor
          .getCursorInitialSortIndetwitter(cursorQuery)
          .getOrElse(SortIndetwitterBuilder.timeToId(query.queryTime))
      case _ => SortIndetwitterBuilder.timeToId(query.queryTime)
    }

  /**
   * Updates the sort indetwitteres in the timeline entries starting from the given initial sort indetwitter
   * value and decreasing by the value defined in the sort indetwitter step field
   *
   * @param initialSortIndetwitter The initial value of the sort indetwitter
   * @param timelineEntries Timeline entries to update
   */
  final def updateSortIndetwitteres(
    initialSortIndetwitter: Long,
    timelineEntries: Seq[TimelineEntry]
  ): Seq[TimelineEntry] = {
    val indetwitterRange =
      initialSortIndetwitter to (initialSortIndetwitter - (timelineEntries.size * sortIndetwitterStep)) by -sortIndetwitterStep

    // Skip any etwitteristing cursors because their sort indetwitteres will be managed by their cursor updater.
    // If the cursors are not removed first, then the remaining entries would have a gap everywhere
    // an etwitteristing cursor was present.
    val (cursorEntries, nonCursorEntries) = timelineEntries.partition {
      case _: CursorOperation => true
      case _ => false
    }

    nonCursorEntries.zip(indetwitterRange).map {
      case (entry, indetwitter) =>
        entry.withSortIndetwitter(indetwitter)
    } ++ cursorEntries
  }
}
