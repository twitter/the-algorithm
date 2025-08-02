package com.twitter.simclusters_v2.scalding

import com.twitter.bijection.Injection
import com.twitter.frigate.user_sampler.common.EmployeeIds
import com.twitter.hashing.KeyHasher
import com.twitter.logging.Logger
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.dalv2.remote_access.EtwitterplicitLocation
import com.twitter.scalding_internal.dalv2.remote_access.ProcAtla
import com.twitter.scalding_internal.job.TwitterEtwitterecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchEtwitterecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledEtwitterecutionApp
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.scalding.common.TypedRichPipe._
import com.twitter.simclusters_v2.scalding.common.Util
import com.twitter.simclusters_v2.thriftscala.EdgeWithDecayedWeights
import com.twitter.simclusters_v2.thriftscala.NeighborWithWeights
import com.twitter.simclusters_v2.thriftscala.NormsAndCounts
import com.twitter.simclusters_v2.thriftscala.UserAndNeighbors
import com.twitter.usersource.snapshot.flat.UsersourceFlatScalaDataset
import flockdb_tools.datasets.flock.FlockFollowsEdgesScalaDataset

case class Edge(srcId: Long, destId: Long, isFollowEdge: Boolean, favWeight: Double)

object UserUserNormalizedGraph {

  // The common function for applying logarithmic transformation
  def logTransformation(weight: Double): Double = {
    math.matwitter(math.log10(1.0 + weight), 0.0)
  }

  def getFollowEdges(implicit dateRange: DateRange, uniqueID: UniqueID): TypedPipe[(Long, Long)] = {
    val numInputFollowEdges = Stat("num_input_follow_edges")
    DAL
      .readMostRecentSnapshot(FlockFollowsEdgesScalaDataset)
      .toTypedPipe
      .collect {
        case edge if edge.state == 0 =>
          numInputFollowEdges.inc()
          (edge.sourceId, edge.destinationId)
      }
  }

