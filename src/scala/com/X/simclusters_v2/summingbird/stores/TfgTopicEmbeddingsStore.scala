package com.X.simclusters_v2.summingbird.stores

import com.X.frigate.common.store.strato.StratoStore
import com.X.simclusters_v2.common.ModelVersions
import com.X.simclusters_v2.common.ModelVersions._
import com.X.simclusters_v2.thriftscala.EmbeddingType
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.simclusters_v2.thriftscala.SimClustersEmbeddingId
import com.X.simclusters_v2.thriftscala.TopicId
import com.X.simclusters_v2.thriftscala.{SimClustersEmbedding => ThriftSimClustersEmbedding}
import com.X.storehaus.ReadableStore
import com.X.strato.client.Client
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.simclusters_v2.common.SimClustersEmbedding

/**
 * TopicId -> List< cluster>
 */
object TfgTopicEmbeddingsStore {

  private val favBasedColumn20M145K2020 =
    "recommendations/simclusters_v2/embeddings/favBasedTFGTopic20M145K2020"

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

    getStore(stratoClient, favBasedColumn20M145K2020)
      .composeKeyMapping[TopicId] { topicId =>
        SimClustersEmbeddingId(
          EmbeddingType.FavTfgTopic,
          ModelVersions.Model20M145K2020,
          InternalId.TopicId(topicId)
        )
      }
      .mapValues(SimClustersEmbedding(_))
  }
}
