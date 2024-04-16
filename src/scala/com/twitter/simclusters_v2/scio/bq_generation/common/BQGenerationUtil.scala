package com.tw ter.s mclusters_v2.sc o
package bq_generat on.common

 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls
 mport org.joda.t  .DateT  

object BQGenerat onUt l {
  // Consu r Embedd ngs BQ table deta ls
  val  nterested nEmbedd ngs20M145K2020Table = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "s mclusters_v2_user_to_ nterested_ n_20M_145K_2020",
  )
  val mtsConsu rEmbedd ngsFav90P20MTable = BQTableDeta ls(
    "twttr-bq-cassowary-prod",
    "user",
    "mts_consu r_embedd ngs_fav90p_20m",
  )

  // Common SQL path
  val T etFavCountSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/t et_fav_count.sql"

  val NSFWT et dDenyl stSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/nsfw_t et_denyl st.sql"

  val ClusterTopT ets ntersect onW hFavBased ndexSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/cluster_top_t ets_ ntersect on_w h_fav_based_ ndex.sql"

  // Read  nterested n 2020
  def get nterested n2020SQL(
    queryDate: DateT  ,
    lookBackDays:  nt
  ): Str ng = {
    s"""
       |SELECT user d, 
       |        cluster dToScores.key AS cluster d,
       |        cluster dToScores.value.logFavScore AS userScore,
       |        cluster dToScores.value.logFavScoreClusterNormal zedOnly AS clusterNormal zedLogFavScore,
       |FROM `$ nterested nEmbedd ngs20M145K2020Table`, UNNEST(cluster dToScores) AS cluster dToScores
       |WHERE DATE(_PART T ONT ME) = 
       |  (  -- Get latest part  on t  
       |  SELECT MAX(DATE(_PART T ONT ME)) latest_part  on
       |  FROM `$ nterested nEmbedd ngs20M145K2020Table`
       |  WHERE Date(_PART T ONT ME) BETWEEN 
       |      DATE_SUB(Date("${queryDate}"), 
       |       NTERVAL $lookBackDays DAY) AND DATE("$queryDate")
       |  )
       |   AND cluster dToScores.value.logFavScore > 0.0 # m n score threshold for user embedd ng values
       |""".str pMarg n
  }

  // Read MTS Consu r Embedd ngs - Fav90P20M conf g
  def getMTSConsu rEmbedd ngsFav90P20MSQL(
    queryDate: DateT  ,
    lookBackDays:  nt
  ): Str ng = {
    //   read t  most recent snapshot of MTS Consu r Embedd ngs Fav90P20M
    s"""
       |SELECT user d,             
       |    cluster dToScores.key AS cluster d,
       |    cluster dToScores.value.logFavUserScore AS userScore,
       |    cluster dToScores.value.logFavUserScoreClusterNormal zed AS clusterNormal zedLogFavScore
       |    FROM `$mtsConsu rEmbedd ngsFav90P20MTable`, UNNEST(embedd ng.cluster dToScores) AS cluster dToScores
       |WHERE DATE( ngest onT  ) = (  
       |    -- Get latest part  on t  
       |    SELECT MAX(DATE( ngest onT  )) latest_part  on
       |    FROM `$mtsConsu rEmbedd ngsFav90P20MTable`
       |    WHERE Date( ngest onT  ) BETWEEN 
       |        DATE_SUB(Date("${queryDate}"), 
       |         NTERVAL  $lookBackDays DAY) AND DATE("${queryDate}")
       |) AND cluster dToScores.value.logFavUserScore > 0.0
       |""".str pMarg n
  }

  /*
   * For a spec f c t et engage nt, retr eve t  user  d, t et  d, and t  stamp
   *
   * Return:
   *  Str ng - User d, T et d and T  stamp table SQL str ng format
   *           Table Sc ma
   *              - user d: Long
   *              - t et d: Long
   *              - tsM ll s: Long
   */
  def getUserT etEngage ntEventPa rSQL(
    startT  : DateT  ,
    endT  : DateT  ,
    userT etEngage ntEventPa rSqlPath: Str ng,
    userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng]
  ): Str ng = {
    val templateVar ables = Map(
      "START_T ME" -> startT  .toStr ng(),
      "END_T ME" -> endT  .toStr ng(),
      "NO_OLDER_TWEETS_THAN_DATE" -> startT  .toStr ng()
    ) ++ userT etEngage ntEventPa rTemplateVar able
    BQQueryUt ls.getBQQueryFromSqlF le(userT etEngage ntEventPa rSqlPath, templateVar ables)
  }

  /*
   * Retr eve t ets and t  # of favs   got from a g ven t   w ndow
   *
   * Return:
   *  Str ng - T et d  and fav count table SQL str ng format
   *           Table Sc ma
   *              - t et d: Long
   *              - favCount: Long
   */
  def getT et dW hFavCountSQL(
    startT  : DateT  ,
    endT  : DateT  ,
  ): Str ng = {
    val templateVar ables =
      Map(
        "START_T ME" -> startT  .toStr ng(),
        "END_T ME" -> endT  .toStr ng(),
      )
    BQQueryUt ls.getBQQueryFromSqlF le(T etFavCountSQLPath, templateVar ables)
  }

  /*
   * From a g ven t   w ndow, retr eve t et ds that  re created by spec f c author or  d a type
   *
   *  nput:
   *  - startT  : DateT  
   *  - endT  : DateT  
   *  - f lter d aType: Opt on[ nt]
   *       d aType
   *        1:  mage
   *        2: G F
   *        3: V deo
   * - f lterNSFWAuthor: Boolean
   *      W t r   want to f lter out NSFW t et authors
   *
   * Return:
   *  Str ng - T et d table SQL str ng format
   *           Table Sc ma
   *              - t et d: Long
   */
  def getT et dW h d aAndNSFWAuthorF lterSQL(
    startT  : DateT  ,
    endT  : DateT  ,
    f lter d aType: Opt on[ nt],
    f lterNSFWAuthor: Boolean
  ): Str ng = {
    val sql = s"""
                 |SELECT D ST NCT t et d
                 |FROM `twttr-bq-t ets ce-prod.user.unhydrated_flat` t ets ce, UNNEST( d a) AS  d a 
                 |WHERE (DATE(_PART T ONT ME) >= DATE("${startT  }") AND DATE(_PART T ONT ME) <= DATE("${endT  }")) AND
                 |         t  stamp_m ll s((1288834974657 + 
                 |          ((t et d  & 9223372036850581504) >> 22))) >= T MESTAMP("${startT  }")
                 |          AND t  stamp_m ll s((1288834974657 + 
                 |        ((t et d  & 9223372036850581504) >> 22))) <= T MESTAMP("${endT  }")
                 |""".str pMarg n

    val f lter d aStr = f lter d aType match {
      case So ( d aType) => s" AND  d a. d a_type =${ d aType}"
      case _ => ""
    }
    val f lterNSFWAuthorStr =  f (f lterNSFWAuthor) " AND nsfwUser = false" else ""
    sql + f lter d aStr + f lterNSFWAuthorStr
  }

  /*
   * From a g ven t   w ndow, retr eve t et ds that fall  nto t  NSFW deny l st
   *
   *  nput:
   *  - startT  : DateT  
   *  - endT  : DateT  
   *
  * Return:
   *  Str ng - T et d table SQL str ng format
   *           Table Sc ma
   *              - t et d: Long
   */
  def getNSFWT et dDenyl stSQL(
    startT  : DateT  ,
    endT  : DateT  ,
  ): Str ng = {
    val templateVar ables =
      Map(
        "START_T ME" -> startT  .toStr ng(),
        "END_T ME" -> endT  .toStr ng(),
      )
    BQQueryUt ls.getBQQueryFromSqlF le(NSFWT et dDenyl stSQLPath, templateVar ables)
  }

  /*
   * From a g ven cluster  d to top k t ets table and a t   w ndow,
   * (1) Retr eve t  latest fav-based top t ets per cluster table w h n t  t   w ndow
   * (2)  nner jo n w h t  g ven table us ng cluster  d and t et  d
   * (3) Create t  top k t ets per cluster table for t   ntersect on
   *
   *  nput:
   *  - startT  : DateT  
   *  - endT  : DateT  
   *  - topKT etsForClusterKeySQL: Str ng, a SQL query
   *
   * Return:
   *  Str ng - TopKT etsForClusterKey table SQL str ng format
   *           Table Sc ma
   *              - cluster d: Long
   *              - topKT etsForClusterKey: (Long, Long)
   *                  - t et d: Long
   *                  - t etScore: Long
   */
  def generateClusterTopT et ntersect onW hFavBased ndexSQL(
    startT  : DateT  ,
    endT  : DateT  ,
    clusterTopKT ets:  nt,
    topKT etsForClusterKeySQL: Str ng
  ): Str ng = {
    val templateVar ables =
      Map(
        "START_T ME" -> startT  .toStr ng(),
        "END_T ME" -> endT  .toStr ng(),
        "CLUSTER_TOP_K_TWEETS" -> clusterTopKT ets.toStr ng,
        "CLUSTER_TOP_TWEETS_SQL" -> topKT etsForClusterKeySQL
      )
    BQQueryUt ls.getBQQueryFromSqlF le(
      ClusterTopT ets ntersect onW hFavBased ndexSQLPath,
      templateVar ables)
  }

  /*
   * G ven a l st of act on types, bu ld a str ng that  nd cates t  user
   * engaged w h t  t et
   *
   * Example use case:   want to bu ld a SQL query that spec f es t  user engaged
   *  w h t et w h e  r fav or ret et act ons.
   *
   *  nput:
   *  - act onTypes: Seq("ServerT etFav", "ServerT etRet et")
   *  - booleanOperator: "OR"
   * Output: "ServerT etFav.engaged = 1 OR ServerT etRet et.engaged = 1"
   *
   * Example SQL:
   *  SELECT ServerT etFav, ServerT etRet et
   *  FROM table
   *  WHERE ServerT etFav.engaged = 1 OR ServerT etRet et.engaged = 1
   */
  def bu ldAct onTypesEngage nt nd catorStr ng(
    act onTypes: Seq[Str ng],
    booleanOperator: Str ng = "OR"
  ): Str ng = {
    act onTypes.map(act on => f"""${act on}.engaged = 1""").mkStr ng(f""" ${booleanOperator} """)
  }
}

case class BQTableDeta ls(
  projectNa : Str ng,
  tableNa : Str ng,
  datasetNa : Str ng) {
  overr de def toStr ng: Str ng = s"${projectNa }.${tableNa }.${datasetNa }"
}
