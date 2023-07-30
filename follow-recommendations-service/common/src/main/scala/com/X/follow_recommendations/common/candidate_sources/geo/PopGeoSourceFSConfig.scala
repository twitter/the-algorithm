package com.X.follow_recommendations.common.candidate_sources.geo

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.timelines.configapi.FSBoundedParam
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.FSParam
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
