package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.content

 mport com.tw ter.ho _m xer.model.ContentFeatures
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .ut l.DataRecordConverters.R chDataRecordWrapper
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.features.common. nReplyToT etT  l nesSharedFeatures

object  nReplyToContentFeatureAdapter
    extends T  l nesMutat ngAdapterBase[Opt on[ContentFeatures]] {

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    //  d a Features
     nReplyToT etT  l nesSharedFeatures.ASPECT_RAT O_DEN,
     nReplyToT etT  l nesSharedFeatures.ASPECT_RAT O_NUM,
     nReplyToT etT  l nesSharedFeatures.HE GHT_1,
     nReplyToT etT  l nesSharedFeatures.HE GHT_2,
     nReplyToT etT  l nesSharedFeatures.V DEO_DURAT ON,
    // TextFeatures
     nReplyToT etT  l nesSharedFeatures.NUM_CAPS,
     nReplyToT etT  l nesSharedFeatures.TWEET_LENGTH,
     nReplyToT etT  l nesSharedFeatures.HAS_QUEST ON,
  )

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  overr de def setFeatures(
    contentFeatures: Opt on[ContentFeatures],
    r chDataRecord: R chDataRecord
  ): Un  = {
     f (contentFeatures.nonEmpty) {
      val features = contentFeatures.get
      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.ASPECT_RAT O_DEN,
        features.aspectRat oNum.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.ASPECT_RAT O_NUM,
        features.aspectRat oNum.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.HE GHT_1,
        features.  ghts.flatMap(_.l ft(0)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.HE GHT_2,
        features.  ghts.flatMap(_.l ft(1)).map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.V DEO_DURAT ON,
        features.v deoDurat onMs.map(_.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.NUM_CAPS,
        So (features.numCaps.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.TWEET_LENGTH,
        So (features.length.toDouble)
      )

      r chDataRecord.setFeatureValueFromOpt on(
         nReplyToT etT  l nesSharedFeatures.HAS_QUEST ON,
        So (features.hasQuest on)
      )
    }
  }
}
