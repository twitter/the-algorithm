package com.tw ter.s mclusters_v2.sc o.bq_generat on
package s mclusters_ ndex_generat on

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.getNSFWT et dDenyl stSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.getT et dW hFavCountSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.getT et dW h d aAndNSFWAuthorF lterSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.getUserT etEngage ntEventPa rSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common.BQGenerat onUt l.generateClusterTopT et ntersect onW hFavBased ndexSQL
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.s mclusters_ ndex_generat on.Conf g.s mclustersEngage ntBased ndexGenerat onSQLPath
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common. ndexGenerat onUt l.TopKT etsForClusterKey
 mport com.tw ter.s mclusters_v2.sc o.bq_generat on.common. ndexGenerat onUt l.parseClusterTopKT etsFn
 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.joda.t  .DateT  

object Engage ntEventBasedClusterToT et ndexFromBQ {

  /*
   * Reads t  user-t et- nteract on table and apply t et fav count f lter
   * Returns t  post processed table results  n SQL str ng format
   *
*  nput:
   *   - startT  : DateT  
   *       T  earl est t  stamp from t  user-t et- nteract on table
   *   - endT  : DateT  
   *       T  latest t  stamp from t  user-t et- nteract on table
   *   - m nFavCount:  nt
   *       W t r   want to enable t et fav count f lters
   *
* Return:
   *   Str ng - Post processed table results  n SQL str ng format
   */
  def getT et nteract onTableW hFavCountF lter(
    startT  : DateT  ,
    endT  : DateT  ,
    m nFavCount:  nt
  ): Str ng = {
     f (m nFavCount > 0) {
      val t etFavCountSQL = getT et dW hFavCountSQL(startT  , endT  )
      s"""
         |  W TH t et_fav_count AS (${t etFavCountSQL})
         |  SELECT user d, t et d, tsM ll s
         |  FROM user_t et_ nteract on_w h_m n_ nteract on_count_f lter
         |  JO N t et_fav_count
         |  US NG(t et d)
         |  WHERE t et_fav_count.favCount >= ${m nFavCount}
         |""".str pMarg n
    } else {
      // D rectly read from t  table w hout apply ng any f lters
      s"SELECT user d, t et d, tsM ll s FROM user_t et_ nteract on_w h_m n_ nteract on_count_f lter"
    }
  }

  /*
   * Reads t  user-t et- nteract on table and apply  alth and v deo f lters  f spec f ed.
   * Returns t  post processed table results  n SQL str ng format
   *
  *  nput:
   *   - tableNa : Str ng
   *       Sc ma of t  table
   *         user d: Long
   *         t et d: Long
   *         tsM ll s: Long
   *   - startT  : DateT  
   *       T  earl est t  stamp from t  user-t et- nteract on table
   *   - endT  : DateT  
   *       T  latest t  stamp from t  user-t et- nteract on table
   *   - enable althAndV deoF lters: Boolean
   *       W t r   want to enable  alth f lters and v deo only f lters
   *
  * Return:
   *   Str ng - Post processed table results  n SQL str ng format
   */
  def getT et nteract onTableW h althF lter(
    startT  : DateT  ,
    endT  : DateT  ,
    enable althAndV deoF lters: Boolean,
  ): Str ng = {
     f (enable althAndV deoF lters) {
      // Get SQL for t ets w h  d a and NSFW f lter
      val t etW h d aAndNSFWAuthorF lterSQL = getT et dW h d aAndNSFWAuthorF lterSQL(
        startT  ,
        endT  ,
        f lter d aType = So (3), // V deoT ets  d aType = 3
        f lterNSFWAuthor = true
      )
      // Get SQL for NSFW t et  d deny l st
      val nsfwT etDenyl stSQL = getNSFWT et dDenyl stSQL(startT  , endT  )
      // Comb ne t   alth f lter SQLs
      s"""
         |SELECT user d, t et d, tsM ll s FROM user_t et_ nteract on_w h_fav_count_f lter JO N (
         |  ${t etW h d aAndNSFWAuthorF lterSQL}
         |    AND t et d NOT  N (${nsfwT etDenyl stSQL})
         |) US NG(t et d)
         |""".str pMarg n
    } else {
      // D rectly read from t  table w hout apply ng any f lters
      s"SELECT user d, t et d, tsM ll s FROM user_t et_ nteract on_w h_fav_count_f lter"
    }
  }

