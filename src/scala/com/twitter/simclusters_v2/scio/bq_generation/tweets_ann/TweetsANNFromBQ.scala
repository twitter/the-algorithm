package com.tw ter.s mclusters_v2.sc o.bq_generat on
package t ets_ann

 mport com.spot fy.sc o.Sc oContext
 mport com.spot fy.sc o.values.SCollect on
 mport com.tw ter.s mclusters_v2.thr ftscala.Cand dateT et
 mport com.tw ter.wtf.beam.bq_embedd ng_export.BQQueryUt ls
 mport org.apac .avro.gener c.Gener cData
 mport org.apac .avro.gener c.Gener cRecord
 mport org.apac .beam.sdk. o.gcp.b gquery.B gQuery O
 mport org.apac .beam.sdk. o.gcp.b gquery.Sc maAndRecord
 mport org.apac .beam.sdk.transforms.Ser al zableFunct on
 mport org.joda.t  .DateT  
 mport scala.collect on.mutable.L stBuffer

object T etsANNFromBQ {
  // Default ANN conf g var ables
  val topNClustersPerS ceEmbedd ng = Conf g.S mClustersANNTopNClustersPerS ceEmbedd ng
  val topMT etsPerCluster = Conf g.S mClustersANNTopMT etsPerCluster
  val topKT etsPerUserRequest = Conf g.S mClustersANNTopKT etsPerUserRequest

  // SQL f le paths
  val t etsANNSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/t ets_ann.sql"
  val t etsEmbedd ngGenerat onSQLPath =
    s"/com/tw ter/s mclusters_v2/sc o/bq_generat on/sql/t et_embedd ngs_generat on.sql"

  // Funct on that parses t  Gener cRecord results   read from BQ
  val parseUserToT etRecom ndat onsFunc =
    new Ser al zableFunct on[Sc maAndRecord, UserToT etRecom ndat ons] {
      overr de def apply(record: Sc maAndRecord): UserToT etRecom ndat ons = {
        val gener cRecord: Gener cRecord = record.getRecord()
        UserToT etRecom ndat ons(
          user d = gener cRecord.get("user d").toStr ng.toLong,
          t etCand dates = parseT et dColumn(gener cRecord, "t ets"),
        )
      }
    }

  // Parse t et d cand dates column
  def parseT et dColumn(
    gener cRecord: Gener cRecord,
    columnNa : Str ng
  ): L st[Cand dateT et] = {
    val t et ds: Gener cData.Array[Gener cRecord] =
      gener cRecord.get(columnNa ).as nstanceOf[Gener cData.Array[Gener cRecord]]
    val results: L stBuffer[Cand dateT et] = new L stBuffer[Cand dateT et]()
    t et ds.forEach((sc: Gener cRecord) => {
      results += Cand dateT et(
        t et d = sc.get("t et d").toStr ng.toLong,
        score = So (sc.get("logCos neS m lar yScore").toStr ng.toDouble)
      )
    })
    results.toL st
  }

  def getT etEmbedd ngsSQL(
    queryDate: DateT  ,
    consu rEmbedd ngsSQL: Str ng,
    t etEmbedd ngsSQLPath: Str ng,
    t etEmbedd ngsHalfL fe:  nt,
    t etEmbedd ngsLength:  nt
  ): Str ng = {
    //   read one day of fav events to construct   t et embedd ngs
    val templateVar ables =
      Map(
        "CONSUMER_EMBEDD NGS_SQL" -> consu rEmbedd ngsSQL,
        "QUERY_DATE" -> queryDate.toStr ng(),
        "START_T ME" -> queryDate.m nusDays(1).toStr ng(),
        "END_T ME" -> queryDate.toStr ng(),
        "M N_SCORE_THRESHOLD" -> 0.0.toStr ng,
        "HALF_L FE" -> t etEmbedd ngsHalfL fe.toStr ng,
        "TWEET_EMBEDD NG_LENGTH" -> t etEmbedd ngsLength.toStr ng,
        "NO_OLDER_TWEETS_THAN_DATE" -> queryDate.m nusDays(1).toStr ng(),
      )
    BQQueryUt ls.getBQQueryFromSqlF le(t etEmbedd ngsSQLPath, templateVar ables)
  }

  def getT etRecom ndat onsBQ(
    sc: Sc oContext,
    queryT  stamp: DateT  ,
    consu rEmbedd ngsSQL: Str ng,
    t etEmbedd ngsHalfL fe:  nt,
    t etEmbedd ngsLength:  nt
  ): SCollect on[UserToT etRecom ndat ons] = {
    // Get t  t et embedd ngs SQL str ng based on t  prov ded consu rEmbedd ngsSQL
    val t etEmbedd ngsSQL =
      getT etEmbedd ngsSQL(
        queryT  stamp,
        consu rEmbedd ngsSQL,
        t etsEmbedd ngGenerat onSQLPath,
        t etEmbedd ngsHalfL fe,
        t etEmbedd ngsLength
      )

    // Def ne template var ables wh ch   would l ke to be replaced  n t  correspond ng sql f le
    val templateVar ables =
      Map(
        "CONSUMER_EMBEDD NGS_SQL" -> consu rEmbedd ngsSQL,
        "TWEET_EMBEDD NGS_SQL" -> t etEmbedd ngsSQL,
        "TOP_N_CLUSTER_PER_SOURCE_EMBEDD NG" -> topNClustersPerS ceEmbedd ng.toStr ng,
        "TOP_M_TWEETS_PER_CLUSTER" -> topMT etsPerCluster.toStr ng,
        "TOP_K_TWEETS_PER_USER_REQUEST" -> topKT etsPerUserRequest.toStr ng
      )
    val query = BQQueryUt ls.getBQQueryFromSqlF le(t etsANNSQLPath, templateVar ables)

    // Run S mClusters ANN on BQ and parse t  results
    sc.custom nput(
      s"S mClusters BQ ANN",
      B gQuery O
        .read(parseUserToT etRecom ndat onsFunc)
        .fromQuery(query)
        .us ngStandardSql()
    )
  }

  case class UserToT etRecom ndat ons(
    user d: Long,
    t etCand dates: L st[Cand dateT et])
}
