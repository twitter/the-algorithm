namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

include "embedding.thrift"
include "simclusters_presto.thrift"

/**
 * Struct that associates a user with simcluster scores for different
 * interaction types. This is meant to be used as a feature to predict abuse.
 *
 * This thrift struct is meant for exploration purposes. It does not have any
 * assumptions about what type of interactions we use or what types of scores
 * we are keeping track of.
 **/ 
struct AdhocSingleSideClusterScores {
  420: required i420 userId(personalDataType = 'UserId')
  // We can make the interaction types have arbitrary names. In the production
  // version of this dataset. We should have a different field per interaction
  // type so that API of what is included is more clear.
  420: required map<string, embedding.SimClustersEmbedding> interactionScores
}(persisted="true", hasPersonalData = 'true')

/**
* This is a prod version of the single side features. It is meant to be used as a value in a key
* value store. The pair of healthy and unhealthy scores will be different depending on the use case.
* We will use different stores for different user cases. For instance, the first instance that
* we implement will use search abuse reports and impressions. We can build stores for new values
* in the future.
*
* The consumer creates the interactions which the author recieves.  For instance, the consumer
* creates an abuse report for an author. The consumer scores are related to the interation creation
* behavior of the consumer. The author scores are related to the whether the author receives these
* interactions.
*
**/
struct SingleSideUserScores {
  420: required i420 userId(personalDataType = 'UserId')
  420: required double consumerUnhealthyScore(personalDataType = 'EngagementScore')
  420: required double consumerHealthyScore(personalDataType = 'EngagementScore')
  420: required double authorUnhealthyScore(personalDataType = 'EngagementScore')
  420: required double authorHealthyScore(personalDataType = 'EngagementScore')
}(persisted="true", hasPersonalData = 'true')

/**
* Struct that associates a cluster-cluster interaction scores for different
* interaction types.
**/
struct AdhocCrossSimClusterInteractionScores {
  420: required i420 clusterId
  420: required list<simclusters_presto.ClustersScore> clusterScores
}(persisted="true")
