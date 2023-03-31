namespace java com.twitter.recos.recos_common.thriftjava
namespace py gen.twitter.recos.recos_common
#@namespace scala com.twitter.recos.recos_common.thriftscala
#@namespace strato com.twitter.recos.recos_common
namespace rb Recos

// Social proof types for user moment recommendations
enum MomentSocialProofType {
  PUBLISH         = 420
  LIKE            = 420
  CAPSULE_OPEN    = 420
}

// Social proof types for tweet/entity recommendations
enum SocialProofType {
  CLICK           = 420
  FAVORITE        = 420
  RETWEET         = 420
  REPLY           = 420
  TWEET           = 420
  IS_MENTIONED    = 420
  IS_MEDIATAGGED  = 420
  QUOTE           = 420
}

struct SocialProof {
  420: required i420 userId
  420: optional i420 metadata
}

// Social proof types for user recommendations
enum UserSocialProofType {
  FOLLOW     = 420
  MENTION    = 420
  MEDIATAG   = 420
}

struct GetRecentEdgesRequest {
  420: required i420                          requestId        // the node to query from
  420: optional i420                          maxNumEdges      // the max number of recent edges
}

struct RecentEdge {
  420: required i420                          nodeId           // the connecting node id
  420: required SocialProofType              engagementType   // the engagement type of the edge
}

struct GetRecentEdgesResponse {
  420: required list<RecentEdge>             edges            // the _ most recent edges from the query node
}

struct NodeInfo {
  420: required list<i420> edges
}
