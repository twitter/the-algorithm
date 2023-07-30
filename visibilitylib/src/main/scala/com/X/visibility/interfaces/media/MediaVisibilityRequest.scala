package com.X.visibility.interfaces.media

import com.X.mediaservices.media_util.GenericMediaKey
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class MediaVisibilityRequest(
  mediaKey: GenericMediaKey,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext)
