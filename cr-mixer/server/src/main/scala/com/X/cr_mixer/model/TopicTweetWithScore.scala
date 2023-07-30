package com.X.cr_mixer.model

import com.X.cr_mixer.thriftscala.SimilarityEngineType
import com.X.simclusters_v2.common.TweetId

/***
 * Bind a tweetId with a raw score generated from one single Similarity Engine
 * @param similarityEngineType, which underlying topic source the topic tweet is from
 */
case class TopicTweetWithScore(
  tweetId: TweetId,
  score: Double,
  similarityEngineType: SimilarityEngineType)
