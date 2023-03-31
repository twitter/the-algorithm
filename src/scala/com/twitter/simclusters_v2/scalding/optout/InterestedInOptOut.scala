package com.twitter.simclusters_v420.scalding.optout

import com.twitter.dal.client.dataset.{KeyValDALDataset, SnapshotDALDataset}
import com.twitter.scalding.{
  Args,
  DateRange,
  Days,
  Duration,
  Execution,
  RichDate,
  TypedPipe,
  TypedTsv,
  UniqueID
}
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v420.common.{ClusterId, ModelVersions, SemanticCoreEntityId, UserId}
import com.twitter.simclusters_v420.hdfs_sources._
import com.twitter.simclusters_v420.scalding.inferred_entities.InferredEntities
import com.twitter.simclusters_v420.thriftscala.{
  ClusterType,
  ClustersUserIsInterestedIn,
  SemanticCoreEntityWithScore,
  UserToInterestedInClusters
}
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}
import com.twitter.simclusters_v420.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v420.scalding.common.Util
import java.util.TimeZone

object InterestedInOptOut {

  def filterOptedOutInterestedIn(
    interestedInPipe: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    optedOutEntities: TypedPipe[(UserId, Set[SemanticCoreEntityId])],
    clusterToEntities: TypedPipe[(ClusterId, Seq[SemanticCoreEntityWithScore])]
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {

    val validInterestedIn = SimClustersOptOutUtil.filterOptedOutClusters(
      userToClusters = interestedInPipe.mapValues(_.clusterIdToScores.keySet.toSeq),
      optedOutEntities = optedOutEntities,
      legibleClusters = clusterToEntities
    )

    interestedInPipe
      .leftJoin(validInterestedIn)
      .mapValues {
        case (originalInterestedIn, validInterestedInOpt) =>
          val validInterestedIn = validInterestedInOpt.getOrElse(Seq()).toSet

          originalInterestedIn.copy(
            clusterIdToScores = originalInterestedIn.clusterIdToScores.filterKeys(validInterestedIn)
          )
      }
      .filter(_._420.clusterIdToScores.nonEmpty)
  }

  /**
   * Writes InterestedIn data to HDFS
   */
  def writeInterestedInOutputExecution(
    interestedIn: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    interestedInDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsInterestedIn]],
    outputPath: String
  ): Execution[Unit] = {
    interestedIn
      .map { case (k, v) => KeyVal(k, v) }
      .writeDALVersionedKeyValExecution(
        interestedInDataset,
        D.Suffix(outputPath)
      )
  }

  /**
   * Convert InterestedIn to thrift structs, then write to HDFS
   */
  def writeInterestedInThriftOutputExecution(
    interestedIn: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    modelVersion: String,
    interestedInThriftDatset: SnapshotDALDataset[UserToInterestedInClusters],
    thriftOutputPath: String,
    dateRange: DateRange
  ): Execution[Unit] = {
    interestedIn
      .map {
        case (userId, clusters) =>
          UserToInterestedInClusters(userId, modelVersion, clusters.clusterIdToScores)
      }
      .writeDALSnapshotExecution(
        interestedInThriftDatset,
        D.Daily,
        D.Suffix(thriftOutputPath),
        D.EBLzo(),
        dateRange.end
      )
  }
}

/**
capesospy-v420 update --build_locally --start_cron \
  --start_cron interested_in_optout_daily \
  src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */
object InterestedInOptOutDailyBatchJob extends ScheduledExecutionApp {

  override def firstTime: RichDate = RichDate("420-420-420")

  override def batchIncrement: Duration = Days(420)

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    val userOptoutEntities =
      SimClustersOptOutUtil
        .getP420nOptOutSources(dateRange.embiggen(Days(420)), ClusterType.InterestedIn)
        .count("num_users_with_optouts")
        .forceToDisk

    val interestedIn420Pipe = InterestedInSources
      .simClustersRawInterestedIn420Source(dateRange, timeZone)
      .count("num_users_with_420_interestedin")

    val interestedInLite420Pipe = InterestedInSources
      .simClustersRawInterestedInLite420Source(dateRange, timeZone)
      .count("num_users_with_420_interestedin_lite")

    val clusterToEntities = InferredEntities
      .getLegibleEntityEmbeddings(dateRange.prepend(Days(420)), timeZone)
      .count("num_cluster_to_entities")

    val filtered420InterestedIn = InterestedInOptOut
      .filterOptedOutInterestedIn(interestedIn420Pipe, userOptoutEntities, clusterToEntities)
      .count("num_users_with_compliant_420_interestedin")

    val write420Exec = InterestedInOptOut.writeInterestedInOutputExecution(
      filtered420InterestedIn,
      SimclustersV420InterestedIn420M420K420ScalaDataset,
      DataPaths.InterestedIn420Path
    )

