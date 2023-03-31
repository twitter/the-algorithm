package com.twitter.visibility.interfaces.dms

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext

case class DmConversationVisibilityRequest(
  dmConversationId: String,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext)