  def getTopKT etsForClusterKeyBQ(
    sc: Sc oContext,
    queryT  stamp: DateT  ,
    maxT etAgeH s:  nt,
    consu rEmbedd ngsSQL: Str ng,
    userT etEngage ntEventPa rSqlPath: Str ng,
    userT etEngage ntEventPa rTemplateVar able: Map[Str ng, Str ng],
    enable althAndV deoF lters: Boolean,
    enableFavClusterTopKT ets ntersect on: Boolean,
    m n nteract onCount:  nt,
    m nFavCount:  nt,
    t etEmbedd ngsLength:  nt,
    t etEmbedd ngsHalfL fe:  nt,
    m nEngage ntPerCluster:  nt,
    clusterTopKT ets:  nt
  ): SCollect on[TopKT etsForClusterKey] = {
    // Def ne template var ables wh ch   would l ke to be replaced  n t  correspond ng sql f le
    val startT   = queryT  stamp.m nusH s(maxT etAgeH s)
    val endT   = queryT  stamp

    val  ndexGenerat onTemplateVar ables =
      Map(
        "HALF_L FE" -> t etEmbedd ngsHalfL fe.toStr ng,
        "CURRENT_TS" -> queryT  stamp.toStr ng(),
        "START_T ME" -> startT  .toStr ng(),
        "END_T ME" -> endT  .toStr ng(),
        "USER_TWEET_ENGAGEMENT_TABLE_SQL" ->
          getUserT etEngage ntEventPa rSQL(
            startT  ,
            endT  ,
            userT etEngage ntEventPa rSqlPath,
            userT etEngage ntEventPa rTemplateVar able
          ),
        // M n  nteract on count f lter
        "M N_ NTERACT ON_COUNT" -> m n nteract onCount.toStr ng,
        // M n fav count f lter
        "TWEET_ NTERACT ON_W TH_FAV_COUNT_F LTER_SQL" -> getT et nteract onTableW hFavCountF lter(
          startT  ,
          endT  ,
          m nFavCount
        ),
        //  alth f lter
        "TWEET_ NTERACT ON_W TH_HEALTH_F LTER_SQL" -> getT et nteract onTableW h althF lter(
          startT  ,
          endT  ,
          enable althAndV deoF lters),
        "CONSUMER_EMBEDD NGS_SQL" -> consu rEmbedd ngsSQL,
        "TWEET_EMBEDD NG_LENGTH" -> t etEmbedd ngsLength.toStr ng,
        "M N_ENGAGEMENT_PER_CLUSTER" -> m nEngage ntPerCluster.toStr ng,
        "CLUSTER_TOP_K_TWEETS" -> clusterTopKT ets.toStr ng
      )
    val query = BQQueryUt ls.getBQQueryFromSqlF le(
      s mclustersEngage ntBased ndexGenerat onSQLPath,
       ndexGenerat onTemplateVar ables)

    val postF lterQuery =  f (enableFavClusterTopKT ets ntersect on) {
      generateClusterTopT et ntersect onW hFavBased ndexSQL(
        startT  ,
        endT  ,
        clusterTopKT ets,
        query)
    } else {
      query
    }
    // Generate S mClusters cluster-to-t et  ndex
    sc.custom nput(
      s"S mClusters cluster-to-t et  ndex generat on BQ job",
      B gQuery O
        .read(parseClusterTopKT etsFn(t etEmbedd ngsHalfL fe))
        .fromQuery(postF lterQuery)
        .us ngStandardSql()
    )
  }
}
