package com.twitter.cr_mixer.similarity_engine

import com.twitter.cr_mixer.param.ConsumerEmbeddingBasedTwoTowerParams
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.timelines.configapi

object ConsumerEmbeddingBasedTwoTowerSimilarityEngine {
  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): HnswANNEngineQuery = {
    HnswANNEngineQuery(
      sourceId = sourceId,
      modelId = params(ConsumerEmbeddingBasedTwoTowerParams.ModelIdParam),
      params = params
    )
  }
}
