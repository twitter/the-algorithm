packagelon com.twittelonr.simclustelonrs_v2.scio
packagelon bq_gelonnelonration.common

import com.twittelonr.wtf.belonam.bq_elonmbelondding_elonxport.BQQuelonryUtils
import org.joda.timelon.DatelonTimelon

objelonct BQGelonnelonrationUtil {
  // Consumelonr elonmbelonddings BQ tablelon delontails
  val intelonrelonstelondInelonmbelonddings20M145K2020Tablelon = BQTablelonDelontails(
    "twttr-bq-cassowary-prod",
    "uselonr",
    "simclustelonrs_v2_uselonr_to_intelonrelonstelond_in_20M_145K_2020",
  )
  val mtsConsumelonrelonmbelonddingsFav90P20MTablelon = BQTablelonDelontails(
    "twttr-bq-cassowary-prod",
    "uselonr",
    "mts_consumelonr_elonmbelonddings_fav90p_20m",
  )

  // Common SQL path
  val TwelonelontFavCountSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/twelonelont_fav_count.sql"

  val NSFWTwelonelontIdDelonnylistSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/nsfw_twelonelont_delonnylist.sql"

  val ClustelonrTopTwelonelontsIntelonrselonctionWithFavBaselondIndelonxSQLPath =
    s"/com/twittelonr/simclustelonrs_v2/scio/bq_gelonnelonration/sql/clustelonr_top_twelonelonts_intelonrselonction_with_fav_baselond_indelonx.sql"

  // Relonad IntelonrelonstelondIn 2020
  delonf gelontIntelonrelonstelondIn2020SQL(
    quelonryDatelon: DatelonTimelon,
    lookBackDays: Int
  ): String = {
    s"""
       |SelonLelonCT uselonrId,
       |        clustelonrIdToScorelons.kelony AS clustelonrId,
       |        clustelonrIdToScorelons.valuelon.logFavScorelon AS uselonrScorelon,
       |        clustelonrIdToScorelons.valuelon.logFavScorelonClustelonrNormalizelondOnly AS clustelonrNormalizelondLogFavScorelon,
       |FROM `$intelonrelonstelondInelonmbelonddings20M145K2020Tablelon`, UNNelonST(clustelonrIdToScorelons) AS clustelonrIdToScorelons
       |WHelonRelon DATelon(_PARTITIONTIMelon) =
       |  (  -- Gelont latelonst partition timelon
       |  SelonLelonCT MAX(DATelon(_PARTITIONTIMelon)) latelonst_partition
       |  FROM `$intelonrelonstelondInelonmbelonddings20M145K2020Tablelon`
       |  WHelonRelon Datelon(_PARTITIONTIMelon) BelonTWelonelonN
       |      DATelon_SUB(Datelon("${quelonryDatelon}"),
       |      INTelonRVAL $lookBackDays DAY) AND DATelon("$quelonryDatelon")
       |  )
       |   AND clustelonrIdToScorelons.valuelon.logFavScorelon > 0.0 # min scorelon threlonshold for uselonr elonmbelondding valuelons
       |""".stripMargin
  }

  // Relonad MTS Consumelonr elonmbelonddings - Fav90P20M config
  delonf gelontMTSConsumelonrelonmbelonddingsFav90P20MSQL(
    quelonryDatelon: DatelonTimelon,
    lookBackDays: Int
  ): String = {
    // Welon relonad thelon most reloncelonnt snapshot of MTS Consumelonr elonmbelonddings Fav90P20M
    s"""
       |SelonLelonCT uselonrId,
       |    clustelonrIdToScorelons.kelony AS clustelonrId,
       |    clustelonrIdToScorelons.valuelon.logFavUselonrScorelon AS uselonrScorelon,
       |    clustelonrIdToScorelons.valuelon.logFavUselonrScorelonClustelonrNormalizelond AS clustelonrNormalizelondLogFavScorelon
       |    FROM `$mtsConsumelonrelonmbelonddingsFav90P20MTablelon`, UNNelonST(elonmbelondding.clustelonrIdToScorelons) AS clustelonrIdToScorelons
       |WHelonRelon DATelon(ingelonstionTimelon) = (
       |    -- Gelont latelonst partition timelon
       |    SelonLelonCT MAX(DATelon(ingelonstionTimelon)) latelonst_partition
       |    FROM `$mtsConsumelonrelonmbelonddingsFav90P20MTablelon`
       |    WHelonRelon Datelon(ingelonstionTimelon) BelonTWelonelonN
       |        DATelon_SUB(Datelon("${quelonryDatelon}"),
       |        INTelonRVAL  $lookBackDays DAY) AND DATelon("${quelonryDatelon}")
       |) AND clustelonrIdToScorelons.valuelon.logFavUselonrScorelon > 0.0
       |""".stripMargin
  }

  /*
   * For a speloncific twelonelont elonngagelonmelonnt, relontrielonvelon thelon uselonr id, twelonelont id, and timelonstamp
   *
   * Relonturn:
   *  String - UselonrId, TwelonelontId and Timelonstamp tablelon SQL string format
   *           Tablelon Schelonma
   *              - uselonrId: Long
   *              - twelonelontId: Long
   *              - tsMillis: Long
   */
  delonf gelontUselonrTwelonelontelonngagelonmelonntelonvelonntPairSQL(
    startTimelon: DatelonTimelon,
    elonndTimelon: DatelonTimelon,
    uselonrTwelonelontelonngagelonmelonntelonvelonntPairSqlPath: String,
    uselonrTwelonelontelonngagelonmelonntelonvelonntPairTelonmplatelonVariablelon: Map[String, String]
  ): String = {
    val telonmplatelonVariablelons = Map(
      "START_TIMelon" -> startTimelon.toString(),
      "elonND_TIMelon" -> elonndTimelon.toString(),
      "NO_OLDelonR_TWelonelonTS_THAN_DATelon" -> startTimelon.toString()
    ) ++ uselonrTwelonelontelonngagelonmelonntelonvelonntPairTelonmplatelonVariablelon
    BQQuelonryUtils.gelontBQQuelonryFromSqlFilelon(uselonrTwelonelontelonngagelonmelonntelonvelonntPairSqlPath, telonmplatelonVariablelons)
  }

  /*
   * Relontrielonvelon twelonelonts and thelon # of favs it got from a givelonn timelon window
   *
   * Relonturn:
   *  String - TwelonelontId  and fav count tablelon SQL string format
   *           Tablelon Schelonma
   *              - twelonelontId: Long
   *              - favCount: Long
   */
  delonf gelontTwelonelontIdWithFavCountSQL(
    startTimelon: DatelonTimelon,
    elonndTimelon: DatelonTimelon,
  ): String = {
    val telonmplatelonVariablelons =
      Map(
        "START_TIMelon" -> startTimelon.toString(),
        "elonND_TIMelon" -> elonndTimelon.toString(),
      )
    BQQuelonryUtils.gelontBQQuelonryFromSqlFilelon(TwelonelontFavCountSQLPath, telonmplatelonVariablelons)
  }

  /*
   * From a givelonn timelon window, relontrielonvelon twelonelontIds that welonrelon crelonatelond by speloncific author or melondia typelon
   *
   * Input:
   *  - startTimelon: DatelonTimelon
   *  - elonndTimelon: DatelonTimelon
   *  - filtelonrMelondiaTypelon: Option[Int]
   *      MelondiaTypelon
   *        1: Imagelon
   *        2: GIF
   *        3: Videlono
   * - filtelonrNSFWAuthor: Boolelonan
   *      Whelonthelonr welon want to filtelonr out NSFW twelonelont authors
   *
   * Relonturn:
   *  String - TwelonelontId tablelon SQL string format
   *           Tablelon Schelonma
   *              - twelonelontId: Long
   */
  delonf gelontTwelonelontIdWithMelondiaAndNSFWAuthorFiltelonrSQL(
    startTimelon: DatelonTimelon,
    elonndTimelon: DatelonTimelon,
    filtelonrMelondiaTypelon: Option[Int],
    filtelonrNSFWAuthor: Boolelonan
  ): String = {
    val sql = s"""
                 |SelonLelonCT DISTINCT twelonelontId
                 |FROM `twttr-bq-twelonelontsourcelon-prod.uselonr.unhydratelond_flat` twelonelontsourcelon, UNNelonST(melondia) AS melondia
                 |WHelonRelon (DATelon(_PARTITIONTIMelon) >= DATelon("${startTimelon}") AND DATelon(_PARTITIONTIMelon) <= DATelon("${elonndTimelon}")) AND
                 |         timelonstamp_millis((1288834974657 +
                 |          ((twelonelontId  & 9223372036850581504) >> 22))) >= TIMelonSTAMP("${startTimelon}")
                 |          AND timelonstamp_millis((1288834974657 +
                 |        ((twelonelontId  & 9223372036850581504) >> 22))) <= TIMelonSTAMP("${elonndTimelon}")
                 |""".stripMargin

    val filtelonrMelondiaStr = filtelonrMelondiaTypelon match {
      caselon Somelon(melondiaTypelon) => s" AND melondia.melondia_typelon =${melondiaTypelon}"
      caselon _ => ""
    }
    val filtelonrNSFWAuthorStr = if (filtelonrNSFWAuthor) " AND nsfwUselonr = falselon" elonlselon ""
    sql + filtelonrMelondiaStr + filtelonrNSFWAuthorStr
  }

  /*
   * From a givelonn timelon window, relontrielonvelon twelonelontIds that fall into thelon NSFW delonny list
   *
   * Input:
   *  - startTimelon: DatelonTimelon
   *  - elonndTimelon: DatelonTimelon
   *
  * Relonturn:
   *  String - TwelonelontId tablelon SQL string format
   *           Tablelon Schelonma
   *              - twelonelontId: Long
   */
  delonf gelontNSFWTwelonelontIdDelonnylistSQL(
    startTimelon: DatelonTimelon,
    elonndTimelon: DatelonTimelon,
  ): String = {
    val telonmplatelonVariablelons =
      Map(
        "START_TIMelon" -> startTimelon.toString(),
        "elonND_TIMelon" -> elonndTimelon.toString(),
      )
    BQQuelonryUtils.gelontBQQuelonryFromSqlFilelon(NSFWTwelonelontIdDelonnylistSQLPath, telonmplatelonVariablelons)
  }

  /*
   * From a givelonn clustelonr id to top k twelonelonts tablelon and a timelon window,
   * (1) Relontrielonvelon thelon latelonst fav-baselond top twelonelonts pelonr clustelonr tablelon within thelon timelon window
   * (2) Innelonr join with thelon givelonn tablelon using clustelonr id and twelonelont id
   * (3) Crelonatelon thelon top k twelonelonts pelonr clustelonr tablelon for thelon intelonrselonction
   *
   * Input:
   *  - startTimelon: DatelonTimelon
   *  - elonndTimelon: DatelonTimelon
   *  - topKTwelonelontsForClustelonrKelonySQL: String, a SQL quelonry
   *
   * Relonturn:
   *  String - TopKTwelonelontsForClustelonrKelony tablelon SQL string format
   *           Tablelon Schelonma
   *              - clustelonrId: Long
   *              - topKTwelonelontsForClustelonrKelony: (Long, Long)
   *                  - twelonelontId: Long
   *                  - twelonelontScorelon: Long
   */
  delonf gelonnelonratelonClustelonrTopTwelonelontIntelonrselonctionWithFavBaselondIndelonxSQL(
    startTimelon: DatelonTimelon,
    elonndTimelon: DatelonTimelon,
    clustelonrTopKTwelonelonts: Int,
    topKTwelonelontsForClustelonrKelonySQL: String
  ): String = {
    val telonmplatelonVariablelons =
      Map(
        "START_TIMelon" -> startTimelon.toString(),
        "elonND_TIMelon" -> elonndTimelon.toString(),
        "CLUSTelonR_TOP_K_TWelonelonTS" -> clustelonrTopKTwelonelonts.toString,
        "CLUSTelonR_TOP_TWelonelonTS_SQL" -> topKTwelonelontsForClustelonrKelonySQL
      )
    BQQuelonryUtils.gelontBQQuelonryFromSqlFilelon(
      ClustelonrTopTwelonelontsIntelonrselonctionWithFavBaselondIndelonxSQLPath,
      telonmplatelonVariablelons)
  }

  /*
   * Givelonn a list of action typelons, build a string that indicatelons thelon uselonr
   * elonngagelond with thelon twelonelont
   *
   * elonxamplelon uselon caselon: Welon want to build a SQL quelonry that speloncifielons this uselonr elonngagelond
   *  with twelonelont with elonithelonr fav or relontwelonelont actions.
   *
   * Input:
   *  - actionTypelons: Selonq("SelonrvelonrTwelonelontFav", "SelonrvelonrTwelonelontRelontwelonelont")
   *  - boolelonanOpelonrator: "OR"
   * Output: "SelonrvelonrTwelonelontFav.elonngagelond = 1 OR SelonrvelonrTwelonelontRelontwelonelont.elonngagelond = 1"
   *
   * elonxamplelon SQL:
   *  SelonLelonCT SelonrvelonrTwelonelontFav, SelonrvelonrTwelonelontRelontwelonelont
   *  FROM tablelon
   *  WHelonRelon SelonrvelonrTwelonelontFav.elonngagelond = 1 OR SelonrvelonrTwelonelontRelontwelonelont.elonngagelond = 1
   */
  delonf buildActionTypelonselonngagelonmelonntIndicatorString(
    actionTypelons: Selonq[String],
    boolelonanOpelonrator: String = "OR"
  ): String = {
    actionTypelons.map(action => f"""${action}.elonngagelond = 1""").mkString(f""" ${boolelonanOpelonrator} """)
  }
}

caselon class BQTablelonDelontails(
  projelonctNamelon: String,
  tablelonNamelon: String,
  dataselontNamelon: String) {
  ovelonrridelon delonf toString: String = s"${projelonctNamelon}.${tablelonNamelon}.${dataselontNamelon}"
}
