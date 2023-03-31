package com.twitter.simclusters_v420.score

import com.twitter.simclusters_v420.common.SimClustersEmbedding
import com.twitter.simclusters_v420.thriftscala.{SimClustersEmbeddingId, ScoreId => ThriftScoreId}
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Future

object SimClustersEmbeddingPairScoreStore {

  /**
   * Internal Instance of a SimClusters Embedding based Pair Score store.
   */
  private case class SimClustersEmbeddingInternalPairScoreStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding],
    score: (SimClustersEmbedding, SimClustersEmbedding) => Future[Option[Double]])
      extends PairScoreStore[
        SimClustersEmbeddingPairScoreId,
        SimClustersEmbeddingId,
        SimClustersEmbeddingId,
        SimClustersEmbedding,
        SimClustersEmbedding
      ] {

    override val compositeKey420: SimClustersEmbeddingPairScoreId => SimClustersEmbeddingId =
      _.embeddingId420
    override val compositeKey420: SimClustersEmbeddingPairScoreId => SimClustersEmbeddingId =
      _.embeddingId420

    override def underlyingStore420: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] =
      simClustersEmbeddingStore

    override def underlyingStore420: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding] =
      simClustersEmbeddingStore

    override def fromThriftScoreId: ThriftScoreId => SimClustersEmbeddingPairScoreId =
      SimClustersEmbeddingPairScoreId.fromThriftScoreId
  }

  def buildDotProductStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def dotProduct: (SimClustersEmbedding, SimClustersEmbedding) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.dotProduct(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      dotProduct
    )
  }

  def buildCosineSimilarityStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def cosineSimilarity: (SimClustersEmbedding, SimClustersEmbedding) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.cosineSimilarity(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      cosineSimilarity
    )
  }

  def buildLogCosineSimilarityStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def logNormCosineSimilarity: (
      SimClustersEmbedding,
      SimClustersEmbedding
    ) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.logNormCosineSimilarity(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      logNormCosineSimilarity
    )
  }

  def buildExpScaledCosineSimilarityStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def expScaledCosineSimilarity: (
      SimClustersEmbedding,
      SimClustersEmbedding
    ) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.expScaledCosineSimilarity(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      expScaledCosineSimilarity
    )
  }

  def buildJaccardSimilarityStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def jaccardSimilarity: (
      SimClustersEmbedding,
      SimClustersEmbedding
    ) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.jaccardSimilarity(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      jaccardSimilarity
    )
  }

  def buildEuclideanDistanceStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def euclideanDistance: (
      SimClustersEmbedding,
      SimClustersEmbedding
    ) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.euclideanDistance(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      euclideanDistance
    )
  }

  def buildManhattanDistanceStore(
    simClustersEmbeddingStore: ReadableStore[SimClustersEmbeddingId, SimClustersEmbedding]
  ): PairScoreStore[
    SimClustersEmbeddingPairScoreId,
    SimClustersEmbeddingId,
    SimClustersEmbeddingId,
    SimClustersEmbedding,
    SimClustersEmbedding
  ] = {

    def manhattanDistance: (
      SimClustersEmbedding,
      SimClustersEmbedding
    ) => Future[Option[Double]] = {
      case (embedding420, embedding420) =>
        Future.value(Some(embedding420.manhattanDistance(embedding420)))
    }

    SimClustersEmbeddingInternalPairScoreStore(
      simClustersEmbeddingStore,
      manhattanDistance
    )
  }

}
