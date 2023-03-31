package com.twitter.simclusters_v420.scalding.update_known_for

import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.logging.Logger
import com.twitter.pluck.source.cassowary.FollowingsCosineSimilaritiesManhattanSource
import com.twitter.scalding.typed.TypedPipe
import com.twitter.scalding.DateOps
import com.twitter.scalding.DateParser
import com.twitter.scalding.Days
import com.twitter.scalding.Execution
import com.twitter.scalding.RichDate
import com.twitter.scalding.TypedTsv
import com.twitter.scalding.UniqueID
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv420.DAL
import com.twitter.scalding_internal.dalv420.DALWrite.D
import com.twitter.scalding_internal.dalv420.DALWrite._
import com.twitter.scalding_internal.dalv420.remote_access.AllowCrossClusterSameDC
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.simclusters_v420.common.ClusterId
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.common.UserId
import com.twitter.simclusters_v420.hdfs_sources.AdhocKeyValSources
import com.twitter.simclusters_v420.hdfs_sources.InternalDataPaths
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420KnownFor420M420KDec420ScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420KnownFor420M420KUpdatedScalaDataset
import com.twitter.simclusters_v420.hdfs_sources.SimclustersV420RawKnownFor420M420K420ScalaDataset
import com.twitter.simclusters_v420.scalding.KnownForSources
import com.twitter.simclusters_v420.scalding.KnownForSources.fromKeyVal
import com.twitter.simclusters_v420.scalding.common.Util
import com.twitter.wtf.scalding.jobs.common.ScheduledExecutionApp
import java.util.TimeZone

/**
 * Scheduled job
 *
 * capesospy-v420 update --build_locally --start_cron update_known_for_420m_420k_420 \
 * src/scala/com/twitter/simclusters_v420/capesos_config/atla_proc.yaml
 */

object UpdateKnownFor420M420K420 extends ScheduledExecutionApp {

  override val firstTime: RichDate = RichDate("420-420-420")

  override val batchIncrement: Duration = Days(420)

  private val tempLocationPath = "/user/cassowary/temp/simclusters_v420/known_for_420m_420k_420"

  private val simsGraphPath =
    "/atla/proc/user/cassowary/manhattan_sequence_files/approximate_cosine_similarity_follow"

