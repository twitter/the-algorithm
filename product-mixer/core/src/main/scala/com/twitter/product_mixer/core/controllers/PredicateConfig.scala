package com.tw ter.product_m xer.core.controllers

 mport com.tw ter.product_m xer.core.funct onal_component.common.alert.pred cate.Pred cate

/** S mple representat on for a [[Pred cate]] used for dashboard generat on */
pr vate[core] case class Pred cateConf g(
  operator: Str ng,
  threshold: Double,
  datapo ntsPastThreshold:  nt,
  durat on:  nt,
   tr cGranular y: Str ng)

pr vate[core] object Pred cateConf g {

  /** Convert t  [[Pred cate]]  nto a [[Pred cateConf g]] */
  def apply(pred cate: Pred cate): Pred cateConf g = Pred cateConf g(
    pred cate.operator.toStr ng,
    pred cate.threshold,
    pred cate.datapo ntsPastThreshold,
    pred cate.durat on,
    pred cate. tr cGranular y.un )
}
