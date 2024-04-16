package com.tw ter.ho _m xer.product.l st_recom nded_users

 mport com.tw ter.ho _m xer.product.l st_recom nded_users.cand date_s ce.BlenderUsersCand dateS ce
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator. sG zmoduckVal dUserFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator. sSGSVal dUserFeatureHydrator
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator.RecentL st mbersFeature
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersQuery
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.Urt emCand dateDecorator
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. em.user.UserCand dateUrt emBu lder
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder. tadata.Cl entEvent nfoBu lder
 mport com.tw ter.product_m xer.component_l brary.gate.EmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.decorator.Cand dateDecorator
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.search.blender.thr ftscala.Thr ftBlenderRequest
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class BlenderUsersCand dateP pel neConf g @ nject() (
  blenderUsersCand dateS ce: BlenderUsersCand dateS ce,
   sG zmoduckVal dUserFeatureHydrator:  sG zmoduckVal dUserFeatureHydrator,
   sSGSVal dUserFeatureHydrator:  sSGSVal dUserFeatureHydrator)
    extends Cand dateP pel neConf g[
      L stRecom ndedUsersQuery,
      Thr ftBlenderRequest,
      Long,
      UserCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("BlenderUsers")

  overr de val gates: Seq[Gate[L stRecom ndedUsersQuery]] =
    Seq(EmptySeqFeatureGate(RecentL st mbersFeature))

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    L stRecom ndedUsersQuery,
    Thr ftBlenderRequest
  ] = BlenderUsersCand dateP pel neQueryTransfor r

  overr de val cand dateS ce: BaseCand dateS ce[Thr ftBlenderRequest, Long] =
    blenderUsersCand dateS ce

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    Long,
    UserCand date
  ] = { cand date => UserCand date( d = cand date) }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
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
