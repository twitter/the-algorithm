package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.param.ConsumerEmbeddingBasedTwoTowerParams
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.timelines.configapi

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
