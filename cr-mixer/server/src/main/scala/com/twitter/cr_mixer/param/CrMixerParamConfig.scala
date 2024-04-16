package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .Compos eConf g
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .Param

object CrM xerParamConf g {

  lazy val conf g: Compos eConf g = new Compos eConf g(
    conf gs = Seq(
      AdsParams.conf g,
      BlenderParams.conf g,
      Bypass nterleaveAndRankParams.conf g,
      RankerParams.conf g,
      Consu rBasedWalsParams.conf g,
      Consu rEmbedd ngBasedCand dateGenerat onParams.conf g,
      Consu rEmbedd ngBasedTr pParams.conf g,
      Consu rEmbedd ngBasedTwH NParams.conf g,
      Consu rEmbedd ngBasedTwoTo rParams.conf g,
      Consu rsBasedUserAdGraphParams.conf g,
      Consu rsBasedUserT etGraphParams.conf g,
      Consu rsBasedUserV deoGraphParams.conf g,
      Custom zedRetr evalBasedCand dateGenerat onParams.conf g,
      Custom zedRetr evalBasedOffl ne nterested nParams.conf g,
      Custom zedRetr evalBasedFTROffl ne nterested nParams.conf g,
      Custom zedRetr evalBasedTwh nParams.conf g,
      Earlyb rdFrsBasedCand dateGenerat onParams.conf g,
      FrsParams.conf g,
      GlobalParams.conf g,
       nterested nParams.conf g,
      ProducerBasedCand dateGenerat onParams.conf g,
      ProducerBasedUserAdGraphParams.conf g,
      ProducerBasedUserT etGraphParams.conf g,
      RecentFollowsParams.conf g,
      RecentNegat veS gnalParams.conf g,
      RecentNot f cat onsParams.conf g,
      RecentOr g nalT etsParams.conf g,
      RecentReplyT etsParams.conf g,
      RecentRet etsParams.conf g,
      RecentT etFavor esParams.conf g,
      RelatedT etGlobalParams.conf g,
      RelatedV deoT etGlobalParams.conf g,
      RelatedT etProducerBasedParams.conf g,
      RelatedT etT etBasedParams.conf g,
      RelatedV deoT etT etBasedParams.conf g,
      RealGraph nParams.conf g,
      RealGraphOonParams.conf g,
      RepeatedProf leV s sParams.conf g,
      S mClustersANNParams.conf g,
      Top cT etParams.conf g,
      T etBasedCand dateGenerat onParams.conf g,
      T etBasedUserAdGraphParams.conf g,
      T etBasedUserT etGraphParams.conf g,
      T etBasedUserV deoGraphParams.conf g,
      T etSharesParams.conf g,
      T etBasedTwH NParams.conf g,
      RealGraphOonParams.conf g,
      GoodT etCl ckParams.conf g,
      GoodProf leCl ckParams.conf g,
      UtegT etGlobalParams.conf g,
      V deoT etF lterParams.conf g,
      V deoV ewT etsParams.conf g,
      Un f edUSSS gnalParams.conf g,
    ),
    s mpleNa  = "CrM xerConf g"
  )

  val allParams: Seq[Param[_] w h FSNa ] = {
    AdsParams.AllParams ++
      BlenderParams.AllParams ++
      Bypass nterleaveAndRankParams.AllParams ++
      RankerParams.AllParams ++
      Consu rBasedWalsParams.AllParams ++
      Consu rEmbedd ngBasedCand dateGenerat onParams.AllParams ++
      Consu rEmbedd ngBasedTr pParams.AllParams ++
      Consu rEmbedd ngBasedTwH NParams.AllParams ++
      Consu rEmbedd ngBasedTwoTo rParams.AllParams ++
      Consu rsBasedUserAdGraphParams.AllParams ++
      Consu rsBasedUserT etGraphParams.AllParams ++
      Consu rsBasedUserV deoGraphParams.AllParams ++
      Custom zedRetr evalBasedCand dateGenerat onParams.AllParams ++
      Custom zedRetr evalBasedOffl ne nterested nParams.AllParams ++
      Custom zedRetr evalBasedFTROffl ne nterested nParams.AllParams ++
      Custom zedRetr evalBasedTwh nParams.AllParams ++
      Earlyb rdFrsBasedCand dateGenerat onParams.AllParams ++
      FrsParams.AllParams ++
      GlobalParams.AllParams ++
       nterested nParams.AllParams ++
      ProducerBasedCand dateGenerat onParams.AllParams ++
      ProducerBasedUserAdGraphParams.AllParams ++
      ProducerBasedUserT etGraphParams.AllParams ++
      RecentFollowsParams.AllParams ++
      RecentNegat veS gnalParams.AllParams ++
      RecentNot f cat onsParams.AllParams ++
      RecentOr g nalT etsParams.AllParams ++
      RecentReplyT etsParams.AllParams ++
      RecentRet etsParams.AllParams ++
      RecentT etFavor esParams.AllParams ++
      RelatedT etGlobalParams.AllParams ++
      RelatedV deoT etGlobalParams.AllParams ++
      RelatedT etProducerBasedParams.AllParams ++
      RelatedT etT etBasedParams.AllParams ++
      RelatedV deoT etT etBasedParams.AllParams ++
      RepeatedProf leV s sParams.AllParams ++
      S mClustersANNParams.AllParams ++
      Top cT etParams.AllParams ++
      T etBasedCand dateGenerat onParams.AllParams ++
      T etBasedUserAdGraphParams.AllParams ++
      T etBasedUserT etGraphParams.AllParams ++
      T etBasedUserV deoGraphParams.AllParams ++
      T etSharesParams.AllParams ++
      T etBasedTwH NParams.AllParams ++
      RealGraphOonParams.AllParams ++
      RealGraph nParams.AllParams ++
      GoodT etCl ckParams.AllParams ++
      GoodProf leCl ckParams.AllParams ++
      UtegT etGlobalParams.AllParams ++
      V deoT etF lterParams.AllParams ++
      V deoV ewT etsParams.AllParams ++
      Un f edUSSS gnalParams.AllParams
  }
}
