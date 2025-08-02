package com.twitter.visibility.interfaces.spaces

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class SpaceVisibilityRequest(
  spaceId: String,
  safetyLevel: SafetyLevel,
  viewerContetwittert: ViewerContetwittert,
  spaceHostAndAdminUserIds: Option[Seq[Long]])
