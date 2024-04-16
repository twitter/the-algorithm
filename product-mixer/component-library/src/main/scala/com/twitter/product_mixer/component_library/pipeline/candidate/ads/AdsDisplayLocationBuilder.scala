package com.tw ter.product_m xer.component_l brary.p pel ne.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => ads}
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

tra  AdsD splayLocat onBu lder[-Query <: P pel neQuery w h AdsQuery] {

  def apply(query: Query): ads.D splayLocat on
}

case class Stat cAdsD splayLocat onBu lder(d splayLocat on: ads.D splayLocat on)
    extends AdsD splayLocat onBu lder[P pel neQuery w h AdsQuery] {

  def apply(query: P pel neQuery w h AdsQuery): ads.D splayLocat on = d splayLocat on
}
