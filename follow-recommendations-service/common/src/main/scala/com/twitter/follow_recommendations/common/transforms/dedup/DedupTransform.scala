package com.tw ter.follow_recom ndat ons.common.transforms.dedup

 mport com.tw ter.follow_recom ndat ons.common.base.Transform
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.st ch.St ch
 mport scala.collect on.mutable

class DedupTransform[Request, Cand date <: Un versalNoun[Long]]()
    extends Transform[Request, Cand date] {
  overr de def transform(target: Request, cand dates: Seq[Cand date]): St ch[Seq[Cand date]] = {
    val seen = mutable.HashSet[Long]()
    St ch.value(cand dates.f lter(cand date => seen.add(cand date. d)))
  }
}
