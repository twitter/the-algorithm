package com.twitter.product_mixer.core.model.marshalling.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.EntryNamespace
import com.twitter.product_mixer.core.model.marshalling.response.urt.HasEntryNamespace

sealed trait CursorType extends HasEntryNamespace

case object TopCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("top")
}
case object BottomCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("bottom")
}
case object GapCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("gap")
}
case object PivotCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("pivot")
}
case object SubBranchCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("subbranch")
}
case object ShowMoreCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmore")
}
case object ShowMoreThreadsCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmorethreads")
}
case object ShowMoreThreadsPromptCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("showmorethreadsprompt")
}
case object SecondRepliesSectionCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("secondrepliessection")
}
case object ThirdRepliesSectionCursor extends CursorType {
  override val entryNamespace: EntryNamespace = EntryNamespace("thirdrepliessection")
}
