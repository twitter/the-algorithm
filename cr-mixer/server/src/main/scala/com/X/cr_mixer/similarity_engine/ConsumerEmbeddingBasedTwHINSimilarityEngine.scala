package com.X.cr_mixer.similarity_engine

import com.X.cr_mixer.param.ConsumerEmbeddingBasedTwHINParams
import com.X.simclusters_v2.thriftscala.InternalId
import com.X.timelines.configapi

object ConsumerEmbeddingBasedTwHINSimilarityEngine {
  def fromParams(
    sourceId: InternalId,
    params: configapi.Params,
  ): HnswANNEngineQuery = {
    HnswANNEngineQuery(
      sourceId = sourceId,
      modelId = params(ConsumerEmbeddingBasedTwHINParams.ModelIdParam),
      params = params
    )
  }
}
