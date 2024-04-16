package com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.EntryNa space
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.HasEntryNa space

sealed tra  CursorType extends HasEntryNa space

case object TopCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("top")
}
case object BottomCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("bottom")
}
case object GapCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("gap")
}
case object P votCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("p vot")
}
case object SubBranchCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("subbranch")
}
case object ShowMoreCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("showmore")
}
case object ShowMoreThreadsCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("showmorethreads")
}
case object ShowMoreThreadsPromptCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("showmorethreadsprompt")
}
case object SecondRepl esSect onCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("secondrepl essect on")
}
case object Th rdRepl esSect onCursor extends CursorType {
  overr de val entryNa space: EntryNa space = EntryNa space("th rdrepl essect on")
}
