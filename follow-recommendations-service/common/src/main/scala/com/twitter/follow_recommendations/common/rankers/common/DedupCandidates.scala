package com.tw ter.follow_recom ndat ons.common.rankers.common

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport scala.collect on.mutable

object DedupCand dates {
  def apply[C <: Un versalNoun[Long]]( nput: Seq[C]): Seq[C] = {
    val seen = mutable.HashSet[Long]()
     nput.f lter { cand date => seen.add(cand date. d) }
  }
}
