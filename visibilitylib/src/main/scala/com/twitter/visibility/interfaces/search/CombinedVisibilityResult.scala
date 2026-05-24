package com.twitter.visibility.interfaces.search

import com.twitter.visibility.builder.VisibilityResult

case class CombinedVisibilityResult(
  tweetVisibilityResult: VisibilityResult,
  quotedTweetVisibilityResult: Option[VisibilityResult])
