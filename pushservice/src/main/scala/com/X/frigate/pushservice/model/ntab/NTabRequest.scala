package com.X.frigate.pushservice.model.ntab

import com.X.notificationservice.thriftscala.CreateGenericNotificationRequest
import com.X.util.Future

trait NTabRequest {

  def ntabRequest: Future[Option[CreateGenericNotificationRequest]]

}
