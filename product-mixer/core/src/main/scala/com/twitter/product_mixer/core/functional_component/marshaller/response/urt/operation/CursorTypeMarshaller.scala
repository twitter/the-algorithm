package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.operat on

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.operat on._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CursorTypeMarshaller @ nject() () {

  def apply(cursorType: CursorType): urt.CursorType = cursorType match {
    case TopCursor => urt.CursorType.Top
    case BottomCursor => urt.CursorType.Bottom
    case GapCursor => urt.CursorType.Gap
    case P votCursor => urt.CursorType.P vot
    case SubBranchCursor => urt.CursorType.Subbranch
    case ShowMoreCursor => urt.CursorType.ShowMore
    case ShowMoreThreadsCursor => urt.CursorType.ShowMoreThreads
    case ShowMoreThreadsPromptCursor => urt.CursorType.ShowMoreThreadsPrompt
    case SecondRepl esSect onCursor => urt.CursorType.SecondRepl esSect on
    case Th rdRepl esSect onCursor => urt.CursorType.Th rdRepl esSect on
  }

  def unmarshall(cursorType: urt.CursorType): CursorType = cursorType match {
    case urt.CursorType.Top => TopCursor
    case urt.CursorType.Bottom => BottomCursor
    case urt.CursorType.Gap => GapCursor
    case urt.CursorType.P vot => P votCursor
    case urt.CursorType.Subbranch => SubBranchCursor
    case urt.CursorType.ShowMore => ShowMoreCursor
    case urt.CursorType.ShowMoreThreads => ShowMoreThreadsCursor
    case urt.CursorType.ShowMoreThreadsPrompt => ShowMoreThreadsPromptCursor
    case urt.CursorType.SecondRepl esSect on => SecondRepl esSect onCursor
    case urt.CursorType.Th rdRepl esSect on => Th rdRepl esSect onCursor
    case urt.CursorType.EnumUnknownCursorType( d) =>
      throw new UnsupportedOperat onExcept on(s"Unexpected cursor enum f eld: $ d")
  }
}
