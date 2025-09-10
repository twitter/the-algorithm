package com.twitter.tweet_mixer.candidate_source.UTG

import com.twitter.recos.user_tweet_graph.thriftscala.RelatedTweetSimilarityAlgorithm
import com.twitter.tweet_mixer.feature.EntityTypes.TweetId

case class UTGTweetBasedRequest(
  seedTweetIds: Seq[TweetId],
  maxResults: Option[Int],
  minCooccurrence: Option[Int],
  minScore: Option[Double],
  maxTweetAgeInHours: Option[Int],
  maxConsumerSeeds: Option[Int] = None,
  similarityAlgorithm: Option[RelatedTweetSimilarityAlgorithm] =
    Some(RelatedTweetSimilarityAlgorithm.LogCosine),
  enableCache: Boolean = true,
  degreeExponent: Option[Double] = None)
