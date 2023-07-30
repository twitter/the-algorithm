package com.X.frigate.pushservice.model.ibis

import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetFavoriteCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.util.Future

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