  def transformFavEdges(
    input: TypedPipe[EdgeWithDecayedWeights],
    halfLifeInDaysForFavScore: Int
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[(Long, Long, Double)] = {
    val numEdgesWithSpecifiedHalfLife = Stat(
      s"num_edges_with_specified_half_life_${halfLifeInDaysForFavScore}_days")
    val numEdgesWithoutSpecifiedHalfLife = Stat(
      s"num_edges_without_specified_half_life_${halfLifeInDaysForFavScore}_days")
    input
      .flatMap { edge =>
        if (edge.weights.halfLifeInDaysToDecayedSums.contains(halfLifeInDaysForFavScore)) {
          numEdgesWithSpecifiedHalfLife.inc()
          Some((edge.sourceId, edge.destinationId, edge.weights.halfLifeInDaysToDecayedSums(100)))
        } else {
          numEdgesWithoutSpecifiedHalfLife.inc()
          None
        }
      }
  }

  def getFavEdges(
    halfLifeInDaysForFavScore: Int
  )(
    implicit dateRange: DateRange,
    uniqueID: UniqueID
  ): TypedPipe[(Long, Long, Double)] = {
    implicit val tz: java.util.TimeZone = DateOps.UTC
    transformFavEdges(
      DAL
        .readMostRecentSnapshot(UserUserFavGraphScalaDataset)
        .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
        .toTypedPipe,
      halfLifeInDaysForFavScore
    )
  }

  def getNeighborWithWeights(
    inputEdge: Edge,
    followerL2NormOfDest: Double,
    faverL2NormOfDest: Double,
    logFavL2Norm: Double
  ): NeighborWithWeights = {
    val normalizedFollowScore = {
      val numerator = if (inputEdge.isFollowEdge) 1.0 else 0.0
      if (followerL2NormOfDest > 0) numerator / followerL2NormOfDest else 0.0
    }
    val normalizedFavScore =
      if (faverL2NormOfDest > 0) inputEdge.favWeight / faverL2NormOfDest else 0.0
    val logFavScore = if (inputEdge.favWeight > 0) logTransformation(inputEdge.favWeight) else 0.0
    val logFavScoreL2Normalized = if (logFavL2Norm > 0) logFavScore / logFavL2Norm else 0.0
    NeighborWithWeights(
      inputEdge.destId,
      Some(inputEdge.isFollowEdge),
      Some(normalizedFollowScore),
      Some(inputEdge.favWeight),
      Some(normalizedFavScore),
      logFavScore = Some(logFavScore),
      logFavScoreL2Normalized = Some(logFavScoreL2Normalized)
    )
  }

  def addNormalizedWeightsAndAdjListify(
    input: TypedPipe[Edge],
    matwitterNeighborsPerUser: Int,
    normsAndCountsFull: TypedPipe[NormsAndCounts]
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

    val norms = normsAndCountsFull.map { record =>
      (
        record.userId,
        (
          record.followerL2Norm.getOrElse(0.0),
          record.faverL2Norm.getOrElse(0.0),
          record.logFavL2Norm.getOrElse(0.0)))
    }

    implicit val l2b: Long => Array[Byte] = Injection.long2BigEndian
    input
      .map { edge => (edge.destId, edge) }
      .sketch(reducers = 2000)
      .join(norms)
      .map {
        case (destId, (edge, (followNorm, favNorm, logFavNorm))) =>
          numEdgesBeforeTruncation.inc()
          if (edge.isFollowEdge) numFollowEdgesBeforeTruncation.inc()
          if (edge.favWeight > 0) numFavEdgesBeforeTruncation.inc()
          (edge.srcId, getNeighborWithWeights(edge, followNorm, favNorm, logFavNorm))
      }
      .group
      //.withReducers(1000)
      .sortedReverseTake(matwitterNeighborsPerUser)(Ordering.by { twitter: NeighborWithWeights =>
        (
          twitter.favScoreHalfLife100Days.getOrElse(0.0),
          twitter.followScoreNormalizedByNeighborFollowersL2.getOrElse(0.0)
        )
      })
      .map {
        case (srcId, neighborList) =>
          if (neighborList.size >= matwitterNeighborsPerUser) numUsersNeedingNeighborTruncation.inc()
          neighborList.foreach { neighbor =>
            numEdgesAfterTruncation.inc()
            if (neighbor.favScoreHalfLife100Days.etwitterists(_ > 0)) numFavEdgesAfterTruncation.inc()
            if (neighbor.isFollowed.contains(true)) numFollowEdgesAfterTruncation.inc()
          }
          numRecordsInOutputGraph.inc()
          UserAndNeighbors(srcId, neighborList)
      }
  }

  def combineFollowAndFav(
    followEdges: TypedPipe[(Long, Long)],
    favEdges: TypedPipe[(Long, Long, Double)]
  ): TypedPipe[Edge] = {
    (
      followEdges.map { case (src, dest) => ((src, dest), (1, 0.0)) } ++
        favEdges.map { case (src, dest, wt) => ((src, dest), (0, wt)) }
    ).sumByKey
    //.withReducers(2500)
      .map {
        case ((src, dest), (follow, favWt)) =>
          Edge(src, dest, isFollowEdge = follow > 0, favWt)
      }
  }

  def run(
    followEdges: TypedPipe[(Long, Long)],
    favEdges: TypedPipe[(Long, Long, Double)],
    normsAndCounts: TypedPipe[NormsAndCounts],
    matwitterNeighborsPerUser: Int
  )(
    implicit uniqueID: UniqueID
  ): TypedPipe[UserAndNeighbors] = {
    val combined = combineFollowAndFav(followEdges, favEdges)
    addNormalizedWeightsAndAdjListify(
      combined,
      matwitterNeighborsPerUser,
      normsAndCounts
    )
  }
}

object UserUserNormalizedGraphBatch etwittertends TwitterScheduledEtwitterecutionApp {
  private val firstTime: String = "2018-06-16"
  implicit val tz = DateOps.UTC
  implicit val parser = DateParser.default
  private val batchIncrement: Duration = Days(7)
  private val halfLifeInDaysForFavScore = 100

  private val outputPath: String = "/user/cassowary/processed/user_user_normalized_graph"

