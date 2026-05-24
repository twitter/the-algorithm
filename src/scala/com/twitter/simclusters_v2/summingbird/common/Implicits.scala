package com.twitter.simclusters_v2.summingbird.common

import com.twitter.algebird.DecayedValueMonoid
import com.twitter.algebird.Monoid
import com.twitter.algebird_internal.injection.AlgebirdImplicits
import com.twitter.algebird_internal.thriftscala.{DecayedValue => ThriftDecayedValue}
import com.twitter.bijection.Bufferable
import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.simclusters_v2.summingbird.common.Monoids.ClustersWithScoresMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.MultiModelClustersWithScoresMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.MultiModelPersistentSimClustersEmbeddingLongestL2NormMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.MultiModelPersistentSimClustersEmbeddingMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.MultiModelTopKTweetsWithScoresMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.PersistentSimClustersEmbeddingLongestL2NormMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.PersistentSimClustersEmbeddingMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.ScoresMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.TopKClustersWithScoresMonoid
import com.twitter.simclusters_v2.summingbird.common.Monoids.TopKTweetsWithScoresMonoid
import com.twitter.simclusters_v2.thriftscala.FullClusterIdBucket
import com.twitter.simclusters_v2.thriftscala._
import com.twitter.summingbird.batch.Batcher
import com.twitter.tweetypie.thriftscala.StatusCounts

object Implicits {

  // -------------------- Monoids -------------------- //
  implicit val decayedValueMonoid: DecayedValueMonoid = DecayedValueMonoid(0.0)

  implicit val thriftDecayedValueMonoid: ThriftDecayedValueMonoid =
    new ThriftDecayedValueMonoid(Configs.HalfLifeInMs)(decayedValueMonoid)

  implicit val scoresMonoid: ScoresMonoid = new Monoids.ScoresMonoid()

  implicit val clustersWithScoreMonoid: ClustersWithScoresMonoid =
    new Monoids.ClustersWithScoresMonoid()(scoresMonoid)

  implicit val multiModelClustersWithScoresMonoid: Monoid[MultiModelClustersWithScores] =
    new MultiModelClustersWithScoresMonoid()

  implicit val topKClustersWithScoresMonoid: Monoid[TopKClustersWithScores] =
    new TopKClustersWithScoresMonoid(
      Configs.topKClustersPerEntity,
      Configs.scoreThresholdForEntityTopKClustersCache
    )(thriftDecayedValueMonoid)

  implicit val topKTweetsWithScoresMonoid: Monoid[TopKTweetsWithScores] =
    new TopKTweetsWithScoresMonoid(
      Configs.topKTweetsPerCluster,
      Configs.scoreThresholdForClusterTopKTweetsCache,
      Configs.OldestTweetFavEventTimeInMillis
    )(thriftDecayedValueMonoid)

  implicit val topKTweetsWithScoresLightMonoid: Monoid[TopKTweetsWithScores] =
    new TopKTweetsWithScoresMonoid(
      Configs.topKTweetsPerCluster,
      Configs.scoreThresholdForClusterTopKTweetsCache,
      Configs.OldestTweetInLightIndexInMillis
    )(thriftDecayedValueMonoid)

  implicit val MultiModeltopKTweetsWithScoresMonoid: Monoid[MultiModelTopKTweetsWithScores] =
    new MultiModelTopKTweetsWithScoresMonoid(
    )(thriftDecayedValueMonoid)

  implicit val persistentSimClustersEmbeddingMonoid: Monoid[PersistentSimClustersEmbedding] =
    new PersistentSimClustersEmbeddingMonoid()

  implicit val persistentSimClustersEmbeddingLongestL2NormMonoid: Monoid[
    PersistentSimClustersEmbedding
  ] =
    new PersistentSimClustersEmbeddingLongestL2NormMonoid()

  implicit val multiModelPersistentSimClustersEmbeddingMonoid: Monoid[
    MultiModelPersistentSimClustersEmbedding
  ] =
    new MultiModelPersistentSimClustersEmbeddingMonoid()

  implicit val multiModelPersistentSimClustersEmbeddingLongestL2NormMonoid: Monoid[
    MultiModelPersistentSimClustersEmbedding
  ] = new MultiModelPersistentSimClustersEmbeddingLongestL2NormMonoid()

  // -------------------- Codecs -------------------- //
  implicit val longIntPairCodec: Injection[(Long, Int), Array[Byte]] =
    Bufferable.injectionOf[(Long, Int)]

  implicit val simClusterEntityCodec: Injection[SimClusterEntity, Array[Byte]] =
    CompactScalaCodec(SimClusterEntity)

  implicit val fullClusterIdBucket: Injection[FullClusterIdBucket, Array[Byte]] =
    CompactScalaCodec(FullClusterIdBucket)

  implicit val clustersWithScoresCodec: Injection[ClustersWithScores, Array[Byte]] =
    CompactScalaCodec(ClustersWithScores)

  implicit val topKClustersKeyCodec: Injection[EntityWithVersion, Array[Byte]] =
    CompactScalaCodec(EntityWithVersion)

  implicit val topKClustersWithScoresCodec: Injection[TopKClustersWithScores, Array[Byte]] =
    CompactScalaCodec(TopKClustersWithScores)

  implicit val fullClusterIdCodec: Injection[FullClusterId, Array[Byte]] =
    CompactScalaCodec(FullClusterId)

  implicit val topKEntitiesWithScoresCodec: Injection[TopKEntitiesWithScores, Array[Byte]] =
    CompactScalaCodec(TopKEntitiesWithScores)

  implicit val topKTweetsWithScoresCodec: Injection[TopKTweetsWithScores, Array[Byte]] =
    CompactScalaCodec(TopKTweetsWithScores)

  implicit val pairedArrayBytesCodec: Injection[(Array[Byte], Array[Byte]), Array[Byte]] =
    Bufferable.injectionOf[(Array[Byte], Array[Byte])]

  implicit val entityWithClusterInjection: Injection[(SimClusterEntity, FullClusterIdBucket), Array[
    Byte
  ]] =
    Injection
      .connect[(SimClusterEntity, FullClusterIdBucket), (Array[Byte], Array[Byte]), Array[Byte]]

  implicit val topKClustersCodec: Injection[TopKClusters, Array[Byte]] =
    CompactScalaCodec(TopKClusters)

  implicit val topKTweetsCodec: Injection[TopKTweets, Array[Byte]] =
    CompactScalaCodec(TopKTweets)

  implicit val simClustersEmbeddingCodec: Injection[SimClustersEmbedding, Array[Byte]] =
    CompactScalaCodec(SimClustersEmbedding)

  implicit val persistentSimClustersEmbeddingCodec: Injection[PersistentSimClustersEmbedding, Array[
    Byte
  ]] =
    CompactScalaCodec(PersistentSimClustersEmbedding)

  implicit val statusCountsCodec: Injection[StatusCounts, Array[Byte]] =
    CompactScalaCodec(StatusCounts)

  implicit val thriftDecayedValueCodec: Injection[ThriftDecayedValue, Array[Byte]] =
    AlgebirdImplicits.decayedValueCodec

  implicit val batcher: Batcher = Batcher.unit
}
