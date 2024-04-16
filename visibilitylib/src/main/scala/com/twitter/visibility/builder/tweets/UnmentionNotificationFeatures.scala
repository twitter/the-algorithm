package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.not f cat onserv ce.model.not f cat on._
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t etyp e.thr ftscala.Sett ngsUn nt ons
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.T etS ce
 mport com.tw ter.v s b l y.features.Not f cat on sOnUn nt onedV e r

object Un nt onNot f cat onFeatures {
  def ForNonUn nt onNot f cat onFeatures: FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(Not f cat on sOnUn nt onedV e r, false)
  }
}

class Un nt onNot f cat onFeatures(
  t etS ce: T etS ce,
  enableUn nt onHydrat on: Gate[Long],
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver =
    statsRece ver.scope("un nt on_not f cat on_features")
  pr vate[t ] val requestsCounter = scopedStatsRece ver.counter("requests")
  pr vate[t ] val hydrat onsCounter = scopedStatsRece ver.counter("hydrat ons")
  pr vate[t ] val not f cat onsUn nt onUserCounter =
    scopedStatsRece ver
      .scope(Not f cat on sOnUn nt onedV e r.na ).counter("un nt oned_users")

  def forNot f cat on(not f cat on: Not f cat on): FeatureMapBu lder => FeatureMapBu lder = {
    requestsCounter. ncr()

    val  sUn nt onNot f cat on = t et d(not f cat on) match {
      case So (t et d)  f enableUn nt onHydrat on(not f cat on.target) =>
        hydrat onsCounter. ncr()
        t etS ce
          .getT et(t et d)
          .map {
            case So (t et) =>
              t et.sett ngsUn nt ons match {
                case So (Sett ngsUn nt ons(So (un nt onedUser ds))) =>
                   f (un nt onedUser ds.conta ns(not f cat on.target)) {
                    not f cat onsUn nt onUserCounter. ncr()
                    true
                  } else {
                    false
                  }
                case _ => false
              }
            case _ => false
          }
      case _ => St ch.False
    }
    _.w hFeature(Not f cat on sOnUn nt onedV e r,  sUn nt onNot f cat on)
  }

  pr vate[t ] def t et d(not f cat on: Not f cat on): Opt on[Long] = {
    not f cat on match {
      case n:  nt onNot f cat on => So (n. nt on ngT et d)
      case n: Favor ed nt on ngT etNot f cat on => So (n. nt on ngT et d)
      case n: Favor edReplyToY T etNot f cat on => So (n.replyT et d)
      case n:  nt onQuoteNot f cat on => So (n. nt on ngT et d)
      case n: React on nt on ngT etNot f cat on => So (n. nt on ngT et d)
      case n: ReplyNot f cat on => So (n.reply ngT et d)
      case n: Ret eted nt onNot f cat on => So (n. nt on ngT et d)
      case n: Ret etedReplyToY T etNot f cat on => So (n.replyT et d)
      case n: ReplyToConversat onNot f cat on => So (n.reply ngT et d)
      case n: React onReplyToY T etNot f cat on => So (n.replyT et d)
      case _ => None
    }

  }

}
