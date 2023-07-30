package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.finagle.mtls.authentication.ServiceIdentifier
import com.X.inject.XModule
import com.X.relevance_platform.simclustersann.multicluster.ClusterConfig
import com.X.relevance_platform.simclustersann.multicluster.ClusterConfigMapper
import com.X.simclustersann.exceptions.MissingClusterConfigForSimClustersAnnVariantException
import javax.inject.Singleton

object ClusterConfigModule extends XModule {
  @Singleton
  @Provides
  def providesClusterConfig(
    serviceIdentifier: ServiceIdentifier,
    clusterConfigMapper: ClusterConfigMapper
  ): ClusterConfig = {
    val serviceName = serviceIdentifier.service

    clusterConfigMapper.getClusterConfig(serviceName) match {
      case Some(config) => config
      case None => throw MissingClusterConfigForSimClustersAnnVariantException(serviceName)
    }
  }
}
