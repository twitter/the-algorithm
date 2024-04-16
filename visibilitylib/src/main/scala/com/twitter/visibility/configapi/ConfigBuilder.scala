package com.tw ter.v s b l y.conf gap 

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.t  l nes.conf gap .Conf g
 mport com.tw ter.ut l. mo ze
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yDec ders
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yExper  ntsConf g
 mport com.tw ter.v s b l y.conf gap .conf gs.V s b l yFeatureSw c s
 mport com.tw ter.v s b l y.models.SafetyLevel

object Conf gBu lder {

  def apply(statsRece ver: StatsRece ver, dec der: Dec der, logger: Logger): Conf gBu lder = {
    val dec derGateBu lder: Dec derGateBu lder =
      new Dec derGateBu lder(dec der)

    new Conf gBu lder(
      dec derGateBu lder,
      statsRece ver,
      logger
    )
  }
}

class Conf gBu lder(
  dec derGateBu lder: Dec derGateBu lder,
  statsRece ver: StatsRece ver,
  logger: Logger) {

  def bu ld mo zed: SafetyLevel => Conf g =  mo ze(bu ld)

  def bu ld(safetyLevel: SafetyLevel): Conf g = {
    new Compos eConf g(
      V s b l yExper  ntsConf g.conf g(safetyLevel) :+
        V s b l yDec ders.conf g(dec derGateBu lder, logger, statsRece ver, safetyLevel) :+
        V s b l yFeatureSw c s.conf g(statsRece ver, logger)
    )
  }
}
