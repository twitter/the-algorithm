package com.X.visibility.interfaces.blender

import com.X.visibility.builder.VisibilityResult

case class CombinedVisibilityResult(
  tweetVisibilityResult: VisibilityResult,
  quotedTweetVisibilityResult: Option[VisibilityResult])
