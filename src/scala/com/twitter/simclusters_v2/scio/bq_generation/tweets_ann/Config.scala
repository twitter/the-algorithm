packagelon com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.twelonelonts_ann

objelonct Config {
  /*
   * Common root path
   */
  val RootMHPath: String = "manhattan_selonquelonncelon_filelons/offlinelon_sann/"
  val RootThriftPath: String = "procelonsselond/offlinelon_sann/"
  val AdhocRootPath = "adhoc/offlinelon_sann/"

  /*
   * Variablelons for MH output path
   */
  val IIKFANNOutputPath: String = "twelonelonts_ann/iikf"
  val IIKFHL0elonL15ANNOutputPath: String = "twelonelonts_ann/iikf_hl_0_elonl_15"
  val IIKFHL2elonL15ANNOutputPath: String = "twelonelonts_ann/iikf_hl_2_elonl_15"
  val IIKFHL2elonL50ANNOutputPath: String = "twelonelonts_ann/iikf_hl_2_elonl_50"
  val IIKFHL8elonL50ANNOutputPath: String = "twelonelonts_ann/iikf_hl_8_elonl_50"
  val MTSConsumelonrelonmbelonddingsANNOutputPath: String = "twelonelonts_ann/mts_consumelonr_elonmbelonddings"

  /*
   * Variablelons for twelonelont elonmbelonddings gelonnelonration
   */
  val SimClustelonrsTwelonelontelonmbelonddingsGelonnelonrationHalfLifelon: Int = 28800000 // 8hrs in ms
  val SimClustelonrsTwelonelontelonmbelonddingsGelonnelonrationelonmbelonddingLelonngth: Int = 15

  /*
   * Variablelons for ANN
   */
  val SimClustelonrsANNTopNClustelonrsPelonrSourcelonelonmbelondding: Int = 20
  val SimClustelonrsANNTopMTwelonelontsPelonrClustelonr: Int = 50
  val SimClustelonrsANNTopKTwelonelontsPelonrUselonrRelonquelonst: Int = 200
}
