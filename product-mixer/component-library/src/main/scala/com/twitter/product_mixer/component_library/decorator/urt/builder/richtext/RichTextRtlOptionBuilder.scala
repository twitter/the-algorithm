package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.r chtext

 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  R chTextRtlOpt onBu lder[-Query <: P pel neQuery] {
  def apply(query: Query): Opt on[Boolean]
}

case class Stat cR chTextRtlOpt onBu lder[-Query <: P pel neQuery](rtlOpt on: Opt on[Boolean])
    extends R chTextRtlOpt onBu lder[Query] {
  overr de def apply(query: Query): Opt on[Boolean] = rtlOpt on
}
