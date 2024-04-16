package com.tw ter.ho _m xer.product.l st_recom nded_users.feature_hydrator

 mport com.tw ter.g zmoduck.{thr ftscala => gt}
 mport com.tw ter.ho _m xer.product.l st_recom nded_users.model.L stRecom ndedUsersFeatures. sG zmoduckVal dUserFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.UserCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.spam.rtf.{thr ftscala => rtf}
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport com.tw ter.ut l.Return

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class  sG zmoduckVal dUserFeatureHydrator @ nject() (g zmoduck: G zmoduck)
    extends BulkCand dateFeatureHydrator[P pel neQuery, UserCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er(" sG zmoduckVal dUser")

  overr de val features: Set[Feature[_, _]] = Set( sG zmoduckVal dUserFeature)

  pr vate val queryF elds: Set[gt.QueryF elds] = Set(gt.QueryF elds.Safety)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[UserCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val context = gt.LookupContext(
      forUser d = query.getOpt onalUser d,
       ncludeProtected = true,
      safetyLevel = So (rtf.SafetyLevel.Recom ndat ons)
    )
    val user ds = cand dates.map(_.cand date. d)

    St ch
      .collectToTry(
        user ds.map(user d => g zmoduck.getUserBy d(user d, queryF elds, context))).map {
        userResults =>
          val  dToUserSafetyMap = userResults
            .collect {
              case Return(user) => user
            }.map(user => user. d -> user.safety).toMap

          cand dates.map { cand date =>
            val safety =  dToUserSafetyMap.getOrElse(cand date.cand date. d, None)
            val  sVal dUser = safety. sDef ned &&
              !safety.ex sts(_.deact vated) &&
              !safety.ex sts(_.suspended) &&
              !safety.ex sts(_. sProtected) &&
              !safety.flatMap(_.offboarded).getOrElse(false)

            FeatureMapBu lder()
              .add( sG zmoduckVal dUserFeature,  sVal dUser)
              .bu ld()
          }
      }
  }
}
