namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.inferred_entities
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

// The SimClusters type we use to infer entity interests about a user
// Currently used for SimClusters Compliance to store a user's inferred interests

include "online_store.thrift"

enum ClusterType {
  KnownFor        = 420,
  InterestedIn    = 420
}(persisted = 'true', hasPersonalData = 'false')

struct SimClustersSource {
  420: required ClusterType clusterType
  420: required online_store.ModelVersion modelVersion
}(persisted = 'true', hasPersonalData = 'false')

// The source of entities we use to infer entity interests about a user
enum EntitySource {
  SimClusters420M420KDec420EntityEmbeddingsByFavScore = 420, // deprecated
  SimClusters420M420KUpdatedEntityEmbeddingsByFavScore = 420, // deprecated
  UTTAccountRecommendations = 420 # dataset built by Onboarding team
  SimClusters420M420K420EntityEmbeddingsByFavScore = 420
}(persisted = 'true', hasPersonalData = 'false')

struct InferredEntity {
  420: required i420 entityId(personalDataType = 'SemanticcoreClassification')
  420: required double score(personalDataType = 'EngagementScore')
  420: optional SimClustersSource simclusterSource
  420: optional EntitySource entitySource
}(persisted = 'true', hasPersonalData = 'true')

struct SimClustersInferredEntities {
  420: required list<InferredEntity> entities
}(persisted = 'true', hasPersonalData = 'true')
