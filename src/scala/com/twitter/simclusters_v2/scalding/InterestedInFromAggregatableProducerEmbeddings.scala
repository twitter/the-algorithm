package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.dal.cl ent.dataset.SnapshotDALDataset
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.D
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e.Wr eExtens on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossDC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Cluster d
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AggregatableProducerS mclustersEmbedd ngsByLogFavScore2020ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested nFromAggregatableProducerEmbedd ngs20M145K2020ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2UserTo nterested nFromAggregatableProducerEmbedd ngs20M145K2020ScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserAndNe ghborsF xedPathS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserNormal zedGraphScalaDataset
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.s mclusters_v2.thr ftscala.ModelVers on
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClustersEmbedd ng d
 mport com.tw ter.s mclusters_v2.thr ftscala.UserAndNe ghbors
 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusterScores
 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusters
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
 * Product on job for comput ng  nterested n data set from t  aggregatable producer embedd ngs for t  model vers on 20M145K2020.
 *   wr es t  data set  n KeyVal format to produce a MH DAL data set.
 *
 * A h gh level descr pt on of t  job:
 * - Read t  APE dataset
 * - Apply log1p to t  scores from t  above dataset as t  scores for producers  s h gh
 * - Normal ze t  scores for each producer (offl ne benchmark ng has shown better results from t  step.)
 * - Truncate t  number of clusters for each producer from t  APE dataset to reduce no se
 * - Compute  nterested n
 *
 * To deploy t  job:
 *
 * capesospy-v2 update --bu ld_locally --start_cron  nterested_ n_from_ape_2020 \
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc.yaml
 */
object  nterested nFromAPE2020BatchApp extends  nterested nFromAggregatableProducerEmbedd ngsBase {

  overr de val f rstT  : R chDate = R chDate("2021-03-03")

  overr de val batch ncre nt: Durat on = Days(7)

  overr de def modelVers on: ModelVers on = ModelVers on.Model20m145k2020

  overr de def producerEmbedd ngs nputKVDataset: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ] = AggregatableProducerS mclustersEmbedd ngsByLogFavScore2020ScalaDataset

  overr de def  nterested nFromAPEOutputKVDataset: KeyValDALDataset[
    KeyVal[User d, ClustersUser s nterested n]
  ] = S mclustersV2 nterested nFromAggregatableProducerEmbedd ngs20M145K2020ScalaDataset

  overr de def  nterested nFromAPEOutputThr ftDatset: SnapshotDALDataset[
    UserTo nterested nClusters
  ] = S mclustersV2UserTo nterested nFromAggregatableProducerEmbedd ngs20M145K2020ScalaDataset
}

tra   nterested nFromAggregatableProducerEmbedd ngsBase extends Sc duledExecut onApp {
  def modelVers on: ModelVers on

  def  nterested nFromAPEOutputKVDataset: KeyValDALDataset[
    KeyVal[User d, ClustersUser s nterested n]
  ]

  def producerEmbedd ngs nputKVDataset: KeyValDALDataset[
    KeyVal[S mClustersEmbedd ng d, S mClustersEmbedd ng]
  ]

  def  nterested nFromAPEOutputThr ftDatset: SnapshotDALDataset[UserTo nterested nClusters]

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    // nput args for t  run
    val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
    val maxClustersFromProducer = args. nt("maxClustersPerProducer", 5)
    val maxClustersPerUserF nalResult = args. nt("max nterested nClustersPerUser", 200)

    //Path var ables
    val  nterested nFromProducersPath =
      s"/user/cassowary/manhattan_sequence_f les/ nterested_ n_from_ape/" + modelVers on

    val  nterested nFromProducersThr ftPath =
      s"/user/cassowary/manhattan_sequence_f les/ nterested_ n_from_ape_thr ft/" + modelVers on

    val userUserGraph: TypedP pe[UserAndNe ghbors] =
      DAL
        .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
        .w hRemoteReadPol cy(AllowCrossDC)
        .toTypedP pe

    val producerEmbedd ngs = DAL
      .readMostRecentSnapshotNoOlderThan(
        producerEmbedd ngs nputKVDataset,
        Days(30)).w hRemoteReadPol cy(AllowCrossClusterSa DC).toTypedP pe.map {
        case KeyVal(producer, embedd ngs) => (producer, embedd ngs)
      }

    val result =  nterested nFromAggregatableProducerEmbedd ngsBase.run(
      userUserGraph,
      producerEmbedd ngs,
      maxClustersFromProducer,
      soc alProofThreshold,
      maxClustersPerUserF nalResult,
      modelVers on)

    val keyValExec =
      result
        .map { case (user d, clusters) => KeyVal(user d, clusters) }
        .wr eDALVers onedKeyValExecut on(
           nterested nFromAPEOutputKVDataset,
          D.Suff x( nterested nFromProducersPath)
        )
    val thr ftExec =
      result
        .map {
          case (user d, clusters) =>
            UserTo nterested nClusters(
              user d,
              ModelVers ons.toKnownForModelVers on(modelVers on),
              clusters.cluster dToScores)
        }
        .wr eDALSnapshotExecut on(
           nterested nFromAPEOutputThr ftDatset,
          D.Da ly,
          D.Suff x( nterested nFromProducersThr ftPath),
          D.EBLzo(),
          dateRange.end
        )
    Execut on.z p(keyValExec, thr ftExec).un 
  }
}

