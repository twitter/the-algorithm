package com.twitter.follow_recommendations.products.etwitterplore_tab.configapi

import com.twitter.follow_recommendations.configapi.common.FeatureSwitchConfig
import com.twitter.follow_recommendations.products.etwitterplore_tab.configapi.EtwitterploreTabParams._
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class EtwitterploreTabFSConfig @Inject() () etwittertends FeatureSwitchConfig {
  override val booleanFSParams: Seq[Param[Boolean] with FSName] =
    Seq(EnableProductForSoftUser)
}
