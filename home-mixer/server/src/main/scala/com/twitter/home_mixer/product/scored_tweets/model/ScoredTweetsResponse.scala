package com.twitter.home_mixer.product.scored_tweets.model

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.model.marshalling.HasLength

case class ScoredTweetsResponse(
  scoredTweets: Seq[CandidateWithDetails],
  queryMetadata: Option[QueryMetadata] = None)
    extends HasMarshalling
    with HasLength {
  override def length: Int = scoredTweets.length
}

case class QueryMetadata(
  userActionsSize: Option[Int] = None,
  userActionsContainsExplicitSignals: Option[Boolean] = None)
