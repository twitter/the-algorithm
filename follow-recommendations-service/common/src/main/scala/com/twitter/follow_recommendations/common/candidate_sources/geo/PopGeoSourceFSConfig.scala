package com.twitter.follow_recommendations.common.candidate_sources.geo

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.FSParam
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopGeoSourceFSConfig @Inject() () extends FeatureSwitchConfig {
  override val intFSParams: Seq[FSBoundedParam[Int] with FSName] = Seq(
    PopGeoSourceParams.PopGeoSourceGeoHashMaxPrecision,
    PopGeoSourceParams.PopGeoSourceMaxResultsPerPrecision,
    PopGeoSourceParams.PopGeoSourceGeoHashMinPrecision,
  )
  override val booleanFSParams: Seq[FSParam[Boolean] with FSName] = Seq(
    PopGeoSourceParams.PopGeoSourceReturnFromAllPrecisions,
  )
}
