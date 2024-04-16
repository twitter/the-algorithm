package com.tw ter.product_m xer.core.funct onal_component.gate

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  ShouldCont nue[Query <: P pel neQuery] {
  def apply(query: Query): Boolean
}
