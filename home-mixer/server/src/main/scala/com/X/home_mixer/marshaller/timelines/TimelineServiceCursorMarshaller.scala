package com.X.home_mixer.marshaller.timelines

import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.X.timelineservice.{thriftscala => t}

object TimelineServiceCursorMarshaller {

  def apply(cursor: UrtOrderedCursor): Option[t.Cursor2] = {
    val id = cursor.id.map(_.toString)
    val gapBoundaryId = cursor.gapBoundaryId.map(_.toString)
    cursor.cursorType match {
      case Some(TopCursor) => Some(t.Cursor2(bottom = id))
      case Some(BottomCursor) => Some(t.Cursor2(top = id))
      case Some(GapCursor) => Some(t.Cursor2(top = id, bottom = gapBoundaryId))
      case _ => None
    }
  }
}
