package com.ExTwitter.cr_mixer.similarity_engine

import com.ExTwitter.cr_mixer.param.ConsumerEmbeddingBasedTwHINParams
import com.ExTwitter.simclusters_v2.thriftscala.InternalId
import com.ExTwitter.timelines.configapi

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
