package com.tw ter.product_m xer.core.p pel ne.state

 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Decorat on
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

tra  HasCand datesW hDeta ls[T] {
  def cand datesW hDeta ls: Seq[Cand dateW hDeta ls]
  def updateCand datesW hDeta ls(newCand dates: Seq[Cand dateW hDeta ls]): T

  def updateDecorat ons(decorat on: Seq[Decorat on]): T
}
