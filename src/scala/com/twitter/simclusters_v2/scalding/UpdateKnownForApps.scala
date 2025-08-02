package com.twitter.simclusters_v2.scalding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.pluck.source.cassowary.FollowingsCosineSimilaritiesManhattanSource
import com.twitter.pluck.source.cassowary.SimsCandidatesSource
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledEtwitterecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.scalding.UpdateKnownFor.ClusterScoresForNode
import com.twitter.simclusters_v2.scalding.UpdateKnownFor.NeighborhoodInformation
import com.twitter.simclusters_v2.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v2.scalding.common.Util
import com.twitter.simclusters_v2.thriftscala.ClustersUserIsKnownFor
import com.twitter.usersource.snapshot.flat.UsersourceFlatScalaDataset
import scala.util.Success

object UpdateKnownForApps {

  /**
   * Average edge weight of an input graph
   * @param graph a TypedPipe with nodeId as key and adjacency list as value. We don't care about
   *              the keys in this method.
   * @return avg edge weight wrapped in an option in an etwitterecution
   */
  def getGlobalAvgWeight(graph: TypedPipe[(Long, Map[Long, Float])]): Etwitterecution[Option[Double]] = {
    graph.values
      .flatMap(_.values)
      .map { twitter => (twitter.toDouble, 1L) }
      .sum
      .toOptionEtwitterecution
      .map {
        case Some((sum, cnt)) =>
          val res = sum / cnt
          println("globalAvgWeight is " + res)
          Some(res)
        case _ =>
          println("Input graph to globalAvgWeight seems to be empty")
          None
      }
  }

  /**
   * Average membership score for a particular knownFor assignment
   * @param knownFor TypedPipe from nodeId to the clusters it's been assigned to along with
   *                 membership scores. We don't care about the keys in this method.
   * @return average membership score
   */
  def getAvgMembershipScore(knownFor: TypedPipe[(Long, Array[(Int, Float)])]): Etwitterecution[Double] = {
    knownFor.values
      .flatMap(_.map(_._2))
      .map { twitter => (twitter, 1L) }
      .sum
      .map { case (num, den) => num / den.toDouble }
      .getEtwitterecution
      .onComplete {
        case Success(twitter) => println("Avg. membership score is " + twitter)
        case _ => println("Failed to calculate avg. membership score")
      }
  }

  /**
   * For each cluster, get two statistics about it: the number of nodes assigned to it, and the
   * sum of the membership scores
   *
   * @param knownFor TypedPipe from nodeId to the clusters it's been assigned to along with
   *                 membership scores.
   * @return Map giving the NeighborhoodInformation for each cluster. The nodeCount and
   *         sumOfMembershipWeights fields in NeighborhoodInformation are populated, others are 0.
   */
  def getClusterStats(
    knownFor: TypedPipe[(Long, Array[(Int, Float)])]
  ): Etwitterecution[Map[Int, NeighborhoodInformation]] = {
    knownFor
      .flatMap {
        case (_, clusterArray) =>
          clusterArray.map {
            case (clusterId, score) =>
              Map(clusterId -> (1, score))
          }
      }
      .sum
      .getEtwitterecution
      .map { map =>
        map.mapValues {
          case (count, sum) =>
            NeighborhoodInformation(count, 0, 0, sum)
        }
      }
  }

  /**
   * Adds self-loops and also potentially raises all edge weights to an etwitterponent
   * (typically etwitterponent > 1, and has the effect of increasing inequality in edge weights to
   * "clarify" structure in the graph - currently we just set etwitterponent to 1).
   * @param symmetrizedSims input symmetrized similarity graph
   * @param etwitterponentForEdgeWeight etwitterponent to raise all edge weights to.
   *                              Set to 1.0 to make this a no-op
   * @param matwitterWtToSelfLoopWtMultFactor What to multiply the matwitter wt among non-self-loop edges to
   *                                    derive the weight on the self-loop edge.
   * @return New graph
   */
  def simsGraphForUpdateFromSymmetrizedSims(
    symmetrizedSims: TypedPipe[(Long, Map[Long, Float])],
    etwitterponentForEdgeWeight: Float,
    matwitterWtToSelfLoopWtMultFactor: Float
  ): TypedPipe[(Long, Map[Long, Float])] = {
    val etwitterpWeighted = symmetrizedSims.mapValues { y =>
      y.mapValues { twitter => math.pow(twitter, etwitterponentForEdgeWeight).toFloat }
    }

    TopUsersSimilarityGraph.addSelfLoop(
      input = etwitterpWeighted,
      matwitterToSelfLoopWeight = { twitter: Float => twitter * matwitterWtToSelfLoopWtMultFactor }
    )
  }

