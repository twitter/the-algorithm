package com.twitter.frigate.pushservice.params

import com.twitter.conversions.DurationOps._
import com.twitter.frigate.pushservice.params.InlineActionsEnum._
import com.twitter.frigate.pushservice.params.HighQualityCandidateGroupEnum._
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSEnumSeqParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.util.Duration

object PushFeatureSwitchParams {

  /**
   * List of CRTs to uprank. Last CRT in sequence ends up on top of list
   */
  object ListOfCrtsToUpRank
      etwittertends FSParam[Seq[String]]("rerank_candidates_crt_to_top", default = Seq.empty[String])

  object ListOfCrtsForOpenApp
      etwittertends FSParam[Seq[String]](
        "open_app_allowed_crts",
        default = Seq(
          "f1firstdegreetweet",
          "f1firstdegreephoto",
          "f1firstdegreevideo",
          "geopoptweet",
          "frstweet",
          "trendtweet",
          "hermituser",
          "triangularloopuser"
        ))

  /**
   * List of CRTs to downrank. Last CRT in sequence ends up on bottom of list
   */
  object ListOfCrtsToDownRank
      etwittertends FSParam[Seq[String]](
        name = "rerank_candidates_crt_to_downrank",
        default = Seq.empty[String])

  /**
   * Param to enable VF filtering in Tweetypie (vs using VisibilityLibrary)
   */
  object EnableVFInTweetypie
      etwittertends FSParam[Boolean](
        name = "visibility_filtering_enable_vf_in_tweetypie",
        default = true
      )

  /**
   * Number of matwitter earlybird candidates
   */
  object NumberOfMatwitterEarlybirdInNetworkCandidatesParam
      etwittertends FSBoundedParam(
        name = "frigate_push_matwitter_earlybird_in_network_candidates",
        default = 100,
        min = 0,
        matwitter = 800
      )

  /**
   * Number of matwitter UserTweetEntityGraph candidates to query
   */
  object NumberOfMatwitterUTEGCandidatesQueriedParam
      etwittertends FSBoundedParam(
        name = "frigate_push_matwitter_uteg_candidates_queried",
        default = 30,
        min = 0,
        matwitter = 300
      )

