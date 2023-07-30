package com.X.visibility.interfaces.spaces

import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class SpaceVisibilityRequest(
  spaceId: String,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext,
  spaceHostAndAdminUserIds: Option[Seq[Long]])
