package com.tw ter.ho _m xer.product.scored_t ets.f lter

 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nReplyToT et dFeature
 mport com.tw ter.ho _m xer.ut l.ReplyRet etUt l
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

/**
 * T  f lter removes s ce t ets of ret ets, added v a second EB call  n TLR
 */
object Ret etS ceT etRemov ngF lter extends F lter[P pel neQuery, T etCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Ret etS ceT etRemov ng")

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[F lterResult[T etCand date]] = {
    val (kept, removed) =
      cand dates.part  on(
        _.features.getOrElse(Earlyb rdFeature, None).ex sts(_. sS ceT et)) match {
        case (s ceT ets, nonS ceT ets) =>
          val  nReplyToT et ds: Set[Long] =
            nonS ceT ets
              .f lter(ReplyRet etUt l. sEl g bleReply(_)).flatMap(
                _.features.getOrElse( nReplyToT et dFeature, None)).toSet
          val (keptS ceT ets, removedS ceT ets) = s ceT ets
            .map(_.cand date)
            .part  on(cand date =>  nReplyToT et ds.conta ns(cand date. d))
          (nonS ceT ets.map(_.cand date) ++ keptS ceT ets, removedS ceT ets)
      }
    St ch.value(F lterResult(kept = kept, removed = removed))
  }
}
