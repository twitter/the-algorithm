package com.twitter.visibility.interfaces.cards

import com.twitter.tweetypie.{thriftscala => tweetypiethrift}
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.ViewerContetwittert

case class CardVisibilityRequest(
  cardUri: String,
  authorId: Option[Long],
  tweetOpt: Option[tweetypiethrift.Tweet],
  isPollCardType: Boolean,
  safetyLevel: SafetyLevel,
  viewerContetwittert: ViewerContetwittert)
