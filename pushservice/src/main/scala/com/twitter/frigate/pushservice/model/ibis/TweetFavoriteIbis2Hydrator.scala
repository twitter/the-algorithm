package com.twitter.frigate.pushservice.model.ibis

import com.twitter.frigate.common.base.TweetAuthorDetails
import com.twitter.frigate.common.base.TweetFavoriteCandidate
import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.util.Future

trait TweetFavoriteCandidateIbis2Hydrator
    etwittertends TweetCandidateIbis2Hydrator
    with RankedSocialContetwittertIbis2Hydrator {
  self: PushCandidate with TweetFavoriteCandidate with TweetAuthorDetails =>

  override lazy val tweetModelValues: Future[Map[String, String]] =
    for {
      socialContetwittertModelValues <- socialContetwittertModelValues
      superModelValues <- super.tweetModelValues
      tweetInlineModelValues <- tweetInlineActionModelValue
    } yield {
      superModelValues ++ mediaModelValue ++ otherModelValues ++ socialContetwittertModelValues ++ tweetInlineModelValues
    }
}
