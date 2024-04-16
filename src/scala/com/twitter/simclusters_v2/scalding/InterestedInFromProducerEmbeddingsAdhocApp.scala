package com.tw ter.s mclusters_v2.scald ng

 mport com.tw ter.dal.cl ent.dataset.KeyValDALDataset
 mport com.tw ter.scald ng.Execut on
 mport com.tw ter.scald ng.TypedTsv
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng_ nternal.dalv2.DAL
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.Expl c Locat on
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.ProcAtla
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.s mclusters_v2.common.User d
 mport com.tw ter.s mclusters_v2.hdfs_s ces.ProducerEmbedd ngS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.AdhocKeyValS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.DataS ces
 mport com.tw ter.s mclusters_v2.hdfs_s ces.S mclustersV2 nterested nFromProducerEmbedd ngs20M145KUpdatedScalaDataset
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserAndNe ghborsF xedPathS ce
 mport com.tw ter.s mclusters_v2.hdfs_s ces.UserUserNormal zedGraphScalaDataset
 mport com.tw ter.s mclusters_v2.scald ng.common.Ut l
 mport com.tw ter.s mclusters_v2.thr ftscala.ClustersUser s nterested n
 mport com.tw ter.s mclusters_v2.thr ftscala.Embedd ngType
 mport com.tw ter.s mclusters_v2.thr ftscala.S mClusterW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.TopS mClustersW hScore
 mport com.tw ter.s mclusters_v2.thr ftscala.UserTo nterested nClusterScores
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone
 mport scala.ut l.Random

/**
 * T  f le  mple nts t  job for comput ng users'  nterested n vector from t  producerEmbedd ngs data set.
 *
 *   reads t  UserUserNormal zedGraphScalaDataset to get user-user follow + fav graph, and t n
 * based on t  producerEmbedd ng clusters of each follo d/faved user,   calculate how much a user  s
 *  nterested n a cluster. To compute t  engage nt and determ ne t  clusters for t  user,   reuse
 * t  funct ons def ned  n  nterested nKnownFor.
 *
 * Us ng producerEmbedd ngs  nstead of knownFor to obta n  nterested n  ncreases t  coverage (espec ally
 * for  d um and l ght users) and also t  dens y of t  cluster embedd ngs for t  user.
 */
/**
 * Adhoc job to generate t   nterested n from producer embedd ngs for t  model vers on 20M145KUpdated
 *
 scald ng remote run \
  --target src/scala/com/tw ter/s mclusters_v2/scald ng: nterested_ n_from_producer_embedd ngs \
  --ma n-class com.tw ter.s mclusters_v2.scald ng. nterested nFromProducerEmbedd ngsAdhocApp \
  --user cassowary --cluster blueb rd-qus1 \
  --keytab /var/l b/tss/keys/fluffy/keytabs/cl ent/cassowary.keytab \
  --pr nc pal serv ce_acoount@TW TTER.B Z \
  -- \
  --outputD r /gcs/user/cassowary/adhoc/ nterested_ n_from_prod_embedd ngs/ \
  --date 2020-08-25 --typedTsv true
 */
object  nterested nFromProducerEmbedd ngsAdhocApp extends AdhocExecut onApp {
  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    val outputD r = args("outputD r")
    val  nputGraph = args.opt onal("graph nputD r") match {
      case So ( nputD r) => TypedP pe.from(UserAndNe ghborsF xedPathS ce( nputD r))
      case None =>
        DAL
          .readMostRecentSnapshotNoOlderThan(UserUserNormal zedGraphScalaDataset, Days(30))
          .toTypedP pe
    }
    val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
    val maxClustersPerUserF nalResult = args. nt("max nterested nClustersPerUser", 50)
    val maxClustersFromProducer = args. nt("maxClustersPerProducer", 25)
    val typedTsvTag = args.boolean("typedTsv")

    val embedd ngType =
      Embedd ngType.ProducerFavBasedSemant cCoreEnt y
    val modelVers on = ModelVers ons.Model20M145KUpdated
    val producerEmbedd ngs = ProducerEmbedd ngS ces
      .producerEmbedd ngS ceLegacy(embedd ngType, ModelVers ons.toModelVers on(modelVers on))(
        dateRange.emb ggen(Days(7)))

