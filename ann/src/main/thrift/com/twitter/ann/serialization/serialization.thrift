#@namespace scala com.ExTwitter.ann.serialization.thriftscala

include "com/ExTwitter/ml/api/embedding.thrift"
/**
* Thrift schema for storing embeddings in a file
*/
struct PersistedEmbedding {
  1: required binary id
  2: required embedding.Embedding embedding
}(persisted = 'true')
