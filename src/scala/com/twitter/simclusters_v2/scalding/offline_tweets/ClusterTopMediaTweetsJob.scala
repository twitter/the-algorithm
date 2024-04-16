package com.tw ter.s mclusters_v2.scald ng.offl ne_t ets

 mport com.tw ter.algeb rd.Aggregator.s ze
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.scald ng.typed.TypedP pe
 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateOps
 mport com.tw ter.scald ng.DateParser
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.H s
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Wr eExtens on
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.T  stamp
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.DataPaths
 mport com.tw ter.s mclusters_v2.hdfs_s ces.Offl neClusterTop d aT ets20M145K2020ScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.LogFavBasedPers stentT etEmbedd ngMhExportS ce
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng.embedd ng.common.ExternalDataS ces
 mport com.tw ter.s mclusters_v2.thr ftscala.DayPart  onedCluster d
 mport com.tw ter.s mclusters_v2.thr ftscala.Pers stentS mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.T etW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.T etsW hScore
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.t ets ce.common.thr ftscala. d aType
 mport com.tw ter.t ets ce.common.thr ftscala.UnhydratedFlatT et
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport java.text.S mpleDateFormat

object ClusterTopT etsJob {

  def serv ce dent f er(zone: Str ng, env: Str ng): Serv ce dent f er = Serv ce dent f er(
    role = "cassowary",
    serv ce = "offl ne_cluster_top_ d a_t ets_20M_145K_2020",
    env ron nt = env,
    zone = zone
  )

  pr vate def  s d aT et(t et: UnhydratedFlatT et): Boolean = {
    t et. d a.ex sts {  d aSeq =>
       d aSeq.ex sts { e =>
        e. d aType.conta ns( d aType.V deo)
      }
    }
  }

  pr vate val dateFormatter = new S mpleDateFormat("yyyy-MM-dd")

  def getClusterTop d aT ets(
    pers stentEmbedd ngP pe: TypedP pe[((T et d, T  stamp), Pers stentS mClustersEmbedd ng)],
    t etS ceP pe: TypedP pe[UnhydratedFlatT et],
    maxT etsPerClusterPerPart  on:  nt
  ): TypedP pe[(DayPart  onedCluster d, Seq[(T et d, Double)])] = {
    val  d aT etsP pe = t etS ceP pe.collect {
      case t et  f  s d aT et(t et) => (t et.t et d, ())
    }

    val t etEmbedd ngsP pe: TypedP pe[(T et d, ( nt, Double))] = {
      pers stentEmbedd ngP pe.collect {
        case ((t et d, t  stamp), pers stentEmbedd ng)
             f t  stamp == 1L => // 1L  s t  longest L2 embedd ng

          pers stentEmbedd ng.embedd ng.embedd ng.map { clusterW hScore =>
            (t et d, (clusterW hScore.cluster d, clusterW hScore.score))
          }
      }.flatten
    }

     d aT etsP pe
      .jo n(t etEmbedd ngsP pe)
      .w hReducers(2000)
      .map {
        case (t et d, ((), (cluster d, score))) =>
          val dayPart  on = dateFormatter.format(Snowflake d(t et d).t  . nM ll seconds)
          ((cluster d, dayPart  on), Seq((t et d, score)))
      }
      .sumByKey
      .mapValues(_.sortBy(-_._2).take(maxT etsPerClusterPerPart  on))
      .map { case ((c d, part  on), values) => (DayPart  onedCluster d(c d, part  on), values) }
  }

  // Convert to Manhattan compat ble format
  def toKeyVal(
    clusterTopT ets: TypedP pe[(DayPart  onedCluster d, Seq[(T et d, Double)])],
  ): TypedP pe[KeyVal[DayPart  onedCluster d, T etsW hScore]] = {
    clusterTopT ets.map {
      case (key, t etsW hScores) =>
        val thr ft = t etsW hScores.map { t => T etW hScore(t._1, t._2) }
        KeyVal(key, T etsW hScore(thr ft))
    }
  }
}

