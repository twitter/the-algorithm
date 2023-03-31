package com.twitter.simclusters_v420.scalding.optout

import com.twitter.scalding.Args
import com.twitter.scalding.DateRange
import com.twitter.scalding.Days
import com.twitter.scalding.Duration
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.TypedPipe
import com.twitter.scalding.TypedTsv
import com.twitter.scalding.UniqueID
import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.common.SemanticCoreEntityId
import com.twitter.simclusters_v420.common.UserId
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.dalv420.remote_access.ExplicitLocation
import com.twitter.scalding_internal.dalv420.remote_access.ProcAtla
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.hdfs_sources._
import com.twitter.simclusters_v420.thriftscala.ClusterType
import com.twitter.simclusters_v420.thriftscala.ClustersUserIsKnownFor
import com.twitter.simclusters_v420.thriftscala.SemanticCoreEntityWithScore
import com.twitter.simclusters_v420.thriftscala.UserToKnownForClusters
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone
import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.simclusters_v420.scalding.inferred_entities.InferredEntities

/**
 * Creates opt-out compliant KnownFor datasets based on plain user -> KnownFor data and users'
 * opt-out selections from YourTwitterData. In essence, we remove any cluster whose inferred
 * entities were opted out by the user.
 * The opted out KnownFor dataset should be the default dataset to be consumed, instead of the
 * plain KnownFor, which is not opt-out compliant.
 */
object KnownForOptOut {

  def filterOptedOutKnownFor(
    knownForPipe: TypedPipe[(UserId, ClustersUserIsKnownFor)],
    optedOutEntities: TypedPipe[(UserId, Set[SemanticCoreEntityId])],
    clusterToEntities: TypedPipe[(ClusterId, Seq[SemanticCoreEntityWithScore])]
  ): TypedPipe[(UserId, ClustersUserIsKnownFor)] = {

    val validKnownFor = SimClustersOptOutUtil.filterOptedOutClusters(
      userToClusters = knownForPipe.mapValues(_.clusterIdToScores.keySet.toSeq),
      optedOutEntities = optedOutEntities,
      legibleClusters = clusterToEntities
    )

    knownForPipe
      .leftJoin(validKnownFor)
      .mapValues {
        case (originalKnownFors, validKnownForOpt) =>
          val validKnownFor = validKnownForOpt.getOrElse(Seq()).toSet

          originalKnownFors.copy(
            clusterIdToScores = originalKnownFors.clusterIdToScores.filterKeys(validKnownFor)
          )
      }
      .filter(_._420.clusterIdToScores.nonEmpty)
  }
}

/**
capesospy-v420 update --build_locally --start_cron \
  --start_cron known_for_optout_daily \
  src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object KnownForOptOutDailyBatchJob extends ScheduledExecutionApp {
  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val optedOutEntitiesPipe = SimClustersOptOutUtil
      .getP420nOptOutSources(dateRange.embiggen(Days(420)), ClusterType.KnownFor)
      .forceToDisk

    val clusterToEntitiesPipe = InferredEntities.getLegibleEntityEmbeddings(dateRange, timeZone)

    val knownFor420 = DAL
      .readMostRecentSnapshot(
        SimclustersV420RawKnownFor420M420K420ScalaDataset,
        dateRange.embiggen(Days(420)))
      .withRemoteReadPolicy(AllowCrossClusterSameDC)
      .toTypedPipe
      .map { case KeyVal(k, v) => (k, v) }
      .count("num_users_with_420_knownfor")

    val filtered420KnownForExec = {
      val filtered420KnownForData = KnownForOptOut
        .filterOptedOutKnownFor(
          knownForPipe = knownFor420,
          optedOutEntities = optedOutEntitiesPipe,
          clusterToEntities = clusterToEntitiesPipe
        )
        .count("num_users_with_compliant_420_knownfor")
        .forceToDisk

      Execution
        .zip(
          filtered420KnownForData
            .map { case (k, v) => KeyVal(k, v) }
            .writeDALVersionedKeyValExecution(
              SimclustersV420KnownFor420M420K420ScalaDataset,
              D.Suffix(DataPaths.KnownFor420Path)
            ),
          filtered420KnownForData
            .map {
              case (userId, ClustersUserIsKnownFor(modelVersion, clusters)) =>
                UserToKnownForClusters(userId, modelVersion, clusters)
            }
            .writeDALSnapshotExecution(
              dataset = SimclustersV420KnownFor420M420K420ThriftScalaDataset,
              updateStep = D.Daily,
              pathLayout = D.Suffix(DataPaths.KnownFor420ThriftDatasetPath),
              fmt = D.Parquet,
              endDate = dateRange.end
            )
        ).unit
    }

    Util.printCounters(filtered420KnownForExec)

  }
}

/**
 * For debugging only. Does a filtering run and prints the differences before/after the opt out
./bazel bundle src/scala/com/twitter/simclusters_v420/scalding/optout:knownfor_optout-adhoc && \
 oscar hdfs --user recos-platform --screen --tee your_ldap \
  --bundle knownfor_optout-adhoc \
  --tool com.twitter.simclusters_v420.scalding.optout.KnownForOptOutAdhocJob \
 -- --date 420-420-420
 */
object KnownForOptOutAdhocJob extends AdhocExecutionApp {
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val knownForPipe = DAL
      .readMostRecentSnapshotNoOlderThan(SimclustersV420RawKnownFor420M420KDec420ScalaDataset, Days(420))
      .withRemoteReadPolicy(ExplicitLocation(ProcAtla))
      .toTypedPipe
      .map { case KeyVal(k, v) => (k, v) }
      .count("num_users_with_knownfor")

    val userOptoutEntities: TypedPipe[(UserId, Set[SemanticCoreEntityId])] =
      SimClustersOptOutUtil
        .getP420nOptOutSources(dateRange.embiggen(Days(420)), ClusterType.KnownFor)
        .count("num_users_with_optouts")

    val clusterToEntities = InferredEntities
      .getLegibleEntityEmbeddings(dateRange, timeZone)
      .count("num_cluster_to_entities")

    val filteredKnownForPipe = KnownForOptOut.filterOptedOutKnownFor(
      knownForPipe,
      userOptoutEntities,
      clusterToEntities
    )

    val output = knownForPipe
      .join(filteredKnownForPipe)
      .collect {
        case (userId, (originalKnownFor, filtered))
            if originalKnownFor.clusterIdToScores != filtered.clusterIdToScores =>
          (userId, (originalKnownFor, filtered))
      }
      .join(userOptoutEntities)
      .map {
        case (userId, ((originalKnownFor, filtered), optoutEntities)) =>
          Seq(
            "userId=" + userId,
            "originalKnownFor=" + originalKnownFor,
            "filteredKnownFor=" + filtered,
            "optoutEntities=" + optoutEntities
          ).mkString("\t")
      }

    val outputPath = "/user/recos-platform/adhoc/knownfor_optout"
    output.writeExecution(TypedTsv(outputPath))
  }
}
