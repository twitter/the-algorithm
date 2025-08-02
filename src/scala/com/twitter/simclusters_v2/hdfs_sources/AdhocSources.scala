package com.twitter.simclusters_v2.hdfs_sources

import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.bijection.Bufferable
import com.twitter.bijection.Injection
import com.twitter.hermit.candidate.thriftscala.Candidates
import com.twitter.scalding.DateRange
import com.twitter.scalding.commons.source.VersionedKeyValSource
import com.twitter.scalding_internal.source.lzo_scrooge.DailySuffitwitterMostRecentLzoScrooge
import com.twitter.scalding_internal.source.lzo_scrooge.FitwitteredPathLzoScrooge
import com.twitter.scalding_internal.source.lzo_scrooge.HourlySuffitwitterMostRecentLzoScrooge
import com.twitter.simclusters_v2.thriftscala._

case class EdgeWithDecayedWtsFitwitteredPathSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[EdgeWithDecayedWeights](path, EdgeWithDecayedWeights)

case class UserAndNeighborsFitwitteredPathSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[UserAndNeighbors](path, UserAndNeighbors)

case class NormsAndCountsFitwitteredPathSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[NormsAndCounts](path, NormsAndCounts)

case class UserToInterestedInClustersFitwitteredPathSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[UserToInterestedInClusters](path, UserToInterestedInClusters)

case class TimelineDataEtwittertractorFitwitteredPathSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[ReferenceTweets](path, ReferenceTweets)

case class TweetClusterScoresHourlySuffitwitterSource(path: String, override val dateRange: DateRange)
    etwittertends HourlySuffitwitterMostRecentLzoScrooge[TweetAndClusterScores](path, dateRange)

case class TweetTopKClustersHourlySuffitwitterSource(path: String, override val dateRange: DateRange)
    etwittertends HourlySuffitwitterMostRecentLzoScrooge[TweetTopKClustersWithScores](
      path,
      dateRange
    )

case class ClusterTopKTweetsHourlySuffitwitterSource(path: String, override val dateRange: DateRange)
    etwittertends HourlySuffitwitterMostRecentLzoScrooge[ClusterTopKTweetsWithScores](
      path,
      dateRange
    )

case class TweetSimilarityUnhydratedPairsSource(path: String, override val dateRange: DateRange)
    etwittertends DailySuffitwitterMostRecentLzoScrooge[LabelledTweetPairs](
      path,
      dateRange
    )

case class WTFCandidatesSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[Candidates](path, Candidates)

case class EmbeddingsLiteSource(path: String)
    etwittertends FitwitteredPathLzoScrooge[EmbeddingsLite](path, EmbeddingsLite)

object AdhocKeyValSources {
  def interestedInSource(path: String): VersionedKeyValSource[Long, ClustersUserIsInterestedIn] = {
    implicit val keyInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    implicit val valInject: Injection[ClustersUserIsInterestedIn, Array[Byte]] =
      CompactScalaCodec(ClustersUserIsInterestedIn)
    VersionedKeyValSource[Long, ClustersUserIsInterestedIn](path)
  }

  def clusterDetailsSource(path: String): VersionedKeyValSource[(String, Int), ClusterDetails] = {
    implicit val keyInject: Injection[(String, Int), Array[Byte]] =
      Bufferable.injectionOf[(String, Int)]
    implicit val valInject: Injection[ClusterDetails, Array[Byte]] =
      CompactScalaCodec(ClusterDetails)
    VersionedKeyValSource[(String, Int), ClusterDetails](path)
  }

  def bipartiteQualitySource(
    path: String
  ): VersionedKeyValSource[(String, Int), BipartiteClusterQuality] = {
    implicit val keyInject: Injection[(String, Int), Array[Byte]] =
      Bufferable.injectionOf[(String, Int)]
    implicit val valInject: Injection[BipartiteClusterQuality, Array[Byte]] =
      CompactScalaCodec(BipartiteClusterQuality)
    VersionedKeyValSource[(String, Int), BipartiteClusterQuality](path)
  }

