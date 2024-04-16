package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.non_ml_features

 mport com.tw ter.ml.ap .constant.SharedFeatures
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport java.lang.{Long => JLong}

case class NonMLCand dateFeatures(
  t et d: Long,
  s ceT et d: Opt on[Long],
  or g nalAuthor d: Opt on[Long],
)

/**
 * def ne non ml features adapter to create a data record wh ch  ncludes many non ml features
 * e.g. pred ct onRequest d, user d, t et d to be used as jo ned key  n batch p pel ne.
 */
object NonMLCand dateFeaturesAdapter extends T  l nesMutat ngAdapterBase[NonMLCand dateFeatures] {

  pr vate val featureContext = new FeatureContext(
    SharedFeatures.TWEET_ D,
    // For Secondary Engage nt data generat on
    T  l nesSharedFeatures.SOURCE_TWEET_ D,
    T  l nesSharedFeatures.OR G NAL_AUTHOR_ D,
  )

  overr de def getFeatureContext: FeatureContext = featureContext

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  overr de def setFeatures(
    nonMLCand dateFeatures: NonMLCand dateFeatures,
    r chDataRecord: R chDataRecord
  ): Un  = {
    r chDataRecord.setFeatureValue[JLong](SharedFeatures.TWEET_ D, nonMLCand dateFeatures.t et d)
    nonMLCand dateFeatures.s ceT et d.foreach(
      r chDataRecord.setFeatureValue[JLong](T  l nesSharedFeatures.SOURCE_TWEET_ D, _))
    nonMLCand dateFeatures.or g nalAuthor d.foreach(
      r chDataRecord.setFeatureValue[JLong](T  l nesSharedFeatures.OR G NAL_AUTHOR_ D, _))
  }
}
