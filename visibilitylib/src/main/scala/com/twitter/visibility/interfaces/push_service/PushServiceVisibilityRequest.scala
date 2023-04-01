package com.twitter.visibility.interfaces.push_service

import com.twitter.gizmoduck.thriftscala.User
import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext

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
