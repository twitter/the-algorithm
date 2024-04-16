package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.UserFollo dTop c dsRepos ory
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
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
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

object UserFollo dTop c dsFeature extends Feature[T etCand date, Seq[Long]]

@S ngleton
class UserFollo dTop c dsFeatureHydrator @ nject() (
  @Na d(UserFollo dTop c dsRepos ory)
  cl ent: KeyValueRepos ory[Seq[Long], Long, Seq[Long]],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("UserFollo dTop c ds")

  overr de val features: Set[Feature[_, _]] = Set(UserFollo dTop c dsFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val poss blyAuthor ds = extractKeys(cand dates)
    val author ds = poss blyAuthor ds.flatten

    val response: Future[KeyValueResult[Long, Seq[Long]]] =
       f (author ds. sEmpty) Future.value(KeyValueResult.empty) else cl ent(author ds)

    response.map { result =>
      poss blyAuthor ds.map { poss blyAuthor d =>
        val value = observedGet(key = poss blyAuthor d, keyValueResult = result)
        val transfor dValue = postTransfor r(value)

        FeatureMapBu lder().add(UserFollo dTop c dsFeature, transfor dValue).bu ld()
      }
    }
  }

  pr vate def postTransfor r( nput: Try[Opt on[Seq[Long]]]): Try[Seq[Long]] = {
     nput.map(_.getOrElse(Seq.empty[Long]))
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
