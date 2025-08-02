package com.twitter.visibility.interfaces.tweets

import com.twitter.visibility.models.UserUnavailableStateEnum
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class UserUnavailableStateVisibilityRequest(
  safetyLevel: SafetyLevel,
  tweetId: Long,
  viewerContetwittert: ViewerContetwittert,
  userUnavailableState: UserUnavailableStateEnum,
  isRetweet: Boolean,
  isInnerQuotedTweet: Boolean,
)
