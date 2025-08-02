package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt

import com.twitter.product_mitwitterer.core.functional_component.marshaller.TransportMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.operation.CursorOperationMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineOperation
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.operation.CursorOperation
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class TimelineOperationMarshaller @Inject() (
  cursorOperationMarshaller: CursorOperationMarshaller) {

  def apply(operation: TimelineOperation): urt.TimelineOperation = operation match {
    case cursorOperation: CursorOperation => cursorOperationMarshaller(cursorOperation)
    case _ =>
      throw new UnsupportedTimelineOperationEtwitterception(operation)
  }
}

class UnsupportedTimelineOperationEtwitterception(operation: TimelineOperation)
    etwittertends UnsupportedOperationEtwitterception(
      "Unsupported timeline operation " + TransportMarshaller.getSimpleName(operation.getClass))
