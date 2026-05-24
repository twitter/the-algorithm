package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.operation

import com.twitter.product_mixer.core.model.marshalling.response.urt.operation.CursorItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CursorItemMarshaller @Inject() (
  cursorTypeMarshaller: CursorTypeMarshaller,
  cursorDisplayTreatmentMarshaller: CursorDisplayTreatmentMarshaller) {

  def apply(cursorItem: CursorItem): urt.TimelineItemContent.TimelineCursor =
    urt.TimelineItemContent.TimelineCursor(
      urt.TimelineCursor(
        value = cursorItem.value,
        cursorType = cursorTypeMarshaller(cursorItem.cursorType),
        displayTreatment = cursorItem.displayTreatment.map(cursorDisplayTreatmentMarshaller(_))
      )
    )
}
