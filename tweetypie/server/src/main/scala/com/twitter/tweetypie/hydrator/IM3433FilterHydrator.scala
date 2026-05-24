package com.twitter.tweetypie.hydrator

import com.twitter.coreservices.IM3433
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core.FilteredState
import com.twitter.tweetypie.core.ValueState

object IM3433FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCtx]

  private val Drop =
    Stitch.exception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(stats: StatsReceiver): Type = {

    ValueHydrator[Unit, TweetCtx] { (_, ctx) =>
      val userAgent = TwitterContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.exists(IM3433.isAffectedClient)
      val mightCrash = userAgentAffected && IM3433.textMightCrashIOS(ctx.text)
      if (mightCrash) Drop else Success
    }
  }
}
