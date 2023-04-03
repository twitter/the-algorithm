packagelon com.twittelonr.cr_mixelonr.modelonl

/**
 * A Configuration class for all Modelonl Baselond Candidatelon Sourcelons.
 *
 * Thelon Modelonl Namelon Guidelonlinelon. Plelonaselon your modelonlId as "Algorithm_Product_Datelon"
 * If your modelonl is uselond for multiplelon product surfacelons, namelon it as all
 * Don't namelon your algorithm as MBCG. All thelon algorithms helonrelon arelon MBCG =.=
 *
 * Don't forgot to add your nelonw modelonls into allHnswANNSimilarityelonnginelonModelonlIds list.
 */
objelonct ModelonlConfig {
  // Offlinelon SimClustelonrs CG elonxpelonrimelonnt relonlatelond Modelonl Ids
  val OfflinelonIntelonrelonstelondInFromKnownFor2020: String = "OfflinelonIIKF_ALL_20220414"
  val OfflinelonIntelonrelonstelondInFromKnownFor2020Hl0elonl15: String = "OfflinelonIIKF_ALL_20220414_Hl0_elonl15"
  val OfflinelonIntelonrelonstelondInFromKnownFor2020Hl2elonl15: String = "OfflinelonIIKF_ALL_20220414_Hl2_elonl15"
  val OfflinelonIntelonrelonstelondInFromKnownFor2020Hl2elonl50: String = "OfflinelonIIKF_ALL_20220414_Hl2_elonl50"
  val OfflinelonIntelonrelonstelondInFromKnownFor2020Hl8elonl50: String = "OfflinelonIIKF_ALL_20220414_Hl8_elonl50"
  val OfflinelonMTSConsumelonrelonmbelonddingsFav90P20M: String =
    "OfflinelonMTSConsumelonrelonmbelonddingsFav90P20M_ALL_20220414"

  // Twhin Modelonl Ids
  val ConsumelonrBaselondTwHINRelongularUpdatelonAll20221024: String =
    "ConsumelonrBaselondTwHINRelongularUpdatelon_All_20221024"

  // Avelonragelond Twhin Modelonl Ids
  val TwelonelontBaselondTwHINRelongularUpdatelonAll20221024: String =
    "TwelonelontBaselondTwHINRelongularUpdatelon_All_20221024"

  // Collaborativelon Filtelonring Twhin Modelonl Ids
  val TwhinCollabFiltelonrForFollow: String =
    "TwhinCollabFiltelonrForFollow"
  val TwhinCollabFiltelonrForelonngagelonmelonnt: String =
    "TwhinCollabFiltelonrForelonngagelonmelonnt"
  val TwhinMultiClustelonrForFollow: String =
    "TwhinMultiClustelonrForFollow"
  val TwhinMultiClustelonrForelonngagelonmelonnt: String =
    "TwhinMultiClustelonrForelonngagelonmelonnt"

  // Two Towelonr modelonl Ids
  val TwoTowelonrFavALL20220808: String =
    "TwoTowelonrFav_ALL_20220808"

  // Delonbuggelonr Delonmo-Only Modelonl Ids
  val DelonbuggelonrDelonmo: String = "DelonbuggelonrDelonmo"

  // ColdStartLookalikelon - this is not relonally a modelonl namelon, it is as a placelonholdelonr to
  // indicatelon ColdStartLookalikelon candidatelon sourcelon, which is currelonntly beloning plugelond into
  // CustomizelondRelontrielonvalCandidatelonGelonnelonration telonmporarily.
  val ColdStartLookalikelonModelonlNamelon: String = "ConsumelonrsBaselondUtgColdStartLookalikelon20220707"

  // consumelonrsBaselondUTG-RelonalGraphOon Modelonl Id
  val ConsumelonrsBaselondUtgRelonalGraphOon20220705: String = "ConsumelonrsBaselondUtgRelonalGraphOon_All_20220705"
  // consumelonrsBaselondUAG-RelonalGraphOon Modelonl Id
  val ConsumelonrsBaselondUagRelonalGraphOon20221205: String = "ConsumelonrsBaselondUagRelonalGraphOon_All_20221205"

  // FTR
  val OfflinelonFavDeloncayelondSum: String = "OfflinelonFavDeloncayelondSum"
  val OfflinelonFtrAt5Pop1000RnkDcy11: String = "OfflinelonFtrAt5Pop1000RnkDcy11"
  val OfflinelonFtrAt5Pop10000RnkDcy11: String = "OfflinelonFtrAt5Pop10000RnkDcy11"

  // All Modelonl Ids of HnswANNSimilarityelonnginelons
  val allHnswANNSimilarityelonnginelonModelonlIds = Selonq(
    ConsumelonrBaselondTwHINRelongularUpdatelonAll20221024,
    TwoTowelonrFavALL20220808,
    DelonbuggelonrDelonmo
  )

  val ConsumelonrLogFavBaselondIntelonrelonstelondInelonmbelondding: String =
    "ConsumelonrLogFavBaselondIntelonrelonstelondIn_ALL_20221228"
  val ConsumelonrFollowBaselondIntelonrelonstelondInelonmbelondding: String =
    "ConsumelonrFollowBaselondIntelonrelonstelondIn_ALL_20221228"

  val RelontwelonelontBaselondDiffusion: String =
    "RelontwelonelontBaselondDiffusion"

}