     mport  nterested nFromProducerEmbedd ngsBatchApp._

    val numProducerMapp ngs = Stat("num_producer_embedd ngs_total")
    val numProducersW hLargeClusterMapp ngs = Stat(
      "num_producers_w h_more_clusters_than_threshold")
    val numProducersW hSmallClusterMapp ngs = Stat(
      "num_producers_w h_clusters_less_than_threshold")
    val totalClustersCoverageProducerEmbedd ngs = Stat("num_clusters_total_producer_embedd ngs")

    val producerEmbedd ngsW hScore = producerEmbedd ngs.map {
      case (user d: Long, topS mClusters: TopS mClustersW hScore) =>
        (
          user d,
          topS mClusters.topClusters.toArray
            .map {
              case (s mCluster: S mClusterW hScore) =>
                (s mCluster.cluster d, s mCluster.score.toFloat)
            }
        )
    }
    val producerEmbedd ngsPruned = producerEmbedd ngsW hScore.map {
      case (producer d, clusterArray) =>
        numProducerMapp ngs. nc()
        val clusterS ze = clusterArray.s ze
        totalClustersCoverageProducerEmbedd ngs. ncBy(clusterS ze)
        val prunedL st =  f (clusterS ze > maxClustersFromProducer) {
          numProducersW hLargeClusterMapp ngs. nc()
          clusterArray
            .sortBy {
              case (_, knownForScore) => -knownForScore
            }.take(maxClustersFromProducer)
        } else {
          numProducersW hSmallClusterMapp ngs. nc()
          clusterArray
        }
        (producer d, prunedL st)
    }

    val result =  nterested nFromKnownFor
      .run(
         nputGraph,
        producerEmbedd ngsPruned,
        soc alProofThreshold,
        maxClustersPerUserF nalResult,
        modelVers on
      )

    val resultW houtSoc al = get nterested nD scardSoc al(result)

     f (typedTsvTag) {
      Ut l.pr ntCounters(
        resultW houtSoc al
          .map {
            case (user d: Long, clusters: ClustersUser s nterested n) =>
              (
                user d,
                clusters.cluster dToScores.keys.toStr ng()
              )
          }
          .wr eExecut on(
            TypedTsv(outputD r)
          )
      )
    } else {
      Ut l.pr ntCounters(
        resultW houtSoc al
          .wr eExecut on(
            AdhocKeyValS ces. nterested nS ce(outputD r)
          )
      )
    }
  }
}

