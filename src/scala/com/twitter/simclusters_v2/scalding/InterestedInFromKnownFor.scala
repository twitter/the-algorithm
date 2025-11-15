package com.twitter.simclusters_v2.scalding

import com.twitter.algebird.Semigroup
import com.twitter.bijection.Injection
import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding.TypedPipe
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DAL
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.job.TwitterExecutionApp
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchExecution
import com.twitter.scalding_internal.job.analytics_batch.AnalyticsBatchExecutionArgs
import com.twitter.scalding_internal.job.analytics_batch.BatchDescription
import com.twitter.scalding_internal.job.analytics_batch.BatchFirstTime
import com.twitter.scalding_internal.job.analytics_batch.BatchIncrement
import com.twitter.scalding_internal.job.analytics_batch.TwitterScheduledExecutionApp
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ClusterId
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.UserId
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.scalding.common.Util
import com.twitter.simclusters_v2.thriftscala._

/**
 * This file implements the job for computing users' interestedIn vector from KnownFor data set.
 *
 * It reads the UserUserNormalizedGraphScalaDataset to get user-user follow + fav graph, and then
 * based on the known-for clusters of each followed/faved user, we calculate how much a user is
 * interestedIn a cluster.
 */

/**
 * Production job for computing interestedIn data set for the model version 20M145K2020.
 *
 * To deploy the job:
 *
 * capesospy-v2 update --build_locally --start_cron interested_in_for_20M_145k_2020 \
 src/scala/com/twitter/simclusters_v2/capesos_config/atla_proc.yaml
 */
object InterestedInFromKnownFor20M145K2020 extends InterestedInFromKnownForBatchBase {
  override val firstTime: String = "2020-10-06"
  override val outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsInterestedIn]] =
    SimclustersV2RawInterestedIn20M145K2020ScalaDataset
  override val outputPath: String = InternalDataPaths.RawInterestedIn2020Path
  override val knownForModelVersion: String = ModelVersions.Model20M145K2020
  override val knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]] =
    SimclustersV2KnownFor20M145K2020ScalaDataset
}

/**
 * base class for the main logic of computing interestedIn from KnownFor data set.
 */
trait InterestedInFromKnownForBatchBase extends TwitterScheduledExecutionApp {
  implicit val tz = DateOps.UTC
  implicit val parser = DateParser.default

  def firstTime: String
  val batchIncrement: Duration = Days(7)
  val lookBackDays: Duration = Days(30)

  def outputKVDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsInterestedIn]]
  def outputPath: String
  def knownForModelVersion: String
  def knownForDALDataset: KeyValDALDataset[KeyVal[Long, ClustersUserIsKnownFor]]

  private lazy val execArgs = AnalyticsBatchExecutionArgs(
    batchDesc = BatchDescription(this.getClass.getName.replace("$", "")),
    firstTime = BatchFirstTime(RichDate(firstTime)),
    lastTime = None,
    batchIncrement = BatchIncrement(batchIncrement)
  )

  override def scheduledJob: Execution[Unit] = AnalyticsBatchExecution(execArgs) {
    implicit dateRange =>
      Execution.withId { implicit uniqueId =>
        Execution.withArgs { args =>
          val normalizedGraph =
            DAL.readMostRecentSnapshot(UserUserNormalizedGraphScalaDataset).toTypedPipe
          val knownFor = KnownForSources.fromKeyVal(
            DAL.readMostRecentSnapshot(knownForDALDataset, dateRange.extend(Days(30))).toTypedPipe,
            knownForModelVersion
          )

          val socialProofThreshold = args.int("socialProofThreshold", 2)
          val maxClustersPerUser = args.int("maxClustersPerUser", 50)

          // Use the standard run method, or runWithTopicAlignment for topic-aligned boosting
          // To enable topic alignment: User B will receive more recommendations from User A
          // for topics that are aligned between both users.
          // Example usage with topic alignment and DM (Direct Message) support:
          // val previousInterestedIn = DAL.readMostRecentSnapshot(outputKVDataset, dateRange.prepend(lookBackDays))
          //   .toTypedPipe.map { case KeyVal(user, interestedIn) => (user, interestedIn) }
          // // Extract DM data from Interaction Graph
          // import com.twitter.interaction_graph.hdfs_sources.InteractionGraphHistoryAggregatedEdgeSnapshotScalaDataset
          // val interactionGraphEdges = DAL.readMostRecentSnapshot(
          //   InteractionGraphHistoryAggregatedEdgeSnapshotScalaDataset,
          //   dateRange
          // ).toTypedPipe
          // val dmData = InterestedInFromKnownFor.extractDirectMessageData(
          //   interactionGraphEdges,
          //   useEwma = true,  // Use exponentially weighted moving average for recent DM interactions
          //   minScore = 0.1  // Minimum DM score threshold
          // )
          // val result = InterestedInFromKnownFor.runWithTopicAlignment(
          //   normalizedGraph,
          //   knownFor,
          //   socialProofThreshold,
          //   maxClustersPerUser,
          //   knownForModelVersion,
          //   topicAlignmentBoost = 1.5, // 1.5x boost for aligned topics
          //   existingInterestedIn = Some(previousInterestedIn),
          //   messagingData = Some(dmData) // Include DM data for topic-aligned boosting
          // )
          val result = InterestedInFromKnownFor
            .run(
              normalizedGraph,
              knownFor,
              socialProofThreshold,
              maxClustersPerUser,
              knownForModelVersion
            )

          val writeKeyValResultExec = result
            .map { case (userId, clusters) => KeyVal(userId, clusters) }
            .writeDALVersionedKeyValExecution(
              outputKVDataset,
              D.Suffix(outputPath)
            )

          // read previous data set for validation purpose
          val previousDataset = if (RichDate(firstTime).timestamp != dateRange.start.timestamp) {
            DAL
              .readMostRecentSnapshot(outputKVDataset, dateRange.prepend(lookBackDays)).toTypedPipe
              .map {
                case KeyVal(user, interestedIn) =>
                  (user, interestedIn)
              }
          } else {
            TypedPipe.empty
          }

          Util.printCounters(
            Execution
              .zip(
                writeKeyValResultExec,
                InterestedInFromKnownFor.dataSetStats(result, "NewResult"),
                InterestedInFromKnownFor.dataSetStats(previousDataset, "OldResult")
              ).unit
          )
        }
      }
  }
}

