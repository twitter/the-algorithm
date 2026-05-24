package com.twitter.simclustersann.exceptions

case class MissingClusterConfigForSimClustersAnnVariantException(sannServiceName: String)
    extends IllegalStateException(
      s"No cluster configuration found for service ($sannServiceName)",
      null)
