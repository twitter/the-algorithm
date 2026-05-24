package com.twitter.tweet_mixer.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object PopularGeoTweetsParams {

  // Enables Pop Geo candidate source
  object PopularGeoTweetsEnable
      extends FSParam[Boolean](
        name = "popular_geo_tweets_enabled",
        default = false
      )

  object GeoSourceIds
      extends FSParam[Seq[String]](
        name = "popular_geo_tweets_source_ids",
        default = Seq(
          "TOP_ER_RANK_BIRD_FILTER_VERIFIED",
          "TOP_MEDIA_ER_RANK_BIRD_FILTER_VERIFIED",
          "TOP_WER_VERIFIED",
          "TOP_MEDIA_WER_VERIFIED"
        )
      )

  object MaxNumCandidatesPerTripSource
      extends FSBoundedParam[Int](
        name = "popular_geo_tweets_max_num_candidates_per_source",
        min = 1,
        max = 1000,
        default = 200
      )

  object MaxNumPopGeoCandidates
      extends FSBoundedParam[Int](
        name = "popular_geo_tweets_max_num_candidates",
        min = 1,
        max = 1000,
        default = 400
      )

  val booleanFSOverrides = Seq(PopularGeoTweetsEnable)

  val boundedIntFSOverrides =
    Seq(MaxNumCandidatesPerTripSource, MaxNumPopGeoCandidates)

  val stringSeqFSOverrides = Seq(GeoSourceIds)
}
