package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.dal.personal_data.{thr ftjava => pd}
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ho _m xer.model.Ho Features.Earlyb rdFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecordOpt onalFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.DoubleDataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Fetch
 mport com.tw ter.strato.generated.cl ent.ml.featureStore.S mClustersUser nterested nT etEmbedd ngDotProduct20M145K2020OnUserT etEdgeCl entColumn
 mport javax. nject. nject
 mport javax. nject.S ngleton

object S mClustersUser nterested nT etEmbedd ngDataRecordFeature
    extends DataRecordOpt onalFeature[T etCand date, Double]
    w h DoubleDataRecordCompat ble {
  overr de val featureNa : Str ng =
    "user-t et.recom ndat ons.s m_clusters_scores.user_ nterested_ n_t et_embedd ng_dot_product_20m_145k_2020"
  overr de val personalDataTypes: Set[pd.PersonalDataType] =
    Set(pd.PersonalDataType. nferred nterests)
}

@S ngleton
class S mClustersUserT etScoresHydrator @ nject() (
  s mClustersColumn: S mClustersUser nterested nT etEmbedd ngDotProduct20M145K2020OnUserT etEdgeCl entColumn,
  statsRece ver: StatsRece ver)
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("S mClustersUserT etScores")

  overr de val features: Set[Feature[_, _]] = Set(
    S mClustersUser nterested nT etEmbedd ngDataRecordFeature)

  pr vate val scopedStatsRece ver = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val keyFoundCounter = scopedStatsRece ver.counter("key/found")
  pr vate val keyLossCounter = scopedStatsRece ver.counter("key/loss")
  pr vate val keyFa lureCounter = scopedStatsRece ver.counter("key/fa lure")
  pr vate val keySk pCounter = scopedStatsRece ver.counter("key/sk p")

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(S mClustersUser nterested nT etEmbedd ngDataRecordFeature, None)
    .bu ld()
  pr vate val M nFavToHydrate = 9

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    St ch.run {
      St ch.collect {
        cand dates.map { cand date =>
          val ebFeatures = cand date.features.getOrElse(Earlyb rdFeature, None)
          val favCount = ebFeatures.flatMap(_.favCountV2).getOrElse(0)
          
           f (ebFeatures. sEmpty || favCount >= M nFavToHydrate) {
            s mClustersColumn.fetc r
              .fetch((query.getRequ redUser d, cand date.cand date. d), Un )
              .map {
                case Fetch.Result(response, _) =>
                   f (response.nonEmpty) keyFoundCounter. ncr() else keyLossCounter. ncr()
                  FeatureMapBu lder()
                    .add(S mClustersUser nterested nT etEmbedd ngDataRecordFeature, response)
                    .bu ld()
                case _ =>
                  keyFa lureCounter. ncr()
                  DefaultFeatureMap
              }
          } else {
            keySk pCounter. ncr()
            St ch.value(DefaultFeatureMap)
          }
        }
      }
    }
  }
}
