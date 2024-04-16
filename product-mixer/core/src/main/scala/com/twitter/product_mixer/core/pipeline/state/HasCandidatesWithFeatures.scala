package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

tra  HasCand datesW hFeatures[Cand date <: Un versalNoun[Any], T] {
  def cand datesW hFeatures: Seq[Cand dateW hFeatures[Cand date]]
  def updateCand datesW hFeatures(newCand dates: Seq[Cand dateW hFeatures[Cand date]]): T
}
