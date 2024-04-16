package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.esc rb rd.{thr ftscala => esb}
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features. d aUnderstand ngAnnotat on dsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.S ceT et dFeature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.T etyp eContentRepos ory
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.content.ContentFeatureAdapter
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.ho _m xer.ut l.t etyp e.content.FeatureExtract on lper
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
 mport com.tw ter.servo.keyvalue.KeyValueResult
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.common.ut l. d aUnderstand ngAnnotat ons
 mport com.tw ter.t etyp e.{thr ftscala => tp}
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.Return
 mport com.tw ter.ut l.Throw
 mport com.tw ter.ut l.Try
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object T etyp eContentDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class T etyp eContentFeatureHydrator @ nject() (
  @Na d(T etyp eContentRepos ory) cl ent: KeyValueRepos ory[Seq[Long], Long, tp.T et],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T etyp eContent")

  overr de val features: Set[Feature[_, _]] = Set(
     d aUnderstand ngAnnotat on dsFeature,
    T etyp eContentDataRecordFeature
  )

  overr de val statScope: Str ng =  dent f er.toStr ng

  pr vate val bulkRequestLatencyStat =
    statsRece ver.scope(statScope).scope("bulkRequest").stat("latency_ms")
  pr vate val postTransfor rLatencyStat =
    statsRece ver.scope(statScope).scope("postTransfor r").stat("latency_ms")
  pr vate val bulkPostTransfor rLatencyStat =
    statsRece ver.scope(statScope).scope("bulkPostTransfor r").stat("latency_ms")

  pr vate val DefaultDataRecord: DataRecord = new DataRecord()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val t et dsToHydrate = cand dates.map(getCand dateOr g nalT et d).d st nct

    val response: Future[KeyValueResult[Long, tp.T et]] = Stat.t  Future(bulkRequestLatencyStat) {
       f (t et dsToHydrate. sEmpty) Future.value(KeyValueResult.empty)
      else cl ent(t et dsToHydrate)
    }

    response.flatMap { result =>
      Stat.t  Future(bulkPostTransfor rLatencyStat) {
        OffloadFuturePools
          .parallel ze[Cand dateW hFeatures[T etCand date], Try[(Seq[Long], DataRecord)]](
            cand dates,
            parTransfor r(result, _),
            parallel sm = 32,
            default = Return((Seq.empty, DefaultDataRecord))
          ).map {
            _.map {
              case Return(result) =>
                FeatureMapBu lder()
                  .add( d aUnderstand ngAnnotat on dsFeature, result._1)
                  .add(T etyp eContentDataRecordFeature, result._2)
                  .bu ld()
              case Throw(e) =>
                FeatureMapBu lder()
                  .add( d aUnderstand ngAnnotat on dsFeature, Throw(e))
                  .add(T etyp eContentDataRecordFeature, Throw(e))
                  .bu ld()
            }
          }
      }
    }
  }

  pr vate def parTransfor r(
    result: KeyValueResult[Long, tp.T et],
    cand date: Cand dateW hFeatures[T etCand date]
  ): Try[(Seq[Long], DataRecord)] = {
    val or g nalT et d = So (getCand dateOr g nalT et d(cand date))
    val value = observedGet(key = or g nalT et d, keyValueResult = result)
    Stat.t  (postTransfor rLatencyStat)(postTransfor r(value))
  }

  pr vate def postTransfor r(
    result: Try[Opt on[tp.T et]]
  ): Try[(Seq[Long], DataRecord)] = {
    result.map { t et =>
      val transfor dValue = t et.map(FeatureExtract on lper.extractFeatures)
      val semant cAnnotat ons = transfor dValue
        .flatMap { contentFeatures =>
          contentFeatures.semant cCoreAnnotat ons.map {
            getNonSens  veH ghRecall d aUnderstand ngAnnotat onEnt y ds
          }
        }.getOrElse(Seq.empty)
      val dataRecord = ContentFeatureAdapter.adaptToDataRecords(transfor dValue).asScala. ad
      (semant cAnnotat ons, dataRecord)
    }
  }

  pr vate def getCand dateOr g nalT et d(
    cand date: Cand dateW hFeatures[T etCand date]
  ): Long = {
    cand date.features
      .getOrElse(S ceT et dFeature, None).getOrElse(cand date.cand date. d)
  }

  pr vate def getNonSens  veH ghRecall d aUnderstand ngAnnotat onEnt y ds(
    semant cCoreAnnotat ons: Seq[esb.T etEnt yAnnotat on]
  ): Seq[Long] =
    semant cCoreAnnotat ons
      .f lter( d aUnderstand ngAnnotat ons. sEl g bleNonSens  veH ghRecallMUAnnotat on)
      .map(_.ent y d)
}
