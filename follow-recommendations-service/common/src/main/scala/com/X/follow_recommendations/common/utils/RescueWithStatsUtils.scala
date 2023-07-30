package com.X.follow_recommendations.common.utils

import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.base.StatsUtil
import com.X.stitch.Stitch
import com.X.util.Duration
import com.X.util.TimeoutException

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
        s.within(timeout)(com.X.finagle.util.DefaultTimer),
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