  /**
   * Runs the job
   * @param args args which specify many parameters
   * @param inputKnownFor
   * @param inputSimsGraph
   * @param defaultEmailAddress by default, the email address to send an to email to, which has
   *                            a bunch of evaluation metrics
   * @param writeKnownForFunction function that takes a knownFor and writes to some
   *                              persistent location
   * @param readKnownForFunction function that reads the knownFor which was written to using the
   *                             writeKnownForFunction
   * @param dateRange dateRange, used for reading UserSource
   * @param uniqueID need for creating stats
   * @return Etwitterecution[Unit] encapsulating the whole job
   */
  def runUpdateKnownForGeneric(
    args: Args,
    inputKnownFor: TypedPipe[(Long, Array[(Int, Float)])],
    inputSimsGraph: TypedPipe[Candidates],
    defaultEmailAddress: String,
    writeKnownForFunction: TypedPipe[(Long, Array[(Int, Float)])] => Etwitterecution[Unit],
    readKnownForFunction: => TypedPipe[(Long, Array[(Int, Float)])],
    includeEvaluationResultsInEmail: Boolean
  )(
    implicit dateRange: DateRange,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val minActiveFollowers = args.int("minActiveFollowers", 400)
    val topK = args.int("topK")
    val matwitterSimsNeighborsForUpdate =
      args.int("matwitterSimsNeighborsForUpdate", 40)
    val minNeighborsInCluster = args.int("minNeighborsInCluster", 2)
    val matwitterWtToSelfLoopWtMultFactor =
      args.float("matwitterWtToSelfLoopWtMultFactor", 2)
    val etwitterponentForEdgeWeight = args.float("etwitterponentForEdgeWeights", 1.0f)
    val updateMethod: ClusterScoresForNode => Double = args("updateMethod") match {
      case "sumScoreIgnoringMembershipScores" => { twitter: ClusterScoresForNode =>
        twitter.sumScoreIgnoringMembershipScores
      }
      case "ratioScoreIgnoringMembershipScores" => { twitter: ClusterScoresForNode =>
        twitter.ratioScoreIgnoringMembershipScores
      }
      case "ratioScoreUsingMembershipScores" => { twitter: ClusterScoresForNode =>
        twitter.ratioScoreUsingMembershipScores
      }
      case twitter @ _ =>
        throw new Etwitterception(s"value for --updateMethod $twitter is unknown. It must be one of " +
          s"[sumScoreIgnoringMembershipScores, ratioScoreIgnoringMembershipScores, ratioScoreUsingMembershipScores]")
    }
    val truePositiveWtFactor = args.float("truePositiveWtFactor", 10)
    val modelVersion = args("outputModelVersion")
    val emailAddress =
      args.optional("emailAddress").getOrElse(defaultEmailAddress)

    val topUsers = TopUsersSimilarityGraph
      .topUserIds(
        DAL
          .readMostRecentSnapshot(UsersourceFlatScalaDataset, dateRange)
          .toTypedPipe,
        minActiveFollowers,
        topK).count("num_top_users")

    TopUsersSimilarityGraph
      .getSubgraphFromUserGroupedInput(
        fullGraph = inputSimsGraph,
        usersToInclude = topUsers,
        matwitterNeighborsPerNode = matwitterSimsNeighborsForUpdate,
        degreeThresholdForStat = minNeighborsInCluster
      )
      .forceToDiskEtwitterecution
      .flatMap { symmetrizedSims =>
        val modifiedSims =
          UpdateKnownForApps.simsGraphForUpdateFromSymmetrizedSims(
            symmetrizedSims = symmetrizedSims,
            etwitterponentForEdgeWeight = etwitterponentForEdgeWeight,
            matwitterWtToSelfLoopWtMultFactor = matwitterWtToSelfLoopWtMultFactor
          )

        val previouslyFamousUsersEtwitterec = inputKnownFor
          .leftJoin(topUsers.asKeys)
          .collect { case (userId, (clusters, None)) => userId }
          .getSummaryString(
            "Users previously in known for but not in topUsers anymore",
            numRecords = 20)

        val clusterStatsEtwitterec = UpdateKnownForApps.getClusterStats(inputKnownFor)

        val globalAvgWeightEtwitterec =
          UpdateKnownForApps.getGlobalAvgWeight(modifiedSims)

        val globalAvgMembershipScoreEtwitterec = UpdateKnownForApps.getAvgMembershipScore(inputKnownFor)

        Etwitterecution.zip(globalAvgWeightEtwitterec, clusterStatsEtwitterec, globalAvgMembershipScoreEtwitterec).flatMap {
          case (Some(globalAvgWeight), clusterStats, globalAvgMembershipScore) =>
            println("Size of clusterStats: " + clusterStats.size)
            println("First few entries from clusterStats: " + clusterStats.take(5))
            println("globalAvgWeight: " + globalAvgWeight)
            println("globalAvgMembershipScore: " + globalAvgMembershipScore)

            val knownForWithUnnormalizedScores = UpdateKnownFor
              .newKnownForScores(
                inputKnownFor,
                modifiedSims,
                globalAvgWeight,
                clusterStats,
                globalAvgMembershipScore
              )
            val writeNewKnownForEtwitterec = writeKnownForFunction(
              UpdateKnownFor.updateGeneric(
                modifiedSims,
                knownForWithUnnormalizedScores,
                clusterStats,
                minNeighborsInCluster,
                globalAvgWeight,
                globalAvgMembershipScore,
                truePositiveWtFactor,
                updateMethod
              )
            )

            writeNewKnownForEtwitterec.flatMap { _ =>
              Util.getCustomCountersString(writeNewKnownForEtwitterec).flatMap { customCountersString =>
                if (includeEvaluationResultsInEmail) {
                  // It's unfortunate that we're not using the newKnownFor directly, but are instead
                  // first writing it out and then reading it back in. The reason for doing it in this
                  // convoluted way is that when we directly use the newKnownFor, the clusterEvaluation
                  // metrics are being incorrectly computed.

                  val newKnownFor = readKnownForFunction

                  val newResultsEtwitterec =
                    ClusterEvaluation
                      .overallEvaluation(symmetrizedSims, newKnownFor, "newKnownForEval")
                  val oldResultsEtwitterec =
                    ClusterEvaluation
                      .overallEvaluation(symmetrizedSims, inputKnownFor, "oldKnownForEval")
                  val minSizeOfBiggerClusterForComparison = 10
                  val compareEtwitterec = CompareClusters.summarize(
                    CompareClusters.compare(
                      KnownForSources.transpose(inputKnownFor),
                      KnownForSources.transpose(newKnownFor),
                      minSizeOfBiggerCluster = minSizeOfBiggerClusterForComparison
                    ))

                  Etwitterecution
                    .zip(oldResultsEtwitterec, newResultsEtwitterec, compareEtwitterec, previouslyFamousUsersEtwitterec)
                    .map {
                      case (oldResults, newResults, compareResults, previouslyFamousUsersString) =>
                        val emailTetwittert = "Evaluation Results for etwitteristing knownFor:\n" +
                          Util.prettyJsonMapper.writeValueAsString(oldResults) +
                          "\n\n-------------------\n\n" +
                          "Evaluation Results for new knownFor:\n" +
                          Util.prettyJsonMapper.writeValueAsString(newResults) +
                          "\n\n-------------------\n\n" +
                          s"Cosine similarity distribution between cluster membership vectors for " +
                          s"clusters with at least $minSizeOfBiggerClusterForComparison members\n" +
                          Util.prettyJsonMapper
                            .writeValueAsString(compareResults) +
                          "\n\n-------------------\n\n" +
                          "Custom counters:\n" + customCountersString +
                          "\n\n-------------------\n\n" +
                          previouslyFamousUsersString

                        Util
                          .sendEmail(
                            emailTetwittert,
                            s"Evaluation results of new knownFor $modelVersion",
                            emailAddress)
                    }
                } else {
                  Util
                    .sendEmail(
                      customCountersString,
                      s"Change in cluster assignments for update of knownFor $modelVersion",
                      emailAddress
                    )
                  Etwitterecution.unit
                }

              }
            }
        }
      }
  }
}

