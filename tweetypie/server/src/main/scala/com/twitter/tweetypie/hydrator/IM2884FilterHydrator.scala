package com.twitter.tweetypie.hydrator

import com.twitter.coreservices.IM2884
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.tweetypie.core.FilteredState
import com.twitter.tweetypie.core.ValueState
import com.twitter.stitch.Stitch

object IM2884FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCttwitter]

  private val Drop =
    Stitch.etwitterception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(stats: StatsReceiver): Type = {

    val im2884 = new IM2884(stats)

    ValueHydrator[Unit, TweetCttwitter] { (_, cttwitter) =>
      val userAgent = TwitterContetwittert().flatMap(_.userAgent)
      val userAgentAffected = userAgent.etwitterists(im2884.isAffectedClient)
      val mightCrash = userAgentAffected && im2884.tetwittertMightCrashIOS(cttwitter.tetwittert)
      if (mightCrash) Drop else Success
    }
  }
}
