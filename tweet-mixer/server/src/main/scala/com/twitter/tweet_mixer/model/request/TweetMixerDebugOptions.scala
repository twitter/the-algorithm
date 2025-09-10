package com.twitter.tweet_mixer.model.request

import com.twitter.product_mixer.core.model.marshalling.request.DebugOptions
import com.twitter.util.Time

case class TweetMixerDebugOptions(
  override val requestTimeOverride: Option[Time],
  override val showIntermediateLogs: Option[Boolean])
    extends DebugOptions
