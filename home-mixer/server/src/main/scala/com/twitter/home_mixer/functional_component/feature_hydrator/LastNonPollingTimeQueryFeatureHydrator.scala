package com.tw ter.ho _m xer.funct onal_component.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Follow ngLastNonPoll ngT  Feature
 mport com.tw ter.ho _m xer.model.Ho Features.LastNonPoll ngT  Feature
 mport com.tw ter.ho _m xer.model.Ho Features.NonPoll ngT  sFeature
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.user_sess on_store.ReadRequest
 mport com.tw ter.user_sess on_store.ReadWr eUserSess onStore
 mport com.tw ter.user_sess on_store.UserSess onDataset
 mport com.tw ter.user_sess on_store.UserSess onDataset.UserSess onDataset
 mport com.tw ter.ut l.T  

 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class LastNonPoll ngT  QueryFeatureHydrator @ nject() (
  userSess onStore: ReadWr eUserSess onStore)
    extends QueryFeatureHydrator[P pel neQuery] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("LastNonPoll ngT  ")

  overr de val features: Set[Feature[_, _]] = Set(
    Follow ngLastNonPoll ngT  Feature,
    LastNonPoll ngT  Feature,
    NonPoll ngT  sFeature
  )

  pr vate val datasets: Set[UserSess onDataset] = Set(UserSess onDataset.NonPoll ngT  s)

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    userSess onStore
      .read(ReadRequest(query.getRequ redUser d, datasets))
      .map { userSess on =>
        val nonPoll ngT  stamps = userSess on.flatMap(_.nonPoll ngT  stamps)

        val lastNonPoll ngT   = nonPoll ngT  stamps
          .flatMap(_.nonPoll ngT  stampsMs. adOpt on)
          .map(T  .fromM ll seconds)

        val follow ngLastNonPoll ngT   = nonPoll ngT  stamps
          .flatMap(_.mostRecentHo LatestNonPoll ngT  stampMs)
          .map(T  .fromM ll seconds)

        val nonPoll ngT  s = nonPoll ngT  stamps
          .map(_.nonPoll ngT  stampsMs)
          .getOrElse(Seq.empty)

        FeatureMapBu lder()
          .add(Follow ngLastNonPoll ngT  Feature, follow ngLastNonPoll ngT  )
          .add(LastNonPoll ngT  Feature, lastNonPoll ngT  )
          .add(NonPoll ngT  sFeature, nonPoll ngT  s)
          .bu ld()
      }
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.9)
  )
}
