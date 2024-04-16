package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.earlyb rd.Earlyb rdAdapter
 mport com.tw ter.ho _m xer.model.Ho Features.Dev ceLanguageFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdSearchResultFeature
 mport com.tw ter.ho _m xer.model.Ho Features. sRet etFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T etUrlsFeature
 mport com.tw ter.ho _m xer.model.Ho Features.UserScreenNa Feature
 mport com.tw ter.ho _m xer.param.Ho M xer nject onNa s.Earlyb rdRepos ory
 mport com.tw ter.ho _m xer.ut l.ObservedKeyValueResultHandler
 mport com.tw ter.ho _m xer.ut l.earlyb rd.Earlyb rdResponseUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
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
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport com.tw ter.servo.keyvalue.KeyValueResult
 mport com.tw ter.servo.repos ory.KeyValueRepos ory
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Return
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton
 mport scala.collect on.JavaConverters._

object Earlyb rdDataRecordFeature
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

@S ngleton
class Earlyb rdFeatureHydrator @ nject() (
  @Na d(Earlyb rdRepos ory) cl ent: KeyValueRepos ory[
    (Seq[Long], Long),
    Long,
    eb.Thr ftSearchResult
  ],
  overr de val statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date]
    w h ObservedKeyValueResultHandler {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("Earlyb rd")

  overr de val features: Set[Feature[_, _]] = Set(
    Earlyb rdDataRecordFeature,
    Earlyb rdFeature,
    Earlyb rdSearchResultFeature,
    T etUrlsFeature
  )

  overr de val statScope: Str ng =  dent f er.toStr ng

  pr vate val scopedStatsRece ver = statsRece ver.scope(statScope)
  pr vate val or g nalKeyFoundCounter = scopedStatsRece ver.counter("or g nalKey/found")
  pr vate val or g nalKeyLossCounter = scopedStatsRece ver.counter("or g nalKey/loss")

  pr vate val ebSearchResultNotEx stPred cate: Cand dateW hFeatures[T etCand date] => Boolean =
    cand date => cand date.features.getOrElse(Earlyb rdSearchResultFeature, None). sEmpty
  pr vate val ebFeaturesNotEx stPred cate: Cand dateW hFeatures[T etCand date] => Boolean =
    cand date => cand date.features.getOrElse(Earlyb rdFeature, None). sEmpty

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val cand datesToHydrate = cand dates.f lter { cand date =>
      val  sEmpty =
        ebFeaturesNotEx stPred cate(cand date) && ebSearchResultNotEx stPred cate(cand date)
       f ( sEmpty) or g nalKeyLossCounter. ncr() else or g nalKeyFoundCounter. ncr()
       sEmpty
    }

    cl ent((cand datesToHydrate.map(_.cand date. d), query.getRequ redUser d))
      .map(handleResponse(query, cand dates, _, cand datesToHydrate))
  }

  pr vate def handleResponse(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]],
    results: KeyValueResult[Long, eb.Thr ftSearchResult],
    cand datesToHydrate: Seq[Cand dateW hFeatures[T etCand date]]
  ): Seq[FeatureMap] = {
    val queryFeatureMap = query.features.getOrElse(FeatureMap.empty)
    val userLanguages = queryFeatureMap.getOrElse(UserLanguagesFeature, Seq.empty)
    val u LanguageCode = queryFeatureMap.getOrElse(Dev ceLanguageFeature, None)
    val screenNa  = queryFeatureMap.getOrElse(UserScreenNa Feature, None)
    val follo dUser ds = queryFeatureMap.getOrElse(SGSFollo dUsersFeature, Seq.empty).toSet

    val searchResults = cand datesToHydrate
      .map { cand date =>
        observedGet(So (cand date.cand date. d), results)
      }.collect {
        case Return(So (value)) => value
      }

    val allSearchResults = searchResults ++
      cand dates.f lter(!ebSearchResultNotEx stPred cate(_)).flatMap { cand date =>
        cand date.features
          .getOrElse(Earlyb rdSearchResultFeature, None)
      }
    val  dToSearchResults = allSearchResults.map(sr => sr. d -> sr).toMap
    val t et dToEbFeatures = Earlyb rdResponseUt l.getT etThr ftFeaturesByT et d(
      searc rUser d = query.getRequ redUser d,
      screenNa  = screenNa ,
      userLanguages = userLanguages,
      u LanguageCode = u LanguageCode,
      follo dUser ds = follo dUser ds,
      mutuallyFollow ngUser ds = Set.empty,
      searchResults = allSearchResults,
      s ceT etSearchResults = Seq.empty,
    )

    cand dates.map { cand date =>
      val transfor dEbFeatures = t et dToEbFeatures.get(cand date.cand date. d)
      val earlyb rdFeatures =
         f (transfor dEbFeatures.nonEmpty) transfor dEbFeatures
        else cand date.features.getOrElse(Earlyb rdFeature, None)

      val cand date sRet et = cand date.features.getOrElse( sRet etFeature, false)
      val s ceT etEbFeatures =
        cand date.features.getOrElse(S ceT etEarlyb rdFeature, None)

      val or g nalT etEbFeatures =
         f (cand date sRet et && s ceT etEbFeatures.nonEmpty)
          s ceT etEbFeatures
        else earlyb rdFeatures

      val earlyb rdDataRecord =
        Earlyb rdAdapter.adaptToDataRecords(or g nalT etEbFeatures).asScala. ad

      FeatureMapBu lder()
        .add(Earlyb rdFeature, earlyb rdFeatures)
        .add(Earlyb rdDataRecordFeature, earlyb rdDataRecord)
        .add(Earlyb rdSearchResultFeature,  dToSearchResults.get(cand date.cand date. d))
        .add(T etUrlsFeature, earlyb rdFeatures.flatMap(_.urlsL st).getOrElse(Seq.empty))
        .bu ld()
    }
  }
}
