package com.twitter.unified_user_actions.client.config

object Constants {
  val UuaKafkaTopicName = "unified_user_actions"
  val UuaEngagementOnlyKafkaTopicName = "unified_user_actions_engagements"
  val UuaKafkaProdClusterName = "/s/kafka/bluebird-1"
  val UuaKafkaStagingClusterName = "/s/kafka/custdevel"
  val UuaProdEnv = "prod"
  val UuaStagingEnv = "staging"
}
