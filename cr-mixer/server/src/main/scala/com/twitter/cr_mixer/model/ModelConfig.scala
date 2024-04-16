package com.tw ter.cr_m xer.model

/**
 * A Conf gurat on class for all Model Based Cand date S ces.
 *
 * T  Model Na  Gu del ne. Please y  model d as "Algor hm_Product_Date"
 *  f y  model  s used for mult ple product surfaces, na    as all
 * Don't na  y  algor hm as MBCG. All t  algor hms  re are MBCG =.=
 *
 * Don't forgot to add y  new models  nto allHnswANNS m lar yEng neModel ds l st.
 */
object ModelConf g {
  // Offl ne S mClusters CG Exper  nt related Model  ds
  val Offl ne nterested nFromKnownFor2020: Str ng = "Offl ne  KF_ALL_20220414"
  val Offl ne nterested nFromKnownFor2020Hl0El15: Str ng = "Offl ne  KF_ALL_20220414_Hl0_El15"
  val Offl ne nterested nFromKnownFor2020Hl2El15: Str ng = "Offl ne  KF_ALL_20220414_Hl2_El15"
  val Offl ne nterested nFromKnownFor2020Hl2El50: Str ng = "Offl ne  KF_ALL_20220414_Hl2_El50"
  val Offl ne nterested nFromKnownFor2020Hl8El50: Str ng = "Offl ne  KF_ALL_20220414_Hl8_El50"
  val Offl neMTSConsu rEmbedd ngsFav90P20M: Str ng =
    "Offl neMTSConsu rEmbedd ngsFav90P20M_ALL_20220414"

  // Twh n Model  ds
  val Consu rBasedTwH NRegularUpdateAll20221024: Str ng =
    "Consu rBasedTwH NRegularUpdate_All_20221024"

  // Averaged Twh n Model  ds
  val T etBasedTwH NRegularUpdateAll20221024: Str ng =
    "T etBasedTwH NRegularUpdate_All_20221024"

  // Collaborat ve F lter ng Twh n Model  ds
  val Twh nCollabF lterForFollow: Str ng =
    "Twh nCollabF lterForFollow"
  val Twh nCollabF lterForEngage nt: Str ng =
    "Twh nCollabF lterForEngage nt"
  val Twh nMult ClusterForFollow: Str ng =
    "Twh nMult ClusterForFollow"
  val Twh nMult ClusterForEngage nt: Str ng =
    "Twh nMult ClusterForEngage nt"

  // Two To r model  ds
  val TwoTo rFavALL20220808: Str ng =
    "TwoTo rFav_ALL_20220808"

  // Debugger Demo-Only Model  ds
  val DebuggerDemo: Str ng = "DebuggerDemo"

  // ColdStartLookal ke - t   s not really a model na ,    s as a placeholder to
  //  nd cate ColdStartLookal ke cand date s ce, wh ch  s currently be ng pluged  nto
  // Custom zedRetr evalCand dateGenerat on temporar ly.
  val ColdStartLookal keModelNa : Str ng = "Consu rsBasedUtgColdStartLookal ke20220707"

  // consu rsBasedUTG-RealGraphOon Model  d
  val Consu rsBasedUtgRealGraphOon20220705: Str ng = "Consu rsBasedUtgRealGraphOon_All_20220705"
  // consu rsBasedUAG-RealGraphOon Model  d
  val Consu rsBasedUagRealGraphOon20221205: Str ng = "Consu rsBasedUagRealGraphOon_All_20221205"

  // FTR
  val Offl neFavDecayedSum: Str ng = "Offl neFavDecayedSum"
  val Offl neFtrAt5Pop1000RnkDcy11: Str ng = "Offl neFtrAt5Pop1000RnkDcy11"
  val Offl neFtrAt5Pop10000RnkDcy11: Str ng = "Offl neFtrAt5Pop10000RnkDcy11"

  // All Model  ds of HnswANNS m lar yEng nes
  val allHnswANNS m lar yEng neModel ds = Seq(
    Consu rBasedTwH NRegularUpdateAll20221024,
    TwoTo rFavALL20220808,
    DebuggerDemo
  )

  val Consu rLogFavBased nterested nEmbedd ng: Str ng =
    "Consu rLogFavBased nterested n_ALL_20221228"
  val Consu rFollowBased nterested nEmbedd ng: Str ng =
    "Consu rFollowBased nterested n_ALL_20221228"

  val Ret etBasedD ffus on: Str ng =
    "Ret etBasedD ffus on"

}
