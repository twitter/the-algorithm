package com.tw ter.s mclusters_v2.scald ng.embedd ng

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter. rm .cand date.thr ftscala.Cand date
 mport com.tw ter. rm .cand date.thr ftscala.Cand dates
 mport com.tw ter.scald ng._
 mport com.tw ter.scald ng.commons.s ce.Vers onedKeyValS ce
 mport com.tw ter.scald ng_ nternal.dalv2.DALWr e._
 mport com.tw ter.scald ng_ nternal.dalv2._
 mport com.tw ter.scald ng_ nternal.dalv2.remote_access.AllowCrossClusterSa DC
 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal
 mport com.tw ter.s mclusters_v2.common.Cos neS m lar yUt l
 mport com.tw ter.s mclusters_v2.hdfs_s ces._
 mport com.tw ter.s mclusters_v2.thr ftscala._
 mport com.tw ter.wtf.scald ng.jobs.common.AdhocExecut onApp
 mport com.tw ter.wtf.scald ng.jobs.common.Sc duledExecut onApp
 mport java.ut l.T  Zone

/**
capesospy-v2 update --bu ld_locally --start_cron \
  --start_cron s m lar_users_by_s mclusters_embedd ngs_job \
  src/scala/com/tw ter/s mclusters_v2/capesos_conf g/atla_proc3.yaml
 */
object S m larUsersByS mClustersEmbedd ngBatchApp extends Sc duledExecut onApp {

  overr de val f rstT  : R chDate = R chDate("2019-07-10")

  overr de val batch ncre nt: Durat on = Days(7)

  pr vate val outputByFav =
    "/user/cassowary/manhattan_sequence_f les/s m lar_users_by_s mclusters_embedd ngs/by_fav"
  pr vate val outputByFollow =
    "/user/cassowary/manhattan_sequence_f les/s m lar_users_by_s mclusters_embedd ngs/by_follow"

  pr vate  mpl c  val value nj: CompactScalaCodec[Cand dates] = CompactScalaCodec(Cand dates)

  pr vate val topClusterEmbedd ngsByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKS mclusterEmbedd ngsByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { clusterScorePa r => clusterScorePa r.key -> clusterScorePa r.value }

  pr vate val topProducersForClusterEmbedd ngByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      S mclusterEmbedd ngTopKProducersByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { producerScoresPa r => producerScoresPa r.key -> producerScoresPa r.value }

  pr vate val topClusterEmbedd ngsByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKS mclusterEmbedd ngsByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { clusterScorePa r => clusterScorePa r.key -> clusterScorePa r.value }

  pr vate val topProducersForClusterEmbedd ngByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      S mclusterEmbedd ngTopKProducersByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { producerScoresPa r => producerScoresPa r.key -> producerScoresPa r.value }

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    Execut on
      .z p(
        S m larUsersByS mClustersEmbedd ng
          .getTopUsersRelatedToUser(
            topClusterEmbedd ngsByFavScore,
            topProducersForClusterEmbedd ngByFavScore
          )
          .map { case (key, value) => KeyVal(key, value) }
          .wr eDALVers onedKeyValExecut on(
            S m larUsersByFavBasedProducerEmbedd ngScalaDataset,
            D.Suff x(outputByFav)
          ),
        S m larUsersByS mClustersEmbedd ng
          .getTopUsersRelatedToUser(
            topClusterEmbedd ngsByFollowScore,
            topProducersForClusterEmbedd ngByFollowScore
          )
          .map { case (key, value) => KeyVal(key, value) }
          .wr eDALVers onedKeyValExecut on(
            S m larUsersByFollowBasedProducerEmbedd ngScalaDataset,
            D.Suff x(outputByFollow)
          )
      ).un 
  }
}

/**
 * Adhoc job to calculate producer's s mcluster embedd ngs, wh ch essent ally ass gns  nterested n
 * S mClusters to each producer, regardless of w t r t  producer has a knownFor ass gn nt.
 *
./bazel bundle src/scala/com/tw ter/s mclusters_v2/scald ng/embedd ng:s m lar_users_by_s mclusters_embedd ngs-adhoc && \
  oscar hdfs --user recos-platform --screen --tee s m lar_users_by_s mclusters_embedd ngs --bundle s m lar_users_by_s mclusters_embedd ngs-adhoc \
  --tool com.tw ter.s mclusters_v2.scald ng.embedd ng.S m larUsersByS mClustersEmbedd ngAdhocApp \
  -- --date 2019-07-10T00 2019-07-10T23
 */
object S m larUsersByS mClustersEmbedd ngAdhocApp extends AdhocExecut onApp {

  pr vate val outputByFav =
    "/user/recos-platform/adhoc/s m lar_users_by_s mclusters_embedd ngs/by_fav"
  pr vate val outputByFollow =
    "/user/recos-platform/adhoc/s m lar_users_by_s mclusters_embedd ngs/by_follow"

  pr vate val topClusterEmbedd ngsByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKS mclusterEmbedd ngsByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { clusterScorePa r => clusterScorePa r.key -> clusterScorePa r.value }

  pr vate val topProducersForClusterEmbedd ngByFavScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      S mclusterEmbedd ngTopKProducersByFavScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { producerScoresPa r => producerScoresPa r.key -> producerScoresPa r.value }

  pr vate val topClusterEmbedd ngsByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      ProducerTopKS mclusterEmbedd ngsByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { clusterScorePa r => clusterScorePa r.key -> clusterScorePa r.value }

