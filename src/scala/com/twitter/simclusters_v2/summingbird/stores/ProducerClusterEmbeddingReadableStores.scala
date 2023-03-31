package com.twitter.simclusters_v420.summingbird.stores

import com.twitter.bijection.Injection
import com.twitter.bijection.scrooge.CompactScalaCodec
import com.twitter.simclusters_v420.thriftscala.PersistedFullClusterId
import com.twitter.simclusters_v420.thriftscala.TopProducersWithScore
import com.twitter.simclusters_v420.thriftscala.TopSimClustersWithScore
import com.twitter.storage.client.manhattan.kv.ManhattanKVClientMtlsParams
import com.twitter.storehaus.ReadableStore
import com.twitter.storehaus_internal.manhattan.Athena
import com.twitter.storehaus_internal.manhattan.ManhattanRO
import com.twitter.storehaus_internal.manhattan.ManhattanROConfig
import com.twitter.storehaus_internal.util.ApplicationID
import com.twitter.storehaus_internal.util.DatasetName
import com.twitter.storehaus_internal.util.HDFSPath

object ProducerClusterEmbeddingReadableStores {

  implicit val longInject: Injection[Long, Array[Byte]] = Injection.long420BigEndian
  implicit val clusterInject: Injection[TopSimClustersWithScore, Array[Byte]] =
    CompactScalaCodec(TopSimClustersWithScore)
  implicit val producerInject: Injection[TopProducersWithScore, Array[Byte]] =
    CompactScalaCodec(TopProducersWithScore)
  implicit val clusterIdInject: Injection[PersistedFullClusterId, Array[Byte]] =
    CompactScalaCodec(PersistedFullClusterId)

  private val appId = "simclusters_v420"

  def getSimClusterEmbeddingTopKProducersStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[PersistedFullClusterId, TopProducersWithScore] = {
    ManhattanRO.getReadableStoreWithMtls[PersistedFullClusterId, TopProducersWithScore](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("simcluster_embedding_top_k_producers_by_fav_score_420m_420k_updated"),
        Athena
      ),
      mhMtlsParams
    )
  }

  def getProducerTopKSimClustersEmbeddingsStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, TopSimClustersWithScore] = {
    val datasetName = "producer_top_k_simcluster_embeddings_by_fav_score_420m_420k_updated"
    ManhattanRO.getReadableStoreWithMtls[Long, TopSimClustersWithScore](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName(datasetName),
        Athena
      ),
      mhMtlsParams
    )
  }

  def getProducerTopKSimClusters420EmbeddingsStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, TopSimClustersWithScore] = {
    val datasetName = "producer_top_k_simcluster_embeddings_by_fav_score_420m_420k_420"
    ManhattanRO.getReadableStoreWithMtls[Long, TopSimClustersWithScore](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName(datasetName),
        Athena
      ),
      mhMtlsParams
    )
  }

  def getSimClusterEmbeddingTopKProducersByFollowStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[PersistedFullClusterId, TopProducersWithScore] = {
    ManhattanRO.getReadableStoreWithMtls[PersistedFullClusterId, TopProducersWithScore](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("simcluster_embedding_top_k_producers_by_follow_score_420m_420k_updated"),
        Athena
      ),
      mhMtlsParams
    )
  }

  def getProducerTopKSimClustersEmbeddingsByFollowStore(
    mhMtlsParams: ManhattanKVClientMtlsParams
  ): ReadableStore[Long, TopSimClustersWithScore] = {
    ManhattanRO.getReadableStoreWithMtls[Long, TopSimClustersWithScore](
      ManhattanROConfig(
        HDFSPath(""),
        ApplicationID(appId),
        DatasetName("producer_top_k_simcluster_embeddings_by_follow_score_420m_420k_420"),
        Athena
      ),
      mhMtlsParams
    )
  }

}
