package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.operation._
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CursorTypeMarshaller @Inject() () {

  def apply(cursorType: CursorType): urt.CursorType = cursorType match {
    case TopCursor => urt.CursorType.Top
    case BottomCursor => urt.CursorType.Bottom
    case GapCursor => urt.CursorType.Gap
    case PivotCursor => urt.CursorType.Pivot
    case SubBranchCursor => urt.CursorType.Subbranch
    case ShowMoreCursor => urt.CursorType.ShowMore
    case ShowMoreThreadsCursor => urt.CursorType.ShowMoreThreads
    case ShowMoreThreadsPromptCursor => urt.CursorType.ShowMoreThreadsPrompt
    case SecondRepliesSectionCursor => urt.CursorType.SecondRepliesSection
    case ThirdRepliesSectionCursor => urt.CursorType.ThirdRepliesSection
  }

  def unmarshall(cursorType: urt.CursorType): CursorType = cursorType match {
    case urt.CursorType.Top => TopCursor
    case urt.CursorType.Bottom => BottomCursor
    case urt.CursorType.Gap => GapCursor
    case urt.CursorType.Pivot => PivotCursor
    case urt.CursorType.Subbranch => SubBranchCursor
    case urt.CursorType.ShowMore => ShowMoreCursor
    case urt.CursorType.ShowMoreThreads => ShowMoreThreadsCursor
    case urt.CursorType.ShowMoreThreadsPrompt => ShowMoreThreadsPromptCursor
    case urt.CursorType.SecondRepliesSection => SecondRepliesSectionCursor
    case urt.CursorType.ThirdRepliesSection => ThirdRepliesSectionCursor
    case urt.CursorType.EnumUnknownCursorType(id) =>
      throw new UnsupportedOperationException(s"Unexpected cursor enum field: $id")
  }
}
