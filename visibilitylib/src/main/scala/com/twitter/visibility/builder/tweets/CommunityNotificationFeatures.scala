package com.tw ter.v s b l y.bu lder.t ets

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.not f cat onserv ce.model.not f cat on.Act v yNot f cat on
 mport com.tw ter.not f cat onserv ce.model.not f cat on. nt onNot f cat on
 mport com.tw ter.not f cat onserv ce.model.not f cat on. nt onQuoteNot f cat on
 mport com.tw ter.not f cat onserv ce.model.not f cat on.Not f cat on
 mport com.tw ter.not f cat onserv ce.model.not f cat on.QuoteT etNot f cat on
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.st ch.St ch
 mport com.tw ter.v s b l y.bu lder.FeatureMapBu lder
 mport com.tw ter.v s b l y.common.T etS ce
 mport com.tw ter.v s b l y.features.Not f cat on sOnCommun yT et
 mport com.tw ter.v s b l y.models.Commun yT et

object Commun yNot f cat onFeatures {
  def ForNonCommun yT etNot f cat on: FeatureMapBu lder => FeatureMapBu lder = {
    _.w hConstantFeature(Not f cat on sOnCommun yT et, false)
  }
}

class Commun yNot f cat onFeatures(
  t etS ce: T etS ce,
  enableCommun yT etHydrat on: Gate[Long],
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver = statsRece ver.scope("commun y_not f cat on_features")
  pr vate[t ] val requestsCounter = scopedStatsRece ver.counter("requests")
  pr vate[t ] val hydrat onsCounter = scopedStatsRece ver.counter("hydrat ons")
  pr vate[t ] val not f cat on sOnCommun yT etCounter =
    scopedStatsRece ver.scope(Not f cat on sOnCommun yT et.na ).counter("true")
  pr vate[t ] val not f cat on sNotOnCommun yT etCounter =
    scopedStatsRece ver.scope(Not f cat on sOnCommun yT et.na ).counter("false")

  def forNot f cat on(not f cat on: Not f cat on): FeatureMapBu lder => FeatureMapBu lder = {
    requestsCounter. ncr()
    val  sCommun yT etResult = getT et dOpt on(not f cat on) match {
      case So (t et d)  f enableCommun yT etHydrat on(not f cat on.target) =>
        hydrat onsCounter. ncr()
        t etS ce
          .getT et(t et d)
          .map {
            case So (t et)  f Commun yT et(t et).nonEmpty =>
              not f cat on sOnCommun yT etCounter. ncr()
              true
            case _ =>
              not f cat on sNotOnCommun yT etCounter. ncr()
              false
          }
      case _ => St ch.False
    }
    _.w hFeature(Not f cat on sOnCommun yT et,  sCommun yT etResult)
  }

  pr vate[t ] def getT et dOpt on(not f cat on: Not f cat on): Opt on[Long] = {
    not f cat on match {
      case n:  nt onNot f cat on => So (n. nt on ngT et d)
      case n:  nt onQuoteNot f cat on => So (n. nt on ngT et d)
      case n: QuoteT etNot f cat on => So (n.quotedT et d)
      case n: Act v yNot f cat on[_]  f n.v s b l yT ets.conta ns(n.object d) => So (n.object d)
      case _ => None
    }
  }
}
