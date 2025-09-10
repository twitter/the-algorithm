package com.twitter.tweet_mixer.model

object ModuleNames {
  // ANN Service clients
  final val TwHINRegularUpdateAnnServiceClientName = "TwHINRegularUpdateAnnServiceClient"

  // ANN MH Embedding Providers
  final val TwHINEmbeddingRegularUpdateMhEmbeddingProducer =
    "TwHINEmbeddingRegularUpdateMhEmbeddingProducer"
  final val ConsumerBasedTwHINEmbeddingRegularUpdateMhEmbeddingProducer =
    "ConsumerBasedTwHINEmbeddingRegularUpdateMhEmbeddingProducer"

  // ANN queryable by id
  final val TwHINAnnQueryableById = "TwHINAnnQueryableById"
  final val ConsumerBasedTwHINAnnQueryableById = "ConsumerBasedTwHINAnnQueryableById"

  // SANN clients
  final val ProdSimClustersANNServiceClientName = "ProdSimClustersANNServiceClient"
  final val SimClustersVideoANNServiceClientName = "SimClustersVideoANNServiceClient"

  // In-memory Caches
  final val TweeypieInMemCache = "TweeypieInMemCache"
  final val MediaMetadataInMemCache = "MediaMetadataInMemCache"
  final val GrokFilterInMemCache = "GrokFilterInMemCache"

  // Manhattan Client
  final val ManhattanAthenaClient = "ManhattanAthenaClient"
  final val ManhattanApolloClient = "ManhattanApolloClient"

  // Manhattan Repo
  final val RealGraphInNetworkScoresOnPremRepo = "RealGraphInNetworkScoresOnPremRepo"

  // Twhin ANN Client
  final val TwHINANNServiceClient = "TwHINANNServiceClient"
  final val TwHINTweetEmbeddingStratoStore = "TwHINTweetEmbeddingStratoStore"
  final val TwhinRebuildUserPositiveEmbeddingsStore = "TwhinRebuildUserPositiveEmbeddingsStore"
  final val VecDBAnnServiceClient = "VecDBAnnServiceClient"
  final val GPURetrievalProdHttpClient = "GPURetrievalProdHttpClient"
  final val GPURetrievalDevelHttpClient = "GPURetrievalDevelHttpClient"

  // TwHIN rebuild ANN
  final val TwHINRebuildTweetEmbeddingStratoStore = "TwHINRebuildTweetEmbeddingStratoStore"

  // Certo Topic Tweets
  final val CertoStratoTopicTweetsStoreName = "CertoStratoTopicTweetsStore"

  final val SkitStratoTopicTweetsStoreName = "SkitTopicTweetsStore"

  final val EarlybirdRealtimeCGEndpoint = "EarlybirdRealtimeCGEndpoint"

  final val MemcachedImpressionBloomFilterStore = "MemcachedImpressionBloomFilterStore"

  final val MemcachedImpressionVideoBloomFilterStore = "MemcachedImpressionVideoBloomFilterStore"

  // Event publishers
  final val ServedCandidatesScribeEventPublisher = "ServedCandidatesScribeEventPublisher"

}
