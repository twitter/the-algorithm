namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.inferred_entities
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

// The SimClusters type we use to infer entity interests about a user
// Currently used for SimClusters Compliance to store a user's inferred interests

include "online_store.thrift"

enum ClusterType {
  KnownFor        = 1,
  InterestedIn    = 2
}(persisted = 'true', hasPersonalData = 'false')

struct SimClustersSource {
  1: required ClusterType clusterType
  2: required online_store.ModelVersion modelVersion
}(persisted = 'true', hasPersonalData = 'false')

// The source of entities we use to infer entity interests about a user
enum EntitySource {
  SimClusters20M145KDec11EntityEmbeddingsByFavScore = 1, // deprecated
  SimClusters20M145KUpdatedEntityEmbeddingsByFavScore = 2, // deprecated
  UTTAccountRecommendations = 3 # dataset built by Onboarding team
  SimClusters20M145K2020EntityEmbeddingsByFavScore = 4
}(persisted = 'true', hasPersonalData = 'false')

struct InferredEntity {
  1: required i64 entityId(personalDataType = 'SemanticcoreClassification')
  2: required double score(personalDataType = 'EngagementScore')
  3: optional SimClustersSource simclusterSource
  4: optional EntitySource entitySource
}(persisted = 'true', hasPersonalData = 'true')

struct SimClustersInferredEntities {
  1: required list<InferredEntity> entities
}(persisted = 'true', hasPersonalData = 'true')