  def entityToClustersSource(
    path: String
  ): VersionedKeyValSource[SimClustersEmbeddingId, SimClustersEmbedding] = {
    implicit val keyInject: Injection[SimClustersEmbeddingId, Array[Byte]] =
      BinaryScalaCodec(SimClustersEmbeddingId)
    implicit val valInject: Injection[SimClustersEmbedding, Array[Byte]] =
      BinaryScalaCodec(SimClustersEmbedding)
    VersionedKeyValSource[SimClustersEmbeddingId, SimClustersEmbedding](path)
  }

  def clusterToEntitiesSource(
    path: String
  ): VersionedKeyValSource[SimClustersEmbeddingId, InternalIdEmbedding] = {
    implicit val keyInject: Injection[SimClustersEmbeddingId, Array[Byte]] = BinaryScalaCodec(
      SimClustersEmbeddingId)
    implicit val valInject: Injection[InternalIdEmbedding, Array[Byte]] =
      BinaryScalaCodec(InternalIdEmbedding)
    VersionedKeyValSource[SimClustersEmbeddingId, InternalIdEmbedding](path)
  }

  // For storing producer-simclusters embeddings
  def topProducerToClusterEmbeddingsSource(
    path: String
  ): VersionedKeyValSource[Long, TopSimClustersWithScore] = {
    implicit val keyInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    implicit val valInject: Injection[TopSimClustersWithScore, Array[Byte]] =
      CompactScalaCodec(TopSimClustersWithScore)
    VersionedKeyValSource[Long, TopSimClustersWithScore](path)
  }

  // For storing producer-simclusters embeddings
  def topClusterEmbeddingsToProducerSource(
    path: String
  ): VersionedKeyValSource[PersistedFullClusterId, TopProducersWithScore] = {
    implicit val keyInject: Injection[PersistedFullClusterId, Array[Byte]] =
      CompactScalaCodec(PersistedFullClusterId)
    implicit val valInject: Injection[TopProducersWithScore, Array[Byte]] =
      CompactScalaCodec(TopProducersWithScore)
    VersionedKeyValSource[PersistedFullClusterId, TopProducersWithScore](path)
  }

  def userToInferredEntitiesSource(
    path: String
  ): VersionedKeyValSource[Long, SimClustersInferredEntities] = {
    implicit val keyInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    implicit val valInject: Injection[SimClustersInferredEntities, Array[Byte]] =
      CompactScalaCodec(SimClustersInferredEntities)
    VersionedKeyValSource[Long, SimClustersInferredEntities](path)
  }

  def knownForAdhocSource(path: String): VersionedKeyValSource[Long, ClustersUserIsKnownFor] = {
    implicit val keyInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    implicit val valInject: Injection[ClustersUserIsKnownFor, Array[Byte]] =
      CompactScalaCodec(ClustersUserIsKnownFor)
    VersionedKeyValSource[Long, ClustersUserIsKnownFor](path)
  }

  def knownForSBFResultsDevelSource(
    path: String
  ): VersionedKeyValSource[Long, Array[(Int, Float)]] = {
    implicit val keyInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    implicit val valInject: Injection[Array[(Int, Float)], Array[Byte]] =
      Bufferable.injectionOf[Array[(Int, Float)]]
    VersionedKeyValSource[Long, Array[(Int, Float)]](path)
  }

  // injection to store adjlist in the mapped indices space for users
  def intermediateSBFResultsDevelSource(
    path: String
  ): VersionedKeyValSource[Int, List[(Int, Float)]] = {
    implicit val keyInject: Injection[Int, Array[Byte]] = Injection.int2BigEndian
    implicit val valInject: Injection[List[(Int, Float)], Array[Byte]] =
      Bufferable.injectionOf[List[(Int, Float)]]
    VersionedKeyValSource[Int, List[(Int, Float)]](path)
  }

  def mappedIndicesDevelSource(path: String): VersionedKeyValSource[Int, Long] = {
    implicit val keyInject: Injection[Int, Array[Byte]] = Injection.int2BigEndian
    implicit val valInject: Injection[Long, Array[Byte]] = Injection.long2BigEndian
    VersionedKeyValSource[Int, Long](path)
  }
}
