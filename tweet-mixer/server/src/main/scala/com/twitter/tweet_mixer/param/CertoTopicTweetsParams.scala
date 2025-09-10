package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object CertoTopicTweetsParams {

  // Enables User Interested in Simclusters candidate source
  object CertoTopicTweetsEnable
      extends FSParam[Boolean](
        name = "certo_topic_tweets_enabled",
        default = false
      )

  object UseProductContextTopicIds
      extends FSParam[Boolean](
        name = "certo_topic_tweets_use_product_context_topic_ids",
        default = false
      )

  object MaxNumCandidatesPerTopic
      extends FSBoundedParam[Int](
        name = "certo_topic_tweets_max_num_candidates_per_topic",
        min = 1,
        max = 1000,
        default = 100
      )

  object MaxNumCandidates
      extends FSBoundedParam[Int](
        name = "certo_topic_tweets_max_num_candidates",
        min = 1,
        max = 1000,
        default = 400
      )

  object MinCertoScore
      extends FSBoundedParam[Double](
        name = "certo_topic_tweets_min_certo_score",
        min = 0.0,
        max = 1.0,
        default = 0.005
      )

  object MinFavCount
      extends FSBoundedParam[Int](
        name = "certo_topic_tweets_min_fav_count",
        min = 5,
        max = 1000,
        default = 10
      )

  val booleanFSOverrides = Seq(CertoTopicTweetsEnable, UseProductContextTopicIds)

  val boundedIntFSOverrides =
    Seq(MaxNumCandidatesPerTopic, MaxNumCandidates, MinFavCount)

  val boundedDoubleFsOverrides = Seq(MinCertoScore)
}
