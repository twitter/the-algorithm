package com.X.frigate.pushservice.take.history

import com.X.eventbus.client.EventBusPublisher
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.util.NotificationScribeUtil
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes
import com.X.frigate.pushservice.params.PushParams
import com.X.frigate.scribe.thriftscala.NotificationScribe
import com.X.frigate.thriftscala.FrigateNotification

class EventBusWriter(
  eventBusPublisher: EventBusPublisher[NotificationScribe],
  stats: StatsReceiver) {
  private def writeSendEventToEventBus(
    target: PushTypes.Target,
    notificationScribe: NotificationScribe
  ): Unit = {
    if (target.params(PushParams.EnablePushSendEventBus)) {
      val result = eventBusPublisher.publish(notificationScribe)
      result.onFailure { _ => stats.counter("push_send_eventbus_failure").incr() }
    }
  }

  def writeToEventBus(
    candidate: PushCandidate,
    frigateNotificationForPersistence: FrigateNotification
  ): Unit = {
    val notificationScribe = NotificationScribeUtil.getNotificationScribe(
      targetId = candidate.target.targetId,
      impressionId = candidate.impressionId,
      frigateNotification = frigateNotificationForPersistence,
      createdAt = candidate.createdAt
    )
    writeSendEventToEventBus(candidate.target, notificationScribe)
  }
}
