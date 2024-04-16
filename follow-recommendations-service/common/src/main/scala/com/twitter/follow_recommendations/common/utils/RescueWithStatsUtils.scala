package com.tw ter.follow_recom ndat ons.common.ut ls

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.base.StatsUt l
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  outExcept on

object RescueW hStatsUt ls {
  def rescueW hStats[T](
    s: St ch[Seq[T]],
    stats: StatsRece ver,
    s ce: Str ng
  ): St ch[Seq[T]] = {
    StatsUt l.prof leSt chSeqResults(s, stats.scope(s ce)).rescue {
      case _: Except on => St ch.N l
    }
  }

  def rescueOpt onalW hStats[T](
    s: St ch[Opt on[T]],
    stats: StatsRece ver,
    s ce: Str ng
  ): St ch[Opt on[T]] = {
    StatsUt l.prof leSt chOpt onalResults(s, stats.scope(s ce)).rescue {
      case _: Except on => St ch.None
    }
  }

  def rescueW hStatsW h n[T](
    s: St ch[Seq[T]],
    stats: StatsRece ver,
    s ce: Str ng,
    t  out: Durat on
  ): St ch[Seq[T]] = {
    val hydratedScopeS ce = stats.scope(s ce)
    StatsUt l
      .prof leSt chSeqResults(
        s.w h n(t  out)(com.tw ter.f nagle.ut l.DefaultT  r),
        hydratedScopeS ce)
      .rescue {
        case _: T  outExcept on =>
          hydratedScopeS ce.counter("t  out"). ncr()
          St ch.N l
        case _: Except on =>
          hydratedScopeS ce.counter("except on"). ncr()
          St ch.N l
      }
  }
}