/**
 * Adhoc job to compute user interestedIn.
 *
 * scalding remote run --target src/scala/com/twitter/simclusters_v2/scalding:interested_in_adhoc \
 * --user recos-platform \
 * --submitter hadoopnest2.atla.twitter.com \
 * --main-class com.twitter.simclusters_v2.scalding.InterestedInFromKnownForAdhoc -- \
 * --date 2019-08-26  --outputDir /user/recos-platform/adhoc/simclusters_interested_in_log_fav
 */
object InterestedInFromKnownForAdhoc extends TwitterExecutionApp {
  def job: Execution[Unit] =
    Execution.getConfigMode.flatMap {
      case (config, mode) =>
        Execution.withId { implicit uniqueId =>
          val args = config.getArgs
          val normalizedGraph = TypedPipe.from(
            UserAndNeighborsFixedPathSource(args("graphInputDir"))
          )
          val socialProofThreshold = args.int("socialProofThreshold", 2)
          val maxClustersPerUser = args.int("maxClustersPerUser", 20)
          val knownForModelVersion = args("knownForModelVersion")
          val knownFor = KnownForSources.readKnownFor(args("knownForInputDir"))

          val outputSink = AdhocKeyValSources.interestedInSource(args("outputDir"))
          Util.printCounters(
            InterestedInFromKnownFor
              .run(
                normalizedGraph,
                knownFor,
                socialProofThreshold,
                maxClustersPerUser,
                knownForModelVersion
              ).writeExecution(outputSink)
          )
        }
    }
}

/**
 * Adhoc job to check the output of an adhoc interestedInSource.
 */
object DumpInterestedInAdhoc extends TwitterExecutionApp {
  def job: Execution[Unit] =
    Execution.getConfigMode.flatMap {
      case (config, mode) =>
        Execution.withId { implicit uniqueId =>
          val args = config.getArgs
          val users = args.list("users").map(_.toLong).toSet
          val input = TypedPipe.from(AdhocKeyValSources.interestedInSource(args("inputDir")))
          input.filter { case (userId, rec) => users.contains(userId) }.toIterableExecution.map {
            s => println(s.map(Util.prettyJsonMapper.writeValueAsString).mkString("\n"))
          }
        }
    }
}

/**
 * Helper functions
 */
object InterestedInFromKnownFor {
  private def ifNanMake0(x: Double): Double = if (x.isNaN) 0.0 else x

