package com.twitter.cr_mixer.param

import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.model.EarlybirdSimilarityEngineType
import com.twitter.cr_mixer.model.EarlybirdSimilarityEngineType_ModelBased
import com.twitter.cr_mixer.model.EarlybirdSimilarityEngineType_RecencyBased
import com.twitter.cr_mixer.model.EarlybirdSimilarityEngineType_TensorflowBased
import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.logging.Logger
import com.twitter.timelines.configapi.BaseConfig
import com.twitter.timelines.configapi.BaseConfigBuilder
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.FeatureSwitchOverrideUtil
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.Param
import com.twitter.util.Duration

object EarlybirdFrsBasedCandidateGenerationParams {
  object CandidateGenerationEarlybirdSimilarityEngineType extends Enumeration {
    protected case class SimilarityEngineType(rankingMode: EarlybirdSimilarityEngineType)
        extends super.Val
    import scala.language.implicitConversions
    implicit def valueToEarlybirdRankingMode(x: Value): SimilarityEngineType =
      x.asInstanceOf[SimilarityEngineType]

    val EarlybirdRankingMode_RecencyBased: SimilarityEngineType = SimilarityEngineType(
      EarlybirdSimilarityEngineType_RecencyBased)
    val EarlybirdRankingMode_ModelBased: SimilarityEngineType = SimilarityEngineType(
      EarlybirdSimilarityEngineType_ModelBased)
    val EarlybirdRankingMode_TensorflowBased: SimilarityEngineType = SimilarityEngineType(
      EarlybirdSimilarityEngineType_TensorflowBased)
  }

  object FrsBasedCandidateGenerationEarlybirdSimilarityEngineTypeParam
      extends FSEnumParam[CandidateGenerationEarlybirdSimilarityEngineType.type](
        name = "frs_based_candidate_generation_earlybird_ranking_mode_id",
        default =
          CandidateGenerationEarlybirdSimilarityEngineType.EarlybirdRankingMode_RecencyBased,
        enum = CandidateGenerationEarlybirdSimilarityEngineType
      )

  object FrsBasedCandidateGenerationRecencyBasedEarlybirdMaxTweetsPerUser
      extends FSBoundedParam[Int](
        name = "frs_based_candidate_generation_earlybird_max_tweets_per_user",
        default = 100,
        min = 0,
        /**
         * Note max should be equal to EarlybirdRecencyBasedCandidateStoreModule.DefaultMaxNumTweetPerUser.
         * Which is the size of the memcached result list.
         */
        max = 100
      )

  object FrsBasedCandidateGenerationEarlybirdMaxTweetAge
      extends FSBoundedParam[Duration](
        name = "frs_based_candidate_generation_earlybird_max_tweet_age_hours",
        default = 24.hours,
        min = 12.hours,
        /**
         * Note max could be related to EarlybirdRecencyBasedCandidateStoreModule.DefaultMaxNumTweetPerUser.
         * Which is the size of the memcached result list for recency based earlybird candidate source.
         * E.g. if max = 720.hours, we may want to increase the DefaultMaxNumTweetPerUser.
         */
        max = 96.hours
      )
      with HasDurationConversion {
    override val durationConversion: DurationConversion = DurationConversion.FromHours
  }

  object FrsBasedCandidateGenerationEarlybirdFilterOutRetweetsAndReplies
      extends FSParam[Boolean](
        name = "frs_based_candidate_generation_earlybird_filter_out_retweets_and_replies",
        default = true
      )

  val AllParams: Seq[Param[_] with FSName] = Seq(
    FrsBasedCandidateGenerationEarlybirdSimilarityEngineTypeParam,
    FrsBasedCandidateGenerationRecencyBasedEarlybirdMaxTweetsPerUser,
    FrsBasedCandidateGenerationEarlybirdMaxTweetAge,
    FrsBasedCandidateGenerationEarlybirdFilterOutRetweetsAndReplies,
  )

  lazy val config: BaseConfig = {
    val booleanOverrides = FeatureSwitchOverrideUtil.getBooleanFSOverrides(
      FrsBasedCandidateGenerationEarlybirdFilterOutRetweetsAndReplies,
    )

    val doubleOverrides = FeatureSwitchOverrideUtil.getBoundedDoubleFSOverrides()

    val intOverrides = FeatureSwitchOverrideUtil.getBoundedIntFSOverrides(
      FrsBasedCandidateGenerationRecencyBasedEarlybirdMaxTweetsPerUser
    )

    val durationFSOverrides =
      FeatureSwitchOverrideUtil.getDurationFSOverrides(
        FrsBasedCandidateGenerationEarlybirdMaxTweetAge
      )

    val enumOverrides = FeatureSwitchOverrideUtil.getEnumFSOverrides(
      NullStatsReceiver,
      Logger(getClass),
      FrsBasedCandidateGenerationEarlybirdSimilarityEngineTypeParam,
    )

    BaseConfigBuilder()
      .set(booleanOverrides: _*)
      .set(doubleOverrides: _*)
      .set(intOverrides: _*)
      .set(enumOverrides: _*)
      .set(durationFSOverrides: _*)
      .build()
  }
}
