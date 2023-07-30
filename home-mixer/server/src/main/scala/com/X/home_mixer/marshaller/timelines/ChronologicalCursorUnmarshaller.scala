package com.X.home_mixer.marshaller.timelines

import com.X.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.X.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.X.timelines.service.{thriftscala => t}

object ChronologicalCursorUnmarshaller {

  def apply(requestCursor: t.RequestCursor): Option[UrtOrderedCursor] = {
    requestCursor match {
      case t.RequestCursor.ChronologicalCursor(cursor) =>
        (cursor.top, cursor.bottom) match {
          case (Some(top), None) =>
            Some(UrtOrderedCursor(top, cursor.top, Some(BottomCursor)))
          case (None, Some(bottom)) =>
            Some(UrtOrderedCursor(bottom, cursor.bottom, Some(TopCursor)))
          case (Some(top), Some(bottom)) =>
            Some(UrtOrderedCursor(top, cursor.top, Some(GapCursor), cursor.bottom))
          case _ => None
        }
      case _ => None
    }
  }
}
