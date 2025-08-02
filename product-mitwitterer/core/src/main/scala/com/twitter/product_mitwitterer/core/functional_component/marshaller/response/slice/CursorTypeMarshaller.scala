package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.slice

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.product_mitwitterer.component_library.{thriftscala => t}
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.NetwittertCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.PreviousCursor
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.CursorType
import com.twitter.product_mitwitterer.core.model.marshalling.response.slice.GapCursor

@Singleton
class CursorTypeMarshaller @Inject() () {

  def apply(cursorType: CursorType): t.CursorType = cursorType match {
    case NetwittertCursor => t.CursorType.Netwittert
    case PreviousCursor => t.CursorType.Previous
    case GapCursor => t.CursorType.Gap
  }

  def unmarshall(cursorType: t.CursorType): CursorType = cursorType match {
    case t.CursorType.Netwittert => NetwittertCursor
    case t.CursorType.Previous => PreviousCursor
    case t.CursorType.Gap => GapCursor
    case t.CursorType.EnumUnknownCursorType(id) =>
      throw new UnsupportedOperationEtwitterception(
        s"Attempted to unmarshall unrecognized cursor type: $id")
  }

}
