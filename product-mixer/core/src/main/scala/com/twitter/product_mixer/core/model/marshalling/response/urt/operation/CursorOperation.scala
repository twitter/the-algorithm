package com.twitter.product_mixer.core.model.marshalling.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorOperation.CursorEntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineEntry
import com.twitter.product_mixer.core.model.marshalling.response.urt.TimelineOperation

object CursorOperation {
  val CursorEntryNamespace = EntryNamespace("cursor")

  private def entryIdentifier(cursorType: CursorType, identifier: Long): String =
    s"$CursorEntryNamespace-${cursorType.entryNamespace.toString}-$identifier"
}

case class CursorOperation(
  override val id: Long,
  override val sortIndex: Option[Long],
  value: String,
  cursorType: CursorType,
  displayTreatment: Option[CursorDisplayTreatment],
  idToReplace: Option[Long])
    extends TimelineOperation {
  override val entryNamespace: EntryNamespace = CursorEntryNamespace

  override lazy val entryIdentifier: String = CursorOperation.entryIdentifier(cursorType, id)

  override def entryIdToReplace: Option[String] =
    idToReplace.map(CursorOperation.entryIdentifier(cursorType, _))

  override def withSortIndex(sortIndex: Long): TimelineEntry = copy(sortIndex = Some(sortIndex))
}
