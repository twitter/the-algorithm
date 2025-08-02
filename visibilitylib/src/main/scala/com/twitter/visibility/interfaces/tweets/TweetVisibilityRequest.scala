package com.twitter.visibility.interfaces.tweets

import com.twitter.tweetypie.thriftscala.Tweet
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class TweetVisibilityRequest(
  tweet: Tweet,
  safetyLevel: SafetyLevel,
  viewerContetwittert: ViewerContetwittert,
  isInnerQuotedTweet: Boolean,
  isRetweet: Boolean,
  hydrateConversationControl: Boolean = false,
  isSourceTweet: Boolean = false)
