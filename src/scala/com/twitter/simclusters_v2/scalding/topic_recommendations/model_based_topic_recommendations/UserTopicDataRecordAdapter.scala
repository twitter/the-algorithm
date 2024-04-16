package com.tw ter.s mclusters_v2.scald ng.top c_recom ndat ons.model_based_top c_recom ndat ons

 mport com.tw ter.ml.ap .ut l.FDsl._
 mport com.tw ter.ml.ap .{DataRecord, FeatureContext,  RecordOneToOneAdapter}

case class UserTop cTra n ngSample(
  user d: Long,
  follo dTop cs: Set[Long],
  not nterestedTop cs: Set[Long],
  userCountry: Str ng,
  userLanguage: Str ng,
  targetTop c d:  nt,
  user nterested nS mClusters: Map[ nt, Double],
  follo dTop csS mClusters: Map[ nt, Double],
  not nterestedTop csS mClusters: Map[ nt, Double])

class UserTop cDataRecordAdapter extends  RecordOneToOneAdapter[UserTop cTra n ngSample] {
   mport UserFeatures._

  /**
   * Get  s feature context used to annotate t  data.
   *
   * @return feature context
   */
  overr de def getFeatureContext: FeatureContext = UserFeatures.FeatureContext

  /**
   * Adapt record of type T to DataRecord.
   *
   * @param record raw record of type T
   *
   * @return a DataRecord
   *
   * @throws com.tw ter.ml.ap . nval dFeatureExcept on
   */
  overr de def adaptToDataRecord(record: UserTop cTra n ngSample): DataRecord = {
    val dr = new DataRecord()

    dr.setFeatureValue(User dFeature, record.user d)
    dr.setFeatureValue(
      UserS mClusterFeatures,
      record.user nterested nS mClusters.map {
        case ( d, score) =>  d.toStr ng -> score
      })
    dr.setFeatureValue(Follo dTop c dFeatures, record.follo dTop cs.map(_.toStr ng))
    dr.setFeatureValue(Not nterestedTop c dFeatures, record.not nterestedTop cs.map(_.toStr ng))
    dr.setFeatureValue(UserCountryFeature, record.userCountry)
    dr.setFeatureValue(UserLanguageFeature, record.userLanguage)

    dr.setFeatureValue(
      Follo dTop cS mClusterAvgFeatures,
      record.follo dTop csS mClusters.map {
        case ( d, score) =>  d.toStr ng -> score
      })

    dr.setFeatureValue(
      Not nterestedTop cS mClusterAvgFeatures,
      record.not nterestedTop csS mClusters.map {
        case ( d, score) =>  d.toStr ng -> score
      })
    dr.setFeatureValue(TargetTop c dFeatures, record.targetTop c d.toLong)
    dr.getRecord
  }
}
