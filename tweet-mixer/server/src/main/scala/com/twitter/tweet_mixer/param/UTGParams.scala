package com.twitter.tweet_mixer.param

import com.twitter.recos.user_tweet_graph.thriftscala.RelatedTweetSimilarityAlgorithm
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSEnumParam
import com.twitter.timelines.configapi.FSParam

//UTG Related Params
object UTGParams {

  object SimilarityAlgorithmEnum extends Enumeration {
    val Cosine, LogCosine, PowerDegree, PowerDegreeWithModifiedScorePreFactor,
      CoocurrenceNormLogDegree, CoocurrenceNormLinearDegree, CoocurrenceNormPowerDegree,
      LogBiasCoocurrenceNormLogDegree, LogBiasCoocurrenceNormPowerDegree: Value = Value
    val enumToSimilarityAlgorithmMap: Map[
      SimilarityAlgorithmEnum.Value,
      RelatedTweetSimilarityAlgorithm
    ] = Map(
      Cosine -> RelatedTweetSimilarityAlgorithm.Cosine,
      LogCosine -> RelatedTweetSimilarityAlgorithm.LogCosine,
      PowerDegree -> RelatedTweetSimilarityAlgorithm.PowerDegree,
      PowerDegreeWithModifiedScorePreFactor -> RelatedTweetSimilarityAlgorithm.PowerDegreeWithModifiedScorePreFactor,
      CoocurrenceNormLogDegree -> RelatedTweetSimilarityAlgorithm.CoocurrenceNormLogDegree,
      CoocurrenceNormLinearDegree -> RelatedTweetSimilarityAlgorithm.CoocurrenceNormLinearDegree,
      CoocurrenceNormPowerDegree -> RelatedTweetSimilarityAlgorithm.CoocurrenceNormPowerDegree,
      LogBiasCoocurrenceNormLogDegree -> RelatedTweetSimilarityAlgorithm.LogBiasCoocurrenceNormLogDegree,
      LogBiasCoocurrenceNormPowerDegree -> RelatedTweetSimilarityAlgorithm.LogBiasCoocurrenceNormPowerDegree
    )
  }
  object MinCoOccurrenceParam
      extends FSBoundedParam[Int](
        name = "user_tweet_graph_min_co_occurrence",
        default = 3,
        min = 0,
        max = 500
      )

  object MaxNumFollowersParam
      extends FSBoundedParam[Int](
        name = "user_tweet_graph_max_num_followers",
        default = 500,
        min = 100,
        max = 1000
      )

  object TweetBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "user_tweet_graph_tweet_based_min_score",
        default = 0.5,
        min = 0.0,
        max = 10.0
      )

  object TweetBasedDegreeExponentParam
      extends FSBoundedParam[Double](
        name = "user_tweet_graph_tweet_based_degree_exponent",
        default = 0.5,
        min = 0.1,
        max = 1.0
      )

  object ConsumersBasedMinScoreParam
      extends FSBoundedParam[Double](
        name = "user_tweet_graph_consumers_based_min_score",
        default = 4.0,
        min = 0.0,
        max = 10.0
      )

  object MaxConsumerSeedsNumParam
      extends FSBoundedParam[Int](
        name = "user_tweet_graph_max_user_seeds_num",
        default = 100,
        min = 0,
        max = 300
      )

  object CoverageExpansionOldTweetEnabledParam
      extends FSParam[Boolean](
        name = "user_tweet_graph_coverage_expansion_old_tweet_enabled",
        default = false
      )

  object EnableUTGCacheParam
      extends FSParam[Boolean](
        name = "user_tweet_graph_enable_cache",
        default = true
      )

  object SimilarityAlgorithm
      extends FSEnumParam[SimilarityAlgorithmEnum.type](
        name = "user_tweet_graph_similarity_algorithm_id",
        default = SimilarityAlgorithmEnum.LogCosine,
        enum = SimilarityAlgorithmEnum
      )

  object EnableTweetEmbeddingBasedFilteringParam
      extends FSParam[Boolean](
        name = "user_tweet_graph_enable_tweet_embedding_based_filtering",
        default = false
      )

  object OutlierFilterPercentileThresholdParam
      extends FSBoundedParam[Int](
        name = "user_tweet_graph_outlier_filter_percentile_threshold",
        default = 80,
        min = 0,
        max = 100
      )

  object OutlierMinRequiredSignalsParam
      extends FSBoundedParam[Int](
        name = "user_tweet_graph_outlier_min_required_signals",
        default = 10,
        min = 0,
        max = 100
      )

  val booleanFSOverrides =
    Seq(
      CoverageExpansionOldTweetEnabledParam,
      EnableUTGCacheParam,
      EnableTweetEmbeddingBasedFilteringParam,
    )

  val boundedIntFSOverrides =
    Seq(
      MinCoOccurrenceParam,
      MaxConsumerSeedsNumParam,
      OutlierFilterPercentileThresholdParam,
      OutlierMinRequiredSignalsParam
    )

  val boundedDoubleFSOverrides =
    Seq(
      TweetBasedMinScoreParam,
      ConsumersBasedMinScoreParam,
      TweetBasedDegreeExponentParam
    )

  val enumFSOverrides = Seq(SimilarityAlgorithm)
}
