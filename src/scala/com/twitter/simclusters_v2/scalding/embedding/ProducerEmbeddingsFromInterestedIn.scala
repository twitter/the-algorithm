package com.twitter.simclusters_v2.scalding.embedding

import com.twitter.dal.client.dataset.KeyValDALDataset
import com.twitter.scalding._
import com.twitter.scalding_internal.dalv2.DALWrite._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.hdfs_sources._
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingJob
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.wtf.scalding.jobs.common.{AdhocExecutionApp, ScheduledExecutionApp}
import java.util.TimeZone

/**
 * Computes the producer's interestedIn cluster embedding. i.e. If a tweet author (producer) is not
 * associated with a KnownFor cluster, do a cross-product between
 * [user, interestedIn] and [user, producer] to find the similarity matrix [interestedIn, producer].
 */
object ProducerEmbeddingsFromInterestedIn {
  val minNumFollowersForProducer: Int = 100
  val minNumFaversForProducer: Int = 100
  val topKUsersToKeep: Int = 300
  val topKClustersToKeep: Int = 60
  val cosineSimilarityThreshold: Double = 0.01

  type ClusterId = Int

  def topProducersToThrift(producersWithScore: Seq[(UserId, Double)]): TopProducersWithScore = {
    val thrift = producersWithScore.map { producer =>
      TopProducerWithScore(producer._1, producer._2)
    }
    TopProducersWithScore(thrift)
  }

  def userToProducerFavScore(neighbor: NeighborWithWeights): Double = {
    neighbor.favScoreHalfLife100DaysNormalizedByNeighborFaversL2.getOrElse(0.0)
  }

  def userToProducerFollowScore(neighbor: NeighborWithWeights): Double = {
    neighbor.followScoreNormalizedByNeighborFollowersL2.getOrElse(0.0)
  }

  def userToClusterFavScore(clusterScore: UserToInterestedInClusterScores): Double = {
    clusterScore.favScoreClusterNormalizedOnly.getOrElse(0.0)
  }

  def userToClusterFollowScore(clusterScore: UserToInterestedInClusterScores): Double = {
    clusterScore.followScoreClusterNormalizedOnly.getOrElse(0.0)
  }

  def getUserSimClustersMatrix(
    simClustersSource: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    extractScore: UserToInterestedInClusterScores => Double,
    modelVersion: ModelVersion
  ): TypedPipe[(UserId, Seq[(Int, Double)])] = {
    simClustersSource.collect {
      case (userId, clusters)
          if ModelVersions.toModelVersion(clusters.knownForModelVersion).equals(modelVersion) =>
        userId -> clusters.clusterIdToScores
          .map {
            case (clusterId, clusterScores) =>
              (clusterId, extractScore(clusterScores))
          }.toSeq.filter(_._2 > 0)
    }
  }

  /**
   * Given a weighted user-producer engagement history matrix, as well as a
   * weighted user-interestedInCluster matrix, do the matrix multiplication to yield a weighted
   * producer-cluster embedding matrix
   */
  def getProducerClusterEmbedding(
    interestedInClusters: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    userProducerEngagementGraph: TypedPipe[UserAndNeighbors],
    userNormsAndCounts: TypedPipe[NormsAndCounts],
    userToProducerScoringFn: NeighborWithWeights => Double,
    userToClusterScoringFn: UserToInterestedInClusterScores => Double,
    userFilter: NormsAndCounts => Boolean, // function to decide whether to compute embeddings for the user or not
    numReducersForMatrixMultiplication: Int,
    modelVersion: ModelVersion,
    threshold: Double
  )(
    implicit uid: UniqueID
  ): TypedPipe[((ClusterId, UserId), Double)] = {
    val userSimClustersMatrix = getUserSimClustersMatrix(
      interestedInClusters,
      userToClusterScoringFn,
      modelVersion
    )

    val userUserNormalizedGraph = getFilteredUserUserNormalizedGraph(
      userProducerEngagementGraph,
      userNormsAndCounts,
      userToProducerScoringFn,
      userFilter
    )

    SimClustersEmbeddingJob
      .legacyMultiplyMatrices(
        userUserNormalizedGraph,
        userSimClustersMatrix,
        numReducersForMatrixMultiplication
      )
      .filter(_._2 >= threshold)
  }

  def getFilteredUserUserNormalizedGraph(
    userProducerEngagementGraph: TypedPipe[UserAndNeighbors],
    userNormsAndCounts: TypedPipe[NormsAndCounts],
    userToProducerScoringFn: NeighborWithWeights => Double,
    userFilter: NormsAndCounts => Boolean
  )(
    implicit uid: UniqueID
  ): TypedPipe[(UserId, (UserId, Double))] = {
    val numUsersCount = Stat("num_users_with_engagements")
    val userUserFilteredEdgeCount = Stat("num_filtered_user_user_engagements")
    val validUsersCount = Stat("num_valid_users")

    val validUsers = userNormsAndCounts.collect {
      case user if userFilter(user) =>
        validUsersCount.inc()
        user.userId
    }

    userProducerEngagementGraph
      .flatMap { userAndNeighbors =>
        numUsersCount.inc()
        userAndNeighbors.neighbors
          .map { neighbor =>
            userUserFilteredEdgeCount.inc()
            (neighbor.neighborId, (userAndNeighbors.userId, userToProducerScoringFn(neighbor)))
          }
          .filter(_._2._2 > 0.0)
      }
      .join(validUsers.asKeys)
      .map {
        case (neighborId, ((userId, score), _)) =>
          (userId, (neighborId, score))
      }
  }

  def fromSimClusterEmbedding[T, E](
    resultMatrix: TypedPipe[((ClusterId, T), Double)],
    topK: Int,
    modelVersion: ModelVersion
  ): TypedPipe[(PersistedFullClusterId, Seq[(T, Double)])] = {
    resultMatrix
      .map {
        case ((clusterId, inputId), score) => (clusterId, (inputId, score))
      }
      .group
      .sortedReverseTake(topK)(Ordering.by(_._2))
      .map {
        case (clusterId, topEntitiesWithScore) =>
          PersistedFullClusterId(modelVersion, clusterId) -> topEntitiesWithScore
      }
  }

  def toSimClusterEmbedding[T](
    resultMatrix: TypedPipe[((ClusterId, T), Double)],
    topK: Int,
    modelVersion: ModelVersion
  )(
    implicit ordering: Ordering[T]
  ): TypedPipe[(T, TopSimClustersWithScore)] = {
    resultMatrix
      .map {
        case ((clusterId, inputId), score) => (inputId, (clusterId, score))
      }
      .group
      //.withReducers(3000) // uncomment for producer-simclusters job
      .sortedReverseTake(topK)(Ordering.by(_._2))
      .map {
        case (inputId, topSimClustersWithScore) =>
          val topSimClusters = topSimClustersWithScore.map {
            case (clusterId, score) => SimClusterWithScore(clusterId, score)
          }
          inputId -> TopSimClustersWithScore(topSimClusters, modelVersion)
      }
  }
}
