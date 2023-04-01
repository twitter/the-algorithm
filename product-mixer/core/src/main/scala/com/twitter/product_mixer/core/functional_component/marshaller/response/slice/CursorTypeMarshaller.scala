package com.twitter.product_mixer.core.functional_component.marshaller.response.slice

import javax.inject.Inject
import javax.inject.Singleton
import com.twitter.product_mixer.component_library.{thriftscala => t}
import com.twitter.product_mixer.core.model.marshalling.response.slice.NextCursor
import com.twitter.product_mixer.core.model.marshalling.response.slice.PreviousCursor
import com.twitter.product_mixer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mixer.core.model.marshalling.response.slice.GapCursor

@Singleton
class CursorTypeMarshaller @Inject() () {

  def apply(cursorType: CursorType): t.CursorType = cursorType match {
    case NextCursor => t.CursorType.Next
    case PreviousCursor => t.CursorType.Previous
    case GapCursor => t.CursorType.Gap
  }

  def unmarshall(cursorType: t.CursorType): CursorType = cursorType match {
    case t.CursorType.Next => NextCursor
    case t.CursorType.Previous => PreviousCursor
    case t.CursorType.Gap => GapCursor
    case t.CursorType.EnumUnknownCursorType(id) =>
      throw new UnsupportedOperationException(
        s"Attempted to unmarshall unrecognized cursor type: $id")
  }

}
