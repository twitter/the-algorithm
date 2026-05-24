package com.twitter.visibility.interfaces.conversations

import com.twitter.visibility.builder.VisibilityResult

case class TimelineConversationsVisibilityResponse(
  visibilityResults: Map[Long, VisibilityResult],
  failedTweetIds: Seq[Long])
