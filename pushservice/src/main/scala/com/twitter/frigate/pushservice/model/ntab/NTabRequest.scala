package com.twitter.frigate.pushservice.model.ntab

import com.twitter.notificationservice.thriftscala.CreateGenericNotificationRequest
import com.twitter.util.Future

trait NTabRequest {

  def ntabRequest: Future[Option[CreateGenericNotificationRequest]]

}
