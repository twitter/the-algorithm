package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.AuthorFeatureRepos ory
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.author_features.AuthorFeaturesAdapter
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.servo.repos ory.KeyValueResult
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.author_features.v1.{thr ftjava => af}
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object AuthorFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class AuthorFeatureHydrator @ nject() (
  @Na d(AuthorFeatureRepos ory) cl ent: KeyValueRepos ory[Seq[Long], Long, af.AuthorFeatures],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("AuthorFeature")

  overr de val features: Set[Feature[_, _]] = Set(AuthorFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val poss blyAuthor ds = extractKeys(cand dates)
    val author ds = poss blyAuthor ds.flatten

    val response: Future[KeyValueResult[Long, af.AuthorFeatures]] =
       f (author ds.nonEmpty) cl ent(author ds)
      else Future.value(KeyValueResult.empty)

    response.map { result =>
      poss blyAuthor ds.map { poss blyAuthor d =>
        val value = observedGet(key = poss blyAuthor d, keyValueResult = result)
        val transfor dValue = postTransfor r(value)

        FeatureMapBu lder().add(AuthorFeature, transfor dValue).bu ld()
      }
    }
  }

  pr vate def postTransfor r(authorFeatures: Try[Opt on[af.AuthorFeatures]]): Try[DataRecord] = {
    authorFeatures.map {
      _.map { features => AuthorFeaturesAdapter.adaptToDataRecords(features).asScala. ad }
        .getOrElse(new DataRecord())
    }
  }

  pr vate def extractKeys(
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[Opt on[Long]] = {
    cand dates.map { cand date =>
      Cand datesUt l.getOr g nalAuthor d(cand date.features)
    }
  }
}
