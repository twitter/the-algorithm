package com.tw ter.ho _m xer.product.scored_t ets.param

 mport com.tw ter.ho _m xer.param.dec der.Dec derKey
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam._
 mport com.tw ter.product_m xer.core.product.ProductParamConf g
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScoredT etsParamConf g @ nject() () extends ProductParamConf g {
  overr de val enabledDec derKey: Dec derKeyNa  = Dec derKey.EnableScoredT etsProduct
  overr de val supportedCl entFSNa : Str ng = SupportedCl entFSNa 

  overr de val booleanDec derOverr des = Seq(
    Cand dateP pel ne.EnableBackf llParam,
    Cand dateP pel ne.EnableT etM xerParam,
    Cand dateP pel ne.EnableFrsParam,
    Cand dateP pel ne.Enable nNetworkParam,
    Cand dateP pel ne.EnableL stsParam,
    Cand dateP pel ne.EnablePopularV deosParam,
    Cand dateP pel ne.EnableUtegParam,
    ScoredT etsParam.EnableS mClustersS m lar yFeatureHydrat onDec derParam
  )

  overr de val booleanFSOverr des = Seq(
    EnableBackf llCand dateP pel neParam,
    EnableScr beScoredCand datesParam
  )

  overr de val bounded ntFSOverr des = Seq(
    Cac dScoredT ets.M nCac dT etsParam,
    Max nNetworkResultsParam,
    MaxOutOfNetworkResultsParam,
    Qual yFactor.Backf llMaxT etsToScoreParam,
    Qual yFactor.T etM xerMaxT etsToScoreParam,
    Qual yFactor.FrsMaxT etsToScoreParam,
    Qual yFactor. nNetworkMaxT etsToScoreParam,
    Qual yFactor.L stsMaxT etsToScoreParam,
    Qual yFactor.PopularV deosMaxT etsToScoreParam,
    Qual yFactor.UtegMaxT etsToScoreParam,
    ServerMaxResultsParam
  )

  overr de val boundedDurat onFSOverr des = Seq(
    Cac dScoredT ets.TTLParam
  )

  overr de val str ngFSOverr des = Seq(
    Scor ng.Ho ModelParam,
    Earlyb rdTensorflowModel. nNetworkParam,
    Earlyb rdTensorflowModel.FrsParam,
    Earlyb rdTensorflowModel.UtegParam
  )

  overr de val boundedDoubleFSOverr des = Seq(
    BlueVer f edAuthor nNetworkMult pl erParam,
    BlueVer f edAuthorOutOfNetworkMult pl erParam,
    Creator nNetworkMult pl erParam,
    CreatorOutOfNetworkMult pl erParam,
    OutOfNetworkScaleFactorParam,
    // Model   ghts
    Scor ng.Model  ghts.FavParam,
    Scor ng.Model  ghts.ReplyParam,
    Scor ng.Model  ghts.Ret etParam,
    Scor ng.Model  ghts.GoodCl ckParam,
    Scor ng.Model  ghts.GoodCl ckV2Param,
    Scor ng.Model  ghts.GoodProf leCl ckParam,
    Scor ng.Model  ghts.ReplyEngagedByAuthorParam,
    Scor ng.Model  ghts.V deoPlayback50Param,
    Scor ng.Model  ghts.ReportParam,
    Scor ng.Model  ghts.Negat veFeedbackV2Param,
    Scor ng.Model  ghts.T etDeta lD llParam,
    Scor ng.Model  ghts.Prof leD lledParam,
    Scor ng.Model  ghts.BookmarkParam,
    Scor ng.Model  ghts.ShareParam,
    Scor ng.Model  ghts.Share nuCl ckParam,
    Scor ng.Model  ghts.StrongNegat veFeedbackParam,
    Scor ng.Model  ghts. akNegat veFeedbackParam
  )

  overr de val longSetFSOverr des = Seq(
    Compet orSetParam
  )

  overr de val str ngSeqFSOverr des = Seq(
    Compet orURLSeqParam
  )
}
