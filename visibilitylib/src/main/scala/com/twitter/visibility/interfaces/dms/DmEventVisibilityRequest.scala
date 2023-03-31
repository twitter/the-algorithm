package com.twitter.visibility.interfaces.dms

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext

case class DmEventVisibilityRequest(
  dmEventId: Long,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext)
