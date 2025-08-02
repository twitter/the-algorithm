package com.twitter.tweetypie.hydrator

import com.twitter.coreservices.IM3433
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core.FilteredState
import com.twitter.tweetypie.core.ValueState

object IM3433FilterHydrator {
  type Type = ValueHydrator[Unit, TweetCttwitter]

  private val Drop =
    Stitch.etwitterception(FilteredState.Unavailable.DropUnspecified)
  private val Success = Stitch.value(ValueState.unmodified(()))

  def apply(stats: StatsReceiver): Type = {

    ValueHydrator[Unit, TweetCttwitter] { (_, cttwitter) =>
      val userAgent = TwitterContetwittert().flatMap(_.userAgent)
      val userAgentAffected = userAgent.etwitterists(IM3433.isAffectedClient)
      val mightCrash = userAgentAffected && IM3433.tetwittertMightCrashIOS(cttwitter.tetwittert)
      if (mightCrash) Drop else Success
    }
  }
}
