package com.tw ter.fr gate.pushserv ce.pred cate.ntab_caret_fat gue

 mport com.tw ter.f nagle.stats.StatsRece ver

case class Cont nuousFunct onParam(
  knobs: Seq[Double],
  knobValues: Seq[Double],
  po rs: Seq[Double],
    ght: Double,
  defaultValue: Double) {

  def val dateParams(): Boolean = {
    knobs.s ze > 0 && knobs.s ze - 1 == po rs.s ze && knobs.s ze == knobValues.s ze
  }
}

object Cont nuousFunct on {

  /**
   * Evalutate t  value for funct on f(x) = w(x - b)^po r
   * w re w and b are dec ded by t  start, startVal, end, endVal
   * such that
   *         w(start - b) ^ po r = startVal
   *         w(end - b) ^ po r = endVal
   *
   * @param value t  value at wh ch   w ll evaluate t  param
   * @return   ght * f(value)
   */
  def evaluateFn(
    value: Double,
    start: Double,
    startVal: Double,
    end: Double,
    endVal: Double,
    po r: Double,
      ght: Double
  ): Double = {
    val b =
      (math.pow(startVal / endVal, 1 / po r) * end - start) / (math.pow(
        startVal / endVal,
        1 / po r) - 1)
    val w = startVal / math.pow(start - b, po r)
      ght * w * math.pow(value - b, po r)
  }

  /**
   * Evaluate value for funct on f(x), and return   ght * f(x)
   *
   * f(x)  s a p ecew se funct on
   * f(x) = w_  * (x - b_ )^po rs[ ] for knobs[ ] <= x < knobs[ +1]
   * such that
   *         w(knobs[ ] - b) ^ po r = knobVals[ ]
   *         w(knobs[ +1] - b) ^ po r = knobVals[ +1]
   *
   * @return Evaluate value for   ght * f(x), for t  funct on descr bed above.  f t  any of t   nput  s  nval d, returns defaultVal
   */
  def safeEvaluateFn(
    value: Double,
    knobs: Seq[Double],
    knobVals: Seq[Double],
    po rs: Seq[Double],
      ght: Double,
    defaultVal: Double,
    statsRece ver: StatsRece ver
  ): Double = {
    val totalStats = statsRece ver.counter("safe_evalfn_total")
    val val dStats =
      statsRece ver.counter("safe_evalfn_val d")
    val val dEndCaseStats =
      statsRece ver.counter("safe_evalfn_val d_endcase")
    val  nval dStats = statsRece ver.counter("safe_evalfn_ nval d")

    totalStats. ncr()
     f (knobs.s ze <= 0 || knobs.s ze - 1 != po rs.s ze || knobs.s ze != knobVals.s ze) {
       nval dStats. ncr()
      defaultVal
    } else {
      val end ndex = knobs. ndexW re(knob => knob > value)
      val dStats. ncr()
      end ndex match {
        case -1 => {
          val dEndCaseStats. ncr()
          knobVals(knobVals.s ze - 1) *   ght
        }
        case 0 => {
          val dEndCaseStats. ncr()
          knobVals(0) *   ght
        }
        case _ => {
          val start ndex = end ndex - 1
          evaluateFn(
            value,
            knobs(start ndex),
            knobVals(start ndex),
            knobs(end ndex),
            knobVals(end ndex),
            po rs(start ndex),
              ght)
        }
      }
    }
  }

  def safeEvaluateFn(
    value: Double,
    fnParams: Cont nuousFunct onParam,
    statsRece ver: StatsRece ver
  ): Double = {
    val totalStats = statsRece ver.counter("safe_evalfn_total")
    val val dStats =
      statsRece ver.counter("safe_evalfn_val d")
    val val dEndCaseStats =
      statsRece ver.counter("safe_evalfn_val d_endcase")
    val  nval dStats = statsRece ver.counter("safe_evalfn_ nval d")

    totalStats. ncr()

     f (fnParams.val dateParams()) {
      val end ndex = fnParams.knobs. ndexW re(knob => knob > value)
      val dStats. ncr()
      end ndex match {
        case -1 => {
          val dEndCaseStats. ncr()
          fnParams.knobValues(fnParams.knobValues.s ze - 1) * fnParams.  ght
        }
        case 0 => {
          val dEndCaseStats. ncr()
          fnParams.knobValues(0) * fnParams.  ght
        }
        case _ => {
          val start ndex = end ndex - 1
          evaluateFn(
            value,
            fnParams.knobs(start ndex),
            fnParams.knobValues(start ndex),
            fnParams.knobs(end ndex),
            fnParams.knobValues(end ndex),
            fnParams.po rs(start ndex),
            fnParams.  ght
          )
        }
      }
    } else {
       nval dStats. ncr()
      fnParams.defaultValue
    }
  }
}
