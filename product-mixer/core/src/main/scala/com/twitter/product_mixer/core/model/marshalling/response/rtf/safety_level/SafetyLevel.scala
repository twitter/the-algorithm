package com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level

/*
  These are model objects for the thrift enum - src/thrift/com/twitter/spam/rtf/safety_level.thrift
  Please add new objects as needed for the marhallers
 */
sealed trait SafetyLevel

case object ConversationFocalTweetSafetyLevel extends SafetyLevel
case object ConversationReplySafetyLevel extends SafetyLevel
case object ConversationInjectedTweetSafetyLevel extends SafetyLevel
case object TimelineFocalTweetSafetyLevel extends SafetyLevel
case object TimelineHomePromotedHydrationSafetyLevel extends SafetyLevel
