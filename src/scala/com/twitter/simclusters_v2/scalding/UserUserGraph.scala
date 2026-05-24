package com.twitter.simclusters_v2.scalding

import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite.{D, WriteExtension}
import com.twitter.scalding_internal.job.analytics_batch.{
  AnalyticsBatchExecution,
  AnalyticsBatchExecutionArgs,
  BatchDescription,
  BatchFirstTime,
  BatchIncrement,
  TwitterScheduledExecutionApp
}
import com.twitter.simclusters_v2.scalding.common.Util
import com.twitter.simclusters_v2.hdfs_sources.{
  UserAndNeighborsFixedPathSource,
  UserUserGraphScalaDataset
}
import com.twitter.simclusters_v2.thriftscala.{NeighborWithWeights, UserAndNeighbors}
import com.twitter.wtf.scalding.jobs.common.AdhocExecutionApp
import java.util.TimeZone

/**
 * This is a scheduled version of the user_user_normalized_graph dataset generation job.
 *
 * The key difference in this implementation is that we donot read the ProducerNormsAndCounts dataset.
 * So we no longer store the following producer normalized scores for the edges in the NeigborWithWeights thrift:
 * followScoreNormalizedByNeighborFollowersL2, favScoreHalfLife100DaysNormalizedByNeighborFaversL2 and logFavScoreL2Normalized
 *
 */
object UserUserGraph {

  def getNeighborWithWeights(
    inputEdge: Edge
  ): NeighborWithWeights = {
    val logFavScore = UserUserNormalizedGraph.logTransformation(inputEdge.favWeight)
    NeighborWithWeights(
      neighborId = inputEdge.destId,
      isFollowed = Some(inputEdge.isFollowEdge),
      favScoreHalfLife100Days = Some(inputEdge.favWeight),
      logFavScore = Some(logFavScore),
    )
  }

  def addWeightsAndAdjListify(
    input: TypedPipe[Edge],
    maxNeighborsPerUser: Int
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[UserAndNeighbors] = {
    val numUsersNeedingNeighborTruncation = Stat("num_users_needing_neighbor_truncation")
    val numEdgesAfterTruncation = Stat("num_edges_after_truncation")
    val numEdgesBeforeTruncation = Stat("num_edges_before_truncation")
    val numFollowEdgesBeforeTruncation = Stat("num_follow_edges_before_truncation")
    val numFavEdgesBeforeTruncation = Stat("num_fav_edges_before_truncation")
    val numFollowEdgesAfterTruncation = Stat("num_follow_edges_after_truncation")
    val numFavEdgesAfterTruncation = Stat("num_fav_edges_after_truncation")
    val numRecordsInOutputGraph = Stat("num_records_in_output_graph")

    input
      .map { edge =>
        numEdgesBeforeTruncation.inc()
        if (edge.isFollowEdge) numFollowEdgesBeforeTruncation.inc()
        if (edge.favWeight > 0) numFavEdgesBeforeTruncation.inc()
        (edge.srcId, getNeighborWithWeights(edge))
      }
      .group
      //      .withReducers(10000)
      .sortedReverseTake(maxNeighborsPerUser)(Ordering.by { x: NeighborWithWeights =>
        x.favScoreHalfLife100Days.getOrElse(0.0)
      })
      .map {
        case (srcId, neighborList) =>
          if (neighborList.size >= maxNeighborsPerUser) numUsersNeedingNeighborTruncation.inc()
          neighborList.foreach { neighbor =>
            numEdgesAfterTruncation.inc()
            if (neighbor.favScoreHalfLife100Days.exists(_ > 0)) numFavEdgesAfterTruncation.inc()
            if (neighbor.isFollowed.contains(true)) numFollowEdgesAfterTruncation.inc()
          }
          numRecordsInOutputGraph.inc()
          UserAndNeighbors(srcId, neighborList)
      }
  }

  def run(
    followEdges: TypedPipe[(Long, Long)],
    favEdges: TypedPipe[(Long, Long, Double)],
    maxNeighborsPerUser: Int
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[UserAndNeighbors] = {
    val combined = UserUserNormalizedGraph.combineFollowAndFav(followEdges, favEdges)
    addWeightsAndAdjListify(
      combined,
      maxNeighborsPerUser
    )
  }
}

/**
 *
 * capesospy-v2 update --build_locally --start_cron user_user_follow_fav_graph \
 * src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml
 */

object UserUserGraphBatch extends TwitterScheduledExecutionApp {
  private val firstTime: String = "2021-04-24"
  implicit val tz = DateOps.UTC
  implicit val parser = DateParser.default
  private val batchIncrement: Duration = Days(2)
  private val halfLifeInDaysForFavScore = 100

  private val outputPath: String = "/user/cassowary/processed/user_user_graph"

  private val execArgs = AnalyticsBatchExecutionArgs(
    batchDesc = BatchDescription(this.getClass.getName.replace("$", "")),
    firstTime = BatchFirstTime(RichDate(firstTime)),
    lastTime = None,
    batchIncrement = BatchIncrement(batchIncrement)
  )

  override def scheduledJob: Execution[Unit] = AnalyticsBatchExecution(execArgs) {
    implicit dateRange =>
      Execution.withId { implicit uniqueId =>
        Execution.withArgs { args =>
          val maxNeighborsPerUser = args.int("maxNeighborsPerUser", 2000)

          Util.printCounters(
            UserUserGraph
              .run(
                UserUserNormalizedGraph.getFollowEdges,
                UserUserNormalizedGraph.getFavEdges(halfLifeInDaysForFavScore),
                maxNeighborsPerUser
              )
              .writeDALSnapshotExecution(
                UserUserGraphScalaDataset,
                D.Daily,
                D.Suffix(outputPath),
                D.EBLzo(),
                dateRange.end)
          )
        }
      }
  }
}

/**
./bazel bundle src/scala/com/twitter/simclusters_v2/scalding:user_user_graph-adhoc
scalding remote run \
--user cassowary \
--keytab /var/lib/tss/keys/fluffy/keytabs/client/cassowary.keytab \
--principal service_acoount@TWITTER.BIZ \
--cluster bluebird-qus1 \
--main-class com.twitter.simclusters_v2.scalding.UserUserGraphAdhoc \
--target src/scala/com/twitter/simclusters_v2/scalding:user_user_graph-adhoc \
-- --date 2021-04-24 --outputDir "/user/cassowary/adhoc/user_user_graph_adhoc"
 */
object UserUserGraphAdhoc extends AdhocExecutionApp {
  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {
    val maxNeighborsPerUser = args.int("maxNeighborsPerUser", 2000)
    val halfLifeInDaysForFavScore = 100
    val outputDir = args("outputDir")
    val userAndNeighbors =
      UserUserGraph
        .run(
          UserUserNormalizedGraph.getFollowEdges,
          UserUserNormalizedGraph.getFavEdges(halfLifeInDaysForFavScore),
          maxNeighborsPerUser)

    Execution
      .zip(
        userAndNeighbors.writeExecution(UserAndNeighborsFixedPathSource(outputDir)),
        userAndNeighbors.writeExecution(TypedTsv(outputDir + "_tsv"))).unit
  }
}
