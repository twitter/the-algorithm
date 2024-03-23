package com.ExTwitter.home_mixer.marshaller.timelines

import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.ExTwitter.timelineservice.{thriftscala => t}

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
