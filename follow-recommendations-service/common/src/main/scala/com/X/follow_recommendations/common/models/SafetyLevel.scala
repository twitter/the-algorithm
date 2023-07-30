package com.X.follow_recommendations.common.models

import com.X.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}

sealed trait SafetyLevel {
  def toThrift: ThriftSafetyLevel
}

object SafetyLevel {
  case object Recommendations extends SafetyLevel {
    override val toThrift = ThriftSafetyLevel.Recommendations
  }

  case object TopicsLandingPageTopicRecommendations extends SafetyLevel {
    override val toThrift = ThriftSafetyLevel.TopicsLandingPageTopicRecommendations
  }
}
