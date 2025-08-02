package com.twitter.simclusters_v2.scalding.embedding.producer

import com.twitter.scalding._
import com.twitter.scalding_internal.multiformat.format.keyval.KeyVal
import com.twitter.scalding_internal.source.lzo_scrooge.FitwitteredPathLzoScrooge
import com.twitter.simclusters_v2.hdfs_sources.{DataSources, InterestedInSources}
import com.twitter.simclusters_v2.scalding.common.matritwitter.{SparseMatritwitter, SparseRowMatritwitter}
import com.twitter.simclusters_v2.scalding.embedding.ProducerEmbeddingsFromInterestedIn
import com.twitter.simclusters_v2.scalding.embedding.common.EmbeddingUtil.{
  ClusterId,
  ProducerId,
  UserId
}
import com.twitter.simclusters_v2.scalding.embedding.common.SimClustersEmbeddingBaseJob
import com.twitter.simclusters_v2.thriftscala.{EmbeddingType, _}
import java.util.TimeZone

/**
 * This file implements a new Producer SimClusters Embeddings.
 * The differences with etwitteristing producer embeddings are:
 *
 * 1) the embedding scores are not normalized, so that one can aggregate multiple producer embeddings by adding them.
 * 2) we use log-fav scores in the user-producer graph and user-simclusters graph.
 * LogFav scores are smoother than fav scores we previously used and they are less sensitive to outliers
 *
 *
 *
 *  The main difference with other normalized embeddings is the `convertEmbeddingToAggregatableEmbeddings` function
 *  where we multiply the normalized embedding with producer's norms. The resulted embeddings are then
 *  unnormalized and aggregatable.
 *
 */
trait AggregatableProducerEmbeddingsBaseApp etwittertends SimClustersEmbeddingBaseJob[ProducerId] {

  val userToProducerScoringFn: NeighborWithWeights => Double
  val userToClusterScoringFn: UserToInterestedInClusterScores => Double
  val modelVersion: ModelVersion

  // Minimum engagement threshold
  val minNumFavers: Int = ProducerEmbeddingsFromInterestedIn.minNumFaversForProducer

  override def numClustersPerNoun: Int = 60

  override def numNounsPerClusters: Int = 500 // this is not used for now

  override def thresholdForEmbeddingScores: Double = 0.01

  override def prepareNounToUserMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseMatritwitter[ProducerId, UserId, Double] = {

    SparseMatritwitter(
      ProducerEmbeddingsFromInterestedIn
        .getFilteredUserUserNormalizedGraph(
          DataSources.userUserNormalizedGraphSource,
          DataSources.userNormsAndCounts,
          userToProducerScoringFn,
          _.faverCount.etwitterists(
            _ > minNumFavers
          )
        )
        .map {
          case (userId, (producerId, score)) =>
            (producerId, userId, score)
        })
  }

  override def prepareUserToClusterMatritwitter(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): SparseRowMatritwitter[UserId, ClusterId, Double] = {
    SparseRowMatritwitter(
      ProducerEmbeddingsFromInterestedIn
        .getUserSimClustersMatritwitter(
          InterestedInSources
            .simClustersInterestedInSource(modelVersion, dateRange.embiggen(Days(5)), timeZone),
          userToClusterScoringFn,
          modelVersion
        )
        .mapValues(_.toMap),
      isSkinnyMatritwitter = true
    )
  }

  // in order to make the embeddings aggregatable, we need to revert the normalization
  // (multiply the norms) we did when computing embeddings in the base job.
  def convertEmbeddingToAggregatableEmbeddings(
    embeddings: TypedPipe[(ProducerId, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): TypedPipe[(ProducerId, Seq[(ClusterId, Double)])] = {
    embeddings.join(prepareNounToUserMatritwitter.rowL2Norms).map {
      case (producerId, (embeddingVec, norm)) =>
        producerId -> embeddingVec.map {
          case (id, score) => (id, score * norm)
        }
    }
  }

  override final def writeClusterToNounsIndetwitter(
    output: TypedPipe[(ClusterId, Seq[(ProducerId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = { Etwitterecution.unit } // we do not need this for now

  /**
   * Override this method to write the manhattan dataset.
   */
  def writeToManhattan(
    output: TypedPipe[KeyVal[SimClustersEmbeddingId, SimClustersEmbedding]]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit]

  /**
   * Override this method to writethrough the thrift dataset.
   */
  def writeToThrift(
    output: TypedPipe[SimClustersEmbeddingWithId]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit]

  val embeddingType: EmbeddingType

  override final def writeNounToClustersIndetwitter(
    output: TypedPipe[(ProducerId, Seq[(ClusterId, Double)])]
  )(
    implicit dateRange: DateRange,
    timeZone: TimeZone,
    uniqueID: UniqueID
  ): Etwitterecution[Unit] = {
    val convertedEmbeddings = convertEmbeddingToAggregatableEmbeddings(output)
      .map {
        case (producerId, topSimClustersWithScore) =>
          val id = SimClustersEmbeddingId(
            embeddingType = embeddingType,
            modelVersion = modelVersion,
            internalId = InternalId.UserId(producerId))

          val embeddings = SimClustersEmbedding(topSimClustersWithScore.map {
            case (clusterId, score) => SimClusterWithScore(clusterId, score)
          })

          SimClustersEmbeddingWithId(id, embeddings)
      }

    val keyValuePairs = convertedEmbeddings.map { simClustersEmbeddingWithId =>
      KeyVal(simClustersEmbeddingWithId.embeddingId, simClustersEmbeddingWithId.embedding)
    }
    val manhattanEtwitterecution = writeToManhattan(keyValuePairs)

    val thriftEtwitterecution = writeToThrift(convertedEmbeddings)

    Etwitterecution.zip(manhattanEtwitterecution, thriftEtwitterecution).unit
  }
}
