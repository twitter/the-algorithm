package com.twitter.visibility.interfaces.blender

import com.twitter.visibility.builder.VisibilityResult

case class CombinedVisibilityResult(
  tweetVisibilityResult: VisibilityResult,
  quotedTweetVisibilityResult: Option[VisibilityResult])
