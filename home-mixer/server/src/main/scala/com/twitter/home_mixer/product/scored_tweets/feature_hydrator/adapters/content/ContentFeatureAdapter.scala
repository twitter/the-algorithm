package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.adapters.content

 mport com.tw ter.ho _m xer.model.ContentFeatures
 mport com.tw ter.ml.ap .Feature
 mport com.tw ter.ml.ap .FeatureContext
 mport com.tw ter.ml.ap .R chDataRecord
 mport com.tw ter.ml.ap .ut l.DataRecordConverters._
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T  l nesMutat ngAdapterBase
 mport com.tw ter.t  l nes.pred ct on.common.adapters.T etLengthType
 mport com.tw ter.t  l nes.pred ct on.features.common.T  l nesSharedFeatures
 mport com.tw ter.t  l nes.pred ct on.features.conversat on_features.Conversat onFeatures
 mport scala.collect on.JavaConverters._

object ContentFeatureAdapter extends T  l nesMutat ngAdapterBase[Opt on[ContentFeatures]] {

  overr de val getFeatureContext: FeatureContext = new FeatureContext(
    Conversat onFeatures. S_SELF_THREAD_TWEET,
    Conversat onFeatures. S_LEAF_ N_SELF_THREAD,
    T  l nesSharedFeatures.ASPECT_RAT O_DEN,
    T  l nesSharedFeatures.ASPECT_RAT O_NUM,
    T  l nesSharedFeatures.B T_RATE,
    T  l nesSharedFeatures.CLASS F CAT ON_LABELS,
    T  l nesSharedFeatures.COLOR_1_BLUE,
    T  l nesSharedFeatures.COLOR_1_GREEN,
    T  l nesSharedFeatures.COLOR_1_PERCENTAGE,
    T  l nesSharedFeatures.COLOR_1_RED,
    T  l nesSharedFeatures.FACE_AREAS,
    T  l nesSharedFeatures.HAS_APP_ NSTALL_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_DESCR PT ON,
    T  l nesSharedFeatures.HAS_QUEST ON,
    T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
    T  l nesSharedFeatures.HAS_T TLE,
    T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON,
    T  l nesSharedFeatures.HE GHT_1,
    T  l nesSharedFeatures.HE GHT_2,
    T  l nesSharedFeatures.HE GHT_3,
    T  l nesSharedFeatures.HE GHT_4,
    T  l nesSharedFeatures. S_360,
    T  l nesSharedFeatures. S_EMBEDDABLE,
    T  l nesSharedFeatures. S_MANAGED,
    T  l nesSharedFeatures. S_MONET ZABLE,
    T  l nesSharedFeatures.MED A_PROV DERS,
    T  l nesSharedFeatures.NUM_CAPS,
    T  l nesSharedFeatures.NUM_COLOR_PALLETTE_ TEMS,
    T  l nesSharedFeatures.NUM_FACES,
    T  l nesSharedFeatures.NUM_MED A_TAGS,
    T  l nesSharedFeatures.NUM_NEWL NES,
    T  l nesSharedFeatures.NUM_ST CKERS,
    T  l nesSharedFeatures.NUM_WH TESPACES,
    T  l nesSharedFeatures.RES ZE_METHOD_1,
    T  l nesSharedFeatures.RES ZE_METHOD_2,
    T  l nesSharedFeatures.RES ZE_METHOD_3,
    T  l nesSharedFeatures.RES ZE_METHOD_4,
    T  l nesSharedFeatures.TWEET_LENGTH,
    T  l nesSharedFeatures.TWEET_LENGTH_TYPE,
    T  l nesSharedFeatures.V DEO_DURAT ON,
    T  l nesSharedFeatures.V EW_COUNT,
    T  l nesSharedFeatures.W DTH_1,
    T  l nesSharedFeatures.W DTH_2,
    T  l nesSharedFeatures.W DTH_3,
    T  l nesSharedFeatures.W DTH_4,
  )

  overr de val commonFeatures: Set[Feature[_]] = Set.empty

  pr vate def getT etLengthType(t etLength:  nt): Long = {
    t etLength match {
      case x  f 0 > x || 280 < x => T etLengthType. NVAL D
      case x  f 0 <= x && x <= 30 => T etLengthType.VERY_SHORT
      case x  f 30 < x && x <= 60 => T etLengthType.SHORT
      case x  f 60 < x && x <= 90 => T etLengthType.MED UM
      case x  f 90 < x && x <= 140 => T etLengthType.LENGTHY
      case x  f 140 < x && x <= 210 => T etLengthType.VERY_LENGTHY
      case x  f x > 210 => T etLengthType.MAX MUM_LENGTH
    }
  }

