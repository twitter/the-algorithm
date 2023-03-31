namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

include "source_type.thrift"
include "com/twitter/simclusters_v2/identifier.thrift"

struct SimilarityEngine {
 1: required source_type.SimilarityEngineType similarityEngineType
 2: optional string modelId
 3: optional double score
} (persisted='true')

struct CandidateGenerationKey {
  1: required source_type.SourceType sourceType
  2: required i64 sourceEventTime (personalDataType = 'PrivateTimestamp')
  3: required identifier.InternalId id
  4: required string modelId
  5: optional source_type.SimilarityEngineType similarityEngineType
  6: optional list<SimilarityEngine> contributingSimilarityEngine
} (persisted='true')
