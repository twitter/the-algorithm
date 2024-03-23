package com.ExTwitter.home_mixer.marshaller.timelines

import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.ExTwitter.timelines.service.{thriftscala => t}

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
