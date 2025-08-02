package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.HasEntryNamespace

sealed trait CursorType etwittertends HasEntryNamespace

case object TopCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("top")
}
case object BottomCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("bottom")
}
case object GapCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("gap")
}
case object PivotCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("pivot")
}
case object SubBranchCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("subbranch")
}
case object ShowMoreCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmore")
}
case object ShowMoreThreadsCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmorethreads")
}
case object ShowMoreThreadsPromptCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmorethreadsprompt")
}
case object SecondRepliesSectionCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("secondrepliessection")
}
case object ThirdRepliesSectionCursor etwittertends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("thirdrepliessection")
}
