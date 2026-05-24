package com.twitter.unified_user_actions.client.config

sealed trait ClientConfig {
  val cluster: ClusterConfig
  val topic: String
  val environment: EnvironmentConfig
}

class AbstractClientConfig(isEngagementOnly: Boolean, env: EnvironmentConfig) extends ClientConfig {
  override val cluster: ClusterConfig = {
    env match {
      case Environments.Prod => Clusters.ProdCluster
      case Environments.Staging => Clusters.StagingCluster
      case _ => Clusters.ProdCluster
    }
  }

  override val topic: String = {
    if (isEngagementOnly) Constants.UuaEngagementOnlyKafkaTopicName
    else Constants.UuaKafkaTopicName
  }

  override val environment: EnvironmentConfig = env
}

object KafkaConfigs {

  /*
   * Unified User Actions Kafka config with all events (engagements and impressions).
   * Use this config when you mainly need impression data and data volume is not an issue.
   */
  case object ProdUnifiedUserActions
      extends AbstractClientConfig(isEngagementOnly = false, env = Environments.Prod)

  /*
   * Unified User Actions Kafka config with engagements events only.
   * Use this config when you only need engagement data. The data volume should be a lot smaller
   * than our main config.
   */
  case object ProdUnifiedUserActionsEngagementOnly
      extends AbstractClientConfig(isEngagementOnly = true, env = Environments.Prod)

  /*
   * Staging Environment for integration and testing. This is not a production config.
   *
   * Unified User Actions Kafka config with all events (engagements and impressions).
   * Use this config when you mainly need impression data and data volume is not an issue.
   */
  case object StagingUnifiedUserActions
      extends AbstractClientConfig(isEngagementOnly = false, env = Environments.Staging)

  /*
   * Staging Environment for integration and testing. This is not a production config.
   *
   * Unified User Actions Kafka config with engagements events only.
   * Use this config when you only need engagement data. The data volume should be a lot smaller
   * than our main config.
   */
  case object StagingUnifiedUserActionsEngagementOnly
      extends AbstractClientConfig(isEngagementOnly = true, env = Environments.Staging)
}
