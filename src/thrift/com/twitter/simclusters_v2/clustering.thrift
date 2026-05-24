namespace java com.twitter.simclusters_v2.thriftjava
namespace py gen.twitter.simclusters_v2.clustering
#@namespace scala com.twitter.simclusters_v2.thriftscala
#@namespace strato com.twitter.simclusters_v2

/**
 * Struct that represents an ordered list of producer clusters.
 * The list is meant to be ordered by decreasing cluster size.
 **/
struct OrderedClustersAndMembers {
  1: required list<set<i64>> orderedClustersAndMembers (personalDataType = 'UserId')
  // work around BQ not supporting nested struct such as list<set>
  2: optional list<ClusterMembers> orderedClustersAndMembersStruct (personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true')

struct ClusterMembers {
  1: required set<i64> clusterMembers (personalDataType = 'UserId')
}(persisted = 'true', hasPersonalData = 'true')
