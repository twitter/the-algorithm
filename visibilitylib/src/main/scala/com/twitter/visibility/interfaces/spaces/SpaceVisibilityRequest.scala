package com.twitter.visibility.interfaces.spaces

import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext

case class SpaceVisibilityRequest(
  spaceId: String,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext,
  spaceHostAndAdminUserIds: Option[Seq[Long]])
