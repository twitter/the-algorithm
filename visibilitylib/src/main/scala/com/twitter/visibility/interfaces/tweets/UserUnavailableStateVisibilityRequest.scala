package com.twitter.visibility.interfaces.tweets

import com.twitter.visibility.models.UserUnavailableStateEnum
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContext

case class UserUnavailableStateVisibilityRequest(
  safetyLevel: SafetyLevel,
  tweetId: Long,
  viewerContext: ViewerContext,
  userUnavailableState: UserUnavailableStateEnum,
  isRetweet: Boolean,
  isInnerQuotedTweet: Boolean,
)
