package com.tw ter.ho _m xer.product.l st_recom nded_users.f lter

 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures.ScoreFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object DropMaxCand datesByAggregatedScoreF lter extends F lter[P pel neQuery, UserCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("DropMaxCand datesByAggregatedScore")

  pr vate val MaxS m larUserCand dates = 150

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[UserCand date]]
  ): St ch[F lterResult[UserCand date]] = {
    val user dToAggregatedScoreMap = cand dates
      .groupBy(_.cand date. d)
      .map {
        case (user d, cand dates) =>
          val aggregatedScore = cand dates.map(_.features.getOrElse(ScoreFeature, 0.0)).sum
          (user d, aggregatedScore)
      }

    val sortedCand dates = cand dates.sortBy(cand date =>
      -user dToAggregatedScoreMap.getOrElse(cand date.cand date. d, 0.0))

    val (kept, removed) = sortedCand dates.map(_.cand date).spl At(MaxS m larUserCand dates)

    St ch.value(F lterResult(kept, removed))
  }
}
