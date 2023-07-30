package com.X.tweetypie.hydrator

import com.X.coreservices.IM2884
import com.X.finagle.stats.StatsReceiver
import com.X.tweetypie.core.FilteredState
import com.X.tweetypie.core.ValueState
import com.X.stitch.Stitch

object IM2884FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCtx]

  private val Drop =
    Stitch.exception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(stats: StatsReceiver): Type = {

    val im2884 = new IM2884(stats)

    ValueHydrator[Unit, TweetCtx] { (_, ctx) =>
      val userAgent = XContext().flatMap(_.userAgent)
      val userAgentAffected = userAgent.exists(im2884.isAffectedClient)
      val mightCrash = userAgentAffected && im2884.textMightCrashIOS(ctx.text)
      if (mightCrash) Drop else Success
    }
  }
}
