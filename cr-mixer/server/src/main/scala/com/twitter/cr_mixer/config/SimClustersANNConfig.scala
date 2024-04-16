package com.tw ter.cr_m xer.conf g

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.cr_m xer.except on. nval dSANNConf gExcept on
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclustersann.thr ftscala.Scor ngAlgor hm
 mport com.tw ter.s mclustersann.thr ftscala.{S mClustersANNConf g => Thr ftS mClustersANNConf g}
 mport com.tw ter.ut l.Durat on

case class S mClustersANNConf g(
  maxNumResults:  nt,
  m nScore: Double,
  cand dateEmbedd ngType: Embedd ngType,
  maxTopT etsPerCluster:  nt,
  maxScanClusters:  nt,
  maxT etCand dateAge: Durat on,
  m nT etCand dateAge: Durat on,
  annAlgor hm: Scor ngAlgor hm) {
  val toSANNConf gThr ft: Thr ftS mClustersANNConf g = Thr ftS mClustersANNConf g(
    maxNumResults = maxNumResults,
    m nScore = m nScore,
    cand dateEmbedd ngType = cand dateEmbedd ngType,
    maxTopT etsPerCluster = maxTopT etsPerCluster,
    maxScanClusters = maxScanClusters,
    maxT etCand dateAgeH s = maxT etCand dateAge. nH s,
    m nT etCand dateAgeH s = m nT etCand dateAge. nH s,
    annAlgor hm = annAlgor hm,
  )
}

object S mClustersANNConf g {

  f nal val DefaultConf g = S mClustersANNConf g(
    maxNumResults = 200,
    m nScore = 0.0,
    cand dateEmbedd ngType = Embedd ngType.LogFavBasedT et,
    maxTopT etsPerCluster = 800,
    maxScanClusters = 50,
    maxT etCand dateAge = 24.h s,
    m nT etCand dateAge = 0.h s,
    annAlgor hm = Scor ngAlgor hm.Cos neS m lar y,
  )

  /*
  S mClustersANNConf g d: Str ng
  Format: Prod - “Embedd ngType_ModelVers on_Default”
  Format: Exper  nt - “Embedd ngType_ModelVers on_Date_Two-D g -Ser al-Number”. Date : YYYYMMDD
   */

