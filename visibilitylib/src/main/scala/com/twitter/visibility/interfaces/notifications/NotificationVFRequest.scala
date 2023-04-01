package com.twitter.visibility.interfaces.notifications

import com.twitter.visibility.models.ContentId
import com.twitter.visibility.models.SafetyLevel

case class NotificationVFRequest(
  recipientId: Long,
  subject: ContentId.UserId,
  safetyLevel: SafetyLevel)
