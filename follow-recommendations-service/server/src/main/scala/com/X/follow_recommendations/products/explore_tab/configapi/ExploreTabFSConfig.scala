package com.X.follow_recommendations.products.explore_tab.configapi

import com.X.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.X.follow_recommendations.products.explore_tab.configapi.ExploreTabParams._
import com.X.timelines.configapi.FSName
import com.X.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreTabFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(EnableProductForSoftUser)
}