  pr vate val FavBasedProducer_Model20m145k2020_Default = DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val FavBasedProducer_Model20m145k2020_20220617_06 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val FavBasedProducer_Model20m145k2020_20220801 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val FavBasedProducer_Model20m145k2020_20220810 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val FavBasedProducer_Model20m145k2020_20220818 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val FavBasedProducer_Model20m145k2020_20220819 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val FavBasedProducer_Model20m145k2020_20221221 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val FavBasedProducer_Model20m145k2020_20221220 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_Default = DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20220617_06 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20220801 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20220810 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20220818 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20220819 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20221221 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )
  // SANN-4 conf g
  pr vate val LogFavLongestL2Embedd ngT et_Model20m145k2020_20221220 =
    LogFavLongestL2Embedd ngT et_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  pr vate val Unf lteredUser nterested n_Model20m145k2020_Default = DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20220617_06 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20220801 =
    Unf lteredUser nterested n_Model20m145k2020_20220617_06.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20220810 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20220818 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20220819 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20221221 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val Unf lteredUser nterested n_Model20m145k2020_20221220 =
    Unf lteredUser nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default = DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220617_06 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220801 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220810 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220818 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220819 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221221 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221220 =
    LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default =
    DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220617_06 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220801 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220810 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220818 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220819 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221221 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221220 =
    LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  pr vate val UserNext nterested n_Model20m145k2020_Default = DefaultConf g.copy()

  // Chunnan's exp on maxT etCand dateAgeDays 2
  pr vate val UserNext nterested n_Model20m145k2020_20220617_06 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      maxT etCand dateAge = 48.h s,
    )

  // Exper  ntal SANN conf g
  pr vate val UserNext nterested n_Model20m145k2020_20220801 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val UserNext nterested n_Model20m145k2020_20220810 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val UserNext nterested n_Model20m145k2020_20220818 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val UserNext nterested n_Model20m145k2020_20220819 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val UserNext nterested n_Model20m145k2020_20221221 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val UserNext nterested n_Model20m145k2020_20221220 =
    UserNext nterested n_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  // V ncent's exper  nt on us ng FollowBasedProducer as query embedd ng type for UserFollow
  pr vate val FollowBasedProducer_Model20m145k2020_Default =
    FavBasedProducer_Model20m145k2020_Default.copy()

  // Exper  ntal SANN conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20220801 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.V deoPlayBack50LogFavBasedT et,
    )

  // SANN-1 conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20220810 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-2 conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20220818 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      cand dateEmbedd ngType = Embedd ngType.LogFavCl ckBasedAdsT et,
      maxT etCand dateAge = 175200.h s,
      maxTopT etsPerCluster = 1600
    )

  // SANN-3 conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20220819 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.PushOpenLogFavBasedT et,
    )

  // SANN-5 conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20221221 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedRealT  T et,
      maxT etCand dateAge = 1.h s
    )

  // SANN-4 conf g
  pr vate val FollowBasedProducer_Model20m145k2020_20221220 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      cand dateEmbedd ngType = Embedd ngType.LogFavBasedEvergreenT et,
      maxT etCand dateAge = 48.h s
    )
  val DefaultConf gMapp ngs: Map[Str ng, S mClustersANNConf g] = Map(
    "FavBasedProducer_Model20m145k2020_Default" -> FavBasedProducer_Model20m145k2020_Default,
    "FavBasedProducer_Model20m145k2020_20220617_06" -> FavBasedProducer_Model20m145k2020_20220617_06,
    "FavBasedProducer_Model20m145k2020_20220801" -> FavBasedProducer_Model20m145k2020_20220801,
    "FavBasedProducer_Model20m145k2020_20220810" -> FavBasedProducer_Model20m145k2020_20220810,
    "FavBasedProducer_Model20m145k2020_20220818" -> FavBasedProducer_Model20m145k2020_20220818,
    "FavBasedProducer_Model20m145k2020_20220819" -> FavBasedProducer_Model20m145k2020_20220819,
    "FavBasedProducer_Model20m145k2020_20221221" -> FavBasedProducer_Model20m145k2020_20221221,
    "FavBasedProducer_Model20m145k2020_20221220" -> FavBasedProducer_Model20m145k2020_20221220,
    "FollowBasedProducer_Model20m145k2020_Default" -> FollowBasedProducer_Model20m145k2020_Default,
    "FollowBasedProducer_Model20m145k2020_20220801" -> FollowBasedProducer_Model20m145k2020_20220801,
    "FollowBasedProducer_Model20m145k2020_20220810" -> FollowBasedProducer_Model20m145k2020_20220810,
    "FollowBasedProducer_Model20m145k2020_20220818" -> FollowBasedProducer_Model20m145k2020_20220818,
    "FollowBasedProducer_Model20m145k2020_20220819" -> FollowBasedProducer_Model20m145k2020_20220819,
    "FollowBasedProducer_Model20m145k2020_20221221" -> FollowBasedProducer_Model20m145k2020_20221221,
    "FollowBasedProducer_Model20m145k2020_20221220" -> FollowBasedProducer_Model20m145k2020_20221220,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_Default" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_Default,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20220617_06" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20220617_06,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20220801" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20220801,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20220810" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20220810,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20220818" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20220818,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20220819" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20220819,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20221221" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20221221,
    "LogFavLongestL2Embedd ngT et_Model20m145k2020_20221220" -> LogFavLongestL2Embedd ngT et_Model20m145k2020_20221220,
    "Unf lteredUser nterested n_Model20m145k2020_Default" -> Unf lteredUser nterested n_Model20m145k2020_Default,
    "Unf lteredUser nterested n_Model20m145k2020_20220617_06" -> Unf lteredUser nterested n_Model20m145k2020_20220617_06,
    "Unf lteredUser nterested n_Model20m145k2020_20220801" -> Unf lteredUser nterested n_Model20m145k2020_20220801,
    "Unf lteredUser nterested n_Model20m145k2020_20220810" -> Unf lteredUser nterested n_Model20m145k2020_20220810,
    "Unf lteredUser nterested n_Model20m145k2020_20220818" -> Unf lteredUser nterested n_Model20m145k2020_20220818,
    "Unf lteredUser nterested n_Model20m145k2020_20220819" -> Unf lteredUser nterested n_Model20m145k2020_20220819,
    "Unf lteredUser nterested n_Model20m145k2020_20221221" -> Unf lteredUser nterested n_Model20m145k2020_20221221,
    "Unf lteredUser nterested n_Model20m145k2020_20221220" -> Unf lteredUser nterested n_Model20m145k2020_20221220,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_Default,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220617_06" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220617_06,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220801" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220801,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220810" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220810,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220818" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220818,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220819" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20220819,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221221" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221221,
    "LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221220" -> LogFavBasedUser nterested nFromAPE_Model20m145k2020_20221220,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_Default,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220617_06" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220617_06,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220801" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220801,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220810" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220810,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220818" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220818,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220819" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20220819,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221221" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221221,
    "LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221220" -> LogFavBasedUser nterestedLouva nMaxpool ngAddressBookFrom  APE_Model20m145k2020_20221220,
    "UserNext nterested n_Model20m145k2020_Default" -> UserNext nterested n_Model20m145k2020_Default,
    "UserNext nterested n_Model20m145k2020_20220617_06" -> UserNext nterested n_Model20m145k2020_20220617_06,
    "UserNext nterested n_Model20m145k2020_20220801" -> UserNext nterested n_Model20m145k2020_20220801,
    "UserNext nterested n_Model20m145k2020_20220810" -> UserNext nterested n_Model20m145k2020_20220810,
    "UserNext nterested n_Model20m145k2020_20220818" -> UserNext nterested n_Model20m145k2020_20220818,
    "UserNext nterested n_Model20m145k2020_20220819" -> UserNext nterested n_Model20m145k2020_20220819,
    "UserNext nterested n_Model20m145k2020_20221221" -> UserNext nterested n_Model20m145k2020_20221221,
    "UserNext nterested n_Model20m145k2020_20221220" -> UserNext nterested n_Model20m145k2020_20221220,
  )

  def getConf g(
    embedd ngType: Str ng,
    modelVers on: Str ng,
     d: Str ng
  ): S mClustersANNConf g = {
    val conf gNa  = embedd ngType + "_" + modelVers on + "_" +  d
    DefaultConf gMapp ngs.get(conf gNa ) match {
      case So (conf g) => conf g
      case None =>
        throw  nval dSANNConf gExcept on(s" ncorrect conf g  d passed  n for SANN $conf gNa ")
    }
  }
}
