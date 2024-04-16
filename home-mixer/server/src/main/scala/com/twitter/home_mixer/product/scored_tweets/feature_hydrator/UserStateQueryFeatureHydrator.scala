package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.UserStateFeature
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.user_ alth.v1.{thr ftscala => uhv1}
 mport com.tw ter.t  l nes.user_ alth.{thr ftscala => uh}
 mport com.tw ter.user_sess on_store.ReadOnlyUserSess onStore
 mport com.tw ter.user_sess on_store.ReadRequest
 mport com.tw ter.user_sess on_store.UserSess onDataset
 mport com.tw ter.user_sess on_store.UserSess onDataset.UserSess onDataset
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class UserStateQueryFeatureHydrator @ nject() (
  userSess onStore: ReadOnlyUserSess onStore)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("UserState")

  overr de val features: Set[Feature[_, _]] = Set(UserStateFeature)

  pr vate val datasets: Set[UserSess onDataset] = Set(UserSess onDataset.User alth)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    userSess onStore
      .read(ReadRequest(query.getRequ redUser d, datasets))
      .map { userSess on =>
        val userState = userSess on.flatMap {
          _.user alth match {
            case So (uh.User alth.V1(uhv1.User alth(userState))) => userState
            case _ => None
          }
        }

        FeatureMapBu lder()
          .add(UserStateFeature, userState)
          .bu ld()
      }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.9)
  )
}
