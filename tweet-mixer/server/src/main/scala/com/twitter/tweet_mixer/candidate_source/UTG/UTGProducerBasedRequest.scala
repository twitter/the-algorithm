package com.twitter.tweet_mixer.candidate_source.UTG

import com.twitter.recos.user_tweet_graph.thriftscala.RelatedTweetSimilarityAlgorithm
import com.twitter.tweet_mixer.feature.EntityTypes.UserId

case class UTGProducerBasedRequest(
  seedUserIds: Seq[UserId],
  maxResults: Option[Int],
  minCooccurrence: Option[Int],
  minScore: Option[Double],
  maxNumFollowers: Option[Int],
  maxTweetAgeInHours: Option[Int],
  similarityAlgorithm: Option[RelatedTweetSimilarityAlgorithm] =
    Some(RelatedTweetSimilarityAlgorithm.LogCosine),
  enableCache: Boolean = true)
