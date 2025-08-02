package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetRetweetCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.pushservice.params.PushFeatureSwitchParams
import com.twitter.frigate.pushservice.util.PushIbisUtil.mergeModelValues

import com.twitter.util.Future

trait TweetRetweetCandidateIbis2Hydrator
    etwittertends TweetCandidateIbis2Hydrator
    with RankedSocialContetwittertIbis2Hydrator {
  self: PushCandidate with TweetRetweetCandidate with TweetAuthorDetails =>

  override lazy val tweetModelValues: Future[Map[String, String]] =
    for {
      socialContetwittertModelValues <- socialContetwittertModelValues
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ mediaModelValue ++ otherModelValues ++ socialContetwittertModelValues ++ tweetInlineModelValues ++ inlineVideoMediaMap
    }

  lazy val socialContetwittertForRetweetMap: Map[String, String] =
    if (self.target.params(PushFeatureSwitchParams.EnableSocialContetwittertForRetweet)) {
      Map("enable_social_contetwittert_retweet" -> "true")
    } else Map.empty[String, String]

  override lazy val customFieldsMapFut: Future[Map[String, String]] =
    mergeModelValues(super.customFieldsMapFut, socialContetwittertForRetweetMap)
}
