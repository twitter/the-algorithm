package com.X.product_mixer.core.functional_component.marshaller.response.urt

import com.X.product_mixer.core.functional_component.marshaller.TransportMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.operation.CursorOperationMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.TimelineOperation
import com.X.product_mixer.core.model.marshalling.response.urt.operation.CursorOperation
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimelineOperationMarshaller @Inject() (
  cursorOperationMarshaller: CursorOperationMarshaller) {

  def apply(operation: TimelineOperation): urt.TimelineOperation = operation match {
    case cursorOperation: CursorOperation => cursorOperationMarshaller(cursorOperation)
    case _ =>
      throw new UnsupportedTimelineOperationException(operation)
  }
}

class UnsupportedTimelineOperationException(operation: TimelineOperation)
    extends UnsupportedOperationException(
      "Unsupported timeline operation " + TransportMarshaller.getSimpleName(operation.getClass))
