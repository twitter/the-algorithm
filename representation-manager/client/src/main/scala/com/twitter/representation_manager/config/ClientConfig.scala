package com.twitter.representation_manager.config

import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclusters_v2.thriftscala.ModelVersion

/*
 * This is RMS client config class.
 * We only support setting up in memory cache params for now, but we expect to enable other
 * customisations in the near future e.g. request timeout
 *
 * --------------------------------------------
 * PLEASE NOTE:
 * Having in-memory cache is not necessarily a free performance win, anyone considering it should
 * investigate rather than blindly enabling it
 * */
class ClientConfig(inMemCacheParamsOverrides: Map[
  (EmbeddingType, ModelVersion),
  InMemoryCacheParams
] = Map.empty) {
  // In memory cache config per embedding
  val inMemCacheParams = DefaultInMemoryCacheConfig.cacheParamsMap ++ inMemCacheParamsOverrides
  val inMemoryCacheConfig = new InMemoryCacheConfig(inMemCacheParams)
}

object DefaultClientConfig extends ClientConfig
