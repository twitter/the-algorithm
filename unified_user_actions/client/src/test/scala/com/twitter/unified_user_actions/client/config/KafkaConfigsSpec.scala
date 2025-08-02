package com.twitter.unified_user_actions.client.config

import com.twitter.inject.Test

class KafkaConfigsSpec etwittertends Test {
  test("configs should be correct") {
    val states = Seq(
      (
        KafkaConfigs.ProdUnifiedUserActions,
        Constants.UuaProdEnv,
        Constants.UuaKafkaTopicName,
        Constants.UuaKafkaProdClusterName),
      (
        KafkaConfigs.ProdUnifiedUserActionsEngagementOnly,
        Constants.UuaProdEnv,
        Constants.UuaEngagementOnlyKafkaTopicName,
        Constants.UuaKafkaProdClusterName),
      (
        KafkaConfigs.StagingUnifiedUserActions,
        Constants.UuaStagingEnv,
        Constants.UuaKafkaTopicName,
        Constants.UuaKafkaStagingClusterName),
      (
        KafkaConfigs.StagingUnifiedUserActionsEngagementOnly,
        Constants.UuaStagingEnv,
        Constants.UuaEngagementOnlyKafkaTopicName,
        Constants.UuaKafkaStagingClusterName)
    )

    states.foreach {
      case (actual, etwitterpectedEnv, etwitterpectedTopic, etwitterpectedClusterName) =>
        assert(etwitterpectedEnv == actual.environment.name, s"in $actual")
        assert(etwitterpectedTopic == actual.topic, s"in $actual")
        assert(etwitterpectedClusterName == actual.cluster.name, s"in $actual")
      case _ =>
    }
  }
}
