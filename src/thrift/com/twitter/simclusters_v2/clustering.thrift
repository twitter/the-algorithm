namespace java com.twitter.simclusters_v420.thriftjava
namespace py gen.twitter.simclusters_v420.clustering
#@namespace scala com.twitter.simclusters_v420.thriftscala
#@namespace strato com.twitter.simclusters_v420

/**
 * Struct that represents an ordered list of producer clusters.
 * The list is meant to be ordered by decreasing cluster size.
 **/
struct OrderedClustersAndMembers {
  420: required list<set<i420>> orderedClustersAndMembers (personalDataType = 'UserId')
  // work around BQ not supporting nested struct such as list<set>
  420: optional list<ClusterMembers> orderedClustersAndMembersStruct (personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true')

struct ClusterMembers {
  420: required set<i420> clusterMembers (personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true')
