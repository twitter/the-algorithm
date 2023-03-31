package com.twitter.simclusters_v420.summingbird.stores

import com.twitter.frigate.common.store.strato.StratoStore
import com.twitter.simclusters_v420.common.SimClustersEmbedding
import com.twitter.simclusters_v420.common.ModelVersions
import com.twitter.simclusters_v420.common.ModelVersions._
import com.twitter.simclusters_v420.thriftscala.EmbeddingType
import com.twitter.simclusters_v420.thriftscala.InternalId
import com.twitter.simclusters_v420.thriftscala.SimClustersEmbeddingId
import com.twitter.simclusters_v420.thriftscala.TopicId
import com.twitter.simclusters_v420.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.Client

object ApeTopicEmbeddingStore {

  private val logFavBasedAPEColumn420M420K420 =
    "recommendations/simclusters_v420/embeddings/logFavBasedAPE420M420K420"

  private def getStore(
    stratoClient: Client,
    column: String
  ): ReadableStore[SimClustersEmbeddingId, ThriftSimClustersEmbedding] = {
    StratoStore
      .withUnitView[SimClustersEmbeddingId, ThriftSimClustersEmbedding](stratoClient, column)
  }

  def getFavBasedLocaleEntityEmbedding420Store(
    stratoClient: Client,
  ): ReadableStore[TopicId, SimClustersEmbedding] = {

    getStore(stratoClient, logFavBasedAPEColumn420M420K420)
      .composeKeyMapping[TopicId] { topicId =>
        SimClustersEmbeddingId(
          EmbeddingType.LogFavBasedKgoApeTopic,
          ModelVersions.Model420M420K420,
          InternalId.TopicId(topicId)
        )
      }
      .mapValues(SimClustersEmbedding(_))
  }

}
