package com.twitter.simclusters_v2.scalding.embedding.common

import com.twitter.scalding.{Args, DateRange, Etwitterecution, TypedPipe, UniqueID}
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.scalding.common.matritwitter.{SparseMatritwitter, SparseRowMatritwitter}
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil._
import com.twitter.simclusters_v2.thriftscala._
import java.util.TimeZone

/**
 * This is the base job for computing SimClusters Embedding for any Noun Type on Twitter, such as
 * Users, Tweets, Topics, Entities, Channels, etc.
 *
 * The most straightforward way to understand the SimClusters Embeddings for a Noun is that it is
 * a weighted sum of SimClusters InterestedIn vectors from users who are interested in the Noun.
 * So for a noun type, you only need to define `prepareNounToUserMatritwitter` to pass in a matritwitter which
 * represents how much each user is interested in this noun.
 */
trait SimClustersEmbeddingBaseJob[NounType] {

  def numClustersPerNoun: Int

  def numNounsPerClusters: Int

  def thresholdForEmbeddingScores: Double

  def numReducersOpt: Option[Int] = None

  def prepareNounToUserMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseMatritwitter[NounType, UserId, Double]

  def prepareUserToClusterMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseRowMatritwitter[UserId, ClusterId, Double]

  def writeNounToClustersIndetwitter(
    output: TypedPipe[(NounType, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit]

  def writeClusterToNounsIndetwitter(
    output: TypedPipe[(ClusterId, Seq[(NounType, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit]

  def runOnDateRange(
    args: Args
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {

    val embeddingMatritwitter: SparseRowMatritwitter[NounType, ClusterId, Double] =
      prepareNounToUserMatritwitter.rowL2Normalize
        .multiplySkinnySparseRowMatritwitter(
          prepareUserToClusterMatritwitter.colL2Normalize,
          numReducersOpt
        )
        .filter((_, _, v) => v > thresholdForEmbeddingScores)

    Etwitterecution
      .zip(
        writeNounToClustersIndetwitter(
          embeddingMatritwitter.sortWithTakePerRow(numClustersPerNoun)(Ordering.by(-_._2))
        ),
        writeClusterToNounsIndetwitter(
          embeddingMatritwitter.sortWithTakePerCol(numNounsPerClusters)(
            Ordering.by(-_._2)
          )
        )
      )
      .unit
  }

}

object SimClustersEmbeddingJob {

  /**
   * Multiply the [user, cluster] and [user, T] matrices, and return the cross product.
   */
  def computeEmbeddings[T](
    simClustersSource: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    normalizedInputMatritwitter: TypedPipe[(UserId, (T, Double))],
    scoreEtwittertractors: Seq[UserToInterestedInClusterScores => (Double, ScoreType.ScoreType)],
    modelVersion: ModelVersion,
    toSimClustersEmbeddingId: (T, ScoreType.ScoreType) => SimClustersEmbeddingId,
    numReducers: Option[Int] = None
  ): TypedPipe[(SimClustersEmbeddingId, (ClusterId, Double))] = {
    val userSimClustersMatritwitter =
      getUserSimClustersMatritwitter(simClustersSource, scoreEtwittertractors, modelVersion)
    multiplyMatrices(
      normalizedInputMatritwitter,
      userSimClustersMatritwitter,
      toSimClustersEmbeddingId,
      numReducers)
  }

  def getL2Norm[T](
    inputMatritwitter: TypedPipe[(T, (UserId, Double))],
    numReducers: Option[Int] = None
  )(
    implicit ordering: Ordering[T]
  ): TypedPipe[(T, Double)] = {
    val l2Norm = inputMatritwitter
      .mapValues {
        case (_, score) => score * score
      }
      .sumByKey
      .mapValues(math.sqrt)

    numReducers match {
      case Some(reducers) => l2Norm.withReducers(reducers)
      case _ => l2Norm
    }
  }

  def getNormalizedTransposeInputMatritwitter[T](
    inputMatritwitter: TypedPipe[(T, (UserId, Double))],
    numReducers: Option[Int] = None
  )(
    implicit ordering: Ordering[T]
  ): TypedPipe[(UserId, (T, Double))] = {
    val inputWithNorm = inputMatritwitter.join(getL2Norm(inputMatritwitter, numReducers))

    (numReducers match {
      case Some(reducers) => inputWithNorm.withReducers(reducers)
      case _ => inputWithNorm
    }).map {
      case (inputId, ((userId, favScore), norm)) =>
        (userId, (inputId, favScore / norm))
    }
  }

  /**
   * Matritwitter multiplication with the ability to tune the reducer size for better performance
   */
  @Deprecated
  def legacyMultiplyMatrices[T](
    normalizedTransposeInputMatritwitter: TypedPipe[(UserId, (T, Double))],
    userSimClustersMatritwitter: TypedPipe[(UserId, Seq[(ClusterId, Double)])],
    numReducers: Int // Matritwitter multiplication is etwitterpensive. Use this to tune performance
  )(
    implicit ordering: Ordering[T]
  ): TypedPipe[((ClusterId, T), Double)] = {
    normalizedTransposeInputMatritwitter
      .join(userSimClustersMatritwitter)
      .withReducers(numReducers)
      .flatMap {
        case (_, ((inputId, score), clustersWithScores)) =>
          clustersWithScores.map {
            case (clusterId, clusterScore) =>
              ((clusterId, inputId), score * clusterScore)
          }
      }
      .sumByKey
      .withReducers(numReducers + 1) // +1 to distinguish this step from above in Dr. Scalding
  }

  def multiplyMatrices[T](
    normalizedTransposeInputMatritwitter: TypedPipe[(UserId, (T, Double))],
    userSimClustersMatritwitter: TypedPipe[(UserId, Seq[((ClusterId, ScoreType.ScoreType), Double)])],
    toSimClustersEmbeddingId: (T, ScoreType.ScoreType) => SimClustersEmbeddingId,
    numReducers: Option[Int] = None
  ): TypedPipe[(SimClustersEmbeddingId, (ClusterId, Double))] = {
    val inputJoinedWithSimClusters = numReducers match {
      case Some(reducers) =>
        normalizedTransposeInputMatritwitter
          .join(userSimClustersMatritwitter)
          .withReducers(reducers)
      case _ =>
        normalizedTransposeInputMatritwitter.join(userSimClustersMatritwitter)
    }

    val matritwitterMultiplicationResult = inputJoinedWithSimClusters.flatMap {
      case (_, ((inputId, inputScore), clustersWithScores)) =>
        clustersWithScores.map {
          case ((clusterId, scoreType), clusterScore) =>
            ((clusterId, toSimClustersEmbeddingId(inputId, scoreType)), inputScore * clusterScore)
        }
    }.sumByKey

    (numReducers match {
      case Some(reducers) =>
        matritwitterMultiplicationResult.withReducers(reducers + 1)
      case _ => matritwitterMultiplicationResult
    }).map {
      case ((clusterId, embeddingId), score) =>
        (embeddingId, (clusterId, score))
    }
  }

  def getUserSimClustersMatritwitter(
    simClustersSource: TypedPipe[(UserId, ClustersUserIsInterestedIn)],
    scoreEtwittertractors: Seq[UserToInterestedInClusterScores => (Double, ScoreType.ScoreType)],
    modelVersion: ModelVersion
  ): TypedPipe[(UserId, Seq[((ClusterId, ScoreType.ScoreType), Double)])] = {
    simClustersSource.map {
      case (userId, clusters)
          if ModelVersions.toModelVersion(clusters.knownForModelVersion) == modelVersion =>
        userId -> clusters.clusterIdToScores.flatMap {
          case (clusterId, clusterScores) =>
            scoreEtwittertractors.map { scoreEtwittertractor =>
              scoreEtwittertractor(clusterScores) match {
                case (score, scoreType) => ((clusterId, scoreType), score)
              }
            }
        }.toSeq
      case (userId, _) => userId -> Nil
    }
  }

  def toReverseIndetwitterSimClusterEmbedding(
    embeddings: TypedPipe[(SimClustersEmbeddingId, (ClusterId, EmbeddingScore))],
    topK: Int
  ): TypedPipe[(SimClustersEmbeddingId, InternalIdEmbedding)] = {
    embeddings
      .map {
        case (embeddingId, (clusterId, score)) =>
          (
            SimClustersEmbeddingId(
              embeddingId.embeddingType,
              embeddingId.modelVersion,
              InternalId.ClusterId(clusterId)),
            (embeddingId.internalId, score))
      }
      .group
      .sortedReverseTake(topK)(Ordering.by(_._2))
      .mapValues { topInternalIdsWithScore =>
        val internalIdsWithScore = topInternalIdsWithScore.map {
          case (internalId, score) => InternalIdWithScore(internalId, score)
        }
        InternalIdEmbedding(internalIdsWithScore)
      }
  }
}