/**
 * Adhoc job to generate t   nterested n from aggregatable producer embedd ngs for t  model vers on 20M145K2020
 *
 * scald ng remote run \
 * --user cassowary \
 * --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
 * --pr nc pal serv ce_acoount@TW TTER.B Z \
 * --cluster blueb rd-qus1 \
 * --ma n-class com.tw ter.s mclusters_v2.scald ng. nterested nFromAPE2020AdhocApp \
 * --target src/scala/com/tw ter/s mclusters_v2/scald ng: nterested_ n_from_ape_2020-adhoc \
 * --hadoop-propert es "mapreduce.map. mory.mb=8192 mapreduce.map.java.opts='-Xmx7618M' mapreduce.reduce. mory.mb=8192 mapreduce.reduce.java.opts='-Xmx7618M'" \
 * -- --outputD r /gcs/user/cassowary/adhoc/y _ldap/ nterested_ n_from_ape_2020_keyval --date 2021-03-05
 */
object  nterested nFromAPE2020AdhocApp extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {
    val outputD r = args("outputD r")
    val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
    val maxClustersPerUserF nalResult = args. nt("max nterested nClustersPerUser", 200)
    val maxClustersFromProducer = args. nt("maxClustersFromProducer", 5)
    val  nputGraph = args.opt onal("graph nputD r") match {
      case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
      case None =>
        DAL
          .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
          .w hRemoteReadPol cy(AllowCrossClusterSa DC)
          .toTypedP pe
    }

    val producerEmbedd ngs = DAL
      .readMostRecentSnapshotNoOlderThan(
        AggregatableProducerS mclustersEmbedd ngsByLogFavScore2020ScalaDataset,
        Days(30)).w hRemoteReadPol cy(AllowCrossClusterSa DC).toTypedP pe.map {
        case KeyVal(producer, embedd ngs) => (producer, embedd ngs)
      }

    val result =  nterested nFromAggregatableProducerEmbedd ngsBase.run(
       nputGraph,
      producerEmbedd ngs,
      maxClustersFromProducer,
      soc alProofThreshold,
      maxClustersPerUserF nalResult,
      ModelVers on.Model20m145k2020)

    result
      .wr eExecut on(AdhocKeyValS ces. nterested nS ce(outputD r))
  }
}

/**
 *  lper funct ons
 */
object  nterested nFromAggregatableProducerEmbedd ngsBase {