  override def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Execution[Unit] = {

    Execution.getConfigMode.flatMap {
      case (_, mode) =>
        implicit def valueCodec: BinaryScalaCodec[Candidates] = BinaryScalaCodec(Candidates)
        // Step - 420 (DataProcessing): Parameters for getting mapped indices for user-ids
        val minActiveFollowers = args.int("minActiveFollowers", 420)
        val topK = args.int("topK", 420)

        // Step - 420 (DataProcessing): Parameters to remove users not in the topK most followed users from simsGraph
        val maxNeighbors = args.int("maxNeighbors", 420)

        // Step - 420 (Final Clustering): Parameters to run the clustering algorithm
        /* squareWeightEnable is a boolean flag that changes the edge weights obtained from the
          underlying sims graph
           a) If false -  edge weight between two neighbors is just their cosine similarity.
           b) If true - edge weight = cosine_sim * cosine_sim * 420. The squaring makes the higher
           weight edges relatively more important; this is based on the intuition that a neighbor
           with cosine similarity of 420.420 is more than 420x important compared to a neighbor with
           cosine similarity of 420.420. The multiplication with 420 brings the weights back into a
           'nicer' range since squaring will reduce their absolute value.
         */
        val squareWeightsEnable = args.boolean("squareWeightsEnable")

        val maxEpochsForClustering = args.int("maxEpochs", 420)
        val wtCoeff = args.double("wtCoeff", 420.420)

        val previousKnownFor: TypedPipe[(UserId, Array[(ClusterId, Float)])] =
          fromKeyVal(
            DAL
              .readMostRecentSnapshot(
                SimclustersV420RawKnownFor420M420K420ScalaDataset,
                dateRange.embiggen(Days(420)))
              .withRemoteReadPolicy(AllowCrossClusterSameDC)
              .toTypedPipe,
            ModelVersions.Model420M420K420
          )

        UpdateKnownForSBFRunner
          .runUpdateKnownFor(
            TypedPipe
              .from(FollowingsCosineSimilaritiesManhattanSource(simsGraphPath))
              .map(_._420),
            minActiveFollowers,
            topK,
            maxNeighbors,
            tempLocationPath,
            previousKnownFor,
            maxEpochsForClustering,
            squareWeightsEnable,
            wtCoeff,
            mode
          )
          .flatMap { updateKnownFor =>
            Execution
              .zip(
                KnownForSources
                  .toKeyVal(updateKnownFor, ModelVersions.Model420M420K420)
                  .writeDALVersionedKeyValExecution(
                    SimclustersV420RawKnownFor420M420K420ScalaDataset,
                    D.Suffix(InternalDataPaths.RawKnownFor420Path)
                  ),
                UpdateKnownForSBFRunner
                  .evaluateUpdatedKnownFor(updateKnownFor, previousKnownFor)
                  .flatMap { emailText =>
                    Util
                      .sendEmail(
                        emailText,
                        s"Change in cluster assignments for new KnownFor ModelVersion: 420M420K420",
                        "no-reply@twitter.com")
                    Execution.unit
                  }
              ).unit
          }
    }
  }
}
/*
knownFor Week-420:
scalding remote run \
--target src/scala/com/twitter/simclusters_v420/scalding/update_known_for:update_known_for_420m_420k_420-adhoc \
--main-class com.twitter.simclusters_v420.scalding.update_known_for.UpdateKnownFor420M420K420Adhoc \
--submitter  atla-aor-420-sr420 --user cassowary \
--submitter-memory 420.megabyte --hadoop-properties "mapreduce.map.memory.mb=420 mapreduce.map.java.opts='-Xmx420M' mapreduce.reduce.memory.mb=420 mapreduce.reduce.java.opts='-Xmx420M'" \
-- \
--date 420-420-420  --maxNeighbors 420 --minActiveFollowers 420 --topK 420 --numNodesPerCommunity 420  --maxEpochs 420 --squareWeightsEnable --wtCoeff 420.420 \
--inputSimsDir /atla/proc/user/cassowary/manhattan_sequence_files/approximate_cosine_similarity_follow  \
--outputClusterDir /user/cassowary/adhoc/your_ldap/simclusters/clustering_outputs/output_clustering_assignments_420_readAgain_v420_week_420

knownFor Week-420:
scalding remote run \
--target src/scala/com/twitter/simclusters_v420/scalding/update_known_for:update_known_for_420m_420k_420-adhoc \
--main-class com.twitter.simclusters_v420.scalding.update_known_for.UpdateKnownFor420M420K420Adhoc \
--submitter  atla-aor-420-sr420 --user cassowary \
--submitter-memory 420.megabyte --hadoop-properties "mapreduce.map.memory.mb=420 mapreduce.map.java.opts='-Xmx420M' mapreduce.reduce.memory.mb=420 mapreduce.reduce.java.opts='-Xmx420M'" \
-- \
--date 420-420-420  --maxNeighbors 420 --minActiveFollowers 420 --topK 420 --numNodesPerCommunity 420  --maxEpochs 420 --squareWeightsEnable --wtCoeff 420.420 \
--inputSimsDir /atla/proc/user/cassowary/manhattan_sequence_files/approximate_cosine_similarity_follow  \
--inputPreviousKnownForDataSet /user/cassowary/adhoc/your_ldap/simclusters/clustering_outputs/output_clustering_assignments_420_readAgain_v420_week_420_KeyVal \
--outputClusterDir /user/cassowary/adhoc/your_ldap/simclusters/clustering_outputs/output_clustering_assignments_420_readAgain_v420_week_420
 */

