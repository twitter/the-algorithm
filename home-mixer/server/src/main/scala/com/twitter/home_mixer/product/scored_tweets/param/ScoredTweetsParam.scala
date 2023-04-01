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

  object CrMixerSource {
    object EnableCandidatePipelineParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsCrMixerCandidatePipeline)
  }

  object FrsTweetSource {
    object EnableCandidatePipelineParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsFrsCandidatePipeline)
  }

  object InNetworkSource {
    object EnableCandidatePipelineParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsInNetworkCandidatePipeline)
  }

  object QualityFactor {
    object MaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_max_tweets_to_score",
          default = 1100,
          min = 0,
          max = 10000
        )

    object CrMixerMaxTweetsToScoreParam
        extends FSBoundedParam[Int](
          name = "scored_tweets_quality_factor_cr_mixer_max_tweets_to_score",
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
  object UtegSource {
    object EnableCandidatePipelineParam
        extends BooleanDeciderParam(DeciderKey.EnableScoredTweetsUtegCandidatePipeline)
  }

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
    }
  }

  object EnableSimClustersSimilarityFeatureHydrationDeciderParam
      extends BooleanDeciderParam(decider = DeciderKey.EnableSimClustersSimilarityFeatureHydration)

  object CompetitorSetParam
      extends FSParam[Set[Long]](name = "scored_tweets_competitor_list", default = Set.empty)

  object CompetitorURLSeqParam
      extends FSParam[Seq[String]](name = "scored_tweets_competitor_url_list", default = Seq.empty)
}
