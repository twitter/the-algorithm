packagelon com.twittelonr.simclustelonrs_v2.scio.bq_gelonnelonration.simclustelonrs_indelonx_gelonnelonration

objelonct Config {
  // Common Root Path
  val RootMHPath: String = "manhattan_selonquelonncelon_filelons/simclustelonrs_to_twelonelont_indelonx/"
  val RootThriftPath: String = "procelonsselond/simclustelonrs_to_twelonelont_indelonx/"
  val AdhocRootPath = "adhoc/simclustelonrs_to_twelonelont_indelonx/"
  // clustelonr-to-twelonelont KelonyVal Dataselont Output Path
  val FavBaselondClustelonrToTwelonelontIndelonxOutputPath = "fav_baselond_indelonx"
  val FavBaselondelonvelonrgrelonelonnContelonntClustelonrToTwelonelontIndelonxOutputPath = "fav_baselond_elonvelonrgrelonelonn_indelonx"
  val FavBaselondVidelonoClustelonrToTwelonelontIndelonxOutputPath = "fav_baselond_videlono_indelonx"
  val VidelonoVielonwBaselondClustelonrToTwelonelontIndelonxOutputPath = "videlono_vielonw_baselond_indelonx"
  val RelontwelonelontBaselondClustelonrToTwelonelontIndelonxOutputPath = "relontwelonelont_baselond_indelonx"
  val RelonplyBaselondClustelonrToTwelonelontIndelonxOutputPath = "relonply_baselond_indelonx"
  val PushOpelonnBaselondClustelonrToTwelonelontIndelonxOutputPath = "push_opelonn_baselond_indelonx"
  val AdsFavBaselondClustelonrToTwelonelontIndelonxOutputPath = "ads_fav_baselond_indelonx"
  val AdsFavClickBaselondClustelonrToTwelonelontIndelonxOutputPath = "ads_fav_click_baselond_indelonx"

  // SQL filelon path
  val simclustelonrselonngagelonmelonntBaselondIndelonxGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/elonngagelonmelonnt_baselond_indelonx_gelonnelonration.sql"
  val unifielondUselonrTwelonelontActionPairGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/unifielond_uselonr_twelonelont_action_pair_gelonnelonration.sql"
  val combinelondUselonrTwelonelontActionPairGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/combinelond_uselonr_twelonelont_action_pair_gelonnelonration.sql"
  val adsUselonrTwelonelontActionPairGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/ads_uselonr_twelonelont_action_pair_gelonnelonration.sql"
  val elonvelonrgrelonelonnContelonntUselonrTwelonelontActionPairGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/elonvelonrgrelonelonn_contelonnt_uselonr_twelonelont_action_pair_gelonnelonration.sql"
  val favBaselondVidelonoTwelonelontActionPairGelonnelonrationSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/uselonr_videlono_twelonelont_fav_elonngagelonmelonnt_gelonnelonration.sql"

  // Tablelon namelon for selonrvelonr/clielonnt elonngagelonmelonnts
  val clielonntelonngagelonmelonntTablelonNamelon: String = "twttr-bq-ielonsourcelon-prod.uselonr.clielonnt_elonngagelonmelonnts"
  val selonrvelonrelonngagelonmelonntTablelonNamelon: String = "twttr-bq-ielonsourcelon-prod.uselonr.selonrvelonr_elonngagelonmelonnts"

  // Twelonelont id column namelons from UUA
  val actionTwelonelontIdColumn: String = "itelonm.twelonelontInfo.actionTwelonelontId"
  val relontwelonelontTwelonelontIdColumn: String = "itelonm.twelonelontInfo.relontwelonelontelondTwelonelontId"
  val relonplyTwelonelontIdColumn: String = "itelonm.twelonelontInfo.inRelonplyToTwelonelontId"
  val pushTwelonelontIdColumn: String = "itelonm.notificationInfo.contelonnt.twelonelontNotification.twelonelontId"

  // Do not elonnablelon helonalth or videlono filtelonrs by delonfault
  val elonnablelonHelonalthAndVidelonoFiltelonrs: Boolelonan = falselon

  // Do not elonnablelon top k twelonelonts pelonr clustelonr intelonrselonction with fav-baselond clustelonrs
  val elonnablelonIntelonrselonctionWithFavBaselondClustelonrTopKTwelonelontsIndelonx: Boolelonan = falselon

  // Min fav/intelonraction threlonshold
  val minIntelonractionCount: Int = 50
  val minFavCount: Int = 50

  // Twelonelont elonmbelonddings configs
  val twelonelontelonmbelonddingsLelonngth: Int = 50
  val twelonelontelonmbelonddingsHalfLifelon: Int = 28800000

  // Clustelonr-to-twelonelont indelonx configs
  val clustelonrTopKTwelonelonts: Int = 2000
  val maxTwelonelontAgelonHours: Int = 24
  val minelonngagelonmelonntPelonrClustelonr: Int = 0

  // Placelonholdelonr action typelon for intelonractions that don't havelon undo elonvelonnts (elon.g. videlono vielonws)
  val PlacelonholdelonrActionTypelon: String = "PLACelonHOLDelonR_ACTION_TYPelon"

  // Ads elonvelonnt elonngagelonmelonnt typelon ids
  val AdsFavelonngagelonmelonntTypelonIds = Selonq(8) // Fav promotelond twelonelont
  val AdsClickelonngagelonmelonntTypelonIds = Selonq(
    1, //URL
    42, // CARD_URL_CLICK
    53, // WelonBSITelon_CARD_CONTAINelonR_CLICK
    54, // WelonBSITelon_CARD_BUTTON_CLICK
    55, // WelonBSITelon_CARD_IMAGelon_CLICK
    56, // WelonBSITelon_CARD_TITLelon_CLICK
    69, // BUYNOW_CARD_CLICK
    70, // BUYNOW_PURCHASelon_SUCCelonSS
    72, // VIDelonO_CTA_URL_CLICK
    76, // VIDelonO_AD_CTA_URL_CLICK
    80, // VIDelonO_CONTelonNT_CTA_URL_CLICK
    84, // CL_OFFelonR_CARD_CLICK
  )

}
