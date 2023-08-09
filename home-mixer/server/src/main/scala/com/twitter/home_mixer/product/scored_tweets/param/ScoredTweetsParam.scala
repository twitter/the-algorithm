package com.twitter.home_mixer.product.scored_tweets.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.timelines.configapi.DurationConversion
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelines.configapi.HasDurationConversion
import com.twitter.timelines.configapi.decider.BooleanDeciderParam
import com.twitter.util.Duration

object ScoredTweetsParam {
  val SupportedClientFSName = "scored_tweets_supported_client"

  object CandidatePipeline {
    object EnableInNetworkParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsInNetworkCandidatePipeline)

    object EnableTweetMixerParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsTweetMixerCandidatePipeline)

    object EnableUtegParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsUtegCandidatePipeline)

    object EnableFrsParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsFrsCandidatePipeline)

    object EnableListsParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsListsCandidatePipeline)

    object EnablePopularVideosParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsPopularVideosCandidatePipeline)

    object EnableBackfillParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsBackfillCandidatePipeline)
  }

  object EnableBackfillCandidatePipelineParam
      extends FSParam[Boolean](
        name = "scored_tweets_enable_backfill_candidate_pipeline",
        default = true
      )

  object QualityFactor {
    object InNetworkMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_earlybird_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 10000
        )

    object UtegMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_uteg_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 10000
        )

    object FrsMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_frs_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 10000
        )

    object TweetMixerMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_tweet_mixer_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 10000
        )

    object ListsMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_lists_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 100
        )

    object PopularVideosMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_popular_videos_max_tweets_to_score",
          default = 40,
          min = 0,
          max = 10000
        )

    object BackfillMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_backfill_max_tweets_to_score",
          default = 500,
          min = 0,
          max = 10000
        )
  }

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_server_max_results",
        default = 120,
        min = 1,
        max = 500
      )

  object MaxInNetworkResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_max_in_network_results",
        default = 60,
        min = 1,
        max = 500
      )

  object MaxOutOfNetworkResultsParam
      extends FSBoundedParam[Int](
        name = "scored_tweets_max_out_of_network_results",
        default = 60,
        min = 1,
        max = 500
      )

  object CachedScoredTweets {
    object TTLParam
        extends FSBoundedParam[Duration](
          name = "scored_tweets_cached_scored_tweets_ttl_minutes",
          default = 3.minutes,
          min = 0.minute,
          max = 60.minutes
        )
        with HasDurationConversion {
      override val durationConversion: DurationConversion = DurationConversion.FromMinutes
    }

    object MinCachedTweetsParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_cached_scored_tweets_min_cached_tweets",
          default = 30,
          min = 0,
          max = 1000
        )
  }

  object Scoring {
    object HomeModelParam
        extends FSParam[String](name = "scored_tweets_home_model", default = "Home")

    object ModelWeights {

      object FavParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_fav",
            default = 1.0,
            min = 0.0,
            max = 100.0
          )

      object RetweetParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_retweet",
            default = 1.0,
            min = 0.0,
            max = 100.0
          )

      object ReplyParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_reply",
            default = 1.0,
            min = 0.0,
            max = 100.0
          )

      object GoodProfileClickParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_profile_click",
            default = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      object VideoPlayback50Param
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_video_playback50",
            default = 1.0,
            min = 0.0,
            max = 100.0
          )

      object ReplyEngagedByAuthorParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_reply_engaged_by_author",
            default = 1.0,
            min = 0.0,
            max = 200.0
          )

      object GoodClickParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_click",
            default = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      object GoodClickV2Param
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_click_v2",
            default = 1.0,
            min = 0.0,
            max = 1000000.0
          )

      object TweetDetailDwellParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_tweet_detail_dwell",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object ProfileDwelledParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_profile_dwelled",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object BookmarkParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_bookmark",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object ShareParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_share",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object ShareMenuClickParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_share_menu_click",
            default = 0.0,
            min = 0.0,
            max = 100.0
          )

      object NegativeFeedbackV2Param
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_negative_feedback_v2",
            default = 1.0,
            min = -1000.0,
            max = 0.0
          )

      object ReportParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_report",
            default = 1.0,
            min = -20000.0,
            max = 0.0
          )

      object WeakNegativeFeedbackParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_weak_negative_feedback",
            default = 0.0,
            min = -1000.0,
            max = 0.0
          )

      object StrongNegativeFeedbackParam
          extends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_strong_negative_feedback",
            default = 0.0,
            min = -1000.0,
            max = 0.0
          )
    }
  }

  object EnableSimClustersSimilarityFeatureHydrationDeciderParam
      extends BooleanDeciderParam(decider = DeciderKey.EnableSimClustersSimilarityFeatureHydration)

  object CompetitorSetParam
      extends FSParam[Set[Long]](name = "scored_tweets_competitor_list", default = Set.empty)

  object CompetitorURLSeqParam
      extends FSParam[Seq[String]](name = "scored_tweets_competitor_url_list", default = Seq.empty)

  object BlueVerifiedAuthorInNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_blue_verified_author_in_network_multiplier",
        default = 4.0,
        min = 0.0,
        max = 100.0
      )

  object BlueVerifiedAuthorOutOfNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_blue_verified_author_out_of_network_multiplier",
        default = 2.0,
        min = 0.0,
        max = 100.0
      )

  object CreatorInNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_creator_in_network_multiplier",
        default = 1.1,
        min = 0.0,
        max = 100.0
      )

  object CreatorOutOfNetworkMultiplierParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_creator_out_of_network_multiplier",
        default = 1.3,
        min = 0.0,
        max = 100.0
      )

  object OutOfNetworkScaleFactorParam
      extends FSBoundedParam[Double](
        name = "scored_tweets_out_of_network_scale_factor",
        default = 1.0,
        min = 0.0,
        max = 100.0
      )

  object EnableScribeScoredCandidatesParam
      extends FSParam[Boolean](name = "scored_tweets_enable_scribing", default = false)

  object EarlybirdTensorflowModel {

    object InNetworkParam
        extends FSParam[String](
          name = "scored_tweets_in_network_earlybird_tensorflow_model",
          default = "timelines_recap_replica")

    object FrsParam
        extends FSParam[String](
          name = "scored_tweets_frs_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica")

    object UtegParam
        extends FSParam[String](
          name = "scored_tweets_uteg_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica")
  }

}
