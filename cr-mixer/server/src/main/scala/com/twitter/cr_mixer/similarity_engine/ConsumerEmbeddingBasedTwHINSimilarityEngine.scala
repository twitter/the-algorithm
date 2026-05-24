package com.twitter.cr_mixer.similarity_engine

import com.twitter.cr_mixer.param.ConsumerEmbeddingBasedTwHINParams
import com.twitter.simclusters_v2.thriftscala.InternalId
import com.twitter.timelines.configapi

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
