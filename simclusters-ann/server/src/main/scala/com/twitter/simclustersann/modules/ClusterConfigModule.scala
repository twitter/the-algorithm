package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.inject.TwitterModule
import com.twitter.relevance_platform.simclustersann.multicluster.ClusterConfig
import com.twitter.relevance_platform.simclustersann.multicluster.ClusterConfigMapper
import com.twitter.simclustersann.etwitterceptions.MissingClusterConfigForSimClustersAnnVariantEtwitterception
import javatwitter.inject.Singleton

object ClusterConfigModule etwittertends TwitterModule {
  @Singleton
  @Provides
  def providesClusterConfig(
    serviceIdentifier: ServiceIdentifier,
    clusterConfigMapper: ClusterConfigMapper
  ): ClusterConfig = {
    val serviceName = serviceIdentifier.service

    clusterConfigMapper.getClusterConfig(serviceName) match {
      case Some(config) => config
      case None => throw MissingClusterConfigForSimClustersAnnVariantEtwitterception(serviceName)
    }
  }
}