  private val etwitterecArgs = AnalyticsBatchEtwitterecutionArgs(
    batchDesc = BatchDescription(this.getClass.getName.replace("$", "")),
    firstTime = BatchFirstTime(RichDate(firstTime)),
    lastTime = None,
    batchIncrement = BatchIncrement(batchIncrement)
  )

  override def scheduledJob: Etwitterecution[Unit] = AnalyticsBatchEtwitterecution(etwitterecArgs) {
    implicit dateRange =>
      Etwitterecution.withId { implicit uniqueId =>
        Etwitterecution.withArgs { args =>
          val matwitterNeighborsPerUser = args.int("matwitterNeighborsPerUser", 2000)

          val producerNormsAndCounts =
            DAL.readMostRecentSnapshot(ProducerNormsAndCountsScalaDataset).toTypedPipe

          Util.printCounters(
            UserUserNormalizedGraph
              .run(
                UserUserNormalizedGraph.getFollowEdges,
                UserUserNormalizedGraph.getFavEdges(halfLifeInDaysForFavScore),
                producerNormsAndCounts,
                matwitterNeighborsPerUser
              )
              .writeDALSnapshotEtwitterecution(
                UserUserNormalizedGraphScalaDataset,
                D.Daily,
                D.Suffitwitter(outputPath),
                D.EBLzo(),
                dateRange.end)
          )
        }
      }
  }
}

object UserUserNormalizedGraphAdhoc etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  implicit val dp = DateParser.default
  val log = Logger()

  def hashToLong(input: Long): Long = {
    val bb = java.nio.ByteBuffer.allocate(8)
    bb.putLong(input)
    Math.abs(KeyHasher.KETAMA.hashKey(bb.array()))
  }

  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          implicit val dateRange: DateRange = DateRange.parse(args.list("date"))
          val halfLifeInDaysForFavScore = 100
          val matwitterNeighborsPerUser = args.int("matwitterNeighborsPerUser", 2000)
          val producerNormsAndCounts = TypedPipe.from(
            NormsAndCountsFitwitteredPathSource(args("normsInputDir"))
          )
          val favEdges = args.optional("favGraphInputDir") match {
            case Some(favGraphInputDir) =>
              UserUserNormalizedGraph.transformFavEdges(
                TypedPipe.from(
                  EdgeWithDecayedWtsFitwitteredPathSource(favGraphInputDir)
                ),
                halfLifeInDaysForFavScore
              )
            case None =>
              UserUserNormalizedGraph.getFavEdges(halfLifeInDaysForFavScore)
          }

          val followEdges = UserUserNormalizedGraph.getFollowEdges

          Util.printCounters(
            UserUserNormalizedGraph
              .run(
                followEdges,
                favEdges,
                producerNormsAndCounts,
                matwitterNeighborsPerUser
              ).writeEtwitterecution(UserAndNeighborsFitwitteredPathSource(args("outputDir")))
          )
        }
    }
}

object DumpUserUserGraphAdhoc etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          val input = args.optional("inputDir") match {
            case Some(inputDir) => TypedPipe.from(UserAndNeighborsFitwitteredPathSource(inputDir))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
                .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
                .toTypedPipe
          }
          val users = args.list("users").map(_.toLong).toSet
          if (users.isEmpty) {
            input.printSummary("Producer norms and counts")
          } else {
            input
              .collect {
                case rec if users.contains(rec.userId) =>
                  (Seq(rec.userId.toString) ++ rec.neighbors.map { n =>
                    Util.prettyJsonMapper.writeValueAsString(n).replaceAll("\n", " ")
                  }).mkString("\n")
              }
              .toIterableEtwitterecution
              .map { strings => println(strings.mkString("\n")) }
          }
        }
    }
}

/*
 * ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding:user_user_normalized_graph && \
 * oscar hdfs --host hadoopnest2.atla.twitter.com --bundle user_user_normalized_graph \
 * --tool com.twitter.simclusters_v2.scalding.EmployeeGraph --screen --screen-detached \
 * --tee your_ldap/employeeGraph20190809 -- --outputDir adhoc/employeeGraph20190809
 */
