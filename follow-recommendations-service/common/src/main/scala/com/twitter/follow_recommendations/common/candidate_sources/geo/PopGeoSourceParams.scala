package com.twitter.follow_recommendations.common.candidate_sources.geo

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object PopGeoSourceParams {
  case object PopGeoSourceGeoHashMinPrecision
      extends FSBoundedParam[Int](
        "pop_geo_source_geo_hash_min_precision",
        default = 2,
        min = 0,
        max = 10)

  case object PopGeoSourceGeoHashMaxPrecision
      extends FSBoundedParam[Int](
        "pop_geo_source_geo_hash_max_precision",
        default = 4,
        min = 0,
        max = 10)

  case object PopGeoSourceReturnFromAllPrecisions
      extends FSParam[Boolean]("pop_geo_source_return_from_all_precisions", default = false)

  case object PopGeoSourceMaxResultsPerPrecision
      extends FSBoundedParam[Int](
        "pop_geo_source_max_results_per_precision",
        default = 200,
        min = 0,
        max = 1000)
}
