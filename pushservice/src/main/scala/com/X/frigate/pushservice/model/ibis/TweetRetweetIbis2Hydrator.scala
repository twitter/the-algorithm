package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetRetweetCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.util.PushIbisUtil.mergeModelValues

import com.X.util.Future

trait TweetRetweetCandidateIbis2Hydrator
    extends TweetCandidateIbis2Hydrator
    with RankedSocialContextIbis2Hydrator {
  self: PushCandidate with TweetRetweetCandidate with TweetAuthorDetails =>

  override lazy val tweetModelValues: Future[Map[String, String]] =
    for {
      socialContextModelValues <- socialContextModelValues
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ mediaModelValue ++ otherModelValues ++ socialContextModelValues ++ tweetInlineModelValues ++ inlineVideoMediaMap
    }

  lazy val socialContextForRetweetMap: Map[String, String] =
    if (self.target.params(PushFeatureSwitchParams.EnableSocialContextForRetweet)) {
      Map("enable_social_context_retweet" -> "true")
    } else Map.empty[String, String]

  override lazy val customFieldsMapFut: Future[Map[String, String]] =
    mergeModelValues(super.customFieldsMapFut, socialContextForRetweetMap)
}
