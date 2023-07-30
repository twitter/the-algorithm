package com.X.visibility.interfaces.notifications

import com.X.visibility.models.ContentId
import com.X.visibility.models.SafetyLevel

case class NotificationVFRequest(
  recipientId: Long,
  subject: ContentId.UserId,
  safetyLevel: SafetyLevel)
