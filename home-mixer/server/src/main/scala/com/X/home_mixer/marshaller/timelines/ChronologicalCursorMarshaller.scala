package com.X.home_mixer.marshaller.timelines

import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.X.timelines.service.{thriftscala => t}

object ChronologicalCursorMarshaller {

  def apply(cursor: UrtOrderedCursor): Option[t.ChronologicalCursor] = {
    cursor.cursorType match {
      case Some(TopCursor) => Some(t.ChronologicalCursor(bottom = cursor.id))
      case Some(BottomCursor) => Some(t.ChronologicalCursor(top = cursor.id))
      case Some(GapCursor) =>
        Some(t.ChronologicalCursor(top = cursor.id, bottom = cursor.gapBoundaryId))
      case _ => None
    }
  }
}