object EmployeeGraph etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          val input = args.optional("inputDir") match {
            case Some(inputDir) => TypedPipe.from(UserAndNeighborsFitwitteredPathSource(inputDir))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
                .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
                .toTypedPipe
          }
          val employeeIds = EmployeeIds.buildMerlinClientAndGetEmployees("frigate-scalding.dev")
          input
            .collect {
              case rec if employeeIds.contains(rec.userId) =>
                rec.neighbors.collect {
                  case n if employeeIds.contains(n.neighborId) =>
                    (
                      rec.userId,
                      n.neighborId,
                      n.favScoreHalfLife100Days.getOrElse(0),
                      n.isFollowed.getOrElse(false))
                }
            }
            .flatten
            .writeEtwitterecution(TypedTsv(args("outputDir")))

        }
    }
}
/*
 * scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding:employee_graph_from_user_user
 * --main-class com.twitter.simclusters_v2.scalding.EmployeeGraphFromUserUser
 * --submitter  hadoopnest2.atla.twitter.com --user recos-platform -- --graphOutputDir "/user/recos-platform/adhoc/employee_graph_from_user_user/"
 */

object EmployeeGraphFromUserUser etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          val graphOutputDir = args("graphOutputDir")
          val input = args.optional("inputDir") match {
            case Some(inputDir) => TypedPipe.from(UserAndNeighborsFitwitteredPathSource(inputDir))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
                .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
                .toTypedPipe
          }
          val employeeIds = EmployeeIds.buildMerlinClientAndGetEmployees("frigate-scalding.dev")
          input
            .collect {
              case rec if employeeIds.contains(rec.userId) =>
                rec
            }
            .writeEtwitterecution(UserAndNeighborsFitwitteredPathSource(graphOutputDir))

        }
    }
}

/*
 * ./bazel bundle src/scala/com/twitter/simclusters_v2/scalding:user_user_normalized_graph && \
 * oscar hdfs --host hadoopnest2.atla.twitter.com --bundle user_user_normalized_graph \
 * --tool com.twitter.simclusters_v2.scalding.VitGraph --screen --screen-detached \
 * --tee your_ldap/vitGraph20190809 -- --outputDir adhoc/vitGraph20190809
 */
object VitGraph etwittertends TwitterEtwitterecutionApp {
  implicit val tz: java.util.TimeZone = DateOps.UTC
  def job: Etwitterecution[Unit] =
    Etwitterecution.getConfigMode.flatMap {
      case (config, mode) =>
        Etwitterecution.withId { implicit uniqueId =>
          val args = config.getArgs
          val minActiveFollowers = args.int("minActiveFollowers")
          val topK = args.int("topK")
          val input = args.optional("inputDir") match {
            case Some(inputDir) => TypedPipe.from(UserAndNeighborsFitwitteredPathSource(inputDir))
            case None =>
              DAL
                .readMostRecentSnapshotNoOlderThan(UserUserNormalizedGraphScalaDataset, Days(30))
                .withRemoteReadPolicy(EtwitterplicitLocation(ProcAtla))
                .toTypedPipe
          }
          val userSource =
            DAL.readMostRecentSnapshotNoOlderThan(UsersourceFlatScalaDataset, Days(30)).toTypedPipe

          TopUsersSimilarityGraph
            .vits(userSource, minActiveFollowers, topK).toIterableEtwitterecution.flatMap { vitsIter =>
              val vits = vitsIter.toSet
              println(s"Found ${vits.size} many vits. First few: " + vits.take(5).mkString(","))
              input
                .collect {
                  case rec if vits.contains(rec.userId) =>
                    rec.neighbors.collect {
                      case n if vits.contains(n.neighborId) =>
                        (
                          rec.userId,
                          n.neighborId,
                          n.favScoreHalfLife100Days.getOrElse(0),
                          n.isFollowed.getOrElse(false))
                    }
                }
                .flatten
                .writeEtwitterecution(TypedTsv(args("outputDir")))
            }

        }
    }

}
