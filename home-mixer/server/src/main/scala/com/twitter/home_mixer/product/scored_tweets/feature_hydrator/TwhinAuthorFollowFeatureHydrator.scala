package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Twh nAuthorFollowFeatureRepos ory
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.twh n_embedd ngs.Twh nAuthorFollowEmbedd ngsAdapter
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .{thr ftscala => ml}
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
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object Twh nAuthorFollowFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Twh nAuthorFollowFeatureHydrator @ nject() (
  @Na d(Twh nAuthorFollowFeatureRepos ory)
  cl ent: KeyValueRepos ory[Seq[Long], Long, ml.FloatTensor],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Twh nAuthorFollow")

  overr de val features: Set[Feature[_, _]] = Set(Twh nAuthorFollowFeature)

  overr de val statScope: Str ng =  dent f er.toStr ng

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val poss blyAuthor ds = extractKeys(cand dates)
    val author ds = poss blyAuthor ds.flatten

    val response: Future[KeyValueResult[Long, ml.FloatTensor]] =
       f (author ds. sEmpty) Future.value(KeyValueResult.empty) else cl ent(author ds)

    response.map { result =>
      poss blyAuthor ds.map { poss blyAuthor d =>
        val value = observedGet(key = poss blyAuthor d, keyValueResult = result)
        val transfor dValue = postTransfor r(value)

        FeatureMapBu lder().add(Twh nAuthorFollowFeature, transfor dValue).bu ld()
      }
    }
  }

  pr vate def postTransfor r(embedd ng: Try[Opt on[ml.FloatTensor]]): Try[DataRecord] = {
    embedd ng.map { floatTensor =>
      Twh nAuthorFollowEmbedd ngsAdapter.adaptToDataRecords(floatTensor).asScala. ad
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