  /**
   * Param to control the matwitter tweet age for users
   */
  object MatwitterTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_matwitter_hours",
        default = 24.hours,
        min = 1.hours,
        matwitter = 72.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for modeling-based candidates
   */
  object ModelingBasedCandidateMatwitterTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_candidate_generation_model_matwitter_hours",
        default = 24.hours,
        min = 1.hours,
        matwitter = 72.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for simcluster-based candidates
   */
  object GeoPopTweetMatwitterAgeInHours
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_geo_pop_matwitter_hours",
        default = 24.hours,
        min = 1.hours,
        matwitter = 120.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for simcluster-based candidates
   */
  object SimclusterBasedCandidateMatwitterTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_simcluster_matwitter_hours",
        default = 24.hours,
        min = 24.hours,
        matwitter = 48.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for Detopic-based candidates
   */
  object DetopicBasedCandidateMatwitterTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_detopic_matwitter_hours",
        default = 24.hours,
        min = 24.hours,
        matwitter = 48.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for F1 candidates
   */
  object F1CandidateMatwitterTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "tweet_age_f1_matwitter_hours",
        default = 24.hours,
        min = 1.hours,
        matwitter = 96.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitter tweet age for Etwitterplore Video Tweet
   */
  object EtwitterploreVideoTweetAgeParam
      etwittertends FSBoundedParam[Duration](
        name = "etwitterplore_video_tweets_age_matwitter_hours",
        default = 48.hours,
        min = 1.hours,
        matwitter = 336.hours // Two weeks
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to no send for new user playbook push if user login for past hours
   */
  object NewUserPlaybookAllowedLastLoginHours
      etwittertends FSBoundedParam[Duration](
        name = "new_user_playbook_allowed_last_login_hours",
        default = 0.hours,
        min = 0.hours,
        matwitter = 72.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * The batch size of RefreshForPushHandler's Take step
   */
  object NumberOfMatwitterCandidatesToBatchInRFPHTakeStep
      etwittertends FSBoundedParam(
        name = "frigate_push_rfph_batch_take_matwitter_size",
        default = 1,
        min = 1,
        matwitter = 10
      )

  /**
   * The matwitterimum number of candidates to batch for Importance Sampling
   */
  object NumberOfMatwitterCandidatesToBatchForImportanceSampling
      etwittertends FSBoundedParam(
        name = "frigate_push_rfph_matwitter_candidates_to_batch_for_importance_sampling",
        default = 65,
        min = 1,
        matwitter = 500
      )

  /**
   * Matwitterimum number of regular MR push in 24.hours/daytime/nighttime
   */
  object MatwitterMrPushSends24HoursParam
      etwittertends FSBoundedParam(
        name = "pushcap_matwitter_sends_24hours",
        default = 5,
        min = 0,
        matwitter = 12
      )

  /**
   * Matwitterimum number of regular MR ntab only channel in 24.hours/daytime/nighttime
   */
  object MatwitterMrNtabOnlySends24HoursParamV3
      etwittertends FSBoundedParam(
        name = "pushcap_matwitter_sends_24hours_ntabonly_v3",
        default = 5,
        min = 0,
        matwitter = 12
      )

  /**
   * Matwitterimum number of regular MR ntab only in 24.hours/daytime/nighttime
   */
  object MatwitterMrPushSends24HoursNtabOnlyUsersParam
      etwittertends FSBoundedParam(
        name = "pushcap_matwitter_sends_24hours_ntab_only",
        default = 5,
        min = 0,
        matwitter = 10
      )

  /**
   * Customized PushCap offset (e.g., to the predicted value)
   */
  object CustomizedPushCapOffset
      etwittertends FSBoundedParam[Int](
        name = "pushcap_customized_offset",
        default = 0,
        min = -2,
        matwitter = 4
      )

  /**
   * Param to enable restricting minimum pushcap assigned with ML models
   * */
  object EnableRestrictedMinModelPushcap
      etwittertends FSParam[Boolean](
        name = "pushcap_restricted_model_min_enable",
        default = false
      )

  /**
   * Param to specify the minimum pushcap allowed to be assigned with ML models
   * */
  object RestrictedMinModelPushcap
      etwittertends FSBoundedParam[Int](
        name = "pushcap_restricted_model_min_value",
        default = 1,
        min = 0,
        matwitter = 9
      )

  object EnablePushcapRefactor
      etwittertends FSParam[Boolean](
        name = "pushcap_enable_refactor",
        default = false
      )

  /**
   * Enables the restrict step in pushservice for a given user
   *
   * Setting this to false may cause a large number of candidates to be passed on to filtering/take
   * step in RefreshForPushHandler, increasing the service latency significantly
   */
  object EnableRestrictStep etwittertends FSParam[Boolean]("frigate_push_rfph_restrict_step_enable", true)

  /**
   * The number of candidates that are able to pass through the restrict step.
   */
  object RestrictStepSize
      etwittertends FSBoundedParam(
        name = "frigate_push_rfph_restrict_step_size",
        default = 65,
        min = 65,
        matwitter = 200
      )

  /**
   * Number of matwitter crMitwitterer candidates to send.
   */
  object NumberOfMatwitterCrMitwittererCandidatesParam
      etwittertends FSBoundedParam(
        name = "cr_mitwitterer_migration_matwitter_num_of_candidates_to_return",
        default = 400,
        min = 0,
        matwitter = 2000
      )

  /**
   * Duration between two MR pushes
   */
  object MinDurationSincePushParam
      etwittertends FSBoundedParam[Duration](
        name = "pushcap_min_duration_since_push_hours",
        default = 4.hours,
        min = 0.hours,
        matwitter = 72.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Each Phase duration to gradually ramp up MagicRecs for new users
   */
  object GraduallyRampUpPhaseDurationDays
      etwittertends FSBoundedParam[Duration](
        name = "pushcap_gradually_ramp_up_phase_duration_days",
        default = 3.days,
        min = 2.days,
        matwitter = 7.days
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to specify interval for target pushcap fatigue
   */
  object TargetPushCapFatigueIntervalHours
      etwittertends FSBoundedParam[Duration](
        name = "pushcap_fatigue_interval_hours",
        default = 24.hours,
        min = 1.hour,
        matwitter = 240.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to specify interval for target ntabOnly fatigue
   */
  object TargetNtabOnlyCapFatigueIntervalHours
      etwittertends FSBoundedParam[Duration](
        name = "pushcap_ntabonly_fatigue_interval_hours",
        default = 24.hours,
        min = 1.hour,
        matwitter = 240.hours
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to use completely etwitterplicit push cap instead of LTV/modeling-based
   */
  object EnableEtwitterplicitPushCap
      etwittertends FSParam[Boolean](
        name = "pushcap_etwitterplicit_enable",
        default = false
      )

  /**
   * Param to control etwitterplicit push cap (non-LTV)
   */
  object EtwitterplicitPushCap
      etwittertends FSBoundedParam[Int](
        name = "pushcap_etwitterplicit_value",
        default = 1,
        min = 0,
        matwitter = 20
      )

  /**
   * Parameters for percentile thresholds of OpenOrNtabClick model in MR filtering model refreshing DDG
   */
  object PercentileThresholdCohort1
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort1",
        default = 0.65,
        min = 0.0,
        matwitter = 1.0
      )

  object PercentileThresholdCohort2
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort2",
        default = 0.03,
        min = 0.0,
        matwitter = 1.0
      )
  object PercentileThresholdCohort3
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort3",
        default = 0.03,
        min = 0.0,
        matwitter = 1.0
      )
  object PercentileThresholdCohort4
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort4",
        default = 0.06,
        min = 0.0,
        matwitter = 1.0
      )
  object PercentileThresholdCohort5
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort5",
        default = 0.06,
        min = 0.0,
        matwitter = 1.0
      )
  object PercentileThresholdCohort6
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_percentile_threshold_cohort6",
        default = 0.8,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Parameters for percentile threshold list of OpenOrNtabCLick model in MR percentile grid search etwitterperiments
   */
  object MrPercentileGridSearchThresholdsCohort1
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort1",
        default = Seq(0.8, 0.75, 0.65, 0.55, 0.45, 0.35, 0.25)
      )
  object MrPercentileGridSearchThresholdsCohort2
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort2",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercentileGridSearchThresholdsCohort3
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort3",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercentileGridSearchThresholdsCohort4
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort4",
        default = Seq(0.15, 0.12, 0.1, 0.08, 0.06, 0.045, 0.03)
      )
  object MrPercentileGridSearchThresholdsCohort5
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort5",
        default = Seq(0.3, 0.2, 0.15, 0.1, 0.08, 0.06, 0.05)
      )
  object MrPercentileGridSearchThresholdsCohort6
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_percentile_grid_search_thresholds_cohort6",
        default = Seq(0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2)
      )

  /**
   * Parameters for threshold list of OpenOrNtabClick model in MF grid search etwitterperiments
   */
  object MfGridSearchThresholdsCohort1
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort1",
        default = Seq(0.030, 0.040, 0.050, 0.062, 0.070, 0.080, 0.090) // default: 0.062
      )
  object MfGridSearchThresholdsCohort2
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort2",
        default = Seq(0.005, 0.010, 0.015, 0.020, 0.030, 0.040, 0.050) // default: 0.020
      )
  object MfGridSearchThresholdsCohort3
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort3",
        default = Seq(0.010, 0.015, 0.020, 0.025, 0.035, 0.045, 0.055) // default: 0.025
      )
  object MfGridSearchThresholdsCohort4
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort4",
        default = Seq(0.015, 0.020, 0.025, 0.030, 0.040, 0.050, 0.060) // default: 0.030
      )
  object MfGridSearchThresholdsCohort5
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort5",
        default = Seq(0.035, 0.040, 0.045, 0.050, 0.060, 0.070, 0.080) // default: 0.050
      )
  object MfGridSearchThresholdsCohort6
      etwittertends FSParam[Seq[Double]](
        name = "frigate_push_modeling_mf_grid_search_thresholds_cohort6",
        default = Seq(0.040, 0.045, 0.050, 0.055, 0.065, 0.075, 0.085) // default: 0.055
      )

  /**
   * Param to specify which global optout models to use to first predict the global scores for users
   */
  object GlobalOptoutModelParam
      etwittertends FSParam[Seq[OptoutModel.ModelNameType]](
        name = "optout_model_global_model_ids",
        default = Seq.empty[OptoutModel.ModelNameType]
      )

  /**
   * Param to specify which optout model to use according to the etwitterperiment bucket
   */
  object BucketOptoutModelParam
      etwittertends FSParam[OptoutModel.ModelNameType](
        name = "optout_model_bucket_model_id",
        default = OptoutModel.D0_has_realtime_features
      )

  /*
   * Param to enable candidate generation model
   * */
  object EnableCandidateGenerationModelParam
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable",
        default = false
      )

  object EnableOverrideForSportsCandidates
      etwittertends FSParam[Boolean](name = "magicfanout_sports_event_enable_override", default = true)

  object EnableEventIdBasedOverrideForSportsCandidates
      etwittertends FSParam[Boolean](
        name = "magicfanout_sports_event_enable_event_id_based_override",
        default = true)

  /**
   * Param to specify the threshold to determine if a user’s optout score is high enough to enter the etwitterperiment.
   */
  object GlobalOptoutThresholdParam
      etwittertends FSParam[Seq[Double]](
        name = "optout_model_global_thresholds",
        default = Seq(1.0, 1.0)
      )

  /**
   * Param to specify the threshold to determine if a user’s optout score is high enough to be assigned
   * with a reduced pushcap based on the bucket membership.
   */
  object BucketOptoutThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "optout_model_bucket_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to specify the reduced pushcap value if the optout probability predicted by the bucket
   * optout model is higher than the specified bucket optout threshold.
   */
  object OptoutEtwitterptPushCapParam
      etwittertends FSBoundedParam[Int](
        name = "optout_model_etwitterpt_push_cap",
        default = 10,
        min = 0,
        matwitter = 10
      )

  /**
   * Param to specify the thresholds to determine which push cap slot the user should be assigned to
   * according to the optout score. For etwitterample,the slot thresholds are [0.1, 0.2, ..., 1.0], the user
   * is assigned to the second slot if the optout score is in (0.1, 0.2].
   */
  object BucketOptoutSlotThresholdParam
      etwittertends FSParam[Seq[Double]](
        name = "optout_model_bucket_slot_thresholds",
        default = Seq.empty[Double]
      )

  /**
   * Param to specify the adjusted push cap of each slot. For etwitterample, if the slot push caps are [1, 2, ..., 10]
   * and the user is assigned to the 2nd slot according to the optout score, the push cap of the user
   * will be adjusted to 2.
   */
  object BucketOptoutSlotPushcapParam
      etwittertends FSParam[Seq[Int]](
        name = "optout_model_bucket_slot_pushcaps",
        default = Seq.empty[Int]
      )

  /**
   * Param to specify if the optout score based push cap adjustment is enabled
   */
  object EnableOptoutAdjustedPushcap
      etwittertends FSParam[Boolean](
        "optout_model_enable_optout_adjusted_pushcap",
        false
      )

  /**
   * Param to specify which weighted open or ntab click model to use
   */
  object WeightedOpenOrNtabClickRankingModelParam
      etwittertends FSParam[WeightedOpenOrNtabClickModel.ModelNameType](
        name = "frigate_push_modeling_oonc_ranking_model_id",
        default = WeightedOpenOrNtabClickModel.Periodically_Refreshed_Prod_Model
      )

  /**
   * Param to disable heavy ranker
   */
  object DisableHeavyRankingModelFSParam
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_disable_heavy_ranking",
        default = false
      )

  /**
   * Param to specify which weighted open or ntab click model to use for Android modelling etwitterperiment
   */
  object WeightedOpenOrNtabClickRankingModelForAndroidParam
      etwittertends FSParam[WeightedOpenOrNtabClickModel.ModelNameType](
        name = "frigate_push_modeling_oonc_ranking_model_for_android_id",
        default = WeightedOpenOrNtabClickModel.Periodically_Refreshed_Prod_Model
      )

  /**
   * Param to specify which weighted open or ntab click model to use for FILTERING
   */
  object WeightedOpenOrNtabClickFilteringModelParam
      etwittertends FSParam[WeightedOpenOrNtabClickModel.ModelNameType](
        name = "frigate_push_modeling_oonc_filtering_model_id",
        default = WeightedOpenOrNtabClickModel.Periodically_Refreshed_Prod_Model
      )

  /**
   * Param to specify which quality predicate to use for ML filtering
   */
  object QualityPredicateIdParam
      etwittertends FSEnumParam[QualityPredicateEnum.type](
        name = "frigate_push_modeling_quality_predicate_id",
        default = QualityPredicateEnum.WeightedOpenOrNtabClick,
        enum = QualityPredicateEnum
      )

  /**
   * Param to control threshold for any quality predicates using etwitterplicit thresholds
   */
  object QualityPredicateEtwitterplicitThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_modeling_quality_predicate_etwitterplicit_threshold",
        default = 0.1,
        min = 0,
        matwitter = 1)

  /**
   * MagicFanout relatwittered eventID fatigue interval (when we want to enable multiple updates for the same event)
   */
  object MagicFanoutRelatwitteredEventIdFatigueIntervalInHours
      etwittertends FSBoundedParam[Int](
        name = "frigate_push_magicfanout_relatwittered_event_id_fatigue_interval_in_hours",
        default = 24,
        min = 0,
        matwitter = 720
      )

  /**
   * MagicFanout DenyListed Countries
   */
  object MagicFanoutDenyListedCountries
      etwittertends FSParam[Seq[String]](
        "frigate_push_magicfanout_denylisted_countries",
        Seq.empty[String])

  object MagicFanoutSportsEventDenyListedCountries
      etwittertends FSParam[Seq[String]](
        "magicfanout_sports_event_denylisted_countries",
        Seq.empty[String])

  /**
   * MagicFanout matwitterimum erg rank for a given push event for non heavy users
   */
  object MagicFanoutRankErgThresholdNonHeavy
      etwittertends FSBoundedParam[Int](
        name = "frigate_push_magicfanout_erg_rank_threshold_non_heavy",
        default = 25,
        min = 1,
        matwitter = 50
      )

  /**
   * MagicFanout matwitterimum erg rank for a given push event for heavy users
   */
  object MagicFanoutRankErgThresholdHeavy
      etwittertends FSBoundedParam[Int](
        name = "frigate_push_magicfanout_erg_rank_threshold_heavy",
        default = 20,
        min = 1,
        matwitter = 50
      )

  object EnablePushMitwittererReplacingAllSources
      etwittertends FSParam[Boolean](
        name = "push_mitwitterer_enable_replacing_all_sources",
        default = false
      )

  object EnablePushMitwittererReplacingAllSourcesWithControl
      etwittertends FSParam[Boolean](
        name = "push_mitwitterer_enable_replacing_all_sources_with_control",
        default = false
      )

  object EnablePushMitwittererReplacingAllSourcesWithEtwittertra
      etwittertends FSParam[Boolean](
        name = "push_mitwitterer_enable_replacing_all_sources_with_etwittertra",
        default = false
      )

  object EnablePushMitwittererSource
      etwittertends FSParam[Boolean](
        name = "push_mitwitterer_enable_source",
        default = false
      )

  object PushMitwittererMatwitterResults
      etwittertends FSBoundedParam[Int](
        name = "push_mitwitterer_matwitter_results",
        default = 10,
        min = 1,
        matwitter = 5000
      )

  /**
   * Enable tweets from trends that have been annotated by curators
   */
  object EnableCuratedTrendTweets
      etwittertends FSParam[Boolean](name = "trend_tweet_curated_trends_enable", default = false)

  /**
   * Enable tweets from trends that haven't been annotated by curators
   */
  object EnableNonCuratedTrendTweets
      etwittertends FSParam[Boolean](name = "trend_tweet_non_curated_trends_enable", default = false)

  /**
   * Matwitterimum trend tweet notifications in fitwittered duration
   */
  object MatwitterTrendTweetNotificationsInDuration
      etwittertends FSBoundedParam[Int](
        name = "trend_tweet_matwitter_notifications_in_duration",
        min = 0,
        default = 0,
        matwitter = 20)

  /**
   * Duration in days over which trend tweet notifications fatigue is applied
   */
  object TrendTweetNotificationsFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "trend_tweet_notifications_fatigue_in_days",
        default = 1.day,
        min = Duration.Bottom,
        matwitter = Duration.Top
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Matwitterimum number of trends candidates to query from event-recos endpoint
   */
  object MatwitterRecommendedTrendsToQuery
      etwittertends FSBoundedParam[Int](
        name = "trend_tweet_matwitter_trends_to_query",
        min = 0,
        default = 0,
        matwitter = 100)

  /**
   * Fitwitter missing event-associated interests in MagicFanoutNoOptoutInterestsPredicate
   */
  object MagicFanoutFitwitterNoOptoutInterestsBugParam
      etwittertends FSParam[Boolean]("frigate_push_magicfanout_fitwitter_no_optout_interests", default = true)

  object EnableSimclusterOfflineAggFeatureForEtwitterpt
      etwittertends FSParam[Boolean]("frigate_enable_simcluster_offline_agg_feature", false)

  /**
   * Param to enable removal of UTT domain for
   */
  object ApplyMagicFanoutBroadEntityInterestRankThresholdPredicate
      etwittertends FSParam[Boolean](
        "frigate_push_magicfanout_broad_entity_interest_rank_threshold_predicate",
        false
      )

  object HydrateEventReasonsFeatures
      etwittertends FSParam[Boolean](
        name = "frigate_push_magicfanout_hydrate_event_reasons_features",
        false
      )

  /**
   * Param to enable online MR history features
   */
  object EnableHydratingOnlineMRHistoryFeatures
      etwittertends FSParam[Boolean](
        name = "feature_hydration_online_mr_history",
        default = false
      )

  /**
   * Param to enable bold title on favorite and retweet push copy for Android in DDG 10220
   */
  object MRBoldTitleFavoriteAndRetweetParam
      etwittertends FSEnumParam[MRBoldTitleFavoriteAndRetweetEtwitterperimentEnum.type](
        name = "frigate_push_bold_title_favorite_and_retweet_id",
        default = MRBoldTitleFavoriteAndRetweetEtwitterperimentEnum.ShortTitle,
        enum = MRBoldTitleFavoriteAndRetweetEtwitterperimentEnum
      )

  /**
   * Param to enable high priority push
   */
  object EnableHighPriorityPush
      etwittertends FSParam[Boolean]("frigate_push_magicfanout_enable_high_priority_push", false)

  /**
   * Param to redirect sports crt event to a custom url
   */
  object EnableSearchURLRedirectForSportsFanout
      etwittertends FSParam[Boolean]("magicfanout_sports_event_enable_search_url_redirect", false)

  /**
   * Param to enable score fanout notification for sports
   */
  object EnableScoreFanoutNotification
      etwittertends FSParam[Boolean]("magicfanout_sports_event_enable_score_fanout", false)

  /**
   * Param to add custom search url for sports crt event
   */
  object SearchURLRedirectForSportsFanout
      etwittertends FSParam[String](
        name = "magicfanout_sports_event_search_url_redirect",
        default = "https://twitter.com/etwitterplore/tabs/ipl",
      )

  /**
   * Param to enable high priority sports push
   */
  object EnableHighPrioritySportsPush
      etwittertends FSParam[Boolean]("magicfanout_sports_event_enable_high_priority_push", false)

  /**
   * Param to control rank threshold for magicfanout user follow
   */
  object MagicFanoutRealgraphRankThreshold
      etwittertends FSBoundedParam[Int](
        name = "magicfanout_realgraph_threshold",
        default = 500,
        matwitter = 500,
        min = 100
      )

  /**
   * Topic score threshold for topic proof tweet candidates topic annotations
   * */
  object TopicProofTweetCandidatesTopicScoreThreshold
      etwittertends FSBoundedParam[Double](
        name = "topics_as_social_proof_topic_score_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  /**
   * Enable Topic Proof Tweet Recs
   */
  object EnableTopicProofTweetRecs
      etwittertends FSParam[Boolean](name = "topics_as_social_proof_enable", default = true)

  /**
   * Enable health filters for topic tweet notifications
   */
  object EnableHealthFiltersForTopicProofTweet
      etwittertends FSParam[Boolean](
        name = "topics_as_social_proof_enable_health_filters",
        default = false)

  /**
   * Disable health filters for CrMitwitterer candidates
   */
  object DisableHealthFiltersForCrMitwittererCandidates
      etwittertends FSParam[Boolean](
        name = "health_and_quality_filter_disable_for_crmitwitterer_candidates",
        default = false)

  object EnableMagicFanoutNewsForYouNtabCopy
      etwittertends FSParam[Boolean](name = "send_handler_enable_nfy_ntab_copy", default = false)

  /**
   * Param to enable semi-personalized high quality candidates in pushservice
   * */
  object HighQualityCandidatesEnableCandidateSource
      etwittertends FSParam[Boolean](
        name = "high_quality_candidates_enable_candidate_source",
        default = false
      )

  /**
   * Param to decide semi-personalized high quality candidates
   * */
  object HighQualityCandidatesEnableGroups
      etwittertends FSEnumSeqParam[HighQualityCandidateGroupEnum.type](
        name = "high_quality_candidates_enable_groups_ids",
        default = Seq(AgeBucket, Language),
        enum = HighQualityCandidateGroupEnum
      )

  /**
   * Param to decide semi-personalized high quality candidates
   * */
  object HighQualityCandidatesNumberOfCandidates
      etwittertends FSBoundedParam[Int](
        name = "high_quality_candidates_number_of_candidates",
        default = 0,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param to enable small domain falling back to bigger domains for high quality candidates in pushservice
   * */
  object HighQualityCandidatesEnableFallback
      etwittertends FSParam[Boolean](
        name = "high_quality_candidates_enable_fallback",
        default = false
      )

  /**
   * Param to decide whether to fallback to bigger domain for high quality candidates
   * */
  object HighQualityCandidatesMinNumOfCandidatesToFallback
      etwittertends FSBoundedParam[Int](
        name = "high_quality_candidates_min_num_of_candidates_to_fallback",
        default = 50,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param to specific source ids for high quality candidates
   * */
  object HighQualityCandidatesFallbackSourceIds
      etwittertends FSParam[Seq[String]](
        name = "high_quality_candidates_fallback_source_ids",
        default = Seq("HQ_C_COUNT_PASS_QUALITY_SCORES"))

  /**
   * Param to decide groups for semi-personalized high quality candidates
   * */
  object HighQualityCandidatesFallbackEnabledGroups
      etwittertends FSEnumSeqParam[HighQualityCandidateGroupEnum.type](
        name = "high_quality_candidates_fallback_enabled_groups_ids",
        default = Seq(Country),
        enum = HighQualityCandidateGroupEnum
      )

  /**
   * Param to control what heavy ranker model to use for scribing scores
   */
  object HighQualityCandidatesHeavyRankingModel
      etwittertends FSParam[String](
        name = "high_quality_candidates_heavy_ranking_model",
        default = "Periodically_Refreshed_Prod_Model_V11"
      )

  /**
   * Param to control what non personalized quality "Cnn" model to use for scribing scores
   */
  object HighQualityCandidatesNonPersonalizedQualityCnnModel
      etwittertends FSParam[String](
        name = "high_quality_candidates_non_personalized_quality_cnn_model",
        default = "Q1_2023_Mr_Tf_Quality_Model_cnn"
      )

  /**
   * Param to control what nsfw health model to use for scribing scores
   */
  object HighQualityCandidatesBqmlNsfwModel
      etwittertends FSParam[String](
        name = "high_quality_candidates_bqml_nsfw_model",
        default = "Q2_2022_Mr_Bqml_Health_Model_NsfwV0"
      )

  /**
   * Param to control what reportodel to use for scribing scores
   */
  object HighQualityCandidatesBqmlReportModel
      etwittertends FSParam[String](
        name = "high_quality_candidates_bqml_report_model",
        default = "Q3_2022_15266_Mr_Bqml_Non_Personalized_Report_Model_with_Media_Embeddings"
      )

  /**
   * Param to specify the threshold to determine if a tweet contains nudity media
   */
  object TweetMediaSensitiveCategoryThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "tweet_media_sensitive_category_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to boost candidates from subscription creators
   */
  object BoostCandidatesFromSubscriptionCreators
      etwittertends FSParam[Boolean](
        name = "subscription_enable_boost_candidates_from_active_creators",
        default = false
      )

  /**
   * Param to soft rank candidates from subscription creators
   */
  object SoftRankCandidatesFromSubscriptionCreators
      etwittertends FSParam[Boolean](
        name = "subscription_enable_soft_rank_candidates_from_active_creators",
        default = false
      )

  /**
   * Param as factor to control how much we want to boost creator tweets
   */
  object SoftRankFactorForSubscriptionCreators
      etwittertends FSBoundedParam[Double](
        name = "subscription_soft_rank_factor_for_boost",
        default = 1.0,
        min = 0.0,
        matwitter = Double.MatwitterValue
      )

  /**
   * Param to enable new OON copy for Push Notifications
   */
  object EnableNewMROONCopyForPush
      etwittertends FSParam[Boolean](
        name = "mr_copy_enable_new_mr_oon_copy_push",
        default = true
      )

  /**
   * Param to enable generated inline actions on OON Notifications
   */
  object EnableOONGeneratedInlineActions
      etwittertends FSParam[Boolean](
        name = "mr_inline_enable_oon_generated_actions",
        default = false
      )

  /**
   * Param to control dynamic inline actions for Out-of-Network copies
   */
  object OONTweetDynamicInlineActionsList
      etwittertends FSEnumSeqParam[InlineActionsEnum.type](
        name = "mr_inline_oon_tweet_dynamic_action_ids",
        default = Seq(Follow, Retweet, Favorite),
        enum = InlineActionsEnum
      )

  object HighOONCTweetFormat
      etwittertends FSEnumParam[IbisTemplateFormatEnum.type](
        name = "mr_copy_high_oonc_format_id",
        default = IbisTemplateFormatEnum.template1,
        enum = IbisTemplateFormatEnum
      )

  object LowOONCTweetFormat
      etwittertends FSEnumParam[IbisTemplateFormatEnum.type](
        name = "mr_copy_low_oonc_format_id",
        default = IbisTemplateFormatEnum.template1,
        enum = IbisTemplateFormatEnum
      )

  /**
   * Param to enable dynamic inline actions based on FSParams for Tweet copies (not OON)
   */
  object EnableTweetDynamicInlineActions
      etwittertends FSParam[Boolean](
        name = "mr_inline_enable_tweet_dynamic_actions",
        default = false
      )

  /**
   * Param to control dynamic inline actions for Tweet copies (not OON)
   */
  object TweetDynamicInlineActionsList
      etwittertends FSEnumSeqParam[InlineActionsEnum.type](
        name = "mr_inline_tweet_dynamic_action_ids",
        default = Seq(Reply, Retweet, Favorite),
        enum = InlineActionsEnum
      )

  object UseInlineActionsV1
      etwittertends FSParam[Boolean](
        name = "mr_inline_use_inline_action_v1",
        default = true
      )

  object UseInlineActionsV2
      etwittertends FSParam[Boolean](
        name = "mr_inline_use_inline_action_v2",
        default = false
      )

  object EnableInlineFeedbackOnPush
      etwittertends FSParam[Boolean](
        name = "mr_inline_enable_inline_feedback_on_push",
        default = false
      )

  object InlineFeedbackSubstitutePosition
      etwittertends FSBoundedParam[Int](
        name = "mr_inline_feedback_substitute_position",
        min = 0,
        matwitter = 2,
        default = 2, // default to substitute or append last inline action
      )

  /**
   * Param to control dynamic inline actions for web notifications
   */
  object EnableDynamicInlineActionsForDesktopWeb
      etwittertends FSParam[Boolean](
        name = "mr_inline_enable_dynamic_actions_for_desktop_web",
        default = false
      )

  object EnableDynamicInlineActionsForMobileWeb
      etwittertends FSParam[Boolean](
        name = "mr_inline_enable_dynamic_actions_for_mobile_web",
        default = false
      )

  /**
   * Param to define dynamic inline action types for web notifications (both desktop web + mobile web)
   */
  object TweetDynamicInlineActionsListForWeb
      etwittertends FSEnumSeqParam[InlineActionsEnum.type](
        name = "mr_inline_tweet_dynamic_action_for_web_ids",
        default = Seq(Retweet, Favorite),
        enum = InlineActionsEnum
      )

  /**
   * Param to enable MR Override Notifications for Android
   */
  object EnableOverrideNotificationsForAndroid
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_override_notifications_for_android",
        default = false
      )

  /**
   * Param to enable MR Override Notifications for iOS
   */
  object EnableOverrideNotificationsForIos
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_override_notifications_for_ios",
        default = false
      )

  /**
   * Param to enable gradually ramp up notification
   */
  object EnableGraduallyRampUpNotification
      etwittertends FSParam[Boolean](
        name = "pushcap_gradually_ramp_up_enable",
        default = false
      )

  /**
   * Param to control the minInrerval for fatigue between consecutive MFNFY pushes
   */
  object MFMinIntervalFatigue
      etwittertends FSBoundedParam[Duration](
        name = "frigate_push_magicfanout_fatigue_min_interval_consecutive_pushes_minutes",
        default = 240.minutes,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromMinutes
  }

  /**
   * Param to control the interval for MFNFY pushes
   */
  object MFPushIntervalInHours
      etwittertends FSBoundedParam[Duration](
        name = "frigate_push_magicfanout_fatigue_push_interval_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of Sports MF pushes in a period of time
   */
  object SportsMatwitterNumberOfPushesInInterval
      etwittertends FSBoundedParam[Int](
        name = "magicfanout_sports_event_fatigue_matwitter_pushes_in_interval",
        default = 2,
        min = 0,
        matwitter = 6)

  /**
   * Param to control the minInterval for fatigue between consecutive sports pushes
   */
  object SportsMinIntervalFatigue
      etwittertends FSBoundedParam[Duration](
        name = "magicfanout_sports_event_fatigue_min_interval_consecutive_pushes_minutes",
        default = 240.minutes,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromMinutes
  }

  /**
   * Param to control the interval for sports pushes
   */
  object SportsPushIntervalInHours
      etwittertends FSBoundedParam[Duration](
        name = "magicfanout_sports_event_fatigue_push_interval_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of same event sports MF pushes in a period of time
   */
  object SportsMatwitterNumberOfPushesInIntervalPerEvent
      etwittertends FSBoundedParam[Int](
        name = "magicfanout_sports_event_fatigue_matwitter_pushes_in_per_event_interval",
        default = 2,
        min = 0,
        matwitter = 6)

  /**
   * Param to control the minInterval for fatigue between consecutive same event sports pushes
   */
  object SportsMinIntervalFatiguePerEvent
      etwittertends FSBoundedParam[Duration](
        name = "magicfanout_sports_event_fatigue_min_interval_consecutive_pushes_per_event_minutes",
        default = 240.minutes,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromMinutes
  }

  /**
   * Param to control the interval for same event sports pushes
   */
  object SportsPushIntervalInHoursPerEvent
      etwittertends FSBoundedParam[Duration](
        name = "magicfanout_sports_event_fatigue_push_interval_per_event_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of MF pushes in a period of time
   */
  object MFMatwitterNumberOfPushesInInterval
      etwittertends FSBoundedParam[Int](
        name = "frigate_push_magicfanout_fatigue_matwitter_pushes_in_interval",
        default = 2,
        min = 0,
        matwitter = 6)

  /**
   * Param to enable custom duration for fatiguing
   */
  object GPEnableCustomMagicFanoutCricketFatigue
      etwittertends FSParam[Boolean](
        name = "global_participation_cricket_magicfanout_enable_custom_fatigue",
        default = false
      )

  /**
   * Param to enable e2e scribing for target filtering step
   */
  object EnableMrRequestScribingForTargetFiltering
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_enable_for_target_filtering",
        default = false
      )

  /**
   * Param to enable e2e scribing for candidate filtering step
   */
  object EnableMrRequestScribingForCandidateFiltering
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_enable_for_candidate_filtering",
        default = false
      )

  /**
   * Param to enable e2e scribing with feature hydrating
   */
  object EnableMrRequestScribingWithFeatureHydrating
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_enable_with_feature_hydrating",
        default = false
      )

  /*
   * TargetLevel Feature list for Mr request scribing
   */
  object TargetLevelFeatureListForMrRequestScribing
      etwittertends FSParam[Seq[String]](
        name = "mr_request_scribing_target_level_feature_list",
        default = Seq.empty
      )

  /**
   * Param to enable \eps-greedy etwitterploration for BigFiltering/LTV-based filtering
   */
  object EnableMrRequestScribingForEpsGreedyEtwitterploration
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_eps_greedy_etwitterploration_enable",
        default = false
      )

  /**
   * Param to control epsilon in \eps-greedy etwitterploration for BigFiltering/LTV-based filtering
   */
  object MrRequestScribingEpsGreedyEtwitterplorationRatio
      etwittertends FSBoundedParam[Double](
        name = "mr_request_scribing_eps_greedy_etwitterploration_ratio",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to enable scribing dismiss model score
   */
  object EnableMrRequestScribingDismissScore
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_dismiss_score_enable",
        default = false
      )

  /**
   * Param to enable scribing BigFiltering supervised model(s) score(s)
   */
  object EnableMrRequestScribingBigFilteringSupervisedScores
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_bigfiltering_supervised_scores_enable",
        default = false
      )

  /**
   * Param to enable scribing BigFiltering RL model(s) score(s)
   */
  object EnableMrRequestScribingBigFilteringRLScores
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_bigfiltering_rl_scores_enable",
        default = false
      )

  /**
   * Param to flatten mr request scribe
   */
  object EnableFlattenMrRequestScribing
      etwittertends FSParam[Boolean](
        name = "mr_request_scribing_enable_flatten",
        default = false
      )

  /**
   * Param to enable NSFW token based filtering
   */
  object EnableNsfwTokenBasedFiltering
      etwittertends FSParam[Boolean](
        name = "health_and_quality_filter_enable_nsfw_token_based_filtering",
        default = false
      )

  object NsfwTokensParam
      etwittertends FSParam[Seq[String]](
        name = "health_and_quality_filter_nsfw_tokens",
        default = Seq("nsfw", "18+", "\uD83D\uDD1E"))

  object MinimumAllowedAuthorAccountAgeInHours
      etwittertends FSBoundedParam[Int](
        name = "health_and_quality_filter_minimum_allowed_author_account_age_in_hours",
        default = 0,
        min = 0,
        matwitter = 168
      )

  /**
   * Param to enable the profanity filter
   */
  object EnableProfanityFilterParam
      etwittertends FSParam[Boolean](
        name = "health_and_quality_filter_enable_profanity_filter",
        default = false
      )

  /**
   * Param to enable query the author media representation store
   */
  object EnableQueryAuthorMediaRepresentationStore
      etwittertends FSParam[Boolean](
        name = "health_and_quality_filter_enable_query_author_media_representation_store",
        default = false
      )

  /**
   * Threshold to filter a tweet based on the author sensitive media score
   */
  object AuthorSensitiveMediaFilteringThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_and_quality_filter_author_sensitive_media_filtering_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to filter a tweet based on the author sensitive media score
   */
  object AuthorSensitiveMediaFilteringThresholdForMrTwistly
      etwittertends FSBoundedParam[Double](
        name = "health_and_quality_filter_author_sensitive_media_filtering_threshold_for_mrtwistly",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to enable filtering the SimCluster tweet if it has AbuseStrike_Top2Percent entitiy
   */
  object EnableAbuseStrikeTop2PercentFilterSimCluster
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_abuse_strike_top_2_percent_filter_sim_cluster",
        default = false
      )

  /**
   * Param to enable filtering the SimCluster tweet if it has AbuseStrike_Top1Percent entitiy
   */
  object EnableAbuseStrikeTop1PercentFilterSimCluster
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_abuse_strike_top_1_percent_filter_sim_cluster",
        default = false
      )

  /**
   * Param to enable filtering the SimCluster tweet if it has AbuseStrike_Top0.5Percent entitiy
   */
  object EnableAbuseStrikeTop05PercentFilterSimCluster
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_abuse_strike_top_05_percent_filter_sim_cluster",
        default = false
      )

  object EnableAgathaUserHealthModelPredicate
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_agatha_user_health_model_predicate",
        default = false
      )

  /**
   * Threshold to filter a tweet based on the agatha_calibrated_nsfw score of its author for MrTwistly
   */
  object AgathaCalibratedNSFWThresholdForMrTwistly
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_calibrated_nsfw_threshold_for_mrtwistly",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to filter a tweet based on the agatha_calibrated_nsfw score of its author
   */
  object AgathaCalibratedNSFWThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_calibrated_nsfw_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to filter a tweet based on the agatha_nsfw_tetwittert_user score of its author for MrTwistly
   */
  object AgathaTetwittertNSFWThresholdForMrTwistly
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_tetwittert_nsfw_threshold_for_mrtwistly",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to filter a tweet based on the agatha_nsfw_tetwittert_user score of its author
   */
  object AgathaTetwittertNSFWThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_tetwittert_nsfw_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to bucket a user based on the agatha_calibrated_nsfw score of the tweet author
   */
  object AgathaCalibratedNSFWBucketThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_calibrated_nsfw_bucket_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold to bucket a user based on the agatha_nsfw_tetwittert_user score of the tweet author
   */
  object AgathaTetwittertNSFWBucketThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_agatha_tetwittert_nsfw_bucket_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to enable filtering using pnsfw_tetwittert_tweet model.
   */
  object EnableHealthSignalStorePnsfwTweetTetwittertPredicate
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_pnsfw_tweet_tetwittert_predicate",
        default = false
      )

  /**
   * Threshold score for filtering based on pnsfw_tetwittert_tweet Model.
   */
  object PnsfwTweetTetwittertThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_tweet_tetwittert_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold score for bucketing based on pnsfw_tetwittert_tweet Model.
   */
  object PnsfwTweetTetwittertBucketingThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_tweet_tetwittert_bucketing_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Enable filtering tweets with media based on pnsfw_media_tweet Model for OON tweets only.
   */
  object PnsfwTweetMediaFilterOonOnly
      etwittertends FSParam[Boolean](
        name = "health_signal_store_pnsfw_tweet_media_filter_oon_only",
        default = true
      )

  /**
   * Threshold score for filtering tweets with media based on pnsfw_media_tweet Model.
   */
  object PnsfwTweetMediaThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_tweet_media_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold score for filtering tweets with images based on pnsfw_media_tweet Model.
   */
  object PnsfwTweetImageThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_tweet_image_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold score for filtering quote/reply tweets based on source tweet's media
   */
  object PnsfwQuoteTweetThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_quote_tweet_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold score for bucketing based on pnsfw_media_tweet Model.
   */
  object PnsfwTweetMediaBucketingThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_pnsfw_tweet_media_bucketing_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to enable filtering using multilingual psnfw predicate
   */
  object EnableHealthSignalStoreMultilingualPnsfwTweetTetwittertPredicate
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_multilingual_pnsfw_tweet_tetwittert_predicate",
        default = false
      )

  /**
   * Language sequence we will query pnsfw scores for
   */
  object MultilingualPnsfwTweetTetwittertSupportedLanguages
      etwittertends FSParam[Seq[String]](
        name = "health_signal_store_multilingual_pnsfw_tweet_supported_languages",
        default = Seq.empty[String],
      )

  /**
   * Threshold score per language for bucketing based on pnsfw scores.
   */
  object MultilingualPnsfwTweetTetwittertBucketingThreshold
      etwittertends FSParam[Seq[Double]](
        name = "health_signal_store_multilingual_pnsfw_tweet_tetwittert_bucketing_thresholds",
        default = Seq.empty[Double],
      )

  /**
   * Threshold score per language for filtering based on pnsfw scores.
   */
  object MultilingualPnsfwTweetTetwittertFilteringThreshold
      etwittertends FSParam[Seq[Double]](
        name = "health_signal_store_multilingual_pnsfw_tweet_tetwittert_filtering_thresholds",
        default = Seq.empty[Double],
      )

  /**
   * List of models to threshold scores for bucketing purposes
   */
  object MultilingualPnsfwTweetTetwittertBucketingModelList
      etwittertends FSEnumSeqParam[NsfwTetwittertDetectionModel.type](
        name = "health_signal_store_multilingual_pnsfw_tweet_tetwittert_bucketing_models_ids",
        default = Seq(NsfwTetwittertDetectionModel.ProdModel),
        enum = NsfwTetwittertDetectionModel
      )

  object MultilingualPnsfwTweetTetwittertModel
      etwittertends FSEnumParam[NsfwTetwittertDetectionModel.type](
        name = "health_signal_store_multilingual_pnsfw_tweet_tetwittert_model",
        default = NsfwTetwittertDetectionModel.ProdModel,
        enum = NsfwTetwittertDetectionModel
      )

  /**
   * Param to determine media should be enabled for android
   */
  object EnableEventSquareMediaAndroid
      etwittertends FSParam[Boolean](
        name = "mr_enable_event_media_square_android",
        default = false
      )

  /**
   * Param to determine etwitterpanded media should be enabled for android
   */
  object EnableEventPrimaryMediaAndroid
      etwittertends FSParam[Boolean](
        name = "mr_enable_event_media_primary_android",
        default = false
      )

  /**
   * Param to determine media should be enabled for ios for MagicFanout
   */
  object EnableEventSquareMediaIosMagicFanoutNewsEvent
      etwittertends FSParam[Boolean](
        name = "mr_enable_event_media_square_ios_mf",
        default = false
      )

  /**
   * Param to configure HTL Visit fatigue
   */
  object HTLVisitFatigueTime
      etwittertends FSBoundedParam[Int](
        name = "frigate_push_htl_visit_fatigue_time",
        default = 20,
        min = 0,
        matwitter = 72) {

    // Fatigue duration for HTL visit
    final val DefaultHoursToFatigueAfterHtlVisit = 20
    final val OldHoursToFatigueAfterHtlVisit = 8
  }

  object MagicFanoutNewsUserGeneratedEventsEnable
      etwittertends FSParam[Boolean](
        name = "magicfanout_news_user_generated_events_enable",
        default = false)

  object MagicFanoutSkipAccountCountryPredicate
      etwittertends FSParam[Boolean]("magicfanout_news_skip_account_country_predicate", false)

  object MagicFanoutNewsEnableDescriptionCopy
      etwittertends FSParam[Boolean](name = "magicfanout_news_enable_description_copy", default = false)

  /**
   *  Enables Custom Targeting for MagicFnaout News events in Pushservice
   */
  object MagicFanoutEnableCustomTargetingNewsEvent
      etwittertends FSParam[Boolean]("magicfanout_news_event_custom_targeting_enable", false)

  /**
   * Enable Topic Copy in MF
   */
  object EnableTopicCopyForMF
      etwittertends FSParam[Boolean](
        name = "magicfanout_enable_topic_copy",
        default = false
      )

  /**
   * Enable Topic Copy in MF for implicit topics
   */
  object EnableTopicCopyForImplicitTopics
      etwittertends FSParam[Boolean](
        name = "magicfanout_enable_topic_copy_erg_interests",
        default = false
      )

  /**
   * Enable NewCreator push
   */
  object EnableNewCreatorPush
      etwittertends FSParam[Boolean](
        name = "new_creator_enable_push",
        default = false
      )

  /**
   * Enable CreatorSubscription push
   */
  object EnableCreatorSubscriptionPush
      etwittertends FSParam[Boolean](
        name = "creator_subscription_enable_push",
        default = false
      )

  /**
   * Featureswitch param to enable/disable push recommendations
   */
  object EnablePushRecommendationsParam
      etwittertends FSParam[Boolean](name = "push_recommendations_enabled", default = false)

  object DisableMlInFilteringFeatureSwitchParam
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_disable_ml_in_filtering",
        default = false
      )

  object EnableMinDurationModifier
      etwittertends FSParam[Boolean](
        name = "min_duration_modifier_enable_hour_modifier",
        default = false
      )

  object EnableMinDurationModifierV2
      etwittertends FSParam[Boolean](
        name = "min_duration_modifier_enable_hour_modifier_v2",
        default = false
      )

  object MinDurationModifierStartHourList
      etwittertends FSParam[Seq[Int]](
        name = "min_duration_modifier_start_time_list",
        default = Seq(),
      )

  object MinDurationModifierEndHourList
      etwittertends FSParam[Seq[Int]](
        name = "min_duration_modifier_start_end_list",
        default = Seq(),
      )

  object MinDurationTimeModifierConst
      etwittertends FSParam[Seq[Int]](
        name = "min_duration_modifier_const_list",
        default = Seq(),
      )

  object EnableQueryUserOpenedHistory
      etwittertends FSParam[Boolean](
        name = "min_duration_modifier_enable_query_user_opened_history",
        default = false
      )

  object EnableMinDurationModifierByUserHistory
      etwittertends FSParam[Boolean](
        name = "min_duration_modifier_enable_hour_modifier_by_user_history",
        default = false
      )

  object EnableRandomHourForQuickSend
      etwittertends FSParam[Boolean](
        name = "min_duration_modifier_enable_random_hour_for_quick_send",
        default = false
      )

  object SendTimeByUserHistoryMatwitterOpenedThreshold
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_matwitter_opened_threshold",
        default = 4,
        min = 0,
        matwitter = 100)

  object SendTimeByUserHistoryNoSendsHours
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_no_sends_hours",
        default = 1,
        min = 0,
        matwitter = 24)

  object SendTimeByUserHistoryQuickSendBeforeHours
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_quick_send_before_hours",
        default = 0,
        min = 0,
        matwitter = 24)

  object SendTimeByUserHistoryQuickSendAfterHours
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_quick_send_after_hours",
        default = 0,
        min = 0,
        matwitter = 24)

  object SendTimeByUserHistoryQuickSendMinDurationInMinute
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_quick_send_min_duration",
        default = 0,
        min = 0,
        matwitter = 1440)

  object SendTimeByUserHistoryNoSendMinDuration
      etwittertends FSBoundedParam[Int](
        name = "min_duration_modifier_no_send_min_duration",
        default = 24,
        min = 0,
        matwitter = 24)

  object EnableMfGeoTargeting
      etwittertends FSParam[Boolean](
        name = "frigate_push_magicfanout_geo_targeting_enable",
        default = false)

  /**
   * Enable RUtwitter Tweet landing page for push open. When this param is enabled, user will go to RUtwitter
   * landing page instead of Tweet details page when opening MagicRecs push.
   */
  object EnableRutwitterLandingPage
      etwittertends FSParam[Boolean](name = "frigate_push_enable_rutwitter_landing_page", default = false)

  /**
   * Enable RUtwitter Tweet landing page for Ntab Click. When this param is enabled, user will go to RUtwitter
   * landing page instead of Tweet details page when click MagicRecs entry on Ntab.
   */
  object EnableNTabRutwitterLandingPage
      etwittertends FSParam[Boolean](name = "frigate_push_enable_ntab_rutwitter_landing_page", default = false)

  /**
   * Param to enable Onboarding Pushes
   */
  object EnableOnboardingPushes
      etwittertends FSParam[Boolean](
        name = "onboarding_push_enable",
        default = false
      )

  /**
   * Param to enable Address Book Pushes
   */
  object EnableAddressBookPush
      etwittertends FSParam[Boolean](
        name = "onboarding_push_enable_address_book_push",
        default = false
      )

  /**
   * Param to enable Complete Onboarding Pushes
   */
  object EnableCompleteOnboardingPush
      etwittertends FSParam[Boolean](
        name = "onboarding_push_enable_complete_onboarding_push",
        default = false
      )

  /**
   * Param to enable Smart Push Config for MR Override Notifs on Android
   */
  object EnableOverrideNotificationsSmartPushConfigForAndroid
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_smart_push_config_for_android",
        default = false)

  /**
   * Param to control the min duration since last MR push for Onboarding Pushes
   */
  object MrMinDurationSincePushForOnboardingPushes
      etwittertends FSBoundedParam[Duration](
        name = "onboarding_push_min_duration_since_push_days",
        default = 4.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to control the push fatigue for Onboarding Pushes
   */
  object FatigueForOnboardingPushes
      etwittertends FSBoundedParam[Duration](
        name = "onboarding_push_fatigue_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to specify the matwitterimum number of Onboarding Push Notifs in a specified period of time
   */
  object MatwitterOnboardingPushInInterval
      etwittertends FSBoundedParam[Int](
        name = "onboarding_push_matwitter_in_interval",
        default = 1,
        min = 0,
        matwitter = 10
      )

  /**
   * Param to disable the Onboarding Push Notif Fatigue
   */
  object DisableOnboardingPushFatigue
      etwittertends FSParam[Boolean](
        name = "onboarding_push_disable_push_fatigue",
        default = false
      )

  /**
   * Param to control the inverter for fatigue between consecutive TopTweetsByGeoPush
   */
  object TopTweetsByGeoPushInterval
      etwittertends FSBoundedParam[Duration](
        name = "top_tweets_by_geo_interval_days",
        default = 0.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to control the inverter for fatigue between consecutive TripTweets
   */
  object HighQualityTweetsPushInterval
      etwittertends FSBoundedParam[Duration](
        name = "high_quality_candidates_push_interval_days",
        default = 1.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Etwitterpiry TTL duration for Tweet Notification types written to history store
   */
  object FrigateHistoryTweetNotificationWriteTtl
      etwittertends FSBoundedParam[Duration](
        name = "frigate_notification_history_tweet_write_ttl_days",
        default = 60.days,
        min = Duration.Bottom,
        matwitter = Duration.Top
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Etwitterpiry TTL duration for Notification written to history store
   */
  object FrigateHistoryOtherNotificationWriteTtl
      etwittertends FSBoundedParam[Duration](
        name = "frigate_notification_history_other_write_ttl_days",
        default = 90.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to control matwitterimum number of TopTweetsByGeoPush pushes to receive in an interval
   */
  object MatwitterTopTweetsByGeoPushGivenInterval
      etwittertends FSBoundedParam[Int](
        name = "top_tweets_by_geo_push_given_interval",
        default = 1,
        min = 0,
        matwitter = 10
      )

  /**
   * Param to control matwitterimum number of HighQualityTweet pushes to receive in an interval
   */
  object MatwitterHighQualityTweetsPushGivenInterval
      etwittertends FSBoundedParam[Int](
        name = "high_quality_candidates_matwitter_push_given_interval",
        default = 3,
        min = 0,
        matwitter = 10
      )

  /**
   * Param to downrank/backfill top tweets by geo candidates
   */
  object BackfillRankTopTweetsByGeoCandidates
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_backfill_rank",
        default = false
      )

  /**
   * Determine whether to use aggressive thresholds for Health filtering on SearchTweet
   */
  object PopGeoTweetEnableAggressiveThresholds
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_enable_aggressive_health_thresholds",
        default = false
      )

  /**
   * Param to apply different scoring functions to select top tweets by geo candidates
   */
  object ScoringFuncForTopTweetsByGeo
      etwittertends FSParam[String](
        name = "top_tweets_by_geo_scoring_function",
        default = "Pop8H",
      )

  /**
   * Param to query different stores in pop geo service.
   */
  object TopTweetsByGeoCombinationParam
      etwittertends FSEnumParam[TopTweetsForGeoCombination.type](
        name = "top_tweets_by_geo_combination_id",
        default = TopTweetsForGeoCombination.Default,
        enum = TopTweetsForGeoCombination
      )

  /**
   * Param for popgeo tweet version
   */
  object PopGeoTweetVersionParam
      etwittertends FSEnumParam[PopGeoTweetVersion.type](
        name = "top_tweets_by_geo_version_id",
        default = PopGeoTweetVersion.Prod,
        enum = PopGeoTweetVersion
      )

  /**
   * Param to query what length of hash for geoh store
   */
  object GeoHashLengthList
      etwittertends FSParam[Seq[Int]](
        name = "top_tweets_by_geo_hash_length_list",
        default = Seq(4),
      )

  /**
   * Param to include country code results as back off .
   */
  object EnableCountryCodeBackoffTopTweetsByGeo
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_enable_country_code_backoff",
        default = false,
      )

  /**
   * Param to decide ranking function for fetched top tweets by geo
   */
  object RankingFunctionForTopTweetsByGeo
      etwittertends FSEnumParam[TopTweetsForGeoRankingFunction.type](
        name = "top_tweets_by_geo_ranking_function_id",
        default = TopTweetsForGeoRankingFunction.Score,
        enum = TopTweetsForGeoRankingFunction
      )

  /**
   * Param to enable top tweets by geo candidates
   */
  object EnableTopTweetsByGeoCandidates
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_enable_candidate_source",
        default = false
      )

  /**
   * Param to enable top tweets by geo candidates for dormant users
   */
  object EnableTopTweetsByGeoCandidatesForDormantUsers
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_enable_candidate_source_dormant_users",
        default = false
      )

  /**
   * Param to specify the matwitterimum number of Top Tweets by Geo candidates to take
   */
  object MatwitterTopTweetsByGeoCandidatesToTake
      etwittertends FSBoundedParam[Int](
        name = "top_tweets_by_geo_candidates_to_take",
        default = 10,
        min = 0,
        matwitter = 100
      )

  /**
   * Param to min duration since last MR push for top tweets by geo pushes
   */
  object MrMinDurationSincePushForTopTweetsByGeoPushes
      etwittertends FSBoundedParam[Duration](
        name = "top_tweets_by_geo_min_duration_since_last_mr_days",
        default = 3.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to enable FRS candidate tweets
   */
  object EnableFrsCandidates
      etwittertends FSParam[Boolean](
        name = "frs_tweet_candidate_enable_adaptor",
        default = false
      )

  /**
   * Param to enable FRSTweet candidates for topic setting users
   * */
  object EnableFrsTweetCandidatesTopicSetting
      etwittertends FSParam[Boolean](
        name = "frs_tweet_candidate_enable_adaptor_for_topic_setting",
        default = false
      )

  /**
   * Param to enable topic annotations for FRSTweet candidates tweets
   * */
  object EnableFrsTweetCandidatesTopicAnnotation
      etwittertends FSParam[Boolean](
        name = "frs_tweet_candidate_enable_topic_annotation",
        default = false
      )

  /**
   * Param to enable topic copy for FRSTweet candidates tweets
   * */
  object EnableFrsTweetCandidatesTopicCopy
      etwittertends FSParam[Boolean](
        name = "frs_tweet_candidate_enable_topic_copy",
        default = false
      )

  /**
   * Topic score threshold for FRSTweet candidates topic annotations
   * */
  object FrsTweetCandidatesTopicScoreThreshold
      etwittertends FSBoundedParam[Double](
        name = "frs_tweet_candidate_topic_score_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  /**
   * Param to enable mr modeling-based candidates tweets
   * */
  object EnableMrModelingBasedCandidates
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable_adaptor",
        default = false
      )

  /**
   Param to enable mr modeling-based candidates tweets for topic setting users
   * */
  object EnableMrModelingBasedCandidatesTopicSetting
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable_adaptor_for_topic_setting",
        default = false
      )

  /**
   * Param to enable topic annotations for mr modeling-based candidates tweets
   * */
  object EnableMrModelingBasedCandidatesTopicAnnotation
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable_adaptor_topic_annotation",
        default = false
      )

  /**
   * Topic score threshold for mr modeling based candidates topic annotations
   * */
  object MrModelingBasedCandidatesTopicScoreThreshold
      etwittertends FSBoundedParam[Double](
        name = "candidate_generation_model_topic_score_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  /**
   * Param to enable topic copy for mr modeling-based candidates tweets
   * */
  object EnableMrModelingBasedCandidatesTopicCopy
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable_topic_copy",
        default = false
      )

  /**
   * Number of matwitter mr modeling based candidates
   * */
  object NumberOfMatwitterMrModelingBasedCandidates
      etwittertends FSBoundedParam[Int](
        name = "candidate_generation_model_matwitter_mr_modeling_based_candidates",
        default = 200,
        min = 0,
        matwitter = 1000
      )

  /**
   * Enable the traffic to use fav threshold
   * */
  object EnableThresholdOfFavMrModelingBasedCandidates
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_enable_fav_threshold",
        default = false
      )

  /**
   * Threshold of fav for mr modeling based candidates
   * */
  object ThresholdOfFavMrModelingBasedCandidates
      etwittertends FSBoundedParam[Int](
        name = "candidate_generation_model_fav_threshold",
        default = 0,
        min = 0,
        matwitter = 500
      )

  /**
   * Filtered threshold for mr modeling based candidates
   * */
  object CandidateGenerationModelCosineThreshold
      etwittertends FSBoundedParam[Double](
        name = "candidate_generation_model_cosine_threshold",
        default = 0.9,
        min = 0.0,
        matwitter = 1.0
      )

  /*
   * ANN hyparameters
   * */
  object ANNEfQuery
      etwittertends FSBoundedParam[Int](
        name = "candidate_generation_model_ann_ef_query",
        default = 300,
        min = 50,
        matwitter = 1500
      )

  /**
   * Param to do real A/B impression for FRS candidates to avoid dilution
   */
  object EnableResultFromFrsCandidates
      etwittertends FSParam[Boolean](
        name = "frs_tweet_candidate_enable_returned_result",
        default = false
      )

  /**
   * Param to enable hashspace candidate tweets
   */
  object EnableHashspaceCandidates
      etwittertends FSParam[Boolean](
        name = "hashspace_candidate_enable_adaptor",
        default = false
      )

  /**
   * Param to enable hashspace candidates tweets for topic setting users
   * */
  object EnableHashspaceCandidatesTopicSetting
      etwittertends FSParam[Boolean](
        name = "hashspace_candidate_enable_adaptor_for_topic_setting",
        default = false
      )

  /**
   * Param to enable topic annotations for hashspace candidates tweets
   * */
  object EnableHashspaceCandidatesTopicAnnotation
      etwittertends FSParam[Boolean](
        name = "hashspace_candidate_enable_topic_annotation",
        default = false
      )

  /**
   * Param to enable topic copy for hashspace candidates tweets
   * */
  object EnableHashspaceCandidatesTopicCopy
      etwittertends FSParam[Boolean](
        name = "hashspace_candidate_enable_topic_copy",
        default = false
      )

  /**
   * Topic score threshold for hashspace candidates topic annotations
   * */
  object HashspaceCandidatesTopicScoreThreshold
      etwittertends FSBoundedParam[Double](
        name = "hashspace_candidate_topic_score_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  /**
   * Param to do real A/B impression for hashspace candidates to avoid dilution
   */
  object EnableResultFromHashspaceCandidates
      etwittertends FSParam[Boolean](
        name = "hashspace_candidate_enable_returned_result",
        default = false
      )

  /**
   * Param to enable detopic tweet candidates in adaptor
   */
  object EnableDeTopicTweetCandidates
      etwittertends FSParam[Boolean](
        name = "detopic_tweet_candidate_enable_adaptor",
        default = false
      )

  /**
   * Param to enable detopic tweet candidates results (to avoid dilution)
   */
  object EnableDeTopicTweetCandidateResults
      etwittertends FSParam[Boolean](
        name = "detopic_tweet_candidate_enable_results",
        default = false
      )

  /**
   * Param to specify whether to provide a custom list of topics in request
   */
  object EnableDeTopicTweetCandidatesCustomTopics
      etwittertends FSParam[Boolean](
        name = "detopic_tweet_candidate_enable_custom_topics",
        default = false
      )

  /**
   * Param to specify whether to provide a custom language in request
   */
  object EnableDeTopicTweetCandidatesCustomLanguages
      etwittertends FSParam[Boolean](
        name = "detopic_tweet_candidate_enable_custom_languages",
        default = false
      )

  /**
   * Number of detopic tweet candidates in the request
   * */
  object NumberOfDeTopicTweetCandidates
      etwittertends FSBoundedParam[Int](
        name = "detopic_tweet_candidate_num_candidates_in_request",
        default = 600,
        min = 0,
        matwitter = 3000
      )

  /**
   * Matwitter Number of detopic tweet candidates returned in adaptor
   * */
  object NumberOfMatwitterDeTopicTweetCandidatesReturned
      etwittertends FSBoundedParam[Int](
        name = "detopic_tweet_candidate_matwitter_num_candidates_returned",
        default = 200,
        min = 0,
        matwitter = 3000
      )

  /**
   * Param to enable F1 from protected Authors
   */
  object EnableF1FromProtectedTweetAuthors
      etwittertends FSParam[Boolean](
        "f1_enable_protected_tweets",
        false
      )

  /**
   * Param to enable safe user tweet tweetypie store
   */
  object EnableSafeUserTweetTweetypieStore
      etwittertends FSParam[Boolean](
        "mr_infra_enable_use_safe_user_tweet_tweetypie",
        false
      )

  /**
   * Param to min duration since last MR push for top tweets by geo pushes
   */
  object EnableMrMinDurationSinceMrPushFatigue
      etwittertends FSParam[Boolean](
        name = "top_tweets_by_geo_enable_min_duration_since_mr_fatigue",
        default = false
      )

  /**
   * Param to check time since last time user logged in for geo top tweets by geo push
   */
  object TimeSinceLastLoginForGeoPopTweetPush
      etwittertends FSBoundedParam[Duration](
        name = "top_tweets_by_geo_time_since_last_login_in_days",
        default = 14.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to check time since last time user logged in for geo top tweets by geo push
   */
  object MinimumTimeSinceLastLoginForGeoPopTweetPush
      etwittertends FSBoundedParam[Duration](
        name = "top_tweets_by_geo_minimum_time_since_last_login_in_days",
        default = 14.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /** How long we wait after a user visited the app before sending them a space fanout rec */
  object SpaceRecsAppFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "space_recs_app_fatigue_duration_hours",
        default = 4.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /** The fatigue time-window for OON space fanout recs, e.g. 1 push every 3 days */
  object OONSpaceRecsFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "space_recs_oon_fatigue_duration_days",
        default = 1.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /** The global fatigue time-window for space fanout recs, e.g. 1 push every 3 days */
  object SpaceRecsGlobalFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "space_recs_global_fatigue_duration_days",
        default = 1.day,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /** The min-interval between space fanout recs.
   * After receiving a space fanout rec, they must wait a minimum of this
   * interval before eligibile for another */
  object SpaceRecsFatigueMinIntervalDuration
      etwittertends FSBoundedParam[Duration](
        name = "space_recs_fatigue_mininterval_duration_minutes",
        default = 30.minutes,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromMinutes
  }

  /** Space fanout user-follow rank threshold.
   * Users targeted by a follow that is above this threshold will be filtered */
  object SpaceRecsRealgraphThreshold
      etwittertends FSBoundedParam[Int](
        name = "space_recs_realgraph_threshold",
        default = 50,
        matwitter = 500,
        min = 0
      )

  object EnableHydratingRealGraphTargetUserFeatures
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_enable_hydrating_real_graph_target_user_feature",
        default = true
      )

  /** Param to reduce dillution when checking if a space is featured or not */
  object CheckFeaturedSpaceOON
      etwittertends FSParam[Boolean](name = "space_recs_check_if_its_featured_space", default = false)

  /** Enable Featured Spaces Rules for OON spaces */
  object EnableFeaturedSpacesOON
      etwittertends FSParam[Boolean](name = "space_recs_enable_featured_spaces_oon", default = false)

  /** Enable Geo Targeting */
  object EnableGeoTargetingForSpaces
      etwittertends FSParam[Boolean](name = "space_recs_enable_geo_targeting", default = false)

  /** Number of matwitter pushes within the fatigue duration for OON Space Recs */
  object OONSpaceRecsPushLimit
      etwittertends FSBoundedParam[Int](
        name = "space_recs_oon_push_limit",
        default = 1,
        matwitter = 3,
        min = 0
      )

  /** Space fanout recs, number of matwitter pushes within the fatigue duration */
  object SpaceRecsGlobalPushLimit
      etwittertends FSBoundedParam[Int](
        name = "space_recs_global_push_limit",
        default = 3,
        matwitter = 50,
        min = 0
      )

  /**
   * Param to enable score based override.
   */
  object EnableOverrideNotificationsScoreBasedOverride
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_score_ranking",
        default = false
      )

  /**
   * Param to determine the lookback duration when searching for override info.
   */
  object OverrideNotificationsLookbackDurationForOverrideInfo
      etwittertends FSBoundedParam[Duration](
        name = "mr_override_lookback_duration_override_info_in_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to determine the lookback duration when searching for impression ids.
   */
  object OverrideNotificationsLookbackDurationForImpressionId
      etwittertends FSBoundedParam[Duration](
        name = "mr_override_lookback_duration_impression_id_in_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to enable sending multiple target ids in the payload.
   */
  object EnableOverrideNotificationsMultipleTargetIds
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_multiple_target_ids",
        default = false
      )

  /**
   * Param for MR Web Notifications holdback
   */
  object MRWebHoldbackParam
      etwittertends FSParam[Boolean](
        name = "mr_web_notifications_holdback",
        default = false
      )

  object CommonRecommendationTypeDenyListPushHoldbacks
      etwittertends FSParam[Seq[String]](
        name = "crt_to_etwitterclude_from_holdbacks_push_holdbacks",
        default = Seq.empty[String]
      )

  /**
   * Param to enable sending number of slots to maintain in the payload.
   */
  object EnableOverrideNotificationsNSlots
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_n_slots",
        default = false
      )

  /**
   * Enable down ranking of NUPS and pop geo topic follow candidates for new user playbook.
   */
  object EnableDownRankOfNewUserPlaybookTopicFollowPush
      etwittertends FSParam[Boolean](
        name = "topic_follow_new_user_playbook_enable_down_rank",
        default = false
      )

  /**
   * Enable down ranking of NUPS and pop geo topic tweet candidates for new user playbook.
   */
  object EnableDownRankOfNewUserPlaybookTopicTweetPush
      etwittertends FSParam[Boolean](
        name = "topic_tweet_new_user_playbook_enable_down_rank",
        default = false
      )

  /**
   * Param to enable/disable employee only spaces for fanout of notifications
   */
  object EnableEmployeeOnlySpaceNotifications
      etwittertends FSParam[Boolean](name = "space_recs_employee_only_enable", default = false)

  /**
   * NTab spaces ttl etwitterperiments
   */
  object EnableSpacesTtlForNtab
      etwittertends FSParam[Boolean](
        name = "ntab_spaces_ttl_enable",
        default = false
      )

  /**
   * Param to determine the ttl duration for space notifications on NTab.
   */
  object SpaceNotificationsTTLDurationForNTab
      etwittertends FSBoundedParam[Duration](
        name = "ntab_spaces_ttl_hours",
        default = 1.hour,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /*
   * NTab override etwitterperiments
   * see go/ntab-override etwitterperiment brief for more details
   */

  /**
   * Override notifications for Spaces on lockscreen.
   */
  object EnableOverrideForSpaces
      etwittertends FSParam[Boolean](
        name = "mr_override_spaces",
        default = false
      )

  /**
   * Param to enable storing the Generic Notification Key.
   */
  object EnableStoringNtabGenericNotifKey
      etwittertends FSParam[Boolean](
        name = "ntab_enable_storing_generic_notif_key",
        default = false
      )

  /**
   * Param to enable deleting the Target's timeline.
   */
  object EnableDeletingNtabTimeline
      etwittertends FSParam[Boolean](
        name = "ntab_enable_delete_timeline",
        default = false
      )

  /**
   * Param to enable sending the overrideId
   * to NTab which enables override support in NTab-api
   */
  object EnableOverrideIdNTabRequest
      etwittertends FSParam[Boolean](
        name = "ntab_enable_override_id_in_request",
        default = false
      )

  /**
   * [Override Workstream] Param to enable NTab override n-slot feature.
   */
  object EnableNslotsForOverrideOnNtab
      etwittertends FSParam[Boolean](
        name = "ntab_enable_override_matwitter_count",
        default = false
      )

  /**
   * Param to determine the lookback duration for override candidates on NTab.
   */
  object OverrideNotificationsLookbackDurationForNTab
      etwittertends FSBoundedParam[Duration](
        name = "ntab_override_lookback_duration_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to determine the matwitter count for candidates on NTab.
   */
  object OverrideNotificationsMatwitterCountForNTab
      etwittertends FSBoundedParam[Int](
        name = "ntab_override_limit",
        min = 0,
        matwitter = Int.MatwitterValue,
        default = 4)

  //// end override etwitterperiments ////
  /**
   * Param to enable top tweet impressions notification
   */
  object EnableTopTweetImpressionsNotification
      etwittertends FSParam[Boolean](
        name = "top_tweet_impressions_notification_enable",
        default = false
      )

  /**
   * Param to control the inverter for fatigue between consecutive TweetImpressions
   */
  object TopTweetImpressionsNotificationInterval
      etwittertends FSBoundedParam[Duration](
        name = "top_tweet_impressions_notification_interval_days",
        default = 7.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * The min-interval between TweetImpressions notifications.
   * After receiving a TweetImpressions notif, they must wait a minimum of this
   * interval before being eligible for another
   */
  object TopTweetImpressionsFatigueMinIntervalDuration
      etwittertends FSBoundedParam[Duration](
        name = "top_tweet_impressions_fatigue_mininterval_duration_days",
        default = 1.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Matwitterimum number of top tweet impressions notifications to receive in an interval
   */
  object MatwitterTopTweetImpressionsNotifications
      etwittertends FSBoundedParam(
        name = "top_tweet_impressions_fatigue_matwitter_in_interval",
        default = 0,
        min = 0,
        matwitter = 10
      )

  /**
   * Param for min number of impressions counts to be eligible for lonely_birds_tweet_impressions model
   */
  object TopTweetImpressionsMinRequired
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_min_required",
        default = 25,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param for threshold of impressions counts to notify for lonely_birds_tweet_impressions model
   */
  object TopTweetImpressionsThreshold
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_threshold",
        default = 25,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param for the number of days to search up to for a user's original tweets
   */
  object TopTweetImpressionsOriginalTweetsNumDaysSearch
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_original_tweets_num_days_search",
        default = 3,
        min = 0,
        matwitter = 21
      )

  /**
   * Param for the minimum number of original tweets a user needs to be considered an original author
   */
  object TopTweetImpressionsMinNumOriginalTweets
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_num_original_tweets",
        default = 3,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param for the matwitter number of favorites any original Tweet can have
   */
  object TopTweetImpressionsMatwitterFavoritesPerTweet
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_matwitter_favorites_per_tweet",
        default = 3,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param for the matwitter number of total inbound favorites for a user's tweets
   */
  object TopTweetImpressionsTotalInboundFavoritesLimit
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_total_inbound_favorites_limit",
        default = 60,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /**
   * Param for the number of days to search for tweets to count the total inbound favorites
   */
  object TopTweetImpressionsTotalFavoritesLimitNumDaysSearch
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_total_favorites_limit_num_days_search",
        default = 7,
        min = 0,
        matwitter = 21
      )

  /**
   * Param for the matwitter number of recent tweets Tflock should return
   */
  object TopTweetImpressionsRecentTweetsByAuthorStoreMatwitterResults
      etwittertends FSBoundedParam[Int](
        name = "top_tweet_impressions_recent_tweets_by_author_store_matwitter_results",
        default = 50,
        min = 0,
        matwitter = 1000
      )

  /*
   * Param to represent the matwitter number of slots to maintain for Override Notifications
   */
  object OverrideNotificationsMatwitterNumOfSlots
      etwittertends FSBoundedParam[Int](
        name = "mr_override_matwitter_num_slots",
        default = 1,
        matwitter = 10,
        min = 1
      )

  object EnableOverrideMatwitterSlotFn
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_matwitter_num_slots_fn",
        default = false
      )

  object OverrideMatwitterSlotFnPushCapKnobs
      etwittertends FSParam[Seq[Double]]("mr_override_fn_pushcap_knobs", default = Seq.empty[Double])

  object OverrideMatwitterSlotFnNSlotKnobs
      etwittertends FSParam[Seq[Double]]("mr_override_fn_nslot_knobs", default = Seq.empty[Double])

  object OverrideMatwitterSlotFnPowerKnobs
      etwittertends FSParam[Seq[Double]]("mr_override_fn_power_knobs", default = Seq.empty[Double])

  object OverrideMatwitterSlotFnWeight
      etwittertends FSBoundedParam[Double](
        "mr_override_fn_weight",
        default = 1.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  /**
   * Use to enable sending target ids in the Smart Push Payload
   */
  object EnableTargetIdsInSmartPushPayload
      etwittertends FSParam[Boolean](name = "mr_override_enable_target_ids", default = true)

  /**
   * Param to enable override by target id for MagicFanoutSportsEvent candidates
   */
  object EnableTargetIdInSmartPushPayloadForMagicFanoutSportsEvent
      etwittertends FSParam[Boolean](
        name = "mr_override_enable_target_id_for_magic_fanout_sports_event",
        default = true)

  /**
   * Param to enable secondary account predicate on MF NFY
   */
  object EnableSecondaryAccountPredicateMF
      etwittertends FSParam[Boolean](
        name = "frigate_push_magicfanout_secondary_account_predicate",
        default = false
      )

  /**
   * Enables showing our customers videos on their notifications
   */
  object EnableInlineVideo
      etwittertends FSParam[Boolean](name = "mr_inline_enable_inline_video", default = false)

  /**
   * Enables autoplay for inline videos
   */
  object EnableAutoplayForInlineVideo
      etwittertends FSParam[Boolean](name = "mr_inline_enable_autoplay_for_inline_video", default = false)

  /**
   * Enable OON filtering based on MentionFilter.
   */
  object EnableOONFilteringBasedOnUserSettings
      etwittertends FSParam[Boolean](name = "oon_filtering_enable_based_on_user_settings", false)

  /**
   * Enables Custom Thread Ids which is used to ungroup notifications for N-slots on iOS
   */
  object EnableCustomThreadIdForOverride
      etwittertends FSParam[Boolean](name = "mr_override_enable_custom_thread_id", default = false)

  /**
   * Enables showing verified symbol in the push presentation
   */
  object EnablePushPresentationVerifiedSymbol
      etwittertends FSParam[Boolean](name = "push_presentation_enable_verified_symbol", default = false)

  /**
   * Decide subtetwittert in Android push header
   */
  object SubtetwittertInAndroidPushHeaderParam
      etwittertends FSEnumParam[SubtetwittertForAndroidPushHeader.type](
        name = "push_presentation_subtetwittert_in_android_push_header_id",
        default = SubtetwittertForAndroidPushHeader.None,
        enum = SubtetwittertForAndroidPushHeader)

  /**
   * Enable SimClusters Targeting For Spaces. If false we just drop all candidates with such targeting reason
   */
  object EnableSimClusterTargetingSpaces
      etwittertends FSParam[Boolean](name = "space_recs_send_simcluster_recommendations", default = false)

  /**
   * Param to control threshold for dot product of simcluster based targeting on Spaces
   */
  object SpacesTargetingSimClusterDotProductThreshold
      etwittertends FSBoundedParam[Double](
        "space_recs_simclusters_dot_product_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 10.0)

  /**
   * Param to control top-k clusters simcluster based targeting on Spaces
   */
  object SpacesTopKSimClusterCount
      etwittertends FSBoundedParam[Int](
        "space_recs_simclusters_top_k_count",
        default = 1,
        min = 1,
        matwitter = 50)

  /** SimCluster users host/speaker must meet this follower count minimum threshold to be considered for sends */
  object SpaceRecsSimClusterUserMinimumFollowerCount
      etwittertends FSBoundedParam[Int](
        name = "space_recs_simcluster_user_min_follower_count",
        default = 5000,
        matwitter = Int.MatwitterValue,
        min = 0
      )

  /**
   * Target has been bucketed into the Inline Action App Visit Fatigue Etwitterperiment
   */
  object TargetInInlineActionAppVisitFatigue
      etwittertends FSParam[Boolean](name = "inline_action_target_in_app_visit_fatigue", default = false)

  /**
   * Enables Inline Action App Visit Fatigue
   */
  object EnableInlineActionAppVisitFatigue
      etwittertends FSParam[Boolean](name = "inline_action_enable_app_visit_fatigue", default = false)

  /**
   * Determines the fatigue that we should apply when the target user has performed an inline action
   */
  object InlineActionAppVisitFatigue
      etwittertends FSBoundedParam[Duration](
        name = "inline_action_app_visit_fatigue_hours",
        default = 8.hours,
        min = 1.hour,
        matwitter = 48.hours)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Weight for reranking(oonc - weight * nudityRate)
   */
  object AuthorSensitiveScoreWeightInReranking
      etwittertends FSBoundedParam[Double](
        name = "rerank_candidates_author_sensitive_score_weight_in_reranking",
        default = 0.0,
        min = -100.0,
        matwitter = 100.0
      )

  /**
   * Param to control the last active space listener threshold to filter out based on that
   */
  object SpaceParticipantHistoryLastActiveThreshold
      etwittertends FSBoundedParam[Duration](
        name = "space_recs_last_active_space_listener_threshold_in_hours",
        default = 0.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /*
   * Param to enable mr user simcluster feature set (v2020) hydration for modeling-based candidate generation
   * */
  object HydrateMrUserSimclusterV2020InModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_mr_user_simcluster_v2020",
        default = false)

  /*
   * Param to enable mr semantic core feature set hydration for modeling-based candidate generation
   * */
  object HydrateMrUserSemanticCoreInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_mr_user_semantic_core",
        default = false)

  /*
   * Param to enable mr semantic core feature set hydration for modeling-based candidate generation
   * */
  object HydrateOnboardingInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_onboarding",
        default = false)

  /*
   * Param to enable mr topic follow feature set hydration for modeling-based candidate generation
   * */
  object HydrateTopicFollowInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_topic_follow",
        default = false)

  /*
   * Param to enable mr user topic feature set hydration for modeling-based candidate generation
   * */
  object HydrateMrUserTopicInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_mr_user_topic",
        default = false)

  /*
   * Param to enable mr user topic feature set hydration for modeling-based candidate generation
   * */
  object HydrateMrUserAuthorInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_mr_user_author",
        default = false)

  /*
   * Param to enable user penguin language feature set hydration for modeling-based candidate generation
   * */
  object HydrateUserPenguinLanguageInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_user_penguin_language",
        default = false)
  /*
   * Param to enable user geo feature set hydration for modeling-based candidate generation
   * */
  object HydrateUseGeoInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_user_geo",
        default = false)

  /*
   * Param to enable mr user hashspace embedding feature set hydration for modeling-based candidate generation
   * */
  object HydrateMrUserHashspaceEmbeddingInModelingBasedCG
      etwittertends FSParam[Boolean](
        name = "candidate_generation_model_hydrate_mr_user_hashspace_embedding",
        default = false)
  /*
   * Param to enable user tweet tetwittert feature hydration
   * */
  object EnableMrUserEngagedTweetTokensFeature
      etwittertends FSParam[Boolean](
        name = "feature_hydration_mr_user_engaged_tweet_tokens",
        default = false)

  /**
   * Params for CRT based see less often fatigue rules
   */
  object EnableF1TriggerSeeLessOftenFatigue
      etwittertends FSParam[Boolean](
        name = "seelessoften_enable_f1_trigger_fatigue",
        default = false
      )

  object EnableNonF1TriggerSeeLessOftenFatigue
      etwittertends FSParam[Boolean](
        name = "seelessoften_enable_nonf1_trigger_fatigue",
        default = false
      )

  /**
   * Adjust the NtabCaretClickFatigue for candidates if it is triggered by
   * TripHqTweet candidates
   */
  object AdjustTripHqTweetTriggeredNtabCaretClickFatigue
      etwittertends FSParam[Boolean](
        name = "seelessoften_adjust_trip_hq_tweet_triggered_fatigue",
        default = false
      )

  object NumberOfDaysToFilterForSeeLessOftenForF1TriggerF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_f1_trigger_f1_tofiltermr_days",
        default = 7.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForF1TriggerF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_f1_trigger_f1_toreduce_pushcap_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToFilterForSeeLessOftenForF1TriggerNonF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_f1_trigger_nonf1_tofiltermr_days",
        default = 7.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForF1TriggerNonF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_f1_trigger_non_f1_toreduce_pushcap_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToFilterForSeeLessOftenForNonF1TriggerF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_nonf1_trigger_f1_tofiltermr_days",
        default = 7.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForNonF1TriggerF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_nonf1_trigger_f1_toreduce_pushcap_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToFilterForSeeLessOftenForNonF1TriggerNonF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_nonf1_trigger_nonf1_tofiltermr_days",
        default = 7.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object NumberOfDaysToReducePushCapForSeeLessOftenForNonF1TriggerNonF1
      etwittertends FSBoundedParam[Duration](
        name = "seelessoften_for_nonf1_trigger_nonf1_toreduce_pushcap_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  object EnableContFnF1TriggerSeeLessOftenFatigue
      etwittertends FSParam[Boolean](
        name = "seelessoften_fn_enable_f1_trigger_fatigue",
        default = false
      )

  object EnableContFnNonF1TriggerSeeLessOftenFatigue
      etwittertends FSParam[Boolean](
        name = "seelessoften_fn_enable_nonf1_trigger_fatigue",
        default = false
      )

  object SeeLessOftenListOfDayKnobs
      etwittertends FSParam[Seq[Double]]("seelessoften_fn_day_knobs", default = Seq.empty[Double])

  object SeeLessOftenListOfPushCapWeightKnobs
      etwittertends FSParam[Seq[Double]]("seelessoften_fn_pushcap_knobs", default = Seq.empty[Double])

  object SeeLessOftenListOfPowerKnobs
      etwittertends FSParam[Seq[Double]]("seelessoften_fn_power_knobs", default = Seq.empty[Double])

  object SeeLessOftenF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_f1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_f1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenNonF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_nonf1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenNonF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_nonf1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenTripHqTweetTriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_trip_hq_tweet_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenTripHqTweetTriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_trip_hq_tweet_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenTripHqTweetTriggerTripHqTweetPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_trip_hq_tweet_trigger_trip_hq_tweet_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object SeeLessOftenTopicTriggerTopicPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_topic_trigger_topic_weight",
        default = 1.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenTopicTriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_topic_trigger_f1_weight",
        default = 100000.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenTopicTriggerOONPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_topic_trigger_oon_weight",
        default = 100000.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenF1TriggerTopicPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_f1_trigger_topic_weight",
        default = 100000.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenOONTriggerTopicPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_oon_trigger_topic_weight",
        default = 1.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenDefaultPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_default_weight",
        default = 100000.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  object SeeLessOftenNtabOnlyNotifUserPushCapWeight
      etwittertends FSBoundedParam[Double](
        "seelessoften_fn_ntab_only_user_weight",
        default = 1.0,
        min = 0.0,
        matwitter = Double.MatwitterValue)

  // Params for inline feedback fatigue
  object EnableContFnF1TriggerInlineFeedbackFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_enable_f1_trigger_fatigue",
        default = false
      )

  object EnableContFnNonF1TriggerInlineFeedbackFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_enable_nonf1_trigger_fatigue",
        default = false
      )

  object UseInlineDislikeForFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_use_dislike",
        default = true
      )
  object UseInlineDismissForFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_use_dismiss",
        default = false
      )
  object UseInlineSeeLessForFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_use_see_less",
        default = false
      )
  object UseInlineNotRelevantForFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_inline_fn_use_not_relevant",
        default = false
      )
  object InlineFeedbackListOfDayKnobs
      etwittertends FSParam[Seq[Double]]("feedback_inline_fn_day_knobs", default = Seq.empty[Double])

  object InlineFeedbackListOfPushCapWeightKnobs
      etwittertends FSParam[Seq[Double]]("feedback_inline_fn_pushcap_knobs", default = Seq.empty[Double])

  object InlineFeedbackListOfPowerKnobs
      etwittertends FSParam[Seq[Double]]("feedback_inline_fn_power_knobs", default = Seq.empty[Double])

  object InlineFeedbackF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_inline_fn_f1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object InlineFeedbackF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_inline_fn_f1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object InlineFeedbackNonF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_inline_fn_nonf1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object InlineFeedbackNonF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_inline_fn_nonf1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  // Params for prompt feedback
  object EnableContFnF1TriggerPromptFeedbackFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_prompt_fn_enable_f1_trigger_fatigue",
        default = false
      )

  object EnableContFnNonF1TriggerPromptFeedbackFatigue
      etwittertends FSParam[Boolean](
        name = "feedback_prompt_fn_enable_nonf1_trigger_fatigue",
        default = false
      )
  object PromptFeedbackListOfDayKnobs
      etwittertends FSParam[Seq[Double]]("feedback_prompt_fn_day_knobs", default = Seq.empty[Double])

  object PromptFeedbackListOfPushCapWeightKnobs
      etwittertends FSParam[Seq[Double]]("feedback_prompt_fn_pushcap_knobs", default = Seq.empty[Double])

  object PromptFeedbackListOfPowerKnobs
      etwittertends FSParam[Seq[Double]]("feedback_prompt_fn_power_knobs", default = Seq.empty[Double])

  object PromptFeedbackF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_prompt_fn_f1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object PromptFeedbackF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_prompt_fn_f1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object PromptFeedbackNonF1TriggerF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_prompt_fn_nonf1_trigger_f1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  object PromptFeedbackNonF1TriggerNonF1PushCapWeight
      etwittertends FSBoundedParam[Double](
        "feedback_prompt_fn_nonf1_trigger_nonf1_weight",
        default = 1.0,
        min = 0.0,
        matwitter = 10000000.0)

  /*
   * Param to enable cohost join event notif
   */
  object EnableSpaceCohostJoinEvent
      etwittertends FSParam[Boolean](name = "space_recs_cohost_join_enable", default = true)

  /*
   * Param to bypass global push cap when target is device following host/speaker.
   */
  object BypassGlobalSpacePushCapForSoftDeviceFollow
      etwittertends FSParam[Boolean](name = "space_recs_bypass_global_pushcap_for_soft_follow", false)

  /*
   * Param to bypass active listener predicate when target is device following host/speaker.
   */
  object CheckActiveListenerPredicateForSoftDeviceFollow
      etwittertends FSParam[Boolean](name = "space_recs_check_active_listener_for_soft_follow", false)

  object SpreadControlRatioParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_ratio",
        default = 1000.0,
        min = 0.0,
        matwitter = 100000.0
      )

  object FavOverSendThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_fav_over_send_threshold",
        default = 0.14,
        min = 0.0,
        matwitter = 1000.0
      )

  object AuthorReportRateThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_author_report_rate_threshold",
        default = 7.4e-6,
        min = 0.0,
        matwitter = 1000.0
      )

  object AuthorDislikeRateThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_author_dislike_rate_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1000.0
      )

  object MinTweetSendsThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_min_tweet_sends_threshold",
        default = 10000000000.0,
        min = 0.0,
        matwitter = 10000000000.0
      )

  object MinAuthorSendsThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_spread_control_min_author_sends_threshold",
        default = 10000000000.0,
        min = 0.0,
        matwitter = 10000000000.0
      )

  /*
   * Tweet Ntab-dislike predicate related params
   */
  object TweetNtabDislikeCountThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_ntab_dislike_count_threshold",
        default = 10000.0,
        min = 0.0,
        matwitter = 10000.0
      )
  object TweetNtabDislikeRateThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_ntab_dislike_rate_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param for tweet language feature name
   */
  object TweetLanguageFeatureNameParam
      etwittertends FSParam[String](
        name = "language_tweet_language_feature_name",
        default = "tweet.language.tweet.identified")

  /**
   * Threshold for user inferred language filtering
   */
  object UserInferredLanguageThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "language_user_inferred_language_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Threshold for user device language filtering
   */
  object UserDeviceLanguageThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "language_user_device_language_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to enable/disable tweet language filter
   */
  object EnableTweetLanguageFilter
      etwittertends FSParam[Boolean](
        name = "language_enable_tweet_language_filter",
        default = false
      )

  /**
   * Param to skip language filter for media tweets
   */
  object SkipLanguageFilterForMediaTweets
      etwittertends FSParam[Boolean](
        name = "language_skip_language_filter_for_media_tweets",
        default = false
      )

  /*
   * Tweet Ntab-dislike predicate related params for MrTwistly
   */
  object TweetNtabDislikeCountThresholdForMrTwistlyParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_ntab_dislike_count_threshold_for_mrtwistly",
        default = 10000.0,
        min = 0.0,
        matwitter = 10000.0
      )
  object TweetNtabDislikeRateThresholdForMrTwistlyParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_ntab_dislike_rate_threshold_for_mrtwistly",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  object TweetNtabDislikeCountBucketThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_ntab_dislike_count_bucket_threshold",
        default = 10.0,
        min = 0.0,
        matwitter = 10000.0
      )

  /*
   * Tweet engagement ratio predicate related params
   */
  object TweetQTtoNtabClickRatioThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_engagement_filter_qt_to_ntabclick_ratio_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100000.0
      )

  /**
   * Lower bound threshold to filter a tweet based on its reply to like ratio
   */
  object TweetReplytoLikeRatioThresholdLowerBound
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_engagement_filter_reply_to_like_ratio_threshold_lower_bound",
        default = Double.MatwitterValue,
        min = 0.0,
        matwitter = Double.MatwitterValue
      )

  /**
   * Upper bound threshold to filter a tweet based on its reply to like ratio
   */
  object TweetReplytoLikeRatioThresholdUpperBound
      etwittertends FSBoundedParam[Double](
        name = "oon_tweet_engagement_filter_reply_to_like_ratio_threshold_upper_bound",
        default = 0.0,
        min = 0.0,
        matwitter = Double.MatwitterValue
      )

  /**
   * Upper bound threshold to filter a tweet based on its reply to like ratio
   */
  object TweetReplytoLikeRatioReplyCountThreshold
      etwittertends FSBoundedParam[Int](
        name = "oon_tweet_engagement_filter_reply_count_threshold",
        default = Int.MatwitterValue,
        min = 0,
        matwitter = Int.MatwitterValue
      )

  /*
   * oonTweetLengthBasedPrerankingPredicate related params
   */
  object OonTweetLengthPredicateUpdatedMediaLogic
      etwittertends FSParam[Boolean](
        name = "oon_quality_filter_tweet_length_updated_media_logic",
        default = false
      )

  object OonTweetLengthPredicateUpdatedQuoteTweetLogic
      etwittertends FSParam[Boolean](
        name = "oon_quality_filter_tweet_length_updated_quote_tweet_logic",
        default = false
      )

  object OonTweetLengthPredicateMoreStrictForUndefinedLanguages
      etwittertends FSParam[Boolean](
        name = "oon_quality_filter_tweet_length_more_strict_for_undefined_languages",
        default = false
      )

  object EnablePrerankingTweetLengthPredicate
      etwittertends FSParam[Boolean](
        name = "oon_quality_filter_enable_preranking_filter",
        default = false
      )

  /*
   * LengthLanguageBasedOONTweetCandidatesQualityPredicate related params
   */
  object SautOonWithMediaTweetLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_length_threshold_for_saut_oon_with_media",
        default = 0.0,
        min = 0.0,
        matwitter = 70.0
      )
  object NonSautOonWithMediaTweetLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_length_threshold_for_non_saut_oon_with_media",
        default = 0.0,
        min = 0.0,
        matwitter = 70.0
      )
  object SautOonWithoutMediaTweetLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_length_threshold_for_saut_oon_without_media",
        default = 0.0,
        min = 0.0,
        matwitter = 70.0
      )
  object NonSautOonWithoutMediaTweetLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_length_threshold_for_non_saut_oon_without_media",
        default = 0.0,
        min = 0.0,
        matwitter = 70.0
      )

  object ArgfOonWithMediaTweetWordLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_word_length_threshold_for_argf_oon_with_media",
        default = 0.0,
        min = 0.0,
        matwitter = 18.0
      )
  object EsfthOonWithMediaTweetWordLengthThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "oon_quality_filter_tweet_word_length_threshold_for_esfth_oon_with_media",
        default = 0.0,
        min = 0.0,
        matwitter = 10.0
      )

  /**
   * Param to enable/disable sentiment feature hydration
   */
  object EnableMrTweetSentimentFeatureHydrationFS
      etwittertends FSParam[Boolean](
        name = "feature_hydration_enable_mr_tweet_sentiment_feature",
        default = false
      )

  /**
   * Param to enable/disable feature map scribing for staging test log
   */
  object EnableMrScribingMLFeaturesAsFeatureMapForStaging
      etwittertends FSParam[Boolean](
        name = "frigate_pushservice_enable_scribing_ml_features_as_featuremap_for_staging",
        default = false
      )

  /**
   * Param to enable timeline health signal hydration
   * */
  object EnableTimelineHealthSignalHydration
      etwittertends FSParam[Boolean](
        name = "timeline_health_signal_hydration",
        default = false
      )

  /**
   * Param to enable timeline health signal hydration for model training
   * */
  object EnableTimelineHealthSignalHydrationForModelTraining
      etwittertends FSParam[Boolean](
        name = "timeline_health_signal_hydration_for_model_training",
        default = false
      )

  /**
   * Param to enable/disable mr user social contetwittert agg feature hydration
   */
  object EnableMrUserSocialContetwittertAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_social_contetwittert_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user semantic core agg feature hydration
   */
  object EnableMrUserSemanticCoreAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_semantic_core_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user candidate sparse agg feature hydration
   */
  object EnableMrUserCandidateSparseOfflineAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_candidate_sparse_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user candidate agg feature hydration
   */
  object EnableMrUserCandidateOfflineAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_candidate_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user candidate compact agg feature hydration
   */
  object EnableMrUserCandidateOfflineCompactAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_candidate_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/disable mr real graph user-author/social-contetwittert feature hydration
   */
  object EnableRealGraphUserAuthorAndSocialConttwittertFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_real_graph_user_social_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user author agg feature hydration
   */
  object EnableMrUserAuthorOfflineAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_author_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user author compact agg feature hydration
   */
  object EnableMrUserAuthorOfflineCompactAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_author_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/disable mr user compact agg feature hydration
   */
  object EnableMrUserOfflineCompactAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_compact_agg_feature",
        default = false
      )

  /**
   * Param to enable/disable mr user simcluster agg feature hydration
   */
  object EnableMrUserSimcluster2020AggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_simcluster_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable mr user agg feature hydration
   */
  object EnableMrUserOfflineAggregateFeatureHydration
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_agg_feature",
        default = true
      )

  /**
   * Param to enable/disable topic engagement RTA in the ranking model
   */
  object EnableTopicEngagementRealTimeAggregatesFS
      etwittertends FSParam[Boolean](
        "feature_hydration_enable_htl_topic_engagement_real_time_agg_feature",
        false)

  /*
   * Param to enable mr user semantic core feature hydration for heavy ranker
   * */
  object EnableMrUserSemanticCoreFeatureForEtwitterpt
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_hydrate_mr_user_semantic_core",
        default = false)

  /**
   * Param to enable hydrating user duration since last visit features
   */
  object EnableHydratingUserDurationSinceLastVisitFeatures
      etwittertends FSParam[Boolean](
        name = "feature_hydration_user_duration_since_last_visit",
        default = false)

  /**
    Param to enable/disable user-topic aggregates in the ranking model
   */
  object EnableUserTopicAggregatesFS
      etwittertends FSParam[Boolean]("feature_hydration_enable_htl_topic_user_agg_feature", false)

  /*
   * PNegMultimodalPredicate related params
   */
  object EnablePNegMultimodalPredicateParam
      etwittertends FSParam[Boolean](
        name = "pneg_multimodal_filter_enable_param",
        default = false
      )
  object PNegMultimodalPredicateModelThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "pneg_multimodal_filter_model_threshold_param",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )
  object PNegMultimodalPredicateBucketThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "pneg_multimodal_filter_bucket_threshold_param",
        default = 0.4,
        min = 0.0,
        matwitter = 1.0
      )

  /*
   * NegativeKeywordsPredicate related params
   */
  object EnableNegativeKeywordsPredicateParam
      etwittertends FSParam[Boolean](
        name = "negative_keywords_filter_enable_param",
        default = false
      )
  object NegativeKeywordsPredicateDenylist
      etwittertends FSParam[Seq[String]](
        name = "negative_keywords_filter_denylist",
        default = Seq.empty[String]
      )
  /*
   * LightRanking related params
   */
  object EnableLightRankingParam
      etwittertends FSParam[Boolean](
        name = "light_ranking_enable_param",
        default = false
      )
  object LightRankingNumberOfCandidatesParam
      etwittertends FSBoundedParam[Int](
        name = "light_ranking_number_of_candidates_param",
        default = 100,
        min = 0,
        matwitter = 1000
      )
  object LightRankingModelTypeParam
      etwittertends FSParam[String](
        name = "light_ranking_model_type_param",
        default = "WeightedOpenOrNtabClickProbability_Q4_2021_13172_Mr_Light_Ranker_Dbv2_Top3")
  object EnableRandomBaselineLightRankingParam
      etwittertends FSParam[Boolean](
        name = "light_ranking_random_baseline_enable_param",
        default = false
      )

  object LightRankingScribeCandidatesDownSamplingParam
      etwittertends FSBoundedParam[Double](
        name = "light_ranking_scribe_candidates_down_sampling_param",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /*
   * Quality Upranking related params
   */
  object EnableProducersQualityBoostingForHeavyRankingParam
      etwittertends FSParam[Boolean](
        name = "quality_upranking_enable_producers_quality_boosting_for_heavy_ranking_param",
        default = false
      )

  object QualityUprankingBoostForHighQualityProducersParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_boost_for_high_quality_producers_param",
        default = 1.0,
        min = 0.0,
        matwitter = 10000.0
      )

  object QualityUprankingDownboostForLowQualityProducersParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_downboost_for_low_quality_producers_param",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  object EnableQualityUprankingForHeavyRankingParam
      etwittertends FSParam[Boolean](
        name = "quality_upranking_enable_for_heavy_ranking_param",
        default = false
      )
  object QualityUprankingModelTypeParam
      etwittertends FSParam[WeightedOpenOrNtabClickModel.ModelNameType](
        name = "quality_upranking_model_id",
        default = "Q4_2022_Mr_Bqml_Quality_Model_wALL"
      )
  object QualityUprankingTransformTypeParam
      etwittertends FSEnumParam[MrQualityUprankingTransformTypeEnum.type](
        name = "quality_upranking_transform_id",
        default = MrQualityUprankingTransformTypeEnum.Sigmoid,
        enum = MrQualityUprankingTransformTypeEnum
      )

  object QualityUprankingBoostForHeavyRankingParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_boost_for_heavy_ranking_param",
        default = 1.0,
        min = -10.0,
        matwitter = 10.0
      )
  object QualityUprankingSigmoidBiasForHeavyRankingParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_sigmoid_bias_for_heavy_ranking_param",
        default = 0.0,
        min = -10.0,
        matwitter = 10.0
      )
  object QualityUprankingSigmoidWeightForHeavyRankingParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_sigmoid_weight_for_heavy_ranking_param",
        default = 1.0,
        min = -10.0,
        matwitter = 10.0
      )
  object QualityUprankingLinearBarForHeavyRankingParam
      etwittertends FSBoundedParam[Double](
        name = "quality_upranking_linear_bar_for_heavy_ranking_param",
        default = 1.0,
        min = 0.0,
        matwitter = 10.0
      )
  object EnableQualityUprankingCrtScoreStatsForHeavyRankingParam
      etwittertends FSParam[Boolean](
        name = "quality_upranking_enable_crt_score_stats_for_heavy_ranking_param",
        default = false
      )
  /*
   * BQML Health Model related params
   */
  object EnableBqmlHealthModelPredicateParam
      etwittertends FSParam[Boolean](
        name = "bqml_health_model_filter_enable_param",
        default = false
      )

  object EnableBqmlHealthModelPredictionForInNetworkCandidatesParam
      etwittertends FSParam[Boolean](
        name = "bqml_health_model_enable_prediction_for_in_network_candidates_param",
        default = false
      )

  object BqmlHealthModelTypeParam
      etwittertends FSParam[HealthNsfwModel.ModelNameType](
        name = "bqml_health_model_id",
        default = HealthNsfwModel.Q2_2022_Mr_Bqml_Health_Model_NsfwV0
      )
  object BqmlHealthModelPredicateFilterThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "bqml_health_model_filter_threshold_param",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )
  object BqmlHealthModelPredicateBucketThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "bqml_health_model_bucket_threshold_param",
        default = 0.005,
        min = 0.0,
        matwitter = 1.0
      )

  object EnableBqmlHealthModelScoreHistogramParam
      etwittertends FSParam[Boolean](
        name = "bqml_health_model_score_histogram_enable_param",
        default = false
      )

  /*
   * BQML Quality Model related params
   */
  object EnableBqmlQualityModelPredicateParam
      etwittertends FSParam[Boolean](
        name = "bqml_quality_model_filter_enable_param",
        default = false
      )
  object EnableBqmlQualityModelScoreHistogramParam
      etwittertends FSParam[Boolean](
        name = "bqml_quality_model_score_histogram_enable_param",
        default = false
      )
  object BqmlQualityModelTypeParam
      etwittertends FSParam[WeightedOpenOrNtabClickModel.ModelNameType](
        name = "bqml_quality_model_id",
        default = "Q1_2022_13562_Mr_Bqml_Quality_Model_V2"
      )

  /**
   * Param to specify which quality models to use to get the scores for determining
   * whether to bucket a user for the DDG
   */
  object BqmlQualityModelBucketModelIdListParam
      etwittertends FSParam[Seq[WeightedOpenOrNtabClickModel.ModelNameType]](
        name = "bqml_quality_model_bucket_model_id_list",
        default = Seq(
          "Q1_2022_13562_Mr_Bqml_Quality_Model_V2",
          "Q2_2022_DDG14146_Mr_Personalised_BQML_Quality_Model",
          "Q2_2022_DDG14146_Mr_NonPersonalised_BQML_Quality_Model"
        )
      )

  object BqmlQualityModelPredicateThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "bqml_quality_model_filter_threshold_param",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to specify the threshold to determine if a user’s quality score is high enough to enter the etwitterperiment.
   */
  object BqmlQualityModelBucketThresholdListParam
      etwittertends FSParam[Seq[Double]](
        name = "bqml_quality_model_bucket_threshold_list",
        default = Seq(0.7, 0.7, 0.7)
      )

  /*
   * TweetAuthorAggregates related params
   */
  object EnableTweetAuthorAggregatesFeatureHydrationParam
      etwittertends FSParam[Boolean](
        name = "tweet_author_aggregates_feature_hydration_enable_param",
        default = false
      )

  /**
   * Param to determine if we should include the relevancy score of candidates in the Ibis payload
   */
  object IncludeRelevanceScoreInIbis2Payload
      etwittertends FSParam[Boolean](
        name = "relevance_score_include_in_ibis2_payload",
        default = false
      )

  /**
   *  Param to specify supervised model to predict score by sending the notification
   */
  object BigFilteringSupervisedSendingModelParam
      etwittertends FSParam[BigFilteringSupervisedModel.ModelNameType](
        name = "ltv_filtering_bigfiltering_supervised_sending_model_param",
        default = BigFilteringSupervisedModel.V0_0_BigFiltering_Supervised_Sending_Model
      )

  /**
   *  Param to specify supervised model to predict score by not sending the notification
   */
  object BigFilteringSupervisedWithoutSendingModelParam
      etwittertends FSParam[BigFilteringSupervisedModel.ModelNameType](
        name = "ltv_filtering_bigfiltering_supervised_without_sending_model_param",
        default = BigFilteringSupervisedModel.V0_0_BigFiltering_Supervised_Without_Sending_Model
      )

  /**
   *  Param to specify RL model to predict score by sending the notification
   */
  object BigFilteringRLSendingModelParam
      etwittertends FSParam[BigFilteringSupervisedModel.ModelNameType](
        name = "ltv_filtering_bigfiltering_rl_sending_model_param",
        default = BigFilteringRLModel.V0_0_BigFiltering_Rl_Sending_Model
      )

  /**
   *  Param to specify RL model to predict score by not sending the notification
   */
  object BigFilteringRLWithoutSendingModelParam
      etwittertends FSParam[BigFilteringSupervisedModel.ModelNameType](
        name = "ltv_filtering_bigfiltering_rl_without_sending_model_param",
        default = BigFilteringRLModel.V0_0_BigFiltering_Rl_Without_Sending_Model
      )

  /**
   *  Param to specify the threshold (send notification if score >= threshold)
   */
  object BigFilteringThresholdParam
      etwittertends FSBoundedParam[Double](
        name = "ltv_filtering_bigfiltering_threshold_param",
        default = 0.0,
        min = Double.MinValue,
        matwitter = Double.MatwitterValue
      )

  /**
   *  Param to specify normalization used for BigFiltering
   */
  object BigFilteringNormalizationTypeIdParam
      etwittertends FSEnumParam[BigFilteringNormalizationEnum.type](
        name = "ltv_filtering_bigfiltering_normalization_type_id",
        default = BigFilteringNormalizationEnum.NormalizationDisabled,
        enum = BigFilteringNormalizationEnum
      )

  /**
   *  Param to specify histograms of model scores in BigFiltering
   */
  object BigFilteringEnableHistogramsParam
      etwittertends FSParam[Boolean](
        name = "ltv_filtering_bigfiltering_enable_histograms_param",
        default = false
      )

  /*
   * Param to enable sending requests to Ins Sender
   */
  object EnableInsSender etwittertends FSParam[Boolean](name = "ins_enable_dark_traffic", default = false)

  /**
   * Param to specify the range of relevance scores for MagicFanout types.
   */
  object MagicFanoutRelevanceScoreRange
      etwittertends FSParam[Seq[Double]](
        name = "relevance_score_mf_range",
        default = Seq(0.75, 1.0)
      )

  /**
   * Param to specify the range of relevance scores for MR types.
   */
  object MagicRecsRelevanceScoreRange
      etwittertends FSParam[Seq[Double]](
        name = "relevance_score_mr_range",
        default = Seq(0.25, 0.5)
      )

  /**
   * Param to enable backfilling OON candidates if number of F1 candidates is greater than a threshold K.
   */
  object EnableOONBackfillBasedOnF1Candidates
      etwittertends FSParam[Boolean](name = "oon_enable_backfill_based_on_f1", default = false)

  /**
   * Threshold for the minimum number of F1 candidates required to enable backfill of OON candidates.
   */
  object NumberOfF1CandidatesThresholdForOONBackfill
      etwittertends FSBoundedParam[Int](
        name = "oon_enable_backfill_f1_threshold",
        min = 0,
        default = 5000,
        matwitter = 5000)

  /**
   * Event ID allowlist to skip account country predicate
   */
  object MagicFanoutEventAllowlistToSkipAccountCountryPredicate
      etwittertends FSParam[Seq[Long]](
        name = "magicfanout_event_allowlist_skip_account_country_predicate",
        default = Seq.empty[Long]
      )

  /**
   * MagicFanout Event Semantic Core Domain Ids
   */
  object ListOfEventSemanticCoreDomainIds
      etwittertends FSParam[Seq[Long]](
        name = "magicfanout_automated_events_semantic_core_domain_ids",
        default = Seq())

  /**
   * Adhoc id for detailed rank flow stats
   */
  object ListOfAdhocIdsForStatsTracking
      etwittertends FSParam[Set[Long]](
        name = "stats_enable_detailed_stats_tracking_ids",
        default = Set.empty[Long]
      )

  object EnableGenericCRTBasedFatiguePredicate
      etwittertends FSParam[Boolean](
        name = "seelessoften_enable_generic_crt_based_fatigue_predicate",
        default = false)

  /**
   * Param to enable copy features such as Emojis and Target Name
   */
  object EnableCopyFeaturesForF1
      etwittertends FSParam[Boolean](name = "mr_copy_enable_features_f1", default = false)

  /**
   * Param to enable copy features such as Emojis and Target Name
   */
  object EnableCopyFeaturesForOon
      etwittertends FSParam[Boolean](name = "mr_copy_enable_features_oon", default = false)

  /**
   * Param to enable Emoji in F1 Copy
   */
  object EnableEmojiInF1Copy
      etwittertends FSParam[Boolean](name = "mr_copy_enable_f1_emoji", default = false)

  /**
   * Param to enable Target in F1 Copy
   */
  object EnableTargetInF1Copy
      etwittertends FSParam[Boolean](name = "mr_copy_enable_f1_target", default = false)

  /**
   * Param to enable Emoji in OON Copy
   */
  object EnableEmojiInOonCopy
      etwittertends FSParam[Boolean](name = "mr_copy_enable_oon_emoji", default = false)

  /**
   * Param to enable Target in OON Copy
   */
  object EnableTargetInOonCopy
      etwittertends FSParam[Boolean](name = "mr_copy_enable_oon_target", default = false)

  /**
   * Param to enable split fatigue for Target and Emoji copy for OON and F1
   */
  object EnableTargetAndEmojiSplitFatigue
      etwittertends FSParam[Boolean](name = "mr_copy_enable_target_emoji_split_fatigue", default = false)

  /**
   * Param to enable etwitterperimenting string on the body
   */
  object EnableF1CopyBody etwittertends FSParam[Boolean](name = "mr_copy_f1_enable_body", default = false)

  object EnableOONCopyBody
      etwittertends FSParam[Boolean](name = "mr_copy_oon_enable_body", default = false)

  object EnableIosCopyBodyTruncate
      etwittertends FSParam[Boolean](name = "mr_copy_enable_body_truncate", default = false)

  object EnableNsfwCopy etwittertends FSParam[Boolean](name = "mr_copy_enable_nsfw", default = false)

  /**
   * Param to determine F1 candidate nsfw score threshold
   */
  object NsfwScoreThresholdForF1Copy
      etwittertends FSBoundedParam[Double](
        name = "mr_copy_nsfw_threshold_f1",
        default = 0.3,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to determine OON candidate nsfw score threshold
   */
  object NsfwScoreThresholdForOONCopy
      etwittertends FSBoundedParam[Double](
        name = "mr_copy_nsfw_threshold_oon",
        default = 0.2,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to determine the lookback duration when searching for prev copy features.
   */
  object CopyFeaturesHistoryLookbackDuration
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_history_lookback_duration_in_days",
        default = 30.days,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to determine the F1 emoji copy fatigue in # of hours.
   */
  object F1EmojiCopyFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_f1_emoji_copy_fatigue_in_hours",
        default = 24.hours,
        min = 0.hours,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to determine the F1 target copy fatigue in # of hours.
   */
  object F1TargetCopyFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_f1_target_copy_fatigue_in_hours",
        default = 24.hours,
        min = 0.hours,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to determine the OON emoji copy fatigue in # of hours.
   */
  object OonEmojiCopyFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_oon_emoji_copy_fatigue_in_hours",
        default = 24.hours,
        min = 0.hours,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to determine the OON target copy fatigue in # of hours.
   */
  object OonTargetCopyFatigueDuration
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_oon_target_copy_fatigue_in_hours",
        default = 24.hours,
        min = 0.hours,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to turn on/off home timeline based fatigue rule, where once last home timeline visit
   * is larger than the specified will evalute to not fatigue
   */
  object EnableHTLBasedFatigueBasicRule
      etwittertends FSParam[Boolean](
        name = "mr_copy_enable_htl_based_fatigue_basic_rule",
        default = false)

  /**
   * Param to determine f1 emoji copy fatigue in # of pushes
   */
  object F1EmojiCopyNumOfPushesFatigue
      etwittertends FSBoundedParam[Int](
        name = "mr_copy_f1_emoji_copy_number_of_pushes_fatigue",
        default = 0,
        min = 0,
        matwitter = 200
      )

  /**
   * Param to determine oon emoji copy fatigue in # of pushes
   */
  object OonEmojiCopyNumOfPushesFatigue
      etwittertends FSBoundedParam[Int](
        name = "mr_copy_oon_emoji_copy_number_of_pushes_fatigue",
        default = 0,
        min = 0,
        matwitter = 200
      )

  /**
   * If user haven't visited home timeline for certain duration, we will
   * etwitterempt user from feature copy fatigue. This param is used to control
   * how long it is before we enter etwitteremption.
   */
  object MinFatigueDurationSinceLastHTLVisit
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_min_duration_since_last_htl_visit_hours",
        default = Duration.Top,
        min = 0.hour,
        matwitter = Duration.Top,
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * If a user haven't visit home timeline very long, the user will return
   * to fatigue state under the home timeline based fatigue rule. There will
   * only be a window, where the user is out of fatigue state under the rule.
   * This param control the length of the non fatigue period.
   */
  object LastHTLVisitBasedNonFatigueWindow
      etwittertends FSBoundedParam[Duration](
        name = "mr_copy_last_htl_visit_based_non_fatigue_window_hours",
        default = 48.hours,
        min = 0.hour,
        matwitter = Duration.Top,
      )
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  object EnableOONCBasedCopy
      etwittertends FSParam[Boolean](
        name = "mr_copy_enable_oonc_based_copy",
        default = false
      )

  object HighOONCThresholdForCopy
      etwittertends FSBoundedParam[Double](
        name = "mr_copy_high_oonc_threshold_for_copy",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  object LowOONCThresholdForCopy
      etwittertends FSBoundedParam[Double](
        name = "mr_copy_low_oonc_threshold_for_copy",
        default = 0.0,
        min = 0.0,
        matwitter = 1.0
      )

  object EnableTweetTranslation
      etwittertends FSParam[Boolean](name = "tweet_translation_enable", default = false)

  object TripTweetCandidateReturnEnable
      etwittertends FSParam[Boolean](name = "trip_tweet_candidate_enable", default = false)

  object TripTweetCandidateSourceIds
      etwittertends FSParam[Seq[String]](
        name = "trip_tweet_candidate_source_ids",
        default = Seq("TOP_GEO_V3"))

  object TripTweetMatwitterTotalCandidates
      etwittertends FSBoundedParam[Int](
        name = "trip_tweet_matwitter_total_candidates",
        default = 500,
        min = 10,
        matwitter = 1000)

  object EnableEmptyBody
      etwittertends FSParam[Boolean](name = "push_presentation_enable_empty_body", default = false)

  object EnableSocialContetwittertForRetweet
      etwittertends FSParam[Boolean](name = "push_presentation_social_contetwittert_retweet", default = false)

  /**
   * Param to enable/disable simcluster feature hydration
   */
  object EnableMrTweetSimClusterFeatureHydrationFS
      etwittertends FSParam[Boolean](
        name = "feature_hydration_enable_mr_tweet_simcluster_feature",
        default = false
      )

  /**
   * Param to disable OON candidates based on tweetAuthor
   */
  object DisableOutNetworkTweetCandidatesFS
      etwittertends FSParam[Boolean](name = "oon_filtering_disable_oon_candidates", default = false)

  /**
   * Param to enable Local Viral Tweets
   */
  object EnableLocalViralTweets
      etwittertends FSParam[Boolean](name = "local_viral_tweets_enable", default = true)

  /**
   * Param to enable Etwitterplore Video Tweets
   */
  object EnableEtwitterploreVideoTweets
      etwittertends FSParam[Boolean](name = "etwitterplore_video_tweets_enable", default = false)

  /**
   * Param to enable List Recommendations
   */
  object EnableListRecommendations
      etwittertends FSParam[Boolean](name = "list_recommendations_enable", default = false)

  /**
   * Param to enable IDS List Recommendations
   */
  object EnableIDSListRecommendations
      etwittertends FSParam[Boolean](name = "list_recommendations_ids_enable", default = false)

  /**
   * Param to enable PopGeo List Recommendations
   */
  object EnablePopGeoListRecommendations
      etwittertends FSParam[Boolean](name = "list_recommendations_pop_geo_enable", default = false)

  /**
   * Param to control the inverter for fatigue between consecutive ListRecommendations
   */
  object ListRecommendationsPushInterval
      etwittertends FSBoundedParam[Duration](
        name = "list_recommendations_interval_days",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromDays
  }

  /**
   * Param to control the granularity of GeoHash for ListRecommendations
   */
  object ListRecommendationsGeoHashLength
      etwittertends FSBoundedParam[Int](
        name = "list_recommendations_geo_hash_length",
        default = 5,
        min = 3,
        matwitter = 5)

  /**
   * Param to control matwitterimum number of ListRecommendation pushes to receive in an interval
   */
  object MatwitterListRecommendationsPushGivenInterval
      etwittertends FSBoundedParam[Int](
        name = "list_recommendations_push_given_interval",
        default = 1,
        min = 0,
        matwitter = 10
      )

  /**
   * Param to control the subscriber count for list recommendation
   */
  object ListRecommendationsSubscriberCount
      etwittertends FSBoundedParam[Int](
        name = "list_recommendations_subscriber_count",
        default = 0,
        min = 0,
        matwitter = Integer.MAtwitter_VALUE)

  /**
   * Param to define dynamic inline action types for web notifications (both desktop web + mobile web)
   */
  object LocalViralTweetsBucket
      etwittertends FSParam[String](
        name = "local_viral_tweets_bucket",
        default = "high",
      )

  /**
   * List of CrTags to disable
   */
  object OONCandidatesDisabledCrTagParam
      etwittertends FSParam[Seq[String]](
        name = "oon_enable_oon_candidates_disabled_crtag",
        default = Seq.empty[String]
      )

  /**
   * List of Crt groups to disable
   */
  object OONCandidatesDisabledCrtGroupParam
      etwittertends FSEnumSeqParam[CrtGroupEnum.type](
        name = "oon_enable_oon_candidates_disabled_crt_group_ids",
        default = Seq.empty[CrtGroupEnum.Value],
        enum = CrtGroupEnum
      )

  /**
   * Param to enable launching video tweets in the Immersive Etwitterplore timeline
   */
  object EnableLaunchVideosInImmersiveEtwitterplore
      etwittertends FSParam[Boolean](name = "launch_videos_in_immersive_etwitterplore", default = false)

  /**
   * Param to enable Ntab Entries for Sports Event Notifications
   */
  object EnableNTabEntriesForSportsEventNotifications
      etwittertends FSParam[Boolean](
        name = "magicfanout_sports_event_enable_ntab_entries",
        default = false)

  /**
   * Param to enable Ntab Facepiles for teams in Sport Notifs
   */
  object EnableNTabFacePileForSportsEventNotifications
      etwittertends FSParam[Boolean](
        name = "magicfanout_sports_event_enable_ntab_facepiles",
        default = false)

  /**
   * Param to enable Ntab Override for Sports Event Notifications
   */
  object EnableNTabOverrideForSportsEventNotifications
      etwittertends FSParam[Boolean](
        name = "magicfanout_sports_event_enable_ntab_override",
        default = false)

  /**
   * Param to control the interval for MF Product Launch Notifs
   */
  object ProductLaunchPushIntervalInHours
      etwittertends FSBoundedParam[Duration](
        name = "product_launch_fatigue_push_interval_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of MF Product Launch Notifs in a period of time
   */
  object ProductLaunchMatwitterNumberOfPushesInInterval
      etwittertends FSBoundedParam[Int](
        name = "product_launch_fatigue_matwitter_pushes_in_interval",
        default = 1,
        min = 0,
        matwitter = 10)

  /**
   * Param to control the minInterval for fatigue between consecutive MF Product Launch Notifs
   */
  object ProductLaunchMinIntervalFatigue
      etwittertends FSBoundedParam[Duration](
        name = "product_launch_fatigue_min_interval_consecutive_pushes_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the interval for MF New Creator Notifs
   */
  object NewCreatorPushIntervalInHours
      etwittertends FSBoundedParam[Duration](
        name = "new_creator_fatigue_push_interval_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of MF New Creator Notifs in a period of time
   */
  object NewCreatorPushMatwitterNumberOfPushesInInterval
      etwittertends FSBoundedParam[Int](
        name = "new_creator_fatigue_matwitter_pushes_in_interval",
        default = 1,
        min = 0,
        matwitter = 10)

  /**
   * Param to control the minInterval for fatigue between consecutive MF New Creator Notifs
   */
  object NewCreatorPushMinIntervalFatigue
      etwittertends FSBoundedParam[Duration](
        name = "new_creator_fatigue_min_interval_consecutive_pushes_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the interval for MF New Creator Notifs
   */
  object CreatorSubscriptionPushIntervalInHours
      etwittertends FSBoundedParam[Duration](
        name = "creator_subscription_fatigue_push_interval_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to control the matwitterimum number of MF New Creator Notifs in a period of time
   */
  object CreatorSubscriptionPushMatwitterNumberOfPushesInInterval
      etwittertends FSBoundedParam[Int](
        name = "creator_subscription_fatigue_matwitter_pushes_in_interval",
        default = 1,
        min = 0,
        matwitter = 10)

  /**
   * Param to control the minInterval for fatigue between consecutive MF New Creator Notifs
   */
  object CreatorSubscriptionPushhMinIntervalFatigue
      etwittertends FSBoundedParam[Duration](
        name = "creator_subscription_fatigue_min_interval_consecutive_pushes_in_hours",
        default = 24.hours,
        min = Duration.Bottom,
        matwitter = Duration.Top)
      with HasDurationConversion {
    override val durationConversion = DurationConversion.FromHours
  }

  /**
   * Param to define the landing page deeplink of product launch notifications
   */
  object ProductLaunchLandingPageDeepLink
      etwittertends FSParam[String](
        name = "product_launch_landing_page_deeplink",
        default = ""
      )

  /**
   * Param to define the tap through of product launch notifications
   */
  object ProductLaunchTapThrough
      etwittertends FSParam[String](
        name = "product_launch_tap_through",
        default = ""
      )

  /**
   * Param to skip checking isTargetBlueVerified
   */
  object DisableIsTargetBlueVerifiedPredicate
      etwittertends FSParam[Boolean](
        name = "product_launch_disable_is_target_blue_verified_predicate",
        default = false
      )

  /**
   * Param to enable Ntab Entries for Sports Event Notifications
   */
  object EnableNTabEntriesForProductLaunchNotifications
      etwittertends FSParam[Boolean](name = "product_launch_enable_ntab_entry", default = true)

  /**
   * Param to skip checking isTargetLegacyVerified
   */
  object DisableIsTargetLegacyVerifiedPredicate
      etwittertends FSParam[Boolean](
        name = "product_launch_disable_is_target_legacy_verified_predicate",
        default = false
      )

  /**
   * Param to enable checking isTargetSuperFollowCreator
   */
  object EnableIsTargetSuperFollowCreatorPredicate
      etwittertends FSParam[Boolean](
        name = "product_launch_is_target_super_follow_creator_predicate_enabled",
        default = false
      )

  /**
   * Param to enable Spammy Tweet filter
   */
  object EnableSpammyTweetFilter
      etwittertends FSParam[Boolean](
        name = "health_signal_store_enable_spammy_tweet_filter",
        default = false)

  /**
   * Param to enable Push to Home Android
   */
  object EnableTweetPushToHomeAndroid
      etwittertends FSParam[Boolean](name = "push_to_home_tweet_recs_android", default = false)

  /**
   * Param to enable Push to Home iOS
   */
  object EnableTweetPushToHomeiOS
      etwittertends FSParam[Boolean](name = "push_to_home_tweet_recs_iOS", default = false)

  /**
   * Param to set Spammy Tweet score threshold for OON candidates
   */
  object SpammyTweetOonThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_spammy_tweet_oon_threshold",
        default = 1.1,
        min = 0.0,
        matwitter = 1.1
      )

  object NumFollowerThresholdForHealthAndQualityFilters
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_num_follower_threshold_for_health_and_quality_filters",
        default = 10000000000.0,
        min = 0.0,
        matwitter = 10000000000.0
      )

  object NumFollowerThresholdForHealthAndQualityFiltersPreranking
      etwittertends FSBoundedParam[Double](
        name =
          "health_signal_store_num_follower_threshold_for_health_and_quality_filters_preranking",
        default = 10000000.0,
        min = 0.0,
        matwitter = 10000000000.0
      )

  /**
   * Param to set Spammy Tweet score threshold for IN candidates
   */
  object SpammyTweetInThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_spammy_tweet_in_threshold",
        default = 1.1,
        min = 0.0,
        matwitter = 1.1
      )

  /**
   * Param to control bucketing for the Spammy Tweet score
   */
  object SpammyTweetBucketingThreshold
      etwittertends FSBoundedParam[Double](
        name = "health_signal_store_spammy_tweet_bucketing_threshold",
        default = 1.0,
        min = 0.0,
        matwitter = 1.0
      )

  /**
   * Param to specify the matwitterimum number of Etwitterplore Video Tweets to request
   */
  object MatwitterEtwitterploreVideoTweets
      etwittertends FSBoundedParam[Int](
        name = "etwitterplore_video_tweets_matwitter_candidates",
        default = 100,
        min = 0,
        matwitter = 500
      )

  /**
   * Param to enable social contetwittert feature set
   */
  object EnableBoundedFeatureSetForSocialContetwittert
      etwittertends FSParam[Boolean](
        name = "feature_hydration_user_social_contetwittert_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable stp user social contetwittert feature set
   */
  object EnableStpBoundedFeatureSetForUserSocialContetwittert
      etwittertends FSParam[Boolean](
        name = "feature_hydration_stp_social_contetwittert_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable core user history social contetwittert feature set
   */
  object EnableCoreUserHistoryBoundedFeatureSetForSocialContetwittert
      etwittertends FSParam[Boolean](
        name = "feature_hydration_core_user_history_social_contetwittert_bounded_feature_set_enable",
        default = true)

  /**
   * Param to enable skipping post-ranking filters
   */
  object SkipPostRankingFilters
      etwittertends FSParam[Boolean](
        name = "frigate_push_modeling_skip_post_ranking_filters",
        default = false)

  object MagicFanoutSimClusterDotProductNonHeavyUserThreshold
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_magicfanout_simcluster_non_heavy_user_dot_product_threshold",
        default = 0.0,
        min = 0.0,
        matwitter = 100.0
      )

  object MagicFanoutSimClusterDotProductHeavyUserThreshold
      etwittertends FSBoundedParam[Double](
        name = "frigate_push_magicfanout_simcluster_heavy_user_dot_product_threshold",
        default = 10.0,
        min = 0.0,
        matwitter = 100.0
      )

  object EnableReducedFatigueRulesForSeeLessOften
      etwittertends FSParam[Boolean](
        name = "seelessoften_enable_reduced_fatigue",
        default = false
      )
}
