package com.twitter.follow_recommendations.common.candidate_sources.geo

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object PopGeoQualityFollowSourceParams {
  case object CandidateSourceEnabled
      etwittertends FSParam[Boolean]("pop_geo_quality_follow_source_enabled", false)

  case object PopGeoSourceGeoHashMinPrecision
      etwittertends FSBoundedParam[Int](
        "pop_geo_quality_follow_source_geo_hash_min_precision",
        default = 2,
        min = 0,
        matwitter = 10)

  case object PopGeoSourceGeoHashMatwitterPrecision
      etwittertends FSBoundedParam[Int](
        "pop_geo_quality_follow_source_geo_hash_matwitter_precision",
        default = 3,
        min = 0,
        matwitter = 10)

  case object PopGeoSourceReturnFromAllPrecisions
      etwittertends FSParam[Boolean](
        "pop_geo_quality_follow_source_return_from_all_precisions",
        default = false)

  case object PopGeoSourceMatwitterResultsPerPrecision
      etwittertends FSBoundedParam[Int](
        "pop_geo_quality_follow_source_matwitter_results_per_precision",
        default = 200,
        min = 0,
        matwitter = 1000)

  case object CandidateSourceWeight
      etwittertends FSBoundedParam[Double](
        "pop_geo_quality_follow_source_weight",
        default = 200,
        min = 0.001,
        matwitter = 2000)
}
