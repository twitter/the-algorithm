package com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref

import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.SafetyLevel

case class TweetHydrationContext(
  safetyLevelOverride: Option[SafetyLevel],
  outerTweetContext: Option[OuterTweetContext])
