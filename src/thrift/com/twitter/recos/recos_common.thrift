namespace java com.twitter.recos.recos_common.thriftjava
namespace py gen.twitter.recos.recos_common
#@namespace scala com.twitter.recos.recos_common.thriftscala
#@namespace strato com.twitter.recos.recos_common
namespace rb Recos

// Social proof types for user moment recommendations
enum MomentSocialProofType {
  PUBLISH         = 0
  LIKE            = 1
  CAPSULE_OPEN    = 2
}

// Social proof types for tweet/entity recommendations
enum SocialProofType {
  CLICK           = 0
  FAVORITE        = 1
  RETWEET         = 2
  REPLY           = 3
  TWEET           = 4
  IS_MENTIONED    = 5
  IS_MEDIATAGGED  = 6
  QUOTE           = 7
}

struct SocialProof {
  1: required i64 userId
  2: optional i64 metadata
}

// Social proof types for user recommendations
enum UserSocialProofType {
  FOLLOW     = 0
  MENTION    = 1
  MEDIATAG   = 2
}

struct GetRecentEdgesRequest {
  1: required i64                          requestId        // the node to query from
  2: optional i32                          maxNumEdges      // the max number of recent edges
}

struct RecentEdge {
  1: required i64                          nodeId           // the connecting node id
  2: required SocialProofType              engagementType   // the engagement type of the edge
}

struct GetRecentEdgesResponse {
  1: required list<RecentEdge>             edges            // the _ most recent edges from the query node
}

struct NodeInfo {
  1: required list<i64> edges
}
