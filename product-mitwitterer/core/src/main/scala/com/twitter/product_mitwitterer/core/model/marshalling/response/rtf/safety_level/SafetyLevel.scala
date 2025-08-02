package com.twitter.product_mitwitterer.core.model.marshalling.response.rtf.safety_level

/*
  These are model objects for the thrift enum - src/thrift/com/twitter/spam/rtf/safety_level.thrift
  Please add new objects as needed for the marhallers
 */
sealed trait SafetyLevel

case object ConversationFocalTweetSafetyLevel etwittertends SafetyLevel
case object ConversationReplySafetyLevel etwittertends SafetyLevel
case object ConversationInjectedTweetSafetyLevel etwittertends SafetyLevel
case object TimelineFocalTweetSafetyLevel etwittertends SafetyLevel
case object TimelineHomePromotedHydrationSafetyLevel etwittertends SafetyLevel
