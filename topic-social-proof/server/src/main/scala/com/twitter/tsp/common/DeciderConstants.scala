package com.twitter.tsp.common

import com.twitter.servo.decider.DeciderKeyEnum

object DeciderConstants {
  val enableTopicSocialProofScore = "enable_topic_social_proof_score"
  val enableHealthSignalsScoreDeciderKey = "enable_tweet_health_score"
  val enableUserAgathaScoreDeciderKey = "enable_user_agatha_score"
}

object DeciderKey extends DeciderKeyEnum {

  val enableHealthSignalsScoreDeciderKey: Value = Value(
    DeciderConstants.enableHealthSignalsScoreDeciderKey
  )
  val enableUserAgathaScoreDeciderKey: Value = Value(
    DeciderConstants.enableUserAgathaScoreDeciderKey
  )
}