/**
 * Product on job for comput ng  nterested n data set from t  producer embedd ngs for t  model vers on 20M145KUpdated.
 *   wr es t  data set  n KeyVal format to produce a MH DAL data set.
 *
 * To deploy t  job:
 *
 * capesospy-v2 update --bu ld_locally --start_cron
 * --start_cron  nterested_ n_from_producer_embedd ngs
 * src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object  nterested nFromProducerEmbedd ngsBatchApp extends Sc duledExecut onApp {
  overr de val f rstT  : R chDate = R chDate("2019-11-01")

  overr de val batch ncre nt: Durat on = Days(7)

  def getPrunedEmbedd ngs(
    producerEmbedd ngs: TypedP pe[(Long, TopS mClustersW hScore)],
    maxClustersFromProducer:  nt
  ): TypedP pe[(Long, TopS mClustersW hScore)] = {
    producerEmbedd ngs.map {
      case (producer d, producerClusters) =>
        val prunedProducerClusters =
          producerClusters.topClusters
            .sortBy {
              case s mCluster => -s mCluster.score.toFloat
            }.take(maxClustersFromProducer)
        (producer d, TopS mClustersW hScore(prunedProducerClusters, producerClusters.modelVers on))
    }
  }

  def get nterested nD scardSoc al(
     nterested nFromProducersResult: TypedP pe[(User d, ClustersUser s nterested n)]
  ): TypedP pe[(User d, ClustersUser s nterested n)] = {
     nterested nFromProducersResult.map {
      case (src d, fullClusterL st) =>
        val fullClusterL stW houtSoc al = fullClusterL st.cluster dToScores.map {
          case (cluster d, clusterDeta ls) =>
            val clusterDeta lsW houtSoc al = UserTo nterested nClusterScores(
              followScore = clusterDeta ls.followScore,
              followScoreClusterNormal zedOnly = clusterDeta ls.followScoreClusterNormal zedOnly,
              followScoreProducerNormal zedOnly = clusterDeta ls.followScoreProducerNormal zedOnly,
              followScoreClusterAndProducerNormal zed =
                clusterDeta ls.followScoreClusterAndProducerNormal zed,
              favScore = clusterDeta ls.favScore,
              favScoreClusterNormal zedOnly = clusterDeta ls.favScoreClusterNormal zedOnly,
              favScoreProducerNormal zedOnly = clusterDeta ls.favScoreProducerNormal zedOnly,
              favScoreClusterAndProducerNormal zed =
                clusterDeta ls.favScoreClusterAndProducerNormal zed,
              // Soc al proof  s currently not be ng used anyw re else,  nce be ng d scarded to reduce space for t  dataset
              usersBe ngFollo d = None,
              usersThat reFaved = None,
              numUsers nterested nT ClusterUpperBound =
                clusterDeta ls.numUsers nterested nT ClusterUpperBound,
              logFavScore = clusterDeta ls.logFavScore,
              logFavScoreClusterNormal zedOnly = clusterDeta ls.logFavScoreClusterNormal zedOnly,
              // Counts of t  soc al proof are ma nta ned
              numUsersBe ngFollo d = So (clusterDeta ls.usersBe ngFollo d.getOrElse(N l).s ze),
              numUsersThat reFaved = So (clusterDeta ls.usersThat reFaved.getOrElse(N l).s ze)
            )
            (cluster d, clusterDeta lsW houtSoc al)
        }
        (
          src d,
          ClustersUser s nterested n(
            fullClusterL st.knownForModelVers on,
            fullClusterL stW houtSoc al))
    }
  }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    // nput args for t  run
    val soc alProofThreshold = args. nt("soc alProofThreshold", 2)
    val maxClustersFromProducer = args. nt("maxClustersPerProducer", 25)
    val maxClustersPerUserF nalResult = args. nt("max nterested nClustersPerUser", 50)

    //Path var ables
    val modelVers onUpdated = ModelVers ons.toModelVers on(ModelVers ons.Model20M145KUpdated)
    val rootPath: Str ng = s"/user/cassowary/manhattan_sequence_f les"
    val  nterested nFromProducersPath =
      rootPath + "/ nterested_ n_from_producer_embedd ngs/" + modelVers onUpdated

    // nput adjacency l st and producer embedd ngs
    val userUserNormalGraph =
      DataS ces.userUserNormal zedGraphS ce(dateRange.prepend(Days(7))).forceToD sk
    val outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUser s nterested n]] =
      S mclustersV2 nterested nFromProducerEmbedd ngs20M145KUpdatedScalaDataset
    val producerEmbedd ngs = ProducerEmbedd ngS ces
      .producerEmbedd ngS ceLegacy(
        Embedd ngType.ProducerFavBasedSemant cCoreEnt y,
        modelVers onUpdated)(dateRange.emb ggen(Days(7)))

    val producerEmbedd ngsPruned = getPrunedEmbedd ngs(producerEmbedd ngs, maxClustersFromProducer)
    val producerEmbedd ngsW hScore = producerEmbedd ngsPruned.map {
      case (user d: Long, topS mClusters: TopS mClustersW hScore) =>
        (
          user d,
          topS mClusters.topClusters.toArray
            .map {
              case (s mCluster: S mClusterW hScore) =>
                (s mCluster.cluster d, s mCluster.score.toFloat)
            }
        )
    }

    val  nterested nFromProducersResult =
       nterested nFromKnownFor.run(
        userUserNormalGraph,
        producerEmbedd ngsW hScore,
        soc alProofThreshold,
        maxClustersPerUserF nalResult,
        modelVers onUpdated.toStr ng
      )

    val  nterested nFromProducersW houtSoc al =
      get nterested nD scardSoc al( nterested nFromProducersResult)

    val wr eKeyValResultExec =  nterested nFromProducersW houtSoc al
      .map { case (user d, clusters) => KeyVal(user d, clusters) }
      .wr eDALVers onedKeyValExecut on(
        outputKVDataset,
        D.Suff x( nterested nFromProducersPath)
      )
    wr eKeyValResultExec
  }

}
