package com.ExTwitter.home_mixer.marshaller.timelines

import com.ExTwitter.product_mixer.component_library.model.cursor.UrtOrderedCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.BottomCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.GapCursor
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.operation.TopCursor
import com.ExTwitter.timelines.service.{thriftscala => t}

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