  pr vate val topProducersForClusterEmbedd ngByFollowScore = DAL
    .readMostRecentSnapshotNoOlderThan(
      S mclusterEmbedd ngTopKProducersByFollowScoreUpdatedScalaDataset,
      Days(14)
    )
    .w hRemoteReadPol cy(AllowCrossClusterSa DC)
    .toTypedP pe
    .map { producerScoresPa r => producerScoresPa r.key -> producerScoresPa r.value }

   mpl c  val cand dates nj: CompactScalaCodec[Cand dates] = CompactScalaCodec(Cand dates)

  overr de def runOnDateRange(
    args: Args
  )(
     mpl c  dateRange: DateRange,
    t  Zone: T  Zone,
    un que D: Un que D
  ): Execut on[Un ] = {

    Execut on
      .z p(
        S m larUsersByS mClustersEmbedd ng
          .getTopUsersRelatedToUser(
            topClusterEmbedd ngsByFavScore,
            topProducersForClusterEmbedd ngByFavScore).wr eExecut on(
            Vers onedKeyValS ce[Long, Cand dates](outputByFav))
          .getCounters
          .flatMap {
            case (_, counters) =>
              counters.toMap.toSeq
                .sortBy(e => (e._1.group, e._1.counter))
                .foreach {
                  case (statKey, value) =>
                    pr ntln(s"${statKey.group}\t${statKey.counter}\t$value")
                }
              Execut on.un 
          },
        S m larUsersByS mClustersEmbedd ng
          .getTopUsersRelatedToUser(
            topClusterEmbedd ngsByFollowScore,
            topProducersForClusterEmbedd ngByFollowScore).wr eExecut on(
            Vers onedKeyValS ce[Long, Cand dates](outputByFollow))
          .getCounters
          .flatMap {
            case (_, counters) =>
              counters.toMap.toSeq
                .sortBy(e => (e._1.group, e._1.counter))
                .foreach {
                  case (statKey, value) =>
                    pr ntln(s"${statKey.group}\t${statKey.counter}\t$value")
                }
              Execut on.un 
          }
      ).un 
  }
}

object S m larUsersByS mClustersEmbedd ng {
  pr vate val maxUsersPerCluster = 300
  pr vate val maxClustersPerUser = 50
  pr vate val topK = 100

  def getTopUsersRelatedToUser(
    clusterScores: TypedP pe[(Long, TopS mClustersW hScore)],
    producerScores: TypedP pe[(Pers stedFullCluster d, TopProducersW hScore)]
  )(
     mpl c  un que D: Un que D
  ): TypedP pe[(Long, Cand dates)] = {

    val numUserUserPa r = Stat("num_user_producer_pa rs")
    val numUserClusterPa r = Stat("num_user_cluster_pa rs")
    val numClusterProducerPa r = Stat("num_cluster_producer_pa rs")

    val clusterToUserMap =
      clusterScores.flatMap {
        case (user d, topS mClustersW hScore) =>
          val targetUserClusters =
            topS mClustersW hScore.topClusters.sortBy(-_.score).take(maxClustersPerUser)

          targetUserClusters.map { s mClusterW hScore =>
            numUserClusterPa r. nc()
            s mClusterW hScore.cluster d -> user d
          }
      }

    val clusterToProducerMap = producerScores.flatMap {
      case (pers stedFullCluster d, topProducersW hScore) =>
        numClusterProducerPa r. nc()
        val targetProducers = topProducersW hScore.topProducers
          .sortBy(-_.score)
          .take(maxUsersPerCluster)
        targetProducers.map { topProducerW hScore =>
          pers stedFullCluster d.cluster d -> topProducerW hScore.user d
        }
    }

     mpl c  val  nt nject:  nt => Array[Byte] =  nject on. nt2B gEnd an.toFunct on

    val userToProducerMap =
      clusterToUserMap.group
        .sketch(2000)
        .jo n(clusterToProducerMap.group)
        .values
        .d st nct
        .collect({
          //f lter self-pa r
          case userPa r  f userPa r._1 != userPa r._2 =>
            numUserUserPa r. nc()
            userPa r
        })

    val userEmbedd ngsAllGrouped = clusterScores.map {
      case (user d, topS mClustersW hScore) =>
        val targetUserClusters =
          topS mClustersW hScore.topClusters.sortBy(-_.score).take(maxClustersPerUser)
        val embedd ng = targetUserClusters.map { s mClustersW hScore =>
          s mClustersW hScore.cluster d -> s mClustersW hScore.score
        }.toMap
        val embedd ngNormal zed = Cos neS m lar yUt l.normal ze(embedd ng)
        user d -> embedd ngNormal zed
    }.forceToD sk

    val userToProducerMapJo nW hEmbedd ng =
      userToProducerMap
        .jo n(userEmbedd ngsAllGrouped)
        .map {
          case (user, (producer, userEmbedd ng)) =>
            producer -> (user, userEmbedd ng)
        }
        .jo n(userEmbedd ngsAllGrouped)
        .map {
          case (producer, ((user, userEmbedd ng), producerEmbedd ng)) =>
            user -> (producer, Cos neS m lar yUt l.dotProduct(userEmbedd ng, producerEmbedd ng))
        }
        .group
        .sortW hTake(topK)((a, b) => a._2 > b._2)
        .map {
          case (user d, cand datesL st) =>
            val cand datesSeq = cand datesL st
              .map {
                case (cand date d, score) => Cand date(cand date d, score)
              }
            user d -> Cand dates(user d, cand datesSeq)
        }

    userToProducerMapJo nW hEmbedd ng
  }

}
