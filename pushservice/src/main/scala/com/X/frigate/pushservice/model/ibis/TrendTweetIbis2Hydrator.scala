package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.TrendTweetCandidate
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate

trait TrendTweetIbis2Hydrator extends TweetCandidateIbis2Hydrator {
  self: PushCandidate with TrendTweetCandidate with TweetAuthorDetails =>

  lazy val trendNameModelValue = Map("trend_name" -> trendName)

  override lazy val tweetModelValues = for {
    tweetValues <- super.tweetModelValues
    inlineActionValues <- tweetInlineActionModelValue
  } yield tweetValues ++ inlineActionValues ++ trendNameModelValue
}
