package com.twitter.follow_recommendations.common.models

import com.twitter.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}

sealed trait SafetyLevel {
  def toThrift: ThriftSafetyLevel
}

object SafetyLevel {
  case object Recommendations etwittertends SafetyLevel {
    override val toThrift = ThriftSafetyLevel.Recommendations
  }

  case object TopicsLandingPageTopicRecommendations etwittertends SafetyLevel {
    override val toThrift = ThriftSafetyLevel.TopicsLandingPageTopicRecommendations
  }
}
