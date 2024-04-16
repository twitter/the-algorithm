package com.tw ter.s mclusters_v2.scald ng.optout

 mport com.tw ter.scald ng.Args
 mport com.tw ter.scald ng.DateRange
 mport com.tw ter.scald ng.Days
 mport com.tw ter.scald ng.Durat on
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.R chDate
 mport com.tw ter.scald ng.TypedP pe
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng.Un que D
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.Semant cCoreEnt y d
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.thr ftscala.ClusterType
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser sKnownFor
 mport com.tw ter.s mclusters_v2.thr ftscala.Semant cCoreEnt yW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.UserToKnownForClusters
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport com.tw ter.s mclusters_v2.scald ng.common.TypedR chP pe._
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.scald ng. nferred_ent  es. nferredEnt  es

/**
 * Creates opt-out compl ant KnownFor datasets based on pla n user -> KnownFor data and users'
 * opt-out select ons from Y Tw terData.  n essence,   remove any cluster whose  nferred
 * ent  es  re opted out by t  user.
 * T  opted out KnownFor dataset should be t  default dataset to be consu d,  nstead of t 
 * pla n KnownFor, wh ch  s not opt-out compl ant.
 */
object KnownForOptOut {

  def f lterOptedOutKnownFor(
    knownForP pe: TypedP pe[(User d, ClustersUser sKnownFor)],
    optedOutEnt  es: TypedP pe[(User d, Set[Semant cCoreEnt y d])],
    clusterToEnt  es: TypedP pe[(Cluster d, Seq[Semant cCoreEnt yW hScore])]
  ): TypedP pe[(User d, ClustersUser sKnownFor)] = {

    val val dKnownFor = S mClustersOptOutUt l.f lterOptedOutClusters(
      userToClusters = knownForP pe.mapValues(_.cluster dToScores.keySet.toSeq),
      optedOutEnt  es = optedOutEnt  es,
      leg bleClusters = clusterToEnt  es
    )

    knownForP pe
      .leftJo n(val dKnownFor)
      .mapValues {
        case (or g nalKnownFors, val dKnownForOpt) =>
          val val dKnownFor = val dKnownForOpt.getOrElse(Seq()).toSet

          or g nalKnownFors.copy(
            cluster dToScores = or g nalKnownFors.cluster dToScores.f lterKeys(val dKnownFor)
          )
      }
      .f lter(_._2.cluster dToScores.nonEmpty)
  }
}

/**
capesospy-v2 update --bu ld_locally --start_cron \
  --start_cron known_for_optout_da ly \
  src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object KnownForOptOutDa lyBatchJob extends Sc duledExecut onApp {
  overr de def f rstT  : R chDate = R chDate("2021-03-29")

  overr de def batch ncre nt: Durat on = Days(1)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val optedOutEnt  esP pe = S mClustersOptOutUt l
      .getP13nOptOutS ces(dateRange.emb ggen(Days(2)), ClusterType.KnownFor)
      .forceToD sk

    val clusterToEnt  esP pe =  nferredEnt  es.getLeg bleEnt yEmbedd ngs(dateRange, t  Zone)

    val knownFor2020 = DAL
      .readMostRecentSnapshot(
        S mclustersV2RawKnownFor20M145K2020ScalaDataset,
        dateRange.emb ggen(Days(10)))
      .w hRemoteReadPol cy(AllowCrossClusterSa DC)
      .toTypedP pe
      .map { case KeyVal(k, v) => (k, v) }
      .count("num_users_w h_2020_knownfor")

    val f ltered2020KnownForExec = {
      val f ltered2020KnownForData = KnownForOptOut
        .f lterOptedOutKnownFor(
          knownForP pe = knownFor2020,
          optedOutEnt  es = optedOutEnt  esP pe,
          clusterToEnt  es = clusterToEnt  esP pe
        )
        .count("num_users_w h_compl ant_2020_knownfor")
        .forceToD sk

      Execut on
        .z p(
          f ltered2020KnownForData
            .map { case (k, v) => KeyVal(k, v) }
            .wr eDALVers onedKeyValExecut on(
              S mclustersV2KnownFor20M145K2020ScalaDataset,
              D.Suff x(DataPaths.KnownFor2020Path)
            ),
          f ltered2020KnownForData
            .map {
              case (user d, ClustersUser sKnownFor(modelVers on, clusters)) =>
                UserToKnownForClusters(user d, modelVers on, clusters)
            }
            .wr eDALSnapshotExecut on(
              dataset = S mclustersV2KnownFor20M145K2020Thr ftScalaDataset,
              updateStep = D.Da ly,
              pathLa t = D.Suff x(DataPaths.KnownFor2020Thr ftDatasetPath),
              fmt = D.Parquet,
              endDate = dateRange.end
            )
        ).un 
    }

    Ut l.pr ntCounters(f ltered2020KnownForExec)

  }
}

/**
 * For debugg ng only. Does a f lter ng run and pr nts t  d fferences before/after t  opt out
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/optout:knownfor_optout-adhoc && \
 oscar hdfs --user recos-platform --screen --tee y _ldap \
  --bundle knownfor_optout-adhoc \
  --tool com.tw ter.s mclusters_v2.scald ng.optout.KnownForOptOutAdhocJob \
 -- --date 2019-10-12
 */
object KnownForOptOutAdhocJob extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val knownForP pe = DAL
      .readMostRecentSnapshotNoOlderThan(S mclustersV2RawKnownFor20M145KDec11ScalaDataset, Days(30))
      .w hRemoteReadPol cy(Expl c Locat on(ProcAtla))
      .toTypedP pe
      .map { case KeyVal(k, v) => (k, v) }
      .count("num_users_w h_knownfor")

    val userOptoutEnt  es: TypedP pe[(User d, Set[Semant cCoreEnt y d])] =
      S mClustersOptOutUt l
        .getP13nOptOutS ces(dateRange.emb ggen(Days(4)), ClusterType.KnownFor)
        .count("num_users_w h_optouts")

    val clusterToEnt  es =  nferredEnt  es
      .getLeg bleEnt yEmbedd ngs(dateRange, t  Zone)
      .count("num_cluster_to_ent  es")

    val f lteredKnownForP pe = KnownForOptOut.f lterOptedOutKnownFor(
      knownForP pe,
      userOptoutEnt  es,
      clusterToEnt  es
    )

    val output = knownForP pe
      .jo n(f lteredKnownForP pe)
      .collect {
        case (user d, (or g nalKnownFor, f ltered))
             f or g nalKnownFor.cluster dToScores != f ltered.cluster dToScores =>
          (user d, (or g nalKnownFor, f ltered))
      }
      .jo n(userOptoutEnt  es)
      .map {
        case (user d, ((or g nalKnownFor, f ltered), optoutEnt  es)) =>
          Seq(
            "user d=" + user d,
            "or g nalKnownFor=" + or g nalKnownFor,
            "f lteredKnownFor=" + f ltered,
            "optoutEnt  es=" + optoutEnt  es
          ).mkStr ng("\t")
      }

    val outputPath = "/user/recos-platform/adhoc/knownfor_optout"
    output.wr eExecut on(TypedTsv(outputPath))
  }
}
