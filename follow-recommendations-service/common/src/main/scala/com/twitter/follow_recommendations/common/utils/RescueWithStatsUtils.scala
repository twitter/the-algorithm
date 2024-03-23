package com.ExTwitter.follow_recommendations.common.utils

import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.follow_recommendations.common.base.StatsUtil
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.util.Duration
import com.ExTwitter.util.TimeoutException

object RescueWithStatsUtils {
  def rescueWithStats[T](
    s: Stitch[Seq[T]],
    stats: StatsReceiver,
    source: String
  ): Stitch[Seq[T]] = {
    StatsUtil.profileStitchSeqResults(s, stats.scope(source)).rescue {
      case _: Exception => Stitch.Nil
    }
  }

  def rescueOptionalWithStats[T](
    s: Stitch[Option[T]],
    stats: StatsReceiver,
    source: String
  ): Stitch[Option[T]] = {
    StatsUtil.profileStitchOptionalResults(s, stats.scope(source)).rescue {
      case _: Exception => Stitch.None
    }
  }

  def rescueWithStatsWithin[T](
    s: Stitch[Seq[T]],
    stats: StatsReceiver,
    source: String,
    timeout: Duration
  ): Stitch[Seq[T]] = {
    val hydratedScopeSource = stats.scope(source)
    StatsUtil
      .profileStitchSeqResults(
        s.within(timeout)(com.ExTwitter.finagle.util.DefaultTimer),
        hydratedScopeSource)
      .rescue {
        case _: TimeoutException =>
          hydratedScopeSource.counter("timeout").incr()
          Stitch.Nil
        case _: Exception =>
          hydratedScopeSource.counter("exception").incr()
          Stitch.Nil
      }
  }
}