trait UpdateKnownForBatch etwittertends TwitterScheduledEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  implicit val dp = DateParser.default

  def firstTime: String

  val batchIncrement: Duration = Days(30)

  def batchDescription: String

  private lazy val etwitterecArgs = AnalyticsBatchEtwitterecutionArgs(
    batchDesc = BatchDescription(batchDescription),
    firstTime = BatchFirstTime(RichDate(firstTime)),
    lastTime = None,
    batchIncrement = BatchIncrement(batchIncrement)
  )

  val emailAddress: String = "no-reply@twitter.com"

  def inputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]]

  def inputModelVersion: String

  def outputModelVersion: String

  def outputPath: String

  def outputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]]

  override def scheduledJob: Etwitterecution[Unit] =
    AnalyticsBatchEtwitterecution(etwitterecArgs) { implicit dateRange =>
      Etwitterecution.withId { implicit uniqueId =>
        Etwitterecution.withArgs { args =>
          val inputKnownFor =
            KnownForSources.readDALDataset(inputDALDataset, Days(30), inputModelVersion)

          val inputSimsGraph = TypedPipe
            .from(FollowingsCosineSimilaritiesManhattanSource())
            .map(_._2)

          def writeKnownFor(knownFor: TypedPipe[(Long, Array[(Int, Float)])]): Etwitterecution[Unit] = {
            KnownForSources
              .toKeyVal(knownFor, outputModelVersion)
              .writeDALVersionedKeyValEtwitterecution(
                outputDALDataset,
                D.Suffitwitter(outputPath)
              )
          }

          def readKnownFor =
            KnownForSources.readDALDataset(outputDALDataset, Days(1), outputModelVersion)

          UpdateKnownForApps.runUpdateKnownForGeneric(
            args,
            inputKnownFor,
            inputSimsGraph,
            emailAddress,
            writeKnownFor,
            readKnownFor,
            includeEvaluationResultsInEmail = false
          )
        }
      }
    }
}

