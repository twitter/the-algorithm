package com.twitter.home_mitwitterer.product.scored_tweets.model

import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.model.marshalling.HasMarshalling

case class ScoredTweetsResponse(scoredTweets: Seq[CandidateWithDetails]) etwittertends HasMarshalling
