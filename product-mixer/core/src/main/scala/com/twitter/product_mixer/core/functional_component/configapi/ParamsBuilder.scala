package com.tw ter.product_m xer.core.funct onal_component.conf gap 

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Product
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.t  l nes.conf gap .Params
 mport javax. nject. nject
 mport javax. nject.S ngleton

/** S ngleton object for bu ld ng [[Params]] to overr de */
@S ngleton
class ParamsBu lder @ nject() (
  conf g: Conf g,
  requestContextBu lder: RequestContextBu lder,
  statsRece ver: StatsRece ver) {

  pr vate[t ] val scopedStatsRece ver =
    new  mo z ngStatsRece ver(statsRece ver.scope("conf gap "))

  def bu ld(
    cl entContext: Cl entContext,
    product: Product,
    featureOverr des: Map[Str ng, FeatureValue],
    fsCustomMap nput: Map[Str ng, Any] = Map.empty
  ): Params = {
    val requestContext =
      requestContextBu lder.bu ld(cl entContext, product, featureOverr des, fsCustomMap nput)

    conf g(requestContext, scopedStatsRece ver)
  }
}
