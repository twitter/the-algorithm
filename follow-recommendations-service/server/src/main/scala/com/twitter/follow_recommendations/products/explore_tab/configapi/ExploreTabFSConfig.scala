package com.ExTwitter.follow_recommendations.products.explore_tab.configapi

import com.ExTwitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.ExTwitter.follow_recommendations.products.explore_tab.configapi.ExploreTabParams._
import com.ExTwitter.timelines.configapi.FSName
import com.ExTwitter.timelines.configapi.Param
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExploreTabFSConfig @Inject() () extends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(EnableProductForSoftUser)
}