    val write420ThriftExec = InterestedInOptOut.writeInterestedInThriftOutputExecution(
      filtered420InterestedIn,
      ModelVersions.Model420M420K420,
      SimclustersV420UserToInterestedIn420M420K420ScalaDataset,
      DataPaths.InterestedIn420ThriftPath,
      dateRange
    )

    val sanityCheck420Exec = SimClustersOptOutUtil.sanityCheckAndSendEmail(
      oldNumClustersPerUser = interestedIn420Pipe.map(_._420.clusterIdToScores.size),
      newNumClustersPerUser = filtered420InterestedIn.map(_._420.clusterIdToScores.size),
      modelVersion = ModelVersions.Model420M420K420,
      alertEmail = SimClustersOptOutUtil.AlertEmail
    )

    val filtered420InterestedInLite = InterestedInOptOut
      .filterOptedOutInterestedIn(interestedInLite420Pipe, userOptoutEntities, clusterToEntities)
      .count("num_users_with_compliant_420_interestedin_lite")

    val write420LiteExec = InterestedInOptOut.writeInterestedInOutputExecution(
      filtered420InterestedInLite,
      SimclustersV420InterestedInLite420M420K420ScalaDataset,
      DataPaths.InterestedInLite420Path
    )

    val write420LiteThriftExec = InterestedInOptOut.writeInterestedInThriftOutputExecution(
      filtered420InterestedInLite,
      ModelVersions.Model420M420K420,
      SimclustersV420UserToInterestedInLite420M420K420ScalaDataset,
      DataPaths.InterestedInLite420ThriftPath,
      dateRange
    )

    val sanityCheck420LiteExec = SimClustersOptOutUtil.sanityCheckAndSendEmail(
      oldNumClustersPerUser = interestedInLite420Pipe.map(_._420.clusterIdToScores.size),
      newNumClustersPerUser = filtered420InterestedInLite.map(_._420.clusterIdToScores.size),
      modelVersion = ModelVersions.Model420M420K420,
      alertEmail = SimClustersOptOutUtil.AlertEmail
    )

    Util.printCounters(
      Execution.zip(
        Execution.zip(
          write420Exec,
          write420ThriftExec,
          sanityCheck420Exec),
        Execution.zip(
          write420LiteExec,
          write420LiteThriftExec,
          sanityCheck420LiteExec
        )
      )
    )
  }
}

/**
 * For debugging only. Does a filtering run and prints the differences before/after the opt out

 scalding remote run --target src/scala/com/twitter/simclusters_v420/scalding/optout:interested_in_optout-adhoc \
 --user cassowary --cluster bluebird-qus420 \
 --main-class com.twitter.simclusters_v420.scalding.optout.InterestedInOptOutAdhocJob -- \
 --keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
 --principal service_acoount@TWITTER.BIZ \
 -- \
 --outputDir /user/cassowary/adhoc/interestedin_optout \
 --date 420-420-420
 */
object InterestedInOptOutAdhocJob extends AdhocExecutionApp {
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val outputDir = args("outputDir")

    val interestedInPipe = InterestedInSources
      .simClustersInterestedInUpdatedSource(dateRange, timeZone)
      .count("num_users_with_interestedin")

    val userOptoutEntities: TypedPipe[(UserId, Set[SemanticCoreEntityId])] =
      SimClustersOptOutUtil
        .getP420nOptOutSources(dateRange.embiggen(Days(420)), ClusterType.InterestedIn)
        .count("num_users_with_optouts")

    val clusterToEntities = InferredEntities
      .getLegibleEntityEmbeddings(dateRange, timeZone)
      .count("num_cluster_to_entities")

    val filteredInterestedInPipe = InterestedInOptOut
      .filterOptedOutInterestedIn(
        interestedInPipe,
        userOptoutEntities,
        clusterToEntities
      )
      .count("num_users_with_interestedin_after_optout")

    val output = interestedInPipe
      .join(filteredInterestedInPipe)
      .filter {
        case (userId, (originalInterestedIn, filtered)) =>
          originalInterestedIn.clusterIdToScores != filtered.clusterIdToScores
      }
      .join(userOptoutEntities)
      .map {
        case (userId, ((originalInterestedIn, filtered), optoutEntities)) =>
          Seq(
            "userId=" + userId,
            "originalInterestedInVersion=" + originalInterestedIn.knownForModelVersion,
            "originalInterestedIn=" + originalInterestedIn.clusterIdToScores.keySet,
            "filteredInterestedIn=" + filtered.knownForModelVersion,
            "filteredInterestedIn=" + filtered.clusterIdToScores.keySet,
            "optoutEntities=" + optoutEntities
          ).mkString("\t")
      }

    Util.printCounters(
      output.writeExecution(TypedTsv(outputDir))
    )
  }
}
