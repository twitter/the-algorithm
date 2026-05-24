package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorOperation
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CursorOperationMarshaller @Inject() (
  cursorTypeMarshaller: CursorTypeMarshaller,
  cursorDisplayTreatmentMarshaller: CursorDisplayTreatmentMarshaller) {

  def apply(cursorOperation: CursorOperation): urt.TimelineOperation.Cursor =
    urt.TimelineOperation.Cursor(
      urt.TimelineCursor(
        value = cursorOperation.value,
        cursorType = cursorTypeMarshaller(cursorOperation.cursorType),
        displayTreatment = cursorOperation.displayTreatment.map(cursorDisplayTreatmentMarshaller(_))
      )
    )
}
