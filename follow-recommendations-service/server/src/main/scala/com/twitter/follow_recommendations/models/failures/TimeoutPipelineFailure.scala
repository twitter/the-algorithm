package com.tw ter.follow_recom ndat ons.models.fa lures

 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.Cand dateS ceT  out
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure

object T  outP pel neFa lure {
  def apply(cand dateS ceNa : Str ng): P pel neFa lure = {
    P pel neFa lure(
      Cand dateS ceT  out,
      s"Cand date S ce $cand dateS ceNa  t  d out before return ng cand dates")
  }
}
