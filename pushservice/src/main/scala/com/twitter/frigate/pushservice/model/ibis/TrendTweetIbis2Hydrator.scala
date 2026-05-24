package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.TrendTweetCandidate
import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate

trait TrendTweetIbis2Hydrator extends TweetCandidateIbis2Hydrator {
  self: PushCandidate with TrendTweetCandidate with TweetAuthorDetails =>

  lazy val trendNameModelValue = Map("trend_name" -> trendName)

  override lazy val tweetModelValues = for {
    tweetValues <- super.tweetModelValues
    inlineActionValues <- tweetInlineActionModelValue
  } yield tweetValues ++ inlineActionValues ++ trendNameModelValue
}
