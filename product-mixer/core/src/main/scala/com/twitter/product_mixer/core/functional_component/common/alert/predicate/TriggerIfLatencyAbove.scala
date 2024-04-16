package com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate

 mport com.tw ter.ut l.Durat on

/**
 * A [[Pred cate]] that tr ggers  f t   tr c t   s used w h r ses above t 
 * [[latencyThreshold]] for [[datapo ntsPastThreshold]] per [[durat on]]
 *
 * @note [[latencyThreshold]] must be > 0
 */
case class Tr gger fLatencyAbove(
  latencyThreshold: Durat on,
  overr de val datapo ntsPastThreshold:  nt = 10,
  overr de val durat on:  nt = 15,
  overr de val  tr cGranular y:  tr cGranular y = M nutes)
    extends Pred cate {
  overr de val threshold: Double = latencyThreshold. nM ll s
  overr de val operator: Operator = `>`
  requ re(
    latencyThreshold > Durat on.Zero,
    s"Tr gger fLatencyAbove thresholds must be greater than 0 but got $latencyThreshold")
}
