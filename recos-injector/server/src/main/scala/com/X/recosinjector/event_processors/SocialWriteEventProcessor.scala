package com.X.recosinjector.event_processors

import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.finagle.stats.StatsReceiver
import com.X.recosinjector.edges.{EventToMessageBuilder, UserUserEdge}
import com.X.recosinjector.publishers.KafkaEventPublisher
import com.X.scrooge.ThriftStructCodec
import com.X.socialgraph.thriftscala.WriteEvent
import com.X.util.Future

/**
 * This processor listens to events from social graphs services. In particular, a major use case is
 * to listen to user-user follow events.
 */
class SocialWriteEventProcessor(
  override val eventBusStreamName: String,
  override val thriftStruct: ThriftStructCodec[WriteEvent],
  override val serviceIdentifier: ServiceIdentifier,
  kafkaEventPublisher: KafkaEventPublisher,
  userUserGraphTopic: String,
  userUserGraphMessageBuilder: EventToMessageBuilder[WriteEvent, UserUserEdge]
)(
  override implicit val statsReceiver: StatsReceiver)
    extends EventBusProcessor[WriteEvent] {

  override def processEvent(event: WriteEvent): Future[Unit] = {
    userUserGraphMessageBuilder.processEvent(event).map { edges =>
      edges.foreach { edge =>
        kafkaEventPublisher.publish(edge.convertToRecosHoseMessage, userUserGraphTopic)
      }
    }
  }
}
