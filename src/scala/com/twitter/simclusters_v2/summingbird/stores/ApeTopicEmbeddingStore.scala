package com.twitter.simclusters_v2.summingbird.stores

import com.twitter.frigate.common.store.strato.StratoStore
import com.twitter.simclusters_v2.common.SimClustersEmbedding
import com.twitter.simclusters_v2.common.ModelVersions
import com.twitter.simclusters_v2.common.ModelVersions._
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.simclusters_v2.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client

object ApeTopicEmbeddingStore {

  private val logFavBasedAPEColumn20M145K2020 =
    "recommendations/simclusters_v2/embeddings/logFavBasedAPE20M145K2020"

  private def getStore(
    stratoClient: Client,
    column: String
  ): ReadableStore[SimClustersEmbeddingId, ThriftSimClustersEmbedding] = {
    StratoStore
      .withUnitView[SimClustersEmbeddingId, ThriftSimClustersEmbedding](stratoClient, column)
  }

  def getFavBasedLocaleEntityEmbedding2020Store(
    stratoClient: Client,
  ): ReadableStore[TopicId, SimClustersEmbedding] = {

    getStore(stratoClient, logFavBasedAPEColumn20M145K2020)
      .composeKeyMapping[TopicId] { topicId =>
        SimClustersEmbeddingId(
          EmbeddingType.LogFavBasedKgoApeTopic,
          ModelVersions.Model20M145K2020,
          InternalId.TopicId(topicId)
        )
      }
      .mapValues(SimClustersEmbedding(_))
  }

}
