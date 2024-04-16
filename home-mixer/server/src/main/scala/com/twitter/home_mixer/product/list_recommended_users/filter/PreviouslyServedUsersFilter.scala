package com.tw ter.ho _m xer.product.l st_recom nded_users.f lter

 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator.RecentL st mbersFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lterResult
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.st ch.St ch

object Prev ouslyServedUsersF lter extends F lter[L stRecom ndedUsersQuery, UserCand date] {

  overr de val  dent f er: F lter dent f er = F lter dent f er("Prev ouslyServedUsers")

  overr de def apply(
    query: L stRecom ndedUsersQuery,
    cand dates: Seq[Cand dateW hFeatures[UserCand date]]
  ): St ch[F lterResult[UserCand date]] = {

    val recentL st mbers = query.features.map(_.getOrElse(RecentL st mbersFeature, Seq.empty))

    val servedUser ds = query.p pel neCursor.map(_.excluded ds)

    val excludedUser ds = (recentL st mbers.getOrElse(Seq.empty) ++
      query.selectedUser ds.getOrElse(Seq.empty) ++
      query.excludedUser ds.getOrElse(Seq.empty) ++
      servedUser ds.getOrElse(Seq.empty)).toSet

    val (removed, kept) =
      cand dates.map(_.cand date).part  on(cand date => excludedUser ds.conta ns(cand date. d))

    St ch.value(F lterResult(kept = kept, removed = removed))
  }
}
