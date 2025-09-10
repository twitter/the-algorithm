package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object PopularTopicTweetsParams {

  // Enables Popular Topics candidate source
  object PopularTopicTweetsEnable
      extends FSParam[Boolean](
        name = "popular_topic_tweets_enabled",
        default = false
      )

  object SourceIds
      extends FSParam[Seq[String]](
        name = "popular_topic_tweets_source_ids",
        default = Seq("PopularTopicAll")
      )

  object MaxNumCandidatesPerTripSource
      extends FSBoundedParam[Int](
        name = "popular_topic_tweets_max_num_candidates_per_source",
        min = 1,
        max = 1000,
        default = 100
      )

  object MaxNumCandidates
      extends FSBoundedParam[Int](
        name = "popular_topic_tweets_max_num_candidates",
        min = 1,
        max = 1000,
        default = 400
      )

  object PopTopicIds
      extends FSParam[Seq[Long]](
        name = "popular_topic_tweets_pop_topic_ids",
        default = Seq.empty
      )

  val booleanFSOverrides = Seq(PopularTopicTweetsEnable)

  val boundedIntFSOverrides =
    Seq(MaxNumCandidatesPerTripSource, MaxNumCandidates)

  val stringSeqFSOverrides = Seq(SourceIds)

  val longSeqFSOverrides = Seq(PopTopicIds)
}
