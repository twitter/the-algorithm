package com.twitter.home_mixer.product.scored_tweets.model

import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling

case class ScoredTweetsResponse(scoredTweets: Seq[CandidateWithDetails]) extends HasMarshalling
