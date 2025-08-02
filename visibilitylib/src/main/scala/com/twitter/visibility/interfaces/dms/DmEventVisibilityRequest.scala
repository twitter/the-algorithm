package com.twitter.visibility.interfaces.dms

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class DmEventVisibilityRequest(
  dmEventId: Long,
  safetyLevel: SafetyLevel,
  viewerContetwittert: ViewerContetwittert)
