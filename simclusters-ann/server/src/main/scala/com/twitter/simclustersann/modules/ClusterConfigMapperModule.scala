package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.relevance_platform.simclustersann.multicluster.ClusterConfigMapper
import javax.inject.Singleton

object ClusterConfigMapperModule extends TwitterModule {
  @Singleton
  @Provides
  def providesClusterConfigMapper(
  ): ClusterConfigMapper = {
    ClusterConfigMapper
  }
}