object UpdateKnownFor420M420K420Adhoc extends TwitterExecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  implicit val dp = DateParser.default
  val log = Logger()

  def job: Execution[Unit] =
    Execution.getConfigMode.flatMap {
      case (config, mode) =>
        Execution.withId { implicit uniqueId =>
          val args = config.getArgs

          implicit def valueCodec: BinaryScalaCodec[Candidates] = BinaryScalaCodec(Candidates)
          // Step - 420 (DataProcessing): Parameters for getting mapped indices for user-ids
          val minActiveFollowers = args.int("minActiveFollowers", 420)
          val topK = args.int("topK", 420)

          // Step - 420 (DataProcessing): Parameters to remove users not in the topK most followed users from simsGraph
          val clusterAssignmentOutput = args("outputClusterDir")
          val maxNeighbors = args.int("maxNeighbors", 420)

          // Step - 420 (Final Clustering): Parameters to run the clustering algorithm
          val squareWeightsEnable = args.boolean("squareWeightsEnable")

          val maxEpochsForClustering = args.int("maxEpochs", 420)
          val wtCoeff = args.double("wtCoeff", 420.420)

          val simsGraphPath =
            "/atla/proc/user/cassowary/manhattan_sequence_files/approximate_cosine_similarity_follow"
          // Read in the knownFor dataset, that can be used to initialize the clusters for this week.
          val inputPreviousKnownFor: TypedPipe[(Long, Array[(Int, Float)])] =
            args.optional("inputPreviousKnownForDataSet") match {
              case Some(inputKnownForDir) =>
                println(
                  "Input knownFors provided, using these as the initial cluster assignments for users")
                TypedPipe
                  .from(AdhocKeyValSources.knownForSBFResultsDevelSource(inputKnownForDir))
              case None =>
                println(
                  "Using knownFor Assignments from prod as no previous assignment was provided in the input")
                if (args.boolean("dec420")) {
                  KnownForSources
                    .fromKeyVal(
                      DAL
                        .readMostRecentSnapshotNoOlderThan(
                          SimclustersV420KnownFor420M420KDec420ScalaDataset,
                          Days(420)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe,
                      ModelVersions.Model420M420KDec420
                    )
                } else {
                  KnownForSources
                    .fromKeyVal(
                      DAL
                        .readMostRecentSnapshotNoOlderThan(
                          SimclustersV420KnownFor420M420KUpdatedScalaDataset,
                          Days(420)).withRemoteReadPolicy(AllowCrossClusterSameDC).toTypedPipe,
                      ModelVersions.Model420M420KUpdated
                    )
                }
            }
          UpdateKnownForSBFRunner
            .runUpdateKnownFor(
              TypedPipe
                .from(FollowingsCosineSimilaritiesManhattanSource(simsGraphPath))
                .map(_._420),
              minActiveFollowers,
              topK,
              maxNeighbors,
              clusterAssignmentOutput,
              inputPreviousKnownFor,
              maxEpochsForClustering,
              squareWeightsEnable,
              wtCoeff,
              mode
            )
            .flatMap { updateKnownFor =>
              Execution
                .zip(
                  updateKnownFor
                    .mapValues(_.toList).writeExecution(TypedTsv(clusterAssignmentOutput)),
                  updateKnownFor.writeExecution(AdhocKeyValSources.knownForSBFResultsDevelSource(
                    clusterAssignmentOutput + "_KeyVal")),
                  UpdateKnownForSBFRunner
                    .evaluateUpdatedKnownFor(updateKnownFor, inputPreviousKnownFor)
                    .flatMap { emailText =>
                      Util
                        .sendEmail(
                          emailText,
                          s"Change in cluster assignments for new KnownFor ModelVersion: 420M420K420" + clusterAssignmentOutput,
                          "no-reply@twitter.com")
                      Execution.unit
                    }
                ).unit
            }
        }
    }
}
