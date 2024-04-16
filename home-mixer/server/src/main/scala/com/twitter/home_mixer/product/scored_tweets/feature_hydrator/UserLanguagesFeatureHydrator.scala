package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.UserLanguagesRepos ory
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.QueryFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.search.common.constants.{thr ftscala => scc}
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object UserLanguagesFeature extends Feature[P pel neQuery, Seq[scc.Thr ftLanguage]]

@S ngleton
case class UserLanguagesFeatureHydrator @ nject() (
  @Na d(UserLanguagesRepos ory) cl ent: KeyValueRepos ory[Seq[Long], Long, Seq[
    scc.Thr ftLanguage
  ]],
  statsRece ver: StatsRece ver)
    extends QueryFeatureHydrator[P pel neQuery]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("UserLanguages")

  overr de val features: Set[Feature[_, _]] = Set(UserLanguagesFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def hydrate(query: P pel neQuery): St ch[FeatureMap] = {
    val key = query.getRequ redUser d
    St ch.callFuture(cl ent(Seq(key))).map { result =>
      val feature =
        observedGet(key = So (key), keyValueResult = result).map(_.getOrElse(Seq.empty))
      FeatureMapBu lder()
        .add(UserLanguagesFeature, feature)
        .bu ld()
    }
  }
}
