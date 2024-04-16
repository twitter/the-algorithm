package com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate

/**
 * A [[Pred cate]] that tr ggers  f t   tr c t   s used w h r ses above
 * t  [[threshold]] for [[datapo ntsPastThreshold]] per [[durat on]]
 */
case class Tr gger fAbove(
  overr de val threshold: Double,
  overr de val datapo ntsPastThreshold:  nt = 10,
  overr de val durat on:  nt = 15,
  overr de val  tr cGranular y:  tr cGranular y = M nutes)
    extends Pred cate
    w h ThroughputPred cate {
  overr de val operator: Operator = `>`
}
