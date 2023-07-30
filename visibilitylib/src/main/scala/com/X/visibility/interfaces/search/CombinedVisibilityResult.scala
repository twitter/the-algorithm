package com.X.visibility.interfaces.search

import com.X.visibility.builder.VisibilityResult

case class CombinedVisibilityResult(
  tweetVisibilityResult: VisibilityResult,
  quotedTweetVisibilityResult: Option[VisibilityResult])
