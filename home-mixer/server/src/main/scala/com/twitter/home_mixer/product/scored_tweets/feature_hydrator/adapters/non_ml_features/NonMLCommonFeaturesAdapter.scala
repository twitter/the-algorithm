package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features

 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport java.lang.{Long => JLong}

case class NonMLCommonFeatures(
  user d: Long,
  pred ct onRequest d: Opt on[Long],
  servedT  stamp: Long,
)

/**
 * def ne non ml features adapter to create a data record wh ch  ncludes many non ml features
 * e.g. pred ct onRequest d, user d, t et d to be used as jo ned key  n batch p pel ne.
 */
object NonMLCommonFeaturesAdapter extends T  l nesMutat ngAdapterBase[NonMLCommonFeatures] {

  pr vate val featureContext = new FeatureContext(
    SharedFeatures.USER_ D,
    T  l nesSharedFeatures.PRED CT ON_REQUEST_ D,
    T  l nesSharedFeatures.SERVED_T MESTAMP,
  )

  overr de def getFeatureContext: FeatureContext = featureContext

  overr de val commonFeatures: Set[Feature[_]] = Set(
    SharedFeatures.USER_ D,
    T  l nesSharedFeatures.PRED CT ON_REQUEST_ D,
    T  l nesSharedFeatures.SERVED_T MESTAMP,
  )

  overr de def setFeatures(
    nonMLCommonFeatures: NonMLCommonFeatures,
    r chDataRecord: R chDataRecord
  ): Un  = {
    r chDataRecord.setFeatureValue[JLong](SharedFeatures.USER_ D, nonMLCommonFeatures.user d)
    nonMLCommonFeatures.pred ct onRequest d.foreach(
      r chDataRecord.setFeatureValue[JLong](T  l nesSharedFeatures.PRED CT ON_REQUEST_ D, _))
    r chDataRecord.setFeatureValue[JLong](
      T  l nesSharedFeatures.SERVED_T MESTAMP,
      nonMLCommonFeatures.servedT  stamp)
  }
}
