package com.twitter.visibility.interfaces.media

import com.twitter.mediaservices.media_util.GenericMediaKey
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class MediaVisibilityRequest(
  mediaKey: GenericMediaKey,
  safetyLevel: SafetyLevel,
  viewerContetwittert: ViewerContetwittert)
