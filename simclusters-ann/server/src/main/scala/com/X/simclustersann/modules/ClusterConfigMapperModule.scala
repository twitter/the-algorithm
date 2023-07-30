package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.inject.XModule
import com.X.relevance_platform.simclustersann.multicluster.ClusterConfigMapper
import javax.inject.Singleton

object ClusterConfigMapperModule extends XModule {
  @Singleton
  @Provides
  def providesClusterConfigMapper(
  ): ClusterConfigMapper = {
    ClusterConfigMapper
  }
}
