package com.X.visibility.interfaces.tweets

import com.X.tweetypie.thriftscala.Tweet
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class TweetVisibilityRequest(
  tweet: Tweet,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext,
  isInnerQuotedTweet: Boolean,
  isRetweet: Boolean,
  hydrateConversationControl: Boolean = false,
  isSourceTweet: Boolean = false)
