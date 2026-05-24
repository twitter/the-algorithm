package com.twitter.product_mixer.core.functional_component.marshaller.response.rtf.safety_level

import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.ConversationFocalTweetSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.ConversationInjectedTweetSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.ConversationReplySafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.SafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineFocalTweetSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.twitter.spam.rtf.{thriftscala => thrift}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SafetyLevelMarshaller @Inject() () {

  def apply(safetyLevel: SafetyLevel): thrift.SafetyLevel = safetyLevel match {
    case ConversationFocalTweetSafetyLevel => thrift.SafetyLevel.ConversationFocalTweet
    case ConversationReplySafetyLevel => thrift.SafetyLevel.ConversationReply
    case ConversationInjectedTweetSafetyLevel => thrift.SafetyLevel.ConversationInjectedTweet
    case TimelineFocalTweetSafetyLevel => thrift.SafetyLevel.TimelineFocalTweet
    case TimelineHomePromotedHydrationSafetyLevel =>
      thrift.SafetyLevel.TimelineHomePromotedHydration
  }
}
