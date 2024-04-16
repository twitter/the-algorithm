package com.tw ter.v s b l y.rules

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.v s b l y.conf gap .V s b l yParams
 mport com.tw ter.v s b l y.models.SafetyLevel
 mport com.tw ter.v s b l y.models.Un OfD vers on
 mport com.tw ter.v s b l y.models.V e rContext

case class Evaluat onContext(
  v s b l yPol cy: V s b l yPol cy,
  params: Params,
  statsRece ver: StatsRece ver)
    extends HasParams {

  def ruleEnabled nContext(rule: Rule): Boolean = {
    v s b l yPol cy.pol cyRuleParams
      .get(rule)
      .f lter(_.ruleParams.nonEmpty)
      .map(pol cyRuleParams => {
        (pol cyRuleParams.force || rule.enabled.forall(params(_))) &&
          pol cyRuleParams.ruleParams.forall(params(_))
      })
      .getOrElse(rule. sEnabled(params))
  }
}

object Evaluat onContext {

  def apply(
    safetyLevel: SafetyLevel,
    params: Params,
    statsRece ver: StatsRece ver
  ): Evaluat onContext = {
    val v s b l yPol cy = RuleBase.RuleMap(safetyLevel)
    new Evaluat onContext(v s b l yPol cy, params, statsRece ver)
  }

  case class Bu lder(
    statsRece ver: StatsRece ver,
    v s b l yParams: V s b l yParams,
    v e rContext: V e rContext,
    un sOfD vers on: Seq[Un OfD vers on] = Seq.empty,
     mo zeParams: Gate[Un ] = Gate.False,
  ) {

    pr vate[t ] val emptyContentToUoDCounter =
      statsRece ver.counter("empty_content_ d_to_un _of_d vers on")

    def bu ld(safetyLevel: SafetyLevel): Evaluat onContext = {
      val pol cy = RuleBase.RuleMap(safetyLevel)
      val params =  f ( mo zeParams()) {
        v s b l yParams. mo zed(v e rContext, safetyLevel, un sOfD vers on)
      } else {
        v s b l yParams(v e rContext, safetyLevel, un sOfD vers on)
      }
      new Evaluat onContext(pol cy, params, statsRece ver)
    }

    def w hUn OfD vers on(un OfD vers on: Un OfD vers on*): Bu lder =
      t .copy(un sOfD vers on = un OfD vers on)

    def w h mo zedParams( mo zeParams: Gate[Un ]) = t .copy( mo zeParams =  mo zeParams)
  }

}