/**
 * Sc duled job. Runs every couple of h s (c ck t  .yaml for exact cron sc dule).
 * Reads 21 days of t ets, and t  most recent pers stent t et embedd ngs from a Manhattan dump.
 *   outputs a cluster d-> L st[t et d]  ndex.

capesospy-v2 update --bu ld_locally --start_cron \
offl ne_cluster_top_ d a_t ets_20M_145K_2020 src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object ClusterTop d aT ets20M145K2020BatchJob extends Sc duledExecut onApp {
  overr de def f rstT  : R chDate = R chDate("2021-08-29")

  overr de def batch ncre nt: Durat on = H s(3)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    // max publ c t et has 21 days. read 1 day fe r go g ve so  buffer
    val lookbackDateRange = dateRange.prepend(Days(21))

    val t etS ce: TypedP pe[UnhydratedFlatT et] =
      ExternalDataS ces.flatT etsS ce(lookbackDateRange)

    val pers stentEmbedd ngP pe: TypedP pe[
      ((T et d, T  stamp), Pers stentS mClustersEmbedd ng)
    ] =
      TypedP pe.from(
        new LogFavBasedPers stentT etEmbedd ngMhExportS ce(
          range = lookbackDateRange,
          serv ce dent f er = ClusterTopT etsJob.serv ce dent f er(args("zone"), args("env"))
        ))

    val maxT etsPerClusterPerPart  on = 1200

    val da lyClusterTopT ets = ClusterTopT etsJob.getClusterTop d aT ets(
      pers stentEmbedd ngP pe,
      t etS ce,
      maxT etsPerClusterPerPart  on
    )

    val keyValP pe: TypedP pe[KeyVal[DayPart  onedCluster d, T etsW hScore]] =
      ClusterTopT etsJob.toKeyVal(da lyClusterTopT ets)

    keyValP pe
      .wr eDALVers onedKeyValExecut on(
        Offl neClusterTop d aT ets20M145K2020ScalaDataset,
        D.Suff x(DataPaths.Offl neClusterTop d aT ets2020DatasetPath)
      )
  }
}

/**
Adhoc debugg ng job. Uses Ent y Embedd ngs dataset to  nfer user  nterests

./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_t ets/ &&\
scald ng remote run \
  --ma n-class com.tw ter.s mclusters_v2.scald ng.offl ne_t ets.AdhocClusterTop d aT etsJob \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng/offl ne_t ets/:offl ne_cluster_top_ d a_t ets_20M_145K_2020-adhoc \
  --user cassowary \
  -- --output_d r /scratch_user/cassowary/y _ldap --date 2021-08-30 --zone atla --env prod --ema l y _ldap@tw ter.com
 */
object AdhocClusterTop d aT etsJob extends AdhocExecut onApp {

  /**
   * Run so  stat analys s on t  results, such as t  number of t ets  n a cluster, t et score
   * d str but ons, etc.
   *
   *  deally works on 1 day data only.  f mult ple days data are passed  n,  'll aggregate over
   * mult ple days anyway
   */
  def analyzeClusterResults(
    clusterTopT ets: TypedP pe[(DayPart  onedCluster d, Seq[(T et d, Double)])]
  ): Execut on[Str ng] = {

    val t etS zeExec = Ut l.pr ntSummaryOfNu r cColumn(
      clusterTopT ets.map { case (_, t ets) => t ets.s ze },
      columnNa  = So ("T et s ze d str but on of clusters")
    )

    val scoreD stExec = Ut l.pr ntSummaryOfNu r cColumn(
      clusterTopT ets.flatMap(_._2.map(_._2)),
      columnNa  = So ("Score d str but on of t  t ets")
    )

    val numClustersExec =
      clusterTopT ets.map(_._1._1).d st nct.aggregate(s ze).getOrElseExecut on(0L)

    val numT etsExec =
      clusterTopT ets.flatMap(_._2.map(_._1)).d st nct.aggregate(s ze).getOrElseExecut on(0L)

    Execut on.z p(t etS zeExec, scoreD stExec, numClustersExec, numT etsExec).map {
      case (t etS zeD st, scoreD st, numClusters, numT ets) =>
        s""" 
          |Number of un que t ets = $numT ets
          |Number of clusters = $numClusters
          |------------------------
          |$t etS zeD st
          |------------------------
          |$scoreD st
          |""".str pMarg n
    }
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val startT   = System.currentT  M ll s()
    Execut on.w hArgs { args =>
      Execut on.getMode.flatMap {  mpl c  mode =>
         mpl c  val dateRange: DateRange =
          DateRange.parse(args.l st("date"))(DateOps.UTC, DateParser.default)

        val outputD r = args("output_d r")

        val maxT etsPerCluster = 100

        // max publ c t et has 21 days. read 1 day fe r go g ve so  buffer
        val lookbackDateRange = dateRange.prepend(Days(21))

        val t etS ce: TypedP pe[UnhydratedFlatT et] =
          ExternalDataS ces.flatT etsS ce(lookbackDateRange)

        val pers stentEmbedd ngP pe: TypedP pe[
          ((T et d, T  stamp), Pers stentS mClustersEmbedd ng)
        ] =
          TypedP pe.from(
            new LogFavBasedPers stentT etEmbedd ngMhExportS ce(
              range = lookbackDateRange,
              serv ce dent f er = ClusterTopT etsJob.serv ce dent f er(args("zone"), args("env"))
            ))

        val results = ClusterTopT etsJob.getClusterTop d aT ets(
          pers stentEmbedd ngP pe,
          t etS ce,
          maxT etsPerCluster
        )
        analyzeClusterResults(TypedP pe.empty)
          .flatMap { d str but ons =>
            val t  TakenM n = (System.currentT  M ll s() - startT  ) / 60000
            val text =
              s"""
                 | AdhocClusterTop d aT etsJob f n s d on: $dateRange.
                 | T   taken: $t  TakenM n m nutes.
                 | maxT etsPerCluster: $maxT etsPerCluster.
                 | output_d r: $outputD r
                 | 
                 | $d str but ons
              """.str pMarg n
            Ut l.sendEma l(text, "AdhocClusterTop d aT etsJob f n s d.", args("ema l"))

            results
              .wr eExecut on(TypedTsv(outputD r))
          }
      }
    }
  }
}
