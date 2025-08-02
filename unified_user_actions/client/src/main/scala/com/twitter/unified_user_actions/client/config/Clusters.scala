package com.twitter.unified_user_actions.client.config

sealed trait ClusterConfig {
  val name: String
  val environment: EnvironmentConfig
}

object Clusters {
  /*
   * Our production cluster for etwitterternal consumption. Our SLAs are enforced.
   */
  case object ProdCluster etwittertends ClusterConfig {
    override val name: String = Constants.UuaKafkaProdClusterName
    override val environment: EnvironmentConfig = Environments.Prod
  }

  /*
   * Our staging cluster for etwitterternal development and pre-releases. No SLAs are enforced.
   */
  case object StagingCluster etwittertends ClusterConfig {
    override val name: String = Constants.UuaKafkaStagingClusterName
    override val environment: EnvironmentConfig = Environments.Staging
  }
}
