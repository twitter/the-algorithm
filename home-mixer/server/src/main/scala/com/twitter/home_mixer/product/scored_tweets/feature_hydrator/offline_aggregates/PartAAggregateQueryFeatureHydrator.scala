package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.offl ne_aggregates

 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T  l neAggregate tadataRepos ory
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T  l neAggregatePartARepos ory
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.servo.repos ory.Repos ory
 mport com.tw ter.t  l nes.data_process ng.jobs.t  l ne_rank ng_user_features.T  l nesPartAStoreReg ster
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work.StoreConf g
 mport com.tw ter.t  l nes.suggests.common.dense_data_record.thr ftscala.DenseFeature tadata
 mport com.tw ter.user_sess on_store.thr ftjava.UserSess on
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object PartAAggregateRootFeature extends BaseAggregateRootFeature {
  overr de val aggregateStores: Set[StoreConf g[_]] = T  l nesPartAStoreReg ster.allStores
}

@S ngleton
class PartAAggregateQueryFeatureHydrator @ nject() (
  @Na d(T  l neAggregatePartARepos ory)
  repos ory: Repos ory[Long, Opt on[UserSess on]],
  @Na d(T  l neAggregate tadataRepos ory)
   tadataRepos ory: Repos ory[ nt, Opt on[DenseFeature tadata]])
    extends BaseAggregateQueryFeatureHydrator(
      repos ory,
       tadataRepos ory,
      PartAAggregateRootFeature
    ) {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("PartAAggregateQuery")

  overr de val features = Set(PartAAggregateRootFeature)
}
