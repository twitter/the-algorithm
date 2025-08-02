package com.twitter.follow_recommendations.common.utils

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.follow_recommendations.common.base.StatsUtil
import com.twitter.stitch.Stitch
import com.twitter.util.Duration
import com.twitter.util.TimeoutEtwitterception

object RescueWithStatsUtils {
  def rescueWithStats[T](
    s: Stitch[Seq[T]],
    stats: StatsReceiver,
    source: String
  ): Stitch[Seq[T]] = {
    StatsUtil.profileStitchSeqResults(s, stats.scope(source)).rescue {
      case _: Etwitterception => Stitch.Nil
    }
  }

  def rescueOptionalWithStats[T](
    s: Stitch[Option[T]],
    stats: StatsReceiver,
    source: String
  ): Stitch[Option[T]] = {
    StatsUtil.profileStitchOptionalResults(s, stats.scope(source)).rescue {
      case _: Etwitterception => Stitch.None
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
        s.within(timeout)(com.twitter.finagle.util.DefaultTimer),
        hydratedScopeSource)
      .rescue {
        case _: TimeoutEtwitterception =>
          hydratedScopeSource.counter("timeout").incr()
          Stitch.Nil
        case _: Etwitterception =>
          hydratedScopeSource.counter("etwitterception").incr()
          Stitch.Nil
      }
  }
}
