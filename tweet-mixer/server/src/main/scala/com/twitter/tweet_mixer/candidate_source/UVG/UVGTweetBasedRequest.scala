package com.twitter.tweet_mixer.candidate_source.UVG

import com.twitter.recos.user_video_graph.thriftscala.RelatedTweetSimilarityAlgorithm
import com.twitter.tweet_mixer.feature.EntityTypes.TweetId

case class UVGTweetBasedRequest(
  seedTweetIds: Seq[TweetId],
  maxResults: Option[Int],
  minCooccurrence: Option[Int],
  minScore: Option[Double],
  maxTweetAgeInHours: Option[Int],
  maxConsumerSeeds: Option[Int] = None,
  similarityAlgorithm: Option[RelatedTweetSimilarityAlgorithm] =
    Some(RelatedTweetSimilarityAlgorithm.LogCosine),
  enableCache: Boolean = true,
  maxNumSamplesPerNeighbor: Option[Int] = None,
  maxLeftNodeDegree: Option[Int] = None,
  maxRightNodeDegree: Option[Int] = None,
  sampleRHSTweets: Option[Boolean] = None,
  degreeExponent: Option[Double] = None)
