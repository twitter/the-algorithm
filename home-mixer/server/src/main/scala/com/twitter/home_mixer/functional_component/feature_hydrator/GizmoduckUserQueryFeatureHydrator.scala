package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.g zmoduck.{thr ftscala => gt}
 mport com.tw ter.ho _m xer.model.Ho Features.UserFollow ngCountFeature
 mport com.tw ter.ho _m xer.model.Ho Features.UserScreenNa Feature
 mport com.tw ter.ho _m xer.model.Ho Features.UserTypeFeature
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.st ch.g zmoduck.G zmoduck
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class G zmoduckUserQueryFeatureHydrator @ nject() (g zmoduck: G zmoduck)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("G zmoduckUser")

  overr de val features: Set[Feature[_, _]] =
    Set(UserFollow ngCountFeature, UserTypeFeature, UserScreenNa Feature)

  pr vate val queryF elds: Set[gt.QueryF elds] =
    Set(gt.QueryF elds.Counts, gt.QueryF elds.Safety, gt.QueryF elds.Prof le)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val user d = query.getRequ redUser d
    g zmoduck
      .getUserBy d(
        user d = user d,
        queryF elds = queryF elds,
        context = gt.LookupContext(forUser d = So (user d),  ncludeSoftUsers = true))
      .map { user =>
        FeatureMapBu lder()
          .add(UserFollow ngCountFeature, user.counts.map(_.follow ng.to nt))
          .add(UserTypeFeature, So (user.userType))
          .add(UserScreenNa Feature, user.prof le.map(_.screenNa ))
          .bu ld()
      }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.7)
  )
}
