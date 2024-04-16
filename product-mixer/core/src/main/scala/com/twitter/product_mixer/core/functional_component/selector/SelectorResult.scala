package com.tw ter.product_m xer.core.funct onal_component.selector

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

/**
 * T  result of a [[Selector]] w re  ems that  re added
 * to t  [[result]] are removed from t  [[rema n ngCand dates]]
 */
case class SelectorResult(
  rema n ngCand dates: Seq[Cand dateW hDeta ls],
  result: Seq[Cand dateW hDeta ls])
