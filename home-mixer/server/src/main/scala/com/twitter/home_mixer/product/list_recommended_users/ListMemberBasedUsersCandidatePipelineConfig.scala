package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter. rm .cand date.{thr ftscala => t}
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.cand date_s ce.S m lar yBasedUsersCand dateS ce
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator. sG zmoduckVal dUserFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator. sL st mberFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator. sSGSVal dUserFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator.RecentL st mbersFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.f lter.DropMaxCand datesByAggregatedScoreF lter
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.f lter.Prev ouslyServedUsersF lter
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures. sL st mberFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.user.UserCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Cl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.f lter.Pred cateFeatureF lter
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class L st mberBasedUsersCand dateP pel neConf g @ nject() (
  s m lar yBasedUsersCand dateS ce: S m lar yBasedUsersCand dateS ce,
   sG zmoduckVal dUserFeatureHydrator:  sG zmoduckVal dUserFeatureHydrator,
   sL st mberFeatureHydrator:  sL st mberFeatureHydrator,
   sSGSVal dUserFeatureHydrator:  sSGSVal dUserFeatureHydrator)
    extends Cand dateP pel neConf g[
      L stRecom ndedUsersQuery,
      Seq[Long],
      t.Cand date,
      UserCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("L st mberBasedUsers")

  overr de val gates: Seq[Gate[L stRecom ndedUsersQuery]] =
    Seq(NonEmptySeqFeatureGate(RecentL st mbersFeature))

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[L stRecom ndedUsersQuery, Seq[
    Long
  ]] = { query =>
    query.features.map(_.getOrElse(RecentL st mbersFeature, Seq.empty)).getOrElse(Seq.empty)
  }

  overr de val cand dateS ce: BaseCand dateS ce[Seq[Long], t.Cand date] =
    s m lar yBasedUsersCand dateS ce

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.Cand date]
  ] = Seq(L st mberBasedUsersResponseFeatureTransfro r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.Cand date,
    UserCand date
  ] = { cand date =>
    UserCand date( d = cand date.user d)
  }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
    BaseCand dateFeatureHydrator[L stRecom ndedUsersQuery, UserCand date, _]
  ] = Seq( sL st mberFeatureHydrator)

  overr de val f lters: Seq[F lter[L stRecom ndedUsersQuery, UserCand date]] =
    Seq(
      Prev ouslyServedUsersF lter,
      Pred cateFeatureF lter.fromPred cate(
        F lter dent f er(" sL st mber"),
        shouldKeepCand date = { features => !features.getOrElse( sL st mberFeature, false) }
      ),
      DropMaxCand datesByAggregatedScoreF lter
    )

  overr de val postF lterFeatureHydrat on: Seq[
    BaseCand dateFeatureHydrator[L stRecom ndedUsersQuery, UserCand date, _]
  ] = Seq(
     sG zmoduckVal dUserFeatureHydrator,
     sSGSVal dUserFeatureHydrator
  )

  overr de val decorator: Opt on[Cand dateDecorator[L stRecom ndedUsersQuery, UserCand date]] = {
    val cl entEvent nfoBu lder = Cl entEvent nfoBu lder("user")
    val user emBu lder = UserCand dateUrt emBu lder(cl entEvent nfoBu lder)
    So (Urt emCand dateDecorator(user emBu lder))
  }
}
