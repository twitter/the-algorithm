package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.util.Duration
import com.twitter.conversions.DurationOps._
import com.twitter.timelines.configapi.FSParam

object SkitTopicTweetsParams {

  // Enables User Interested in Simclusters candidate source
  object SkitTopicTweetsEnable
      extends FSParam[Boolean](
        name = "skit_topic_tweets_enabled",
        default = false
      )

  object SkitHighPrecisionTopicTweetsEnable
      extends FSParam[Boolean](
        name = "skit_topic_tweets_high_precisions_enabled",
        default = false
      )

  object UseProductContextTopicIds
      extends FSParam[Boolean](
        name = "skit_topic_tweets_use_product_context_topic_ids",
        default = false
      )

  object MaxNumCandidatesPerTopic
      extends FSBoundedParam[Int](
        name = "skit_topic_tweets_max_num_candidates_per_topic",
        min = 1,
        max = 1000,
        default = 100
      )

  object MaxNumCandidates
      extends FSBoundedParam[Int](
        name = "skit_topic_tweets_max_num_candidates",
        min = 1,
        max = 1000,
        default = 400
      )

  object MinSkitScore
      extends FSBoundedParam[Double](
        name = "skit_topic_tweets_min_certo_score",
        min = 0.0,
        max = 1.0,
        default = 0.005
      )

  object MinFavCount
      extends FSBoundedParam[Int](
        name = "skit_topic_tweets_min_fav_count",
        min = 5,
        max = 1000,
        default = 10
      )

  object SemanticCoreVersionIdParam
      extends FSBoundedParam[Long](
        name = "skit_topic_semantic_core_version_id",
        default = <removed_id>,
        max = Long.MaxValue,
        min = 0L
      )

  object MaxTweetAge
      extends FSBoundedParam[Duration](
        name = "topic_tweet_candidate_generation_max_tweet_age_hours",
        default = 24.hours,
        min = 12.hours,
        max = 48.hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  val booleanFSOverrides =
    Seq(SkitTopicTweetsEnable, UseProductContextTopicIds, SkitHighPrecisionTopicTweetsEnable)

  val boundedIntFSOverrides =
    Seq(MaxNumCandidatesPerTopic, MaxNumCandidates, MinFavCount)

  val boundedDoubleFsOverrides = Seq(MinSkitScore)

  val boundedLongFSOverrides = Seq(SemanticCoreVersionIdParam)

  val boundedDurationFSOverrides = Seq(MaxTweetAge)
}