  overr de def setFeatures(
    contentFeatures: Opt on[ContentFeatures],
    r chDataRecord: R chDataRecord
  ): Un  = {
     f (contentFeatures.nonEmpty) {
      val features = contentFeatures.get
      // Conversat on Features
      r chDataRecord.setFeatureValueFromOpt on(
        Conversat onFeatures. S_SELF_THREAD_TWEET,
        So (features.selfThread tadata.nonEmpty)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        Conversat onFeatures. S_LEAF_ N_SELF_THREAD,
        features.selfThread tadata.map(_. sLeaf)
      )

      //  d a Features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.ASPECT_RAT O_DEN,
        features.aspectRat oDen.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.ASPECT_RAT O_NUM,
        features.aspectRat oNum.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.B T_RATE,
        features.b Rate.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HE GHT_1,
        features.  ghts.flatMap(_.l ft(0)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HE GHT_2,
        features.  ghts.flatMap(_.l ft(1)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HE GHT_3,
        features.  ghts.flatMap(_.l ft(2)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HE GHT_4,
        features.  ghts.flatMap(_.l ft(3)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_MED A_TAGS,
        features.num d aTags.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.RES ZE_METHOD_1,
        features.res ze thods.flatMap(_.l ft(0)).map(_.toLong)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.RES ZE_METHOD_2,
        features.res ze thods.flatMap(_.l ft(1)).map(_.toLong)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.RES ZE_METHOD_3,
        features.res ze thods.flatMap(_.l ft(2)).map(_.toLong)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.RES ZE_METHOD_4,
        features.res ze thods.flatMap(_.l ft(3)).map(_.toLong)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.V DEO_DURAT ON,
        features.v deoDurat onMs.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.W DTH_1,
        features.w dths.flatMap(_.l ft(0)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.W DTH_2,
        features.w dths.flatMap(_.l ft(1)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.W DTH_3,
        features.w dths.flatMap(_.l ft(2)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.W DTH_4,
        features.w dths.flatMap(_.l ft(3)).map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_COLOR_PALLETTE_ TEMS,
        features.numColors.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.COLOR_1_RED,
        features.dom nantColorRed.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.COLOR_1_BLUE,
        features.dom nantColorBlue.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.COLOR_1_GREEN,
        features.dom nantColorGreen.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.COLOR_1_PERCENTAGE,
        features.dom nantColorPercentage
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.MED A_PROV DERS,
        features. d aOr g nProv ders.map(_.toSet.asJava)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures. S_360,
        features. s360
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.V EW_COUNT,
        features.v ewCount.map(_.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures. S_MANAGED,
        features. sManaged
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures. S_MONET ZABLE,
        features. sMonet zable
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures. S_EMBEDDABLE,
        features. sEmbeddable
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_ST CKERS,
        features.st cker ds.map(_.length.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_FACES,
        features.faceAreas.map(_.length.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.FACE_AREAS,
        // guard for except on from max on empty seq
        features.faceAreas.map(faceAreas =>
          faceAreas.map(_.toDouble).reduceOpt on(_ max _).getOrElse(0.0))
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_SELECTED_PREV EW_ MAGE,
        features.hasSelectedPrev ew mage
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_T TLE,
        features.hasT le
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_DESCR PT ON,
        features.hasDescr pt on
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_V S T_S TE_CALL_TO_ACT ON,
        features.hasV s S eCallToAct on
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_APP_ NSTALL_CALL_TO_ACT ON,
        features.hasApp nstallCallToAct on
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_WATCH_NOW_CALL_TO_ACT ON,
        features.hasWatchNowCallToAct on
      )
      // text features
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_CAPS,
        So (features.numCaps.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.TWEET_LENGTH,
        So (features.length.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.TWEET_LENGTH_TYPE,
        So (getT etLengthType(features.length.to nt))
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_WH TESPACES,
        So (features.numWh eSpaces.toDouble)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.HAS_QUEST ON,
        So (features.hasQuest on)
      )
      r chDataRecord.setFeatureValueFromOpt on(
        T  l nesSharedFeatures.NUM_NEWL NES,
        features.numNewl nes.map(_.toDouble)
      )
    }
  }
}
