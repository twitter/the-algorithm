package com.X.tweetypie.hydrator

import com.X.coreservices.IM3433
import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.tweetypie.core.FilteredState
import com.X.tweetypie.core.ValueState

object IM3433FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCtx]

  private val Drop =
    Stitch.exception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(stats: StatsReceiver): Type = {

    ValueHydrator[Unit, TweetCtx] { (_, ctx) =>
      val userAgent = XContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.exists(IM3433.isAffectedClient)
      val mightCrash = userAgentAffected && IM3433.textMightCrashIOS(ctx.text)
      if (mightCrash) Drop else Success
    }
  }
}
