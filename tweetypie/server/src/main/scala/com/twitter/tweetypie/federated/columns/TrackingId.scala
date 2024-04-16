package com.tw ter.t etyp e.federated
package columns

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.t etyp e.StatsRece ver
 mport com.tw ter.ut l.logg ng.Logger

object Track ng d {
  pr vate[t ] val log = Logger(getClass)

  def parse(s: Str ng, statsRece ver: StatsRece ver = NullStatsRece ver): Opt on[Long] = {
    val track ngStats = statsRece ver.scope("track ng_ d_parser")

    val parsedCountCounter = track ngStats.scope("parsed").counter("count")
    val parseFa ledCounter = track ngStats.scope("parse_fa led").counter("count")
    Opt on(s).map(_.tr m).f lter(_.nonEmpty).flatMap {  dStr =>
      try {
        val  d = java.lang.Long.parseLong( dStr, 16)
        parsedCountCounter. ncr()
        So ( d)
      } catch {
        case _: NumberFormatExcept on =>
          parseFa ledCounter. ncr()
          log.warn(s" nval d track ng  D: '$s'")
          None
      }
    }
  }
}
