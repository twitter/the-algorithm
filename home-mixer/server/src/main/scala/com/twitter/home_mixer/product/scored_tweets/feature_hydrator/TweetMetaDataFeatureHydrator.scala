package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Cand dateS ce dFeature
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .ut l.DataRecordConverters._
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecord nAFeature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.ut l.OffloadFuturePools
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport java.lang.{Long => JLong}

object T et taDataDataRecord
    extends DataRecord nAFeature[T etCand date]
    w h FeatureW hDefaultOnFa lure[T etCand date, DataRecord] {
  overr de def defaultValue: DataRecord = new DataRecord()
}

object T et taDataFeatureHydrator
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("T et taData")

  overr de def features: Set[Feature[_, _]] = Set(T et taDataDataRecord)

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = OffloadFuturePools.offload {
    val r chDataRecord = new R chDataRecord()
    setFeatures(r chDataRecord, cand date, ex st ngFeatures)
    FeatureMapBu lder().add(T et taDataDataRecord, r chDataRecord.getRecord).bu ld()
  }

  pr vate def setFeatures(
    r chDataRecord: R chDataRecord,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): Un  = {
    r chDataRecord.setFeatureValue[JLong](SharedFeatures.TWEET_ D, cand date. d)

    r chDataRecord.setFeatureValueFromOpt on(
      T  l nesSharedFeatures.OR G NAL_AUTHOR_ D,
      Cand datesUt l.getOr g nalAuthor d(ex st ngFeatures))

    r chDataRecord.setFeatureValueFromOpt on(
      T  l nesSharedFeatures.CAND DATE_TWEET_SOURCE_ D,
      ex st ngFeatures.getOrElse(Cand dateS ce dFeature, None).map(_.value.toLong))
  }
}
