package com.tw ter.ho _m xer.funct onal_component.f lter

 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport scala.collect on.mutable

object Ret etDedupl cat onF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Ret etDedupl cat on")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    //  f t re are 2 ret ets of t  sa  nat ve t et,   w ll choose t  f rst one
    // T  t ets are returned  n descend ng score order, so   w ll choose t  h g r scored t et
    val dedupedT et dsSet =
      cand dates.part  on(_.features.getOrElse( sRet etFeature, false)) match {
        case (ret ets, nat veT ets) =>
          val nat veT et ds = nat veT ets.map(_.cand date. d)
          val seenT et ds = mutable.Set[Long]() ++ nat veT et ds
          val dedupedRet ets = ret ets.f lter { ret et =>
            val t et dAndS ce d = Cand datesUt l.getT et dAndS ce d(ret et)
            val ret et sUn que = t et dAndS ce d.forall(!seenT et ds.conta ns(_))
             f (ret et sUn que) {
              seenT et ds ++= t et dAndS ce d
            }
            ret et sUn que
          }
          (nat veT ets ++ dedupedRet ets).map(_.cand date. d).toSet
      }

    val (kept, removed) =
      cand dates
        .map(_.cand date).part  on(cand date => dedupedT et dsSet.conta ns(cand date. d))
    St ch.value(F lterResult(kept = kept, removed = removed))
  }
}
