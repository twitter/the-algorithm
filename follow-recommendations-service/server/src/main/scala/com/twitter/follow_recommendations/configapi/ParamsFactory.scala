package com.tw ter.follow_recom ndat ons.conf gap 

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ParamsFactory @ nject() (
  conf g: Conf g,
  requestContextFactory: RequestContextFactory,
  statsRece ver: StatsRece ver) {

  pr vate val stats = new  mo z ngStatsRece ver(statsRece ver.scope("conf gap "))
  def apply(followRecom ndat onServ ceRequestContext: RequestContext): Params =
    conf g(followRecom ndat onServ ceRequestContext, stats)

  def apply(
    cl entContext: Cl entContext,
    d splayLocat on: D splayLocat on,
    featureOverr des: Map[Str ng, FeatureValue]
  ): Params =
    apply(requestContextFactory(cl entContext, d splayLocat on, featureOverr des))
}
