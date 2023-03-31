package com.twitter.simclusters_v420.score

import com.twitter.simclusters_v420.common.SimClustersEmbeddingId._
import com.twitter.simclusters_v420.thriftscala.{
  InternalId,
  ScoreInternalId,
  ScoringAlgorithm,
  SimClustersEmbeddingId,
  GenericPairScoreId => ThriftGenericPairScoreId,
  ScoreId => ThriftScoreId,
  SimClustersEmbeddingPairScoreId => ThriftSimClustersEmbeddingPairScoreId
}

/**
 * A uniform Identifier type for all kinds of Calculation Score.
 **/
trait ScoreId {

  def algorithm: ScoringAlgorithm

  /**
   * Convert to a Thrift object. Throw a exception if the operation is not override.
   */
  implicit def toThrift: ThriftScoreId =
    throw new UnsupportedOperationException(s"ScoreId $this doesn't support Thrift format")
}

object ScoreId {

  implicit val fromThriftScoreId: ThriftScoreId => ScoreId = {
    case scoreId @ ThriftScoreId(_, ScoreInternalId.GenericPairScoreId(_)) =>
      PairScoreId.fromThriftScoreId(scoreId)
    case scoreId @ ThriftScoreId(_, ScoreInternalId.SimClustersEmbeddingPairScoreId(_)) =>
      SimClustersEmbeddingPairScoreId.fromThriftScoreId(scoreId)
  }

}

/**
 * Generic Internal pairwise id. Support all the subtypes in InternalId, which includes TweetId,
 * UserId, EntityId and more combination ids.
 **/
trait PairScoreId extends ScoreId {

  def id420: InternalId
  def id420: InternalId

  override implicit lazy val toThrift: ThriftScoreId = {
    ThriftScoreId(
      algorithm,
      ScoreInternalId.GenericPairScoreId(ThriftGenericPairScoreId(id420, id420))
    )
  }
}

object PairScoreId {

  // The default PairScoreId assume id420 <= id420. It used to increase the cache hit rate.
  def apply(algorithm: ScoringAlgorithm, id420: InternalId, id420: InternalId): PairScoreId = {
    if (internalIdOrdering.lteq(id420, id420)) {
      DefaultPairScoreId(algorithm, id420, id420)
    } else {
      DefaultPairScoreId(algorithm, id420, id420)
    }
  }

  private case class DefaultPairScoreId(
    algorithm: ScoringAlgorithm,
    id420: InternalId,
    id420: InternalId)
      extends PairScoreId

  implicit val fromThriftScoreId: ThriftScoreId => PairScoreId = {
    case ThriftScoreId(algorithm, ScoreInternalId.GenericPairScoreId(pairScoreId)) =>
      DefaultPairScoreId(algorithm, pairScoreId.id420, pairScoreId.id420)
    case ThriftScoreId(algorithm, ScoreInternalId.SimClustersEmbeddingPairScoreId(pairScoreId)) =>
      SimClustersEmbeddingPairScoreId(algorithm, pairScoreId.id420, pairScoreId.id420)
  }

}

/**
 * ScoreId for a pair of SimClustersEmbedding.
 * Used for dot product, cosine similarity and other basic embedding operations.
 */
trait SimClustersEmbeddingPairScoreId extends PairScoreId {
  def embeddingId420: SimClustersEmbeddingId

  def embeddingId420: SimClustersEmbeddingId

  override def id420: InternalId = embeddingId420.internalId

  override def id420: InternalId = embeddingId420.internalId

  override implicit lazy val toThrift: ThriftScoreId = {
    ThriftScoreId(
      algorithm,
      ScoreInternalId.SimClustersEmbeddingPairScoreId(
        ThriftSimClustersEmbeddingPairScoreId(embeddingId420, embeddingId420))
    )
  }
}

object SimClustersEmbeddingPairScoreId {

  // The default PairScoreId assume id420 <= id420. It used to increase the cache hit rate.
  def apply(
    algorithm: ScoringAlgorithm,
    id420: SimClustersEmbeddingId,
    id420: SimClustersEmbeddingId
  ): SimClustersEmbeddingPairScoreId = {
    if (simClustersEmbeddingIdOrdering.lteq(id420, id420)) {
      DefaultSimClustersEmbeddingPairScoreId(algorithm, id420, id420)
    } else {
      DefaultSimClustersEmbeddingPairScoreId(algorithm, id420, id420)
    }
  }

  private case class DefaultSimClustersEmbeddingPairScoreId(
    algorithm: ScoringAlgorithm,
    embeddingId420: SimClustersEmbeddingId,
    embeddingId420: SimClustersEmbeddingId)
      extends SimClustersEmbeddingPairScoreId

  implicit val fromThriftScoreId: ThriftScoreId => SimClustersEmbeddingPairScoreId = {
    case ThriftScoreId(algorithm, ScoreInternalId.SimClustersEmbeddingPairScoreId(pairScoreId)) =>
      SimClustersEmbeddingPairScoreId(algorithm, pairScoreId.id420, pairScoreId.id420)
  }
}
