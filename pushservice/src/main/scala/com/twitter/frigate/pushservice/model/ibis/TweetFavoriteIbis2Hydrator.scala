package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetFavoriteCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.util.Future

trait TweetFavoriteCandidateIbis2Hydrator
    extends TweetCandidateIbis2Hydrator
    with RankedSocialContextIbis2Hydrator {
  self: PushCandidate with TweetFavoriteCandidate with TweetAuthorDetails =>

  override lazy val tweetModelValues: Future[Map[String, String]] =
    for {
      socialContextModelValues <- socialContextModelValues
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ mediaModelValue ++ otherModelValues ++ socialContextModelValues ++ tweetInlineModelValues
    }
}
