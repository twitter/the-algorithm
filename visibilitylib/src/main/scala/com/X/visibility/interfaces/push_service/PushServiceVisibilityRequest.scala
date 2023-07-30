package com.X.visibility.interfaces.push_service

import com.X.gizmoduck.thriftscala.User
import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class PushServiceVisibilityRequest(
  tweet: Tweet,
  author: User,
  viewerContext: ViewerContext,
  safetyLevel: SafetyLevel,
  sourceTweet: Option[Tweet] = None,
  quotedTweet: Option[Tweet] = None,
  isRetweet: Boolean = false,
  isInnerQuotedTweet: Boolean = false,
  isSourceTweet: Boolean = false,
  isOutOfNetworkTweet: Boolean = true,
)