  /**
   *  lper funct on to prune t  embedd ngs
   * @param embedd ngsW hScore embedd ngs
   * @param maxClusters number of clusters to keep, per user d
   * @param un que d for stats
   * @return
   */
  def getPrunedEmbedd ngs(
    embedd ngsW hScore: TypedP pe[(User d, Seq[(Cluster d, Float)])],
    maxClusters:  nt
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, Array[(Cluster d, Float)])] = {
    val numProducerMapp ngs = Stat("num_producer_embedd ngs_total")
    val numProducersW hLargeClusterMapp ngs = Stat(
      "num_producers_w h_more_clusters_than_threshold")
    val numProducersW hSmallClusterMapp ngs = Stat(
      "num_producers_w h_clusters_less_than_threshold")
    val totalClustersCoverageProducerEmbedd ngs = Stat("num_clusters_total_producer_embedd ngs")
    embedd ngsW hScore.map {
      case (producer d, clusterArray) =>
        numProducerMapp ngs. nc()
        val clusterS ze = clusterArray.s ze
        totalClustersCoverageProducerEmbedd ngs. ncBy(clusterS ze)
        val prunedL st =  f (clusterS ze > maxClusters) {
          numProducersW hLargeClusterMapp ngs. nc()
          clusterArray
            .sortBy {
              case (_, knownForScore) => -knownForScore
            }.take(maxClusters)
        } else {
          numProducersW hSmallClusterMapp ngs. nc()
          clusterArray
        }
        (producer d, prunedL st.toArray)
    }
  }

  /**
   *  lper funct on to remove all scores except follow and logFav
   * @param  nterested nResult  nterested n clusters for a user
   * @return
   */
  def get nterested nD scardScores(
     nterested nResult: TypedP pe[(User d, L st[(Cluster d, UserTo nterested nClusterScores)])]
  ): TypedP pe[(User d, L st[(Cluster d, UserTo nterested nClusterScores)])] = {
     nterested nResult.map {
      case (src d, fullClusterL st) =>
        val fullClusterL stW hD scardedScores = fullClusterL st.map {
          case (cluster d, clusterDeta ls) =>
            val clusterDeta lsW houtSoc al = UserTo nterested nClusterScores(
              //   are not plann ng to use t  ot r scores except for logFav and Follow.
              //  nce, sett ng ot rs as None for now,   can add t m back w n needed
              followScore = clusterDeta ls.followScore,
              logFavScore = clusterDeta ls.logFavScore,
              logFavScoreClusterNormal zedOnly = clusterDeta ls.logFavScoreClusterNormal zedOnly
            )
            (cluster d, clusterDeta lsW houtSoc al)
        }
        (src d, fullClusterL stW hD scardedScores)
    }
  }

  /**
   *  lper funct on to normal ze t  embedd ngs
   * @param embedd ngs cluster embedd ngs
   * @return
   */
  def getNormal zedEmbedd ngs(
    embedd ngs: TypedP pe[(User d, Seq[(Cluster d, Float)])]
  ): TypedP pe[(User d, Seq[(Cluster d, Float)])] = {
    embedd ngs.map {
      case (user d, clustersW hScores) =>
        val l2norm = math.sqrt(clustersW hScores.map(_._2).map(score => score * score).sum)
        (
          user d,
          clustersW hScores.map {
            case (cluster d, score) => (cluster d, (score / l2norm).toFloat)
          })
    }
  }

  def run(
    userUserGraph: TypedP pe[UserAndNe ghbors],
    producerEmbedd ngs: TypedP pe[(S mClustersEmbedd ng d, S mClustersEmbedd ng)],
    maxClustersFromProducer:  nt,
    soc alProofThreshold:  nt,
    maxClustersPerUserF nalResult:  nt,
    modelVers on: ModelVers on
  )(
     mpl c  un que d: Un que D
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
     mport  nterested nFromKnownFor._

    val producerEmbedd ngsW hScore: TypedP pe[(User d, Seq[(Cluster d, Float)])] =
      producerEmbedd ngs.map {
        case (
              S mClustersEmbedd ng d(embedd ngType, modelVers on,  nternal d.User d(producer d)),
              s mclusterEmbedd ng) =>
          (
            producer d,
            s mclusterEmbedd ng.embedd ng.map { s mclusterW hScore =>
              // APE dataset has very h gh producer scores,  nce apply ng log to smoot n t m out before
              // comput ng  nterested n
              (s mclusterW hScore.cluster d, math.log(1.0 + s mclusterW hScore.score).toFloat)
            })
      }

    val result = keepOnlyTopClusters(
      get nterested nD scardScores(
        attachNormal zedScores(
          userClusterPa rsW houtNormal zat on(
            userUserGraph,
            getPrunedEmbedd ngs(
              getNormal zedEmbedd ngs(producerEmbedd ngsW hScore),
              maxClustersFromProducer),
            soc alProofThreshold,
          ))),
      maxClustersPerUserF nalResult,
      ModelVers ons.toKnownForModelVers on(modelVers on)
    )
    result
  }
}
