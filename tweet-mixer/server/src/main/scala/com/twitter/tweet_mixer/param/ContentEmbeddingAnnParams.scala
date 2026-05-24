package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam
//UVG Related Params
object ContentEmbeddingAnnParams {
  object MinScoreThreshold
      extends FSBoundedParam[Double](
        name = "content_embedding_ann_tweets_min_score_threshold",
        min = 0,
        max = 1,
        default = 0.6
      )

  object MaxScoreThreshold
      extends FSBoundedParam[Double](
        name = "content_embedding_ann_tweets_max_score_threshold",
        min = 0,
        max = 1,
        default = 0.8
      )

  object NumberOfCandidatesPerPost
      extends FSBoundedParam[Int](
        name = "content_embedding_ann_tweets_num_candidates_per_post",
        min = 0,
        max = 50,
        default = 5
      )

  object DecayByCountry
      extends FSParam[Boolean](
        name = "content_embedding_ann_tweets_decay_by_country",
        default = true
      )

  object IncludeTextSource
      extends FSParam[Boolean](
        name = "content_embedding_ann_tweets_include_text_source",
        default = true
      )

  object IncludeMediaSource
      extends FSParam[Boolean](
        name = "content_embedding_ann_tweets_include_media_source",
        default = true
      )

  val boundedDoubleFSOverrides =
    Seq(MaxScoreThreshold, MinScoreThreshold)

  val boundedIntFSOverrides =
    Seq(NumberOfCandidatesPerPost)

  val booleanFSOverrides =
    Seq(IncludeMediaSource, IncludeTextSource, DecayByCountry)
}
