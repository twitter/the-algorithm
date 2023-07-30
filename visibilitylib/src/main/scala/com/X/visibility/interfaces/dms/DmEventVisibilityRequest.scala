package com.X.visibility.interfaces.dms

import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class DmEventVisibilityRequest(
  dmEventId: Long,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext)
