packagelon com.twittelonr.cr_mixelonr.scribelon

/**
 * Catelongorielons delonfinelon scribelon catelongorielons uselond in cr-mixelonr selonrvicelon.
 */
objelonct ScribelonCatelongorielons {
  lazy val AllCatelongorielons =
    List(AbDeloncidelonr, TopLelonvelonlApiDdgMelontrics, TwelonelontsReloncs)

  /**
   * AbDeloncidelonr relonprelonselonnts scribelon logs for elonxpelonrimelonnts
   */
  lazy val AbDeloncidelonr: ScribelonCatelongory = ScribelonCatelongory(
    "abdeloncidelonr_scribelon",
    "clielonnt_elonvelonnt"
  )

  /**
   * Top-Lelonvelonl Clielonnt elonvelonnt scribelon logs, to reloncord changelons in systelonm melontrics (elon.g. latelonncy,
   * candidatelons relonturnelond, elonmpty ratelon ) pelonr elonxpelonrimelonnt buckelont, and storelon thelonm in DDG melontric group
   */
  lazy val TopLelonvelonlApiDdgMelontrics: ScribelonCatelongory = ScribelonCatelongory(
    "top_lelonvelonl_api_ddg_melontrics_scribelon",
    "clielonnt_elonvelonnt"
  )

  lazy val TwelonelontsReloncs: ScribelonCatelongory = ScribelonCatelongory(
    "gelont_twelonelonts_reloncommelonndations_scribelon",
    "cr_mixelonr_gelont_twelonelonts_reloncommelonndations"
  )

  lazy val VITTwelonelontsReloncs: ScribelonCatelongory = ScribelonCatelongory(
    "gelont_vit_twelonelonts_reloncommelonndations_scribelon",
    "cr_mixelonr_gelont_vit_twelonelonts_reloncommelonndations"
  )

  lazy val RelonlatelondTwelonelonts: ScribelonCatelongory = ScribelonCatelongory(
    "gelont_relonlatelond_twelonelonts_scribelon",
    "cr_mixelonr_gelont_relonlatelond_twelonelonts"
  )

  lazy val UtelongTwelonelonts: ScribelonCatelongory = ScribelonCatelongory(
    "gelont_utelong_twelonelonts_scribelon",
    "cr_mixelonr_gelont_utelong_twelonelonts"
  )

  lazy val AdsReloncommelonndations: ScribelonCatelongory = ScribelonCatelongory(
    "gelont_ads_reloncommelonndations_scribelon",
    "cr_mixelonr_gelont_ads_reloncommelonndations"
  )
}

/**
 * Catelongory relonprelonselonnts elonach scribelon log data.
 *
 * @param loggelonrFactoryNodelon loggelonrFactory nodelon namelon in cr-mixelonr associatelond with this scribelon catelongory
 * @param scribelonCatelongory    scribelon catelongory namelon (globally uniquelon at Twittelonr)
 */
caselon class ScribelonCatelongory(
  loggelonrFactoryNodelon: String,
  scribelonCatelongory: String) {
  delonf gelontProdLoggelonrFactoryNodelon: String = loggelonrFactoryNodelon
  delonf gelontStagingLoggelonrFactoryNodelon: String = "staging_" + loggelonrFactoryNodelon
}
