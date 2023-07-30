package com.X.visibility.interfaces.tweets

import com.X.visibility.models.UserUnavailableStateEnum
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class UserUnavailableStateVisibilityRequest(
  safetyLevel: SafetyLevel,
  tweetId: Long,
  viewerContext: ViewerContext,
  userUnavailableState: UserUnavailableStateEnum,
  isRetweet: Boolean,
  isInnerQuotedTweet: Boolean,
)
