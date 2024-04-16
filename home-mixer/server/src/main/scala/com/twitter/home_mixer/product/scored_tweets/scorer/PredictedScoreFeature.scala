package com.tw ter.ho _m xer.product.scored_t ets.scorer

 mport com.tw ter.dal.personal_data.{thr ftjava => pd}
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Scor ng.Model  ghts
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.datarecord.DataRecordOpt onalFeature
 mport com.tw ter.product_m xer.core.feature.datarecord.DoubleDataRecordCompat ble
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.pred ct on.features.recap.RecapFeatures

sealed tra  Pred ctedScoreFeature
    extends DataRecordOpt onalFeature[T etCand date, Double]
    w h DoubleDataRecordCompat ble {

  overr de val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  def statNa : Str ng
  def model  ghtParam: FSBoundedParam[Double]
  def extractScore: FeatureMap => Opt on[Double] = _.getOrElse(t , None)
}

object Pred ctedFavor eScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_FAVOR TED.getFeatureNa 
  overr de val statNa  = "fav"
  overr de val model  ghtParam = Model  ghts.FavParam
}

object Pred ctedReplyScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_REPL ED.getFeatureNa 
  overr de val statNa  = "reply"
  overr de val model  ghtParam = Model  ghts.ReplyParam
}

object Pred ctedRet etScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_RETWEETED.getFeatureNa 
  overr de val statNa  = "ret et"
  overr de val model  ghtParam = Model  ghts.Ret etParam
}

object Pred ctedReplyEngagedByAuthorScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_REPL ED_REPLY_ENGAGED_BY_AUTHOR.getFeatureNa 
  overr de val statNa  = "reply_engaged_by_author"
  overr de val model  ghtParam = Model  ghts.ReplyEngagedByAuthorParam
}

object Pred ctedGoodCl ckConvoDescFavor edOrRepl edScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V1.getFeatureNa 
  overr de val statNa  = "good_cl ck_convo_desc_favor ed_or_repl ed"
  overr de val model  ghtParam = Model  ghts.GoodCl ckParam

  overr de def extractScore: FeatureMap => Opt on[Double] = { featureMap =>
    val goodCl ckV1Opt = featureMap.getOrElse(t , None)
    val goodCl ckV2Opt = featureMap.getOrElse(Pred ctedGoodCl ckConvoDescUamGt2ScoreFeature, None)

    (goodCl ckV1Opt, goodCl ckV2Opt) match {
      case (So (v1Score), So (v2Score)) => So (Math.max(v1Score, v2Score))
      case _ => goodCl ckV1Opt.orElse(goodCl ckV2Opt)
    }
  }
}

object Pred ctedGoodCl ckConvoDescUamGt2ScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_GOOD_CL CKED_V2.getFeatureNa 
  overr de val statNa  = "good_cl ck_convo_desc_uam_gt_2"
  overr de val model  ghtParam = Model  ghts.GoodCl ckV2Param
}

object Pred ctedGoodProf leCl ckScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_PROF LE_CL CKED_AND_PROF LE_ENGAGED.getFeatureNa 
  overr de val statNa  = "good_prof le_cl ck"
  overr de val model  ghtParam = Model  ghts.GoodProf leCl ckParam
}

object Pred ctedV deoPlayback50ScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_V DEO_PLAYBACK_50.getFeatureNa 
  overr de val statNa  = "v deo_playback_50"
  overr de val model  ghtParam = Model  ghts.V deoPlayback50Param
}

object Pred ctedT etDeta lD llScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_TWEET_DETA L_DWELLED_15_SEC.getFeatureNa 
  overr de val statNa  = "t et_deta l_d ll"
  overr de val model  ghtParam = Model  ghts.T etDeta lD llParam
}

object Pred ctedProf leD lledScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_PROF LE_DWELLED_20_SEC.getFeatureNa 
  overr de val statNa  = "prof le_d ll"
  overr de val model  ghtParam = Model  ghts.Prof leD lledParam
}

object Pred ctedBookmarkScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng = RecapFeatures.PRED CTED_ S_BOOKMARKED.getFeatureNa 
  overr de val statNa  = "bookmark"
  overr de val model  ghtParam = Model  ghts.BookmarkParam
}

object Pred ctedShareScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_SHARED.getFeatureNa 
  overr de val statNa  = "share"
  overr de val model  ghtParam = Model  ghts.ShareParam
}

object Pred ctedShare nuCl ckScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_SHARE_MENU_CL CKED.getFeatureNa 
  overr de val statNa  = "share_ nu_cl ck"
  overr de val model  ghtParam = Model  ghts.Share nuCl ckParam
}

// Negat ve Engage nts
object Pred ctedNegat veFeedbackV2ScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_NEGAT VE_FEEDBACK_V2.getFeatureNa 
  overr de val statNa  = "negat ve_feedback_v2"
  overr de val model  ghtParam = Model  ghts.Negat veFeedbackV2Param
}

object Pred ctedReportedScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_REPORT_TWEET_CL CKED.getFeatureNa 
  overr de val statNa  = "reported"
  overr de val model  ghtParam = Model  ghts.ReportParam
}

object Pred ctedStrongNegat veFeedbackScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_STRONG_NEGAT VE_FEEDBACK.getFeatureNa 
  overr de val statNa  = "strong_negat ve_feedback"
  overr de val model  ghtParam = Model  ghts.StrongNegat veFeedbackParam
}

object Pred cted akNegat veFeedbackScoreFeature extends Pred ctedScoreFeature {
  overr de val featureNa : Str ng =
    RecapFeatures.PRED CTED_ S_WEAK_NEGAT VE_FEEDBACK.getFeatureNa 
  overr de val statNa  = " ak_negat ve_feedback"
  overr de val model  ghtParam = Model  ghts. akNegat veFeedbackParam
}

object Pred ctedScoreFeature {
  val Pred ctedScoreFeatures: Set[Pred ctedScoreFeature] = Set(
    Pred ctedFavor eScoreFeature,
    Pred ctedReplyScoreFeature,
    Pred ctedRet etScoreFeature,
    Pred ctedReplyEngagedByAuthorScoreFeature,
    Pred ctedGoodCl ckConvoDescFavor edOrRepl edScoreFeature,
    Pred ctedGoodCl ckConvoDescUamGt2ScoreFeature,
    Pred ctedGoodProf leCl ckScoreFeature,
    Pred ctedV deoPlayback50ScoreFeature,
    Pred ctedT etDeta lD llScoreFeature,
    Pred ctedProf leD lledScoreFeature,
    Pred ctedBookmarkScoreFeature,
    Pred ctedShareScoreFeature,
    Pred ctedShare nuCl ckScoreFeature,
    // Negat ve Engage nts
    Pred ctedNegat veFeedbackV2ScoreFeature,
    Pred ctedReportedScoreFeature,
    Pred ctedStrongNegat veFeedbackScoreFeature,
    Pred cted akNegat veFeedbackScoreFeature,
  )
}
