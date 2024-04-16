package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s. tr cCenterUserCount ngFeatureRepos ory
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.onboard ng.relevance.features.{thr ftjava => rf}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.servo.keyvalue.KeyValueResult
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object  tr cCenterUserCount ngFeature
    extends Feature[T etCand date, Opt on[rf.MCUserCount ngFeatures]]

@S ngleton
class  tr cCenterUserCount ngFeatureHydrator @ nject() (
  @Na d( tr cCenterUserCount ngFeatureRepos ory) cl ent: KeyValueRepos ory[Seq[
    Long
  ], Long, rf.MCUserCount ngFeatures],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er(" tr cCenterUserCount ng")

  overr de val features: Set[Feature[_, _]] = Set( tr cCenterUserCount ngFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val poss blyAuthor ds = extractKeys(cand dates)
    val user ds = poss blyAuthor ds.flatten

    val response: Future[KeyValueResult[Long, rf.MCUserCount ngFeatures]] =
       f (user ds. sEmpty) Future.value(KeyValueResult.empty) else cl ent(user ds)

    response.map { result =>
      poss blyAuthor ds.map { poss blyAuthor d =>
        val value = observedGet(key = poss blyAuthor d, keyValueResult = result)
        FeatureMapBu lder().add( tr cCenterUserCount ngFeature, value).bu ld()
      }
    }
  }

  pr vate def extractKeys(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[Long]] = {
    cand dates.map { cand date =>
      cand date.features
        .getTry(Author dFeature)
        .toOpt on
        .flatten
    }
  }
}
