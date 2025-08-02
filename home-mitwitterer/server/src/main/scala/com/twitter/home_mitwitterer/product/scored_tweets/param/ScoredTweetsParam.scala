package com.twitter.home_mitwitterer.product.scored_tweets.param

import com.twitter.conversions.DurationOps._
import com.twitter.home_mitwitterer.param.decider.DeciderKey
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
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsInNetworkCandidatePipeline)

    object EnableTweetMitwittererParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsTweetMitwittererCandidatePipeline)

    object EnableUtegParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsUtegCandidatePipeline)

    object EnableFrsParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsFrsCandidatePipeline)

    object EnableListsParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsListsCandidatePipeline)

    object EnablePopularVideosParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsPopularVideosCandidatePipeline)

    object EnableBackfillParam
        etwittertends BooleanDeciderParam(DeciderKey.EnableScoredTweetsBackfillCandidatePipeline)
  }

  object EnableBackfillCandidatePipelineParam
      etwittertends FSParam[Boolean](
        name = "scored_tweets_enable_backfill_candidate_pipeline",
        default = true
      )

  object QualityFactor {
    object InNetworkMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_earlybird_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 10000
        )

    object UtegMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_uteg_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 10000
        )

    object FrsMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_frs_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 10000
        )

    object TweetMitwittererMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_tweet_mitwitterer_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 10000
        )

    object ListsMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_lists_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 100
        )

    object PopularVideosMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_popular_videos_matwitter_tweets_to_score",
          default = 40,
          min = 0,
          matwitter = 10000
        )

    object BackfillMatwitterTweetsToScoreParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_backfill_matwitter_tweets_to_score",
          default = 500,
          min = 0,
          matwitter = 10000
        )
  }

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "scored_tweets_server_matwitter_results",
        default = 120,
        min = 1,
        matwitter = 500
      )

  object MatwitterInNetworkResultsParam
      etwittertends FSBoundedParam[Int](
        name = "scored_tweets_matwitter_in_network_results",
        default = 60,
        min = 1,
        matwitter = 500
      )

  object MatwitterOutOfNetworkResultsParam
      etwittertends FSBoundedParam[Int](
        name = "scored_tweets_matwitter_out_of_network_results",
        default = 60,
        min = 1,
        matwitter = 500
      )

  object CachedScoredTweets {
    object TTLParam
        etwittertends FSBoundedParam[Duration](
          name = "scored_tweets_cached_scored_tweets_ttl_minutes",
          default = 3.minutes,
          min = 0.minute,
          matwitter = 60.minutes
        )
        with HasDurationConversion {
      override val durationConversion: DurationConversion = DurationConversion.FromMinutes
    }

    object MinCachedTweetsParam
        etwittertends FSBoundedParam[Int](
          name = "scored_tweets_cached_scored_tweets_min_cached_tweets",
          default = 30,
          min = 0,
          matwitter = 1000
        )
  }

  object Scoring {
    object HomeModelParam
        etwittertends FSParam[String](name = "scored_tweets_home_model", default = "Home")

    object ModelWeights {

      object FavParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_fav",
            default = 1.0,
            min = 0.0,
            matwitter = 100.0
          )

      object RetweetParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_retweet",
            default = 1.0,
            min = 0.0,
            matwitter = 100.0
          )

      object ReplyParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_reply",
            default = 1.0,
            min = 0.0,
            matwitter = 100.0
          )

      object GoodProfileClickParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_profile_click",
            default = 1.0,
            min = 0.0,
            matwitter = 1000000.0
          )

      object VideoPlayback50Param
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_video_playback50",
            default = 1.0,
            min = 0.0,
            matwitter = 100.0
          )

      object ReplyEngagedByAuthorParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_reply_engaged_by_author",
            default = 1.0,
            min = 0.0,
            matwitter = 200.0
          )

      object GoodClickParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_click",
            default = 1.0,
            min = 0.0,
            matwitter = 1000000.0
          )

      object GoodClickV2Param
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_good_click_v2",
            default = 1.0,
            min = 0.0,
            matwitter = 1000000.0
          )

      object TweetDetailDwellParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_tweet_detail_dwell",
            default = 0.0,
            min = 0.0,
            matwitter = 100.0
          )

      object ProfileDwelledParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_profile_dwelled",
            default = 0.0,
            min = 0.0,
            matwitter = 100.0
          )

      object BookmarkParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_bookmark",
            default = 0.0,
            min = 0.0,
            matwitter = 100.0
          )

      object ShareParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_share",
            default = 0.0,
            min = 0.0,
            matwitter = 100.0
          )

      object ShareMenuClickParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_share_menu_click",
            default = 0.0,
            min = 0.0,
            matwitter = 100.0
          )

      object NegativeFeedbackV2Param
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_negative_feedback_v2",
            default = 1.0,
            min = -1000.0,
            matwitter = 0.0
          )

      object ReportParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_report",
            default = 1.0,
            min = -20000.0,
            matwitter = 0.0
          )

      object WeakNegativeFeedbackParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_weak_negative_feedback",
            default = 0.0,
            min = -1000.0,
            matwitter = 0.0
          )

      object StrongNegativeFeedbackParam
          etwittertends FSBoundedParam[Double](
            name = "scored_tweets_model_weight_strong_negative_feedback",
            default = 0.0,
            min = -1000.0,
            matwitter = 0.0
          )
    }
  }

  object EnableSimClustersSimilarityFeatureHydrationDeciderParam
      etwittertends BooleanDeciderParam(decider = DeciderKey.EnableSimClustersSimilarityFeatureHydration)

  object CompetitorSetParam
      etwittertends FSParam[Set[Long]](name = "scored_tweets_competitor_list", default = Set.empty)

  object CompetitorURLSeqParam
      etwittertends FSParam[Seq[String]](name = "scored_tweets_competitor_url_list", default = Seq.empty)

  object BlueVerifiedAuthorInNetworkMultiplierParam
      etwittertends FSBoundedParam[Double](
        name = "scored_tweets_blue_verified_author_in_network_multiplier",
        default = 4.0,
        min = 0.0,
        matwitter = 100.0
      )

  object BlueVerifiedAuthorOutOfNetworkMultiplierParam
      etwittertends FSBoundedParam[Double](
        name = "scored_tweets_blue_verified_author_out_of_network_multiplier",
        default = 2.0,
        min = 0.0,
        matwitter = 100.0
      )

  object CreatorInNetworkMultiplierParam
      etwittertends FSBoundedParam[Double](
        name = "scored_tweets_creator_in_network_multiplier",
        default = 1.1,
        min = 0.0,
        matwitter = 100.0
      )

  object CreatorOutOfNetworkMultiplierParam
      etwittertends FSBoundedParam[Double](
        name = "scored_tweets_creator_out_of_network_multiplier",
        default = 1.3,
        min = 0.0,
        matwitter = 100.0
      )

  object OutOfNetworkScaleFactorParam
      etwittertends FSBoundedParam[Double](
        name = "scored_tweets_out_of_network_scale_factor",
        default = 1.0,
        min = 0.0,
        matwitter = 100.0
      )

  object EnableScribeScoredCandidatesParam
      etwittertends FSParam[Boolean](name = "scored_tweets_enable_scribing", default = false)

  object EarlybirdTensorflowModel {

    object InNetworkParam
        etwittertends FSParam[String](
          name = "scored_tweets_in_network_earlybird_tensorflow_model",
          default = "timelines_recap_replica")

    object FrsParam
        etwittertends FSParam[String](
          name = "scored_tweets_frs_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica")

    object UtegParam
        etwittertends FSParam[String](
          name = "scored_tweets_uteg_earlybird_tensorflow_model",
          default = "timelines_rectweet_replica")
  }

}
