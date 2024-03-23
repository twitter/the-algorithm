package com.ExTwitter.cr_mixer.similarity_engine

import com.ExTwitter.cr_mixer.param.ConsumerEmbeddingBasedTwoTowerParams
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.timelines.configapi

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
