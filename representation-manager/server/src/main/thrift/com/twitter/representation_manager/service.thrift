namespace java com.twitter.representation_manager.thriftjava
#@namespace scala com.twitter.representation_manager.thriftscala
#@namespace strato com.twitter.representation_manager

include "com/twitter/simclusters_v2/online_store.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"

/**
  * A uniform column view for all kinds of SimClusters based embeddings.
  **/
struct SimClustersEmbeddingView {
  1: required identifier.EmbeddingType embeddingType
  2: required online_store.ModelVersion modelVersion
}(persisted = 'false', hasPersonalData = 'false')
