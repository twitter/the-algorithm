package com.twitter.cr_mitwitterer.param

import com.twitter.conversions.DurationOps._
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

object TopicTweetParams {
  object MatwitterTweetAge
      etwittertends FSBoundedParam[Duration](
        name = "topic_tweet_candidate_generation_matwitter_tweet_age_hours",
        default = 24.hours,
        min = 12.hours,
        matwitter = 48.hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object MatwitterTopicTweetCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "topic_tweet_matwitter_candidates_num",
        default = 200,
        min = 0,
        matwitter = 1000
      )

  object MatwitterSkitTfgCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "topic_tweet_skit_tfg_matwitter_candidates_num",
        default = 100,
        min = 0,
        matwitter = 1000
      )

  object MatwitterSkitHighPrecisionCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "topic_tweet_skit_high_precision_matwitter_candidates_num",
        default = 100,
        min = 0,
        matwitter = 1000
      )

  object MatwitterCertoCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "topic_tweet_certo_matwitter_candidates_num",
        default = 100,
        min = 0,
        matwitter = 1000
      )

  // The min prod score for Certo L2-normalized cosine candidates
  object CertoScoreThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "topic_tweet_certo_score_threshold",
        default = 0.015,
        min = 0,
        matwitter = 1
      )

  object SemanticCoreVersionIdParam
      etwittertends FSParam[Long](
        name = "semantic_core_version_id",
        default = 1380520918896713735L
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    CertoScoreThresholdParam,
    MatwitterTopicTweetCandidatesParam,
    MatwitterTweetAge,
    MatwitterCertoCandidatesParam,
    MatwitterSkitTfgCandidatesParam,
    MatwitterSkitHighPrecisionCandidatesParam,
    SemanticCoreVersionIdParam
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides()

    val doubleOverrides =
      FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides(CertoScoreThresholdParam)

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      MatwitterCertoCandidatesParam,
      MatwitterSkitTfgCandidatesParam,
      MatwitterSkitHighPrecisionCandidatesParam,
      MatwitterTopicTweetCandidatesParam
    )

    val longOverrides = FeatureSwitchOverrideUtil.getLongFSOverrides(SemanticCoreVersionIdParam)

    val durationFSOverrides = FeatureSwitchOverrideUtil.getDurationFSOverrides(MatwitterTweetAge)

    val enumOverrides =
      FeatureSwitchOverrideUtil.getEnumFSOverrides(NullStatsReceiver, Logger(getClass))

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(intOverrides: _*)
      .set(longOverrides: _*)
      .set(enumOverrides: _*)
      .set(durationFSOverrides: _*)
      .build()
  }
}
