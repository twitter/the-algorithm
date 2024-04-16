package com.tw ter.v s b l y.conf gap 

 mport com.tw ter.abdec der.Logg ngABDec der
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.featuresw c s.v2.FeatureSw c s
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.Un OfD vers on
 mport com.tw ter.v s b l y.models.V e rContext

object V s b l yParams {
  def apply(
    log: Logger,
    statsRece ver: StatsRece ver,
    dec der: Dec der,
    abDec der: Logg ngABDec der,
    featureSw c s: FeatureSw c s
  ): V s b l yParams =
    new V s b l yParams(log, statsRece ver, dec der, abDec der, featureSw c s)
}

class V s b l yParams(
  log: Logger,
  statsRece ver: StatsRece ver,
  dec der: Dec der,
  abDec der: Logg ngABDec der,
  featureSw c s: FeatureSw c s) {

  pr vate[t ] val contextFactory = new V s b l yRequestContextFactory(
    abDec der,
    featureSw c s
  )

  pr vate[t ] val conf gBu lder = Conf gBu lder(statsRece ver.scope("conf g"), dec der, log)

  pr vate[t ] val paramStats:  mo z ngStatsRece ver = new  mo z ngStatsRece ver(
    statsRece ver.scope("conf gap _params"))

  def apply(
    v e rContext: V e rContext,
    safetyLevel: SafetyLevel,
    un sOfD vers on: Seq[Un OfD vers on] = Seq.empty
  ): Params = {
    val conf g = conf gBu lder.bu ld(safetyLevel)
    val requestContext = contextFactory(v e rContext, safetyLevel, un sOfD vers on)
    conf g.apply(requestContext, paramStats)
  }

  def  mo zed(
    v e rContext: V e rContext,
    safetyLevel: SafetyLevel,
    un sOfD vers on: Seq[Un OfD vers on] = Seq.empty
  ): Params = {
    val conf g = conf gBu lder.bu ld mo zed(safetyLevel)
    val requestContext = contextFactory(v e rContext, safetyLevel, un sOfD vers on)
    conf g.apply(requestContext, paramStats)
  }
}
