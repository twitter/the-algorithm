package com.twitter.follow_recommendations.common.candidate_sources.geo

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopGeoQualityFollowSourceFSConfig @Inject() () extends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int] with FSName] = Seq(
    PopGeoQualityFollowSourceParams.PopGeoSourceGeoHashMaxPrecision,
    PopGeoQualityFollowSourceParams.PopGeoSourceGeoHashMinPrecision,
    PopGeoQualityFollowSourceParams.PopGeoSourceMaxResultsPerPrecision
  )
  override val doubleFSParams: Seq[FSBoundedParam[Double] with FSName] = Seq(
    PopGeoQualityFollowSourceParams.CandidateSourceWeight
  )
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    PopGeoQualityFollowSourceParams.CandidateSourceEnabled,
    PopGeoQualityFollowSourceParams.PopGeoSourceReturnFromAllPrecisions
  )
}
