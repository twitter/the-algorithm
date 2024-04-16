package com.tw ter.v s b l y.rules.prov ders

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.rules.Evaluat onContext
 mport com.tw ter.v s b l y.rules.V s b l yPol cy

sealed abstract class Prov dedEvaluat onContext(
  v s b l yPol cy: V s b l yPol cy,
  params: Params,
  statsRece ver: StatsRece ver)
    extends Evaluat onContext(
      v s b l yPol cy = v s b l yPol cy,
      params = params,
      statsRece ver = statsRece ver)

object Prov dedEvaluat onContext {

  def  njectRunt  Rules ntoEvaluat onContext(
    evaluat onContext: Evaluat onContext,
    safetyLevel: Opt on[SafetyLevel] = None,
    pol cyProv derOpt: Opt on[Pol cyProv der] = None
  ): Prov dedEvaluat onContext = {
    (pol cyProv derOpt, safetyLevel) match {
      case (So (pol cyProv der), So (safetyLevel)) =>
        new  njectedEvaluat onContext(
          evaluat onContext = evaluat onContext,
          safetyLevel = safetyLevel,
          pol cyProv der = pol cyProv der)
      case (_, _) => new Stat cEvaluat onContext(evaluat onContext)
    }
  }
}

pr vate class Stat cEvaluat onContext(
  evaluat onContext: Evaluat onContext)
    extends Prov dedEvaluat onContext(
      v s b l yPol cy = evaluat onContext.v s b l yPol cy,
      params = evaluat onContext.params,
      statsRece ver = evaluat onContext.statsRece ver)

pr vate class  njectedEvaluat onContext(
  evaluat onContext: Evaluat onContext,
  safetyLevel: SafetyLevel,
  pol cyProv der: Pol cyProv der)
    extends Prov dedEvaluat onContext(
      v s b l yPol cy = pol cyProv der.pol cyForSurface(safetyLevel),
      params = evaluat onContext.params,
      statsRece ver = evaluat onContext.statsRece ver)
