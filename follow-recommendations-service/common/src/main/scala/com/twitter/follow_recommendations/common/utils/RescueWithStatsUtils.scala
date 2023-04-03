packagelon com.twittelonr.follow_reloncommelonndations.common.utils

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.follow_reloncommelonndations.common.baselon.StatsUtil
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Duration
import com.twittelonr.util.Timelonoutelonxcelonption

objelonct RelonscuelonWithStatsUtils {
  delonf relonscuelonWithStats[T](
    s: Stitch[Selonq[T]],
    stats: StatsReloncelonivelonr,
    sourcelon: String
  ): Stitch[Selonq[T]] = {
    StatsUtil.profilelonStitchSelonqRelonsults(s, stats.scopelon(sourcelon)).relonscuelon {
      caselon _: elonxcelonption => Stitch.Nil
    }
  }

  delonf relonscuelonOptionalWithStats[T](
    s: Stitch[Option[T]],
    stats: StatsReloncelonivelonr,
    sourcelon: String
  ): Stitch[Option[T]] = {
    StatsUtil.profilelonStitchOptionalRelonsults(s, stats.scopelon(sourcelon)).relonscuelon {
      caselon _: elonxcelonption => Stitch.Nonelon
    }
  }

  delonf relonscuelonWithStatsWithin[T](
    s: Stitch[Selonq[T]],
    stats: StatsReloncelonivelonr,
    sourcelon: String,
    timelonout: Duration
  ): Stitch[Selonq[T]] = {
    val hydratelondScopelonSourcelon = stats.scopelon(sourcelon)
    StatsUtil
      .profilelonStitchSelonqRelonsults(
        s.within(timelonout)(com.twittelonr.finaglelon.util.DelonfaultTimelonr),
        hydratelondScopelonSourcelon)
      .relonscuelon {
        caselon _: Timelonoutelonxcelonption =>
          hydratelondScopelonSourcelon.countelonr("timelonout").incr()
          Stitch.Nil
        caselon _: elonxcelonption =>
          hydratelondScopelonSourcelon.countelonr("elonxcelonption").incr()
          Stitch.Nil
      }
  }
}
