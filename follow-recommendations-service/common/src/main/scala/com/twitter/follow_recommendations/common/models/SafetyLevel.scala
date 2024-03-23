package com.ExTwitter.follow_recommendations.common.models

import com.ExTwitter.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}

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