  /**
   * Extract direct message (DM) interaction data from Interaction Graph edges.
   * This function reads from the aggregated interaction graph dataset and extracts
   * the num_direct_messages feature for each user pair.
   * 
   * @param interactionGraphEdges TypedPipe of Edge from Interaction Graph.
   *                              Each Edge contains a list of EdgeFeature objects.
   *                              We look for EdgeFeature with FeatureName.NumDirectMessages.
   * @param useEwma If true, uses exponentially weighted moving average (ewma) from TimeSeriesStatistics.
   *                If false, uses mean. Default true (ewma is better for recent interactions).
   * @param minScore Minimum score threshold. Edges with scores below this will be filtered out.
   * @return TypedPipe of (sourceUserId, destUserId, messageScore) tuples.
   *         Format matches the messagingData parameter expected by runWithTopicAlignment.
   * 
   * Example usage:
   * {{{
   *   import com.twitter.interaction_graph.hdfs_sources.InteractionGraphHistoryAggregatedEdgeSnapshotScalaDataset
   *   val interactionGraphEdges = DAL.readMostRecentSnapshot(
   *     InteractionGraphHistoryAggregatedEdgeSnapshotScalaDataset,
   *     dateRange
   *   ).toTypedPipe
   *   val dmData = InterestedInFromKnownFor.extractDirectMessageData(
   *     interactionGraphEdges,
   *     useEwma = true,
   *     minScore = 0.1
   *   )
   * }}}
   */
  def extractDirectMessageData(
    interactionGraphEdges: TypedPipe[com.twitter.interaction_graph.thriftscala.Edge],
    useEwma: Boolean = true,
    minScore: Double = 0.1
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, UserId, Double)] = {
    val numEdgesWithDM = Stat("num_edges_with_direct_messages")
    val numEdgesWithoutDM = Stat("num_edges_without_direct_messages")
    val numEdgesFilteredByMinScore = Stat("num_edges_filtered_by_min_score")

    interactionGraphEdges
      .flatMap { edge =>
        // Find the EdgeFeature with FeatureName.NumDirectMessages
        val dmFeature = edge.features.find(_.name == com.twitter.interaction_graph.thriftscala.FeatureName.NumDirectMessages)
        
        dmFeature match {
          case Some(feature) =>
            numEdgesWithDM.inc()
            // Extract score from TimeSeriesStatistics
            // Use ewma (exponentially weighted moving average) for recent interactions,
            // or mean for average over time
            val score = if (useEwma) {
              feature.tss.ewma
            } else {
              feature.tss.mean
            }
            
            if (score >= minScore) {
              Some((edge.sourceId, edge.destinationId, score))
            } else {
              numEdgesFilteredByMinScore.inc()
              None
            }
          case None =>
            numEdgesWithoutDM.inc()
            None
        }
      }
  }

  case class SrcClusterIntermediateInfo(
    followScore: Double,
    followScoreProducerNormalized: Double,
    favScore: Double,
    favScoreProducerNormalized: Double,
    logFavScore: Double,
    logFavScoreProducerNormalized: Double,
    messageScore: Double,
    messageScoreProducerNormalized: Double,
    followSocialProof: List[Long],
    favSocialProof: List[Long],
    messageSocialProof: List[Long]) {
    // overriding for the sake of unit tests
    override def equals(obj: scala.Any): Boolean = {
      obj match {
        case that: SrcClusterIntermediateInfo =>
          math.abs(followScore - that.followScore) < 1e-5 &&
          math.abs(followScoreProducerNormalized - that.followScoreProducerNormalized) < 1e-5 &&
          math.abs(favScore - that.favScore) < 1e-5 &&
          math.abs(favScoreProducerNormalized - that.favScoreProducerNormalized) < 1e-5 &&
          math.abs(logFavScore - that.logFavScore) < 1e-5 &&
          math.abs(logFavScoreProducerNormalized - that.logFavScoreProducerNormalized) < 1e-5 &&
          math.abs(messageScore - that.messageScore) < 1e-5 &&
          math.abs(messageScoreProducerNormalized - that.messageScoreProducerNormalized) < 1e-5 &&
          followSocialProof.toSet == that.followSocialProof.toSet &&
          favSocialProof.toSet == that.favSocialProof.toSet &&
          messageSocialProof.toSet == that.messageSocialProof.toSet
        case _ => false
      }
    }
  }

  implicit object SrcClusterIntermediateInfoSemigroup
      extends Semigroup[SrcClusterIntermediateInfo] {
    override def plus(
      left: SrcClusterIntermediateInfo,
      right: SrcClusterIntermediateInfo
    ): SrcClusterIntermediateInfo = {
      SrcClusterIntermediateInfo(
        followScore = left.followScore + right.followScore,
        followScoreProducerNormalized =
          left.followScoreProducerNormalized + right.followScoreProducerNormalized,
        favScore = left.favScore + right.favScore,
        favScoreProducerNormalized =
          left.favScoreProducerNormalized + right.favScoreProducerNormalized,
        logFavScore = left.logFavScore + right.logFavScore,
        logFavScoreProducerNormalized =
          left.logFavScoreProducerNormalized + right.logFavScoreProducerNormalized,
        messageScore = left.messageScore + right.messageScore,
        messageScoreProducerNormalized =
          left.messageScoreProducerNormalized + right.messageScoreProducerNormalized,
        followSocialProof =
          Semigroup.plus(left.followSocialProof, right.followSocialProof).distinct,
        favSocialProof = Semigroup.plus(left.favSocialProof, right.favSocialProof).distinct,
        messageSocialProof = Semigroup.plus(left.messageSocialProof, right.messageSocialProof).distinct
      )
    }
  }

  /**
   * @param adjacencyLists User-User follow/fav graph
   * @param knownFor KnownFor data set. Each user can be known for several clusters with certain
   *                 knownFor weights.
   * @param socialProofThreshold A user will only be interested in a cluster if they follow/fav at
   *                             least certain number of users known for this cluster.
   * @param uniqueId required for these Stat
   * @return
   */
  def userClusterPairsWithoutNormalization(
    adjacencyLists: TypedPipe[UserAndNeighbors],
    knownFor: TypedPipe[(Long, Array[(Int, Float)])],
    socialProofThreshold: Int
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[((Long, Int), SrcClusterIntermediateInfo)] = {
    userClusterPairsWithoutNormalizationWithTopicAlignment(
      adjacencyLists,
      knownFor,
      socialProofThreshold,
      topicAlignmentBoost = 1.0,
      existingInterestedIn = None
    )
  }

  /**
   * Enhanced version that supports topic-aligned boosting.
   * When User B follows/favorites/messages User A, this boosts clusters that are aligned between both users.
   * 
   * @param adjacencyLists User-User follow/fav graph
   * @param knownFor KnownFor data set. Each user can be known for several clusters with certain
   *                 knownFor weights.
   * @param socialProofThreshold A user will only be interested in a cluster if they follow/fav/message at
   *                             least certain number of users known for this cluster.
   * @param topicAlignmentBoost Multiplier to boost scores for clusters that are aligned between
   *                            User A (producer) and User B (consumer). Default 1.0 (no boost).
   *                            Set to > 1.0 to increase recommendations from User A to User B
   *                            for aligned topics.
   * @param existingInterestedIn Optional existing InterestedIn embeddings. If provided, clusters
   *                            that exist in both the producer's KnownFor and consumer's existing
   *                            InterestedIn will be boosted by topicAlignmentBoost.
   * @param messagingData Optional messaging interaction data. Format: (sourceUserId, destUserId, messageScore).
   *                     If provided, when User A messages User B, topic-aligned boosting will be applied
   *                     similar to follows/favorites. Message scores should be normalized (e.g., decayed counts).
   * @param uniqueId required for these Stat
   * @return
   */
  def userClusterPairsWithoutNormalizationWithTopicAlignment(
    adjacencyLists: TypedPipe[UserAndNeighbors],
    knownFor: TypedPipe[(Long, Array[(Int, Float)])],
    socialProofThreshold: Int,
    topicAlignmentBoost: Double = 1.0,
    existingInterestedIn: Option[TypedPipe[(UserId, ClustersUserIsInterestedIn)]] = None,
    messagingData: Option[TypedPipe[(UserId, UserId, Double)]] = None
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[((Long, Int), SrcClusterIntermediateInfo)] = {
    val edgesToUsersWithKnownFor = Stat("num_edges_to_users_with_known_for")
    val srcDestClusterTriples = Stat("num_src_dest_cluster_triples")
    val srcClusterPairsBeforeSocialProofThresholding =
      Stat("num_src_cluster_pairs_before_social_proof_thresholding")
    val srcClusterPairsAfterSocialProofThresholding =
      Stat("num_src_cluster_pairs_after_social_proof_thresholding")

    val edges = adjacencyLists.flatMap {
      case UserAndNeighbors(srcId, neighborsWithWeights) =>
        neighborsWithWeights.map { neighborWithWeights =>
          (
            neighborWithWeights.neighborId,
            neighborWithWeights.copy(neighborId = srcId)
          )
        }
    }

    // Join messaging data if provided
    // Format: (destUserId, (srcUserId, messageScore))
    val messagingEdges = messagingData match {
      case Some(messages) =>
        messages.map { case (srcId, destId, score) => (destId, (srcId, score)) }
      case None =>
        TypedPipe.empty[(UserId, (UserId, Double))]
    }

    // Build a map of consumer (User B) -> set of cluster IDs they're interested in
    // This is used to identify topic alignment between User A (producer) and User B (consumer)
    val consumerInterestedInClusters = existingInterestedIn match {
      case Some(interestedIn) =>
        interestedIn.flatMap {
          case (userId, clusters) =>
            clusters.clusterIdToScores.keys.map(clusterId => (userId, clusterId))
        }
        .group
        .toSet
        .toTypedPipe
      case None =>
        TypedPipe.empty[(UserId, Set[ClusterId])]
    }

    implicit val l2b: Long => Array[Byte] = Injection.long2BigEndian

    val edgesWithKnownFor = edges
      .sketch(4000)
      .join(knownFor)

    // Join edges with messaging data
    val edgesWithMessaging = edgesWithKnownFor
      .map { case (destId, (srcWithWeights, clusterArray)) =>
        (destId, (srcWithWeights.neighborId, srcWithWeights, clusterArray))
      }
      .leftJoin(messagingEdges)
      .map {
        case (destId, ((srcId, srcWithWeights, clusterArray), messagingOpt)) =>
          (destId, (srcId, srcWithWeights, clusterArray, messagingOpt))
      }

    // Join with consumer's existing InterestedIn clusters to check for topic alignment
    val edgesWithAlignment = if (existingInterestedIn.isDefined && topicAlignmentBoost > 1.0) {
      edgesWithMessaging
        .map { case (destId, (srcId, srcWithWeights, clusterArray, messagingOpt)) =>
          (srcId, (destId, srcWithWeights, clusterArray, messagingOpt))
        }
        .leftJoin(consumerInterestedInClusters)
        .map {
          case (srcId, ((destId, srcWithWeights, clusterArray, messagingOpt), consumerClustersOpt)) =>
            (destId, (srcWithWeights, clusterArray, consumerClustersOpt.getOrElse(Set.empty[ClusterId]), messagingOpt))
        }
    } else {
      edgesWithMessaging.map {
        case (destId, (srcId, srcWithWeights, clusterArray, messagingOpt)) =>
          (destId, (srcWithWeights, clusterArray, Set.empty[ClusterId], messagingOpt))
      }
    }

    edgesWithAlignment
      .flatMap {
        case (destId, (srcWithWeights, clusterArray, consumerClusters, messagingOpt)) =>
          edgesToUsersWithKnownFor.inc()
          clusterArray.toList.map {
            case (clusterId, knownForScoreF) =>
              val knownForScore = math.max(0.0, knownForScoreF.toDouble)

              // Check if this cluster is aligned between User A (producer) and User B (consumer)
              // Cluster is aligned if it exists in both:
              // 1. User A's KnownFor clusters (this clusterId)
              // 2. User B's existing InterestedIn clusters (consumerClusters)
              val isAligned = consumerClusters.contains(clusterId)
              val alignmentMultiplier = if (isAligned && topicAlignmentBoost > 1.0) {
                topicAlignmentBoost
              } else {
                1.0
              }

              // Get messaging score if available
              val (messageScore, messageScoreProducerNormalized, messageSocialProof) = messagingOpt match {
                case Some((srcId, msgScore)) if msgScore > 0.0 =>
                  // Apply topic alignment boost for messaging if clusters are aligned
                  val baseMessageScore = msgScore * knownForScore
                  val boostedMessageScore = baseMessageScore * alignmentMultiplier
                  // For producer normalization, we use a simple normalization (can be enhanced)
                  val normalizedMessageScore = boostedMessageScore * 0.1 // Simple normalization factor
                  (boostedMessageScore, normalizedMessageScore, List(destId))
                case _ =>
                  (0.0, 0.0, Nil)
              }

              srcDestClusterTriples.inc()
              val baseFollowScore =
                if (srcWithWeights.isFollowed.contains(true)) knownForScore else 0.0
              val followScore = baseFollowScore * alignmentMultiplier
              
              val baseFollowScoreProducerNormalizedOnly =
                srcWithWeights.followScoreNormalizedByNeighborFollowersL2.getOrElse(
                  0.0) * knownForScore
              val followScoreProducerNormalizedOnly = baseFollowScoreProducerNormalizedOnly * alignmentMultiplier
              
              val baseFavScore =
                srcWithWeights.favScoreHalfLife100Days.getOrElse(0.0) * knownForScore
              val favScore = baseFavScore * alignmentMultiplier

              val baseFavScoreProducerNormalizedOnly =
                srcWithWeights.favScoreHalfLife100DaysNormalizedByNeighborFaversL2.getOrElse(
                  0.0) * knownForScore
              val favScoreProducerNormalizedOnly = baseFavScoreProducerNormalizedOnly * alignmentMultiplier

              val baseLogFavScore = srcWithWeights.logFavScore.getOrElse(0.0) * knownForScore
              val logFavScore = baseLogFavScore * alignmentMultiplier

              val baseLogFavScoreProducerNormalizedOnly = srcWithWeights.logFavScoreL2Normalized
                .getOrElse(0.0) * knownForScore
              val logFavScoreProducerNormalizedOnly = baseLogFavScoreProducerNormalizedOnly * alignmentMultiplier

              val followSocialProof = if (srcWithWeights.isFollowed.contains(true)) {
                List(destId)
              } else Nil
              val favSocialProof = if (srcWithWeights.favScoreHalfLife100Days.exists(_ > 0)) {
                List(destId)
              } else Nil

              (
                (srcWithWeights.neighborId, clusterId),
                SrcClusterIntermediateInfo(
                  followScore,
                  followScoreProducerNormalizedOnly,
                  favScore,
                  favScoreProducerNormalizedOnly,
                  logFavScore,
                  logFavScoreProducerNormalizedOnly,
                  messageScore,
                  messageScoreProducerNormalized,
                  followSocialProof,
                  favSocialProof,
                  messageSocialProof
                )
              )
          }
      }
      .sumByKey
      .withReducers(10000)
      .filter {
        case ((_, _), SrcClusterIntermediateInfo(_, _, _, _, _, _, _, _, followProof, favProof, messageProof)) =>
          srcClusterPairsBeforeSocialProofThresholding.inc()
          val distinctSocialProof = (followProof ++ favProof ++ messageProof).toSet
          val result = distinctSocialProof.size >= socialProofThreshold
          if (result) {
            srcClusterPairsAfterSocialProofThresholding.inc()
          }
          result
      }
  }

  /**
   * Add the cluster-level l2 norm scores, and use them to normalize follow/fav scores.
   */
  def attachNormalizedScores(
    intermediate: TypedPipe[((Long, Int), SrcClusterIntermediateInfo)]
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(Long, List[(Int, UserToInterestedInClusterScores)])] = {

    def square(x: Double): Double = x * x

    val clusterCountsAndNorms =
      intermediate
        .map {
          case (
                (_, clusterId),
                SrcClusterIntermediateInfo(
                  followScore,
                  followScoreProducerNormalizedOnly,
                  favScore,
                  favScoreProducerNormalizedOnly,
                  logFavScore,
                  logFavScoreProducerNormalizedOnly,
                  messageScore,
                  messageScoreProducerNormalized,
                  _,
                  _,
                  _
                )
              ) =>
            (
              clusterId,
              (
                1,
                square(followScore),
                square(followScoreProducerNormalizedOnly),
                square(favScore),
                square(favScoreProducerNormalizedOnly),
                square(logFavScore),
                square(logFavScoreProducerNormalizedOnly),
                square(messageScore),
                square(messageScoreProducerNormalized)
              )
            )
        }
        .sumByKey
        //        .withReducers(100)
        .map {
          case (
                clusterId,
                (
                  cnt,
                  squareFollowScore,
                  squareFollowScoreProducerNormalizedOnly,
                  squareFavScore,
                  squareFavScoreProducerNormalizedOnly,
                  squareLogFavScore,
                  squareLogFavScoreProducerNormalizedOnly
                )) =>
            (
              clusterId,
              (
                cnt,
                math.sqrt(squareFollowScore),
                math.sqrt(squareFollowScoreProducerNormalizedOnly),
                math.sqrt(squareFavScore),
                math.sqrt(squareFavScoreProducerNormalizedOnly),
                math.sqrt(squareLogFavScore),
                math.sqrt(squareLogFavScoreProducerNormalizedOnly)
              ))
        }

    implicit val i2b: Int => Array[Byte] = Injection.int2BigEndian

    intermediate
      .map {
        case ((srcId, clusterId), clusterScoresTuple) =>
          (clusterId, (srcId, clusterScoresTuple))
      }
      .sketch(reducers = 900)
      .join(clusterCountsAndNorms)
      .map {
        case (
              clusterId,
              (
                (
                  srcId,
                  SrcClusterIntermediateInfo(
                    followScore,
                    followScoreProducerNormalizedOnly,
                    favScore,
                    favScoreProducerNormalizedOnly,
                    logFavScore,
                    logFavScoreProducerNormalizedOnly, // not used for now
                    messageScore,
                    messageScoreProducerNormalized,
                    followProof,
                    favProof,
                    messageProof
                  )
                ),
                (
                  cnt,
                  followNorm,
                  followProducerNormalizedNorm,
                  favNorm,
                  favProducerNormalizedNorm,
                  logFavNorm,
                  logFavProducerNormalizedNorm, // not used for now
                  messageNorm,
                  messageProducerNormalizedNorm
                )
              )
            ) =>
          (
            srcId,
            List(
              (
                clusterId,
                UserToInterestedInClusterScores(
                  followScore = Some(ifNanMake0(followScore)),
                  followScoreClusterNormalizedOnly = Some(ifNanMake0(followScore / followNorm)),
                  followScoreProducerNormalizedOnly =
                    Some(ifNanMake0(followScoreProducerNormalizedOnly)),
                  followScoreClusterAndProducerNormalized = Some(
                    ifNanMake0(followScoreProducerNormalizedOnly / followProducerNormalizedNorm)),
                  favScore = Some(ifNanMake0(favScore)),
                  favScoreClusterNormalizedOnly = Some(ifNanMake0(favScore / favNorm)),
                  favScoreProducerNormalizedOnly = Some(ifNanMake0(favScoreProducerNormalizedOnly)),
                  favScoreClusterAndProducerNormalized =
                    Some(ifNanMake0(favScoreProducerNormalizedOnly / favProducerNormalizedNorm)),
                  usersBeingFollowed = Some(followProof),
                  usersThatWereFaved = Some(favProof),
                  numUsersInterestedInThisClusterUpperBound = Some(cnt),
                  logFavScore = Some(ifNanMake0(logFavScore)),
                  logFavScoreClusterNormalizedOnly = Some(ifNanMake0(logFavScore / logFavNorm))
                ))
            )
          )
      }
      .sumByKey
      //      .withReducers(1000)
      .toTypedPipe
  }

  /**
   * aggregate cluster scores for each user, to be used instead of attachNormalizedScores
   * when we donot want to compute cluster-level l2 norm scores
   */
  def groupClusterScores(
    intermediate: TypedPipe[((Long, Int), SrcClusterIntermediateInfo)]
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(Long, List[(Int, UserToInterestedInClusterScores)])] = {

    intermediate
      .map {
        case (
              (srcId, clusterId),
              SrcClusterIntermediateInfo(
                followScore,
                followScoreProducerNormalizedOnly,
                favScore,
                favScoreProducerNormalizedOnly,
                logFavScore,
                logFavScoreProducerNormalizedOnly,
                messageScore,
                messageScoreProducerNormalized,
                followProof,
                favProof,
                messageProof
              )
            ) =>
          (
            srcId,
            List(
              (
                clusterId,
                UserToInterestedInClusterScores(
                  followScore = Some(ifNanMake0(followScore)),
                  followScoreProducerNormalizedOnly =
                    Some(ifNanMake0(followScoreProducerNormalizedOnly)),
                  favScore = Some(ifNanMake0(favScore)),
                  favScoreProducerNormalizedOnly = Some(ifNanMake0(favScoreProducerNormalizedOnly)),
                  usersBeingFollowed = Some(followProof),
                  usersThatWereFaved = Some(favProof),
                  logFavScore = Some(ifNanMake0(logFavScore)),
                ))
            )
          )
      }
      .sumByKey
      .withReducers(1000)
      .toTypedPipe
  }

  /**
   * For each user, only keep up to a certain number of clusters.
   * @param allInterests user with a list of interestedIn clusters.
   * @param maxClustersPerUser number of clusters to keep for each user
   * @param knownForModelVersion known for model version
   * @param uniqueId required for these Stat
   * @return
   */
  def keepOnlyTopClusters(
    allInterests: TypedPipe[(Long, List[(Int, UserToInterestedInClusterScores)])],
    maxClustersPerUser: Int,
    knownForModelVersion: String
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(Long, ClustersUserIsInterestedIn)] = {
    val userClusterPairsBeforeUserTruncation =
      Stat("num_user_cluster_pairs_before_user_truncation")
    val userClusterPairsAfterUserTruncation =
      Stat("num_user_cluster_pairs_after_user_truncation")
    val usersWithALotOfClusters =
      Stat(s"num_users_with_more_than_${maxClustersPerUser}_clusters")

    allInterests
      .map {
        case (srcId, fullClusterList) =>
          userClusterPairsBeforeUserTruncation.incBy(fullClusterList.size)
          val truncatedClusters = if (fullClusterList.size > maxClustersPerUser) {
            usersWithALotOfClusters.inc()
            fullClusterList
              .sortBy {
                case (_, clusterScores) =>
                  (
                    -clusterScores.favScore.getOrElse(0.0),
                    -clusterScores.logFavScore.getOrElse(0.0),
                    -clusterScores.followScore.getOrElse(0.0),
                    -clusterScores.logFavScoreClusterNormalizedOnly.getOrElse(0.0),
                    -clusterScores.followScoreProducerNormalizedOnly.getOrElse(0.0)
                  )
              }
              .take(maxClustersPerUser)
          } else {
            fullClusterList
          }
          userClusterPairsAfterUserTruncation.incBy(truncatedClusters.size)
          (srcId, ClustersUserIsInterestedIn(knownForModelVersion, truncatedClusters.toMap))
      }
  }

  def run(
    adjacencyLists: TypedPipe[UserAndNeighbors],
    knownFor: TypedPipe[(UserId, Array[(ClusterId, Float)])],
    socialProofThreshold: Int,
    maxClustersPerUser: Int,
    knownForModelVersion: String
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    keepOnlyTopClusters(
      attachNormalizedScores(
        userClusterPairsWithoutNormalization(
          adjacencyLists,
          knownFor,
          socialProofThreshold
        )
      ),
      maxClustersPerUser,
      knownForModelVersion
    )
  }

  /**
   * Enhanced run method that supports topic-aligned boosting.
   * When User B follows/favorites User A, this boosts clusters that are aligned between both users,
   * resulting in User B receiving more recommendations from User A for aligned topics.
   * 
   * @param adjacencyLists User-User follow/fav graph
   * @param knownFor KnownFor data set
   * @param socialProofThreshold Minimum number of users to follow/fav for a cluster to be considered
   * @param maxClustersPerUser Maximum number of clusters to keep per user
   * @param knownForModelVersion Model version string
   * @param topicAlignmentBoost Multiplier to boost scores for aligned clusters (default 1.0 = no boost).
   *                            Recommended values: 1.5-2.0 for moderate boost, 2.0-3.0 for strong boost.
   * @param existingInterestedIn Optional existing InterestedIn embeddings. If provided, clusters
   *                            that exist in both the producer's KnownFor and consumer's existing
   *                            InterestedIn will be boosted. This enables iterative refinement where
   *                            you can use a previous run's output as input for alignment.
   * @param uniqueId required for these Stat
   * @return
   */
  def runWithTopicAlignment(
    adjacencyLists: TypedPipe[UserAndNeighbors],
    knownFor: TypedPipe[(UserId, Array[(ClusterId, Float)])],
    socialProofThreshold: Int,
    maxClustersPerUser: Int,
    knownForModelVersion: String,
    topicAlignmentBoost: Double = 1.5,
    existingInterestedIn: Option[TypedPipe[(UserId, ClustersUserIsInterestedIn)]] = None,
    messagingData: Option[TypedPipe[(UserId, UserId, Double)]] = None
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    keepOnlyTopClusters(
      attachNormalizedScores(
        userClusterPairsWithoutNormalizationWithTopicAlignment(
          adjacencyLists,
          knownFor,
          socialProofThreshold,
          topicAlignmentBoost,
          existingInterestedIn,
          messagingData
        )
      ),
      maxClustersPerUser,
      knownForModelVersion
    )
  }

  /**
   * run the interestedIn job, cluster normalized scores are not attached to user's clusters.
   */
  def runWithoutClusterNormalizedScores(
    adjacencyLists: TypedPipe[UserAndNeighbors],
    knownFor: TypedPipe[(UserId, Array[(ClusterId, Float)])],
    socialProofThreshold: Int,
    maxClustersPerUser: Int,
    knownForModelVersion: String
  )(
    implicit uniqueId: UniqueID
  ): TypedPipe[(UserId, ClustersUserIsInterestedIn)] = {
    keepOnlyTopClusters(
      groupClusterScores(
        userClusterPairsWithoutNormalization(
          adjacencyLists,
          knownFor,
          socialProofThreshold
        )
      ),
      maxClustersPerUser,
      knownForModelVersion
    )
  }

  /**
   * print out some basic stats of the data set to make sure things are not broken
   */
  def dataSetStats(
    interestedInData: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    dataSetName: String = ""
  ): Execution[Unit] = {

    Execution
      .zip(
        Util.printSummaryOfNumericColumn(
          interestedInData.map {
            case (user, interestedIn) =>
              interestedIn.clusterIdToScores.size
          },
          Some(s"$dataSetName UserInterestedIn Size")
        ),
        Util.printSummaryOfNumericColumn(
          interestedInData.flatMap {
            case (user, interestedIn) =>
              interestedIn.clusterIdToScores.map {
                case (_, scores) =>
                  scores.favScore.getOrElse(0.0)
              }
          },
          Some(s"$dataSetName UserInterestedIn favScore")
        ),
        Util.printSummaryOfNumericColumn(
          interestedInData.flatMap {
            case (user, interestedIn) =>
              interestedIn.clusterIdToScores.map {
                case (_, scores) =>
                  scores.favScoreClusterNormalizedOnly.getOrElse(0.0)
              }
          },
          Some(s"$dataSetName UserInterestedIn favScoreClusterNormalizedOnly")
        ),
        Util.printSummaryOfNumericColumn(
          interestedInData.flatMap {
            case (user, interestedIn) =>
              interestedIn.clusterIdToScores.map {
                case (_, scores) =>
                  scores.logFavScoreClusterNormalizedOnly.getOrElse(0.0)
              }
          },
          Some(s"$dataSetName UserInterestedIn logFavScoreClusterNormalizedOnly")
        )
      ).unit
  }
}