/**
capesospy-v2 update --build_locally --start_cron update_known_for_20M_145k \
 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml
 */
object UpdateKnownFor20M145K etwittertends UpdateKnownForBatch {
  override val firstTime: String = "2019-06-06"

  override val batchIncrement: Duration = Days(7)

  override val batchDescription: String =
    "com.twitter.simclusters_v2.scalding.UpdateKnownFor20M145K"

  override val inputModelVersion: String = ModelVersions.Model20M145KUpdated

  override val inputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]] =
    SimclustersV2RawKnownFor20M145KUpdatedScalaDataset

  override val outputModelVersion: String = ModelVersions.Model20M145KUpdated

  override val outputDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]] =
    SimclustersV2RawKnownFor20M145KUpdatedScalaDataset

  override val outputPath: String = InternalDataPaths.RawKnownForUpdatedPath
}

/** This one's end-to-end, doesn't save any intermediate data etc. **/
object UpdateKnownForAdhoc etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  implicit val dp = DateParser.default

  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          implicit val date: DateRange = DateRange.parse(args("date"))
          val defaultEmailAddress = "your_ldap@twitter.com"

          val inputKnownFor = args.optional("inputKnownForDir") match {
            case Some(inputKnownForDir) => KnownForSources.readKnownFor(inputKnownForDir)
            case None => KnownForSources.knownFor_20M_Dec11_145K
          }

          val inputSimsGraph = TopUsersSimilarityGraph.readSimsInput(
            args.boolean("simsInputIsKeyValSource"),
            args("simsInputDir")
          )

          def readKnownFor() = KnownForSources.readKnownFor(args("outputDir"))

          UpdateKnownForApps.runUpdateKnownForGeneric(
            args,
            inputKnownFor,
            inputSimsGraph,
            defaultEmailAddress,
            { input: TypedPipe[(Long, Array[(Int, Float)])] =>
              KnownForSources.writeKnownForTypedTsv(input, args("outputDir"))
            },
            readKnownFor,
            includeEvaluationResultsInEmail = true
          )
        }
    }
}
