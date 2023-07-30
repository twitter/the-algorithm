package com.X.visibility.interfaces.cards

import com.X.tweetypie.{thriftscala => tweetypiethrift}
import com.X.visibility.models.SafetyLevel
import com.X.visibility.models.ViewerContext

case class CardVisibilityRequest(
  cardUri: String,
  authorId: Option[Long],
  tweetOpt: Option[tweetypiethrift.Tweet],
  isPollCardType: Boolean,
  safetyLevel: SafetyLevel,
  viewerContext: ViewerContext)
