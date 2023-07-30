#@namespace scala com.X.ann.serialization.thriftscala

include "com/X/ml/api/embedding.thrift"
/**
* Thrift schema for storing embeddings in a file
*/
struct PersistedEmbedding {
  1: required binary id
  2: required embedding.Embedding embedding
}(persisted = 'true')
