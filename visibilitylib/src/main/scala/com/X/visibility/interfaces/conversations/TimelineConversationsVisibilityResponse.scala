package com.X.visibility.interfaces.conversations

import com.X.visibility.builder.VisibilityResult

case class TimelineConversationsVisibilityResponse(
  visibilityResults: Map[Long, VisibilityResult],
  failedTweetIds: Seq[Long])
