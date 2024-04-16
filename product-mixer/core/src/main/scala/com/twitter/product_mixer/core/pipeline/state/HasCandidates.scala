package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun

tra  HasCand dates[Cand date <: Un versalNoun[Any], T] {
  def cand dates: Seq[Cand date]
  def updateCand dates(newCand dates: Seq[Cand date]): T
}
