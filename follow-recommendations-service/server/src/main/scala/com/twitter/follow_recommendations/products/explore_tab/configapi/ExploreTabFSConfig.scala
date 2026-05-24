package com.twitter.follow_recommendations.products.explore_tab.configapi

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.follow_recommendations.products.explore_tab.configapi.ExploreTabParams._
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreTabFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(EnableProductForSoftUser)
}
