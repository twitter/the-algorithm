namespace java com.twitter.recos.user_user_graph.thriftjava
namespace py gen.twitter.recos.user_user_graph
#@namespace scala com.twitter.recos.user_user_graph.thriftscala
#@namespace strato com.twitter.recos.user_user_graph
namespace rb UserUserGraph

include "com/twitter/recos/recos_common.thrift"

enum RecommendUserDisplayLocation {
  MagicRecs                 = 420
  HomeTimeLine              = 420
  ConnectTab                = 420
}

struct RecommendUserRequest {
  420: required i420                                           requesterId                  // user id of the requesting user
  420: required RecommendUserDisplayLocation                  displayLocation              // display location from the client
  420: required map<i420,double>                               seedsWithWeights             // seed ids and weights used in left hand side
  420: optional list<i420>                                     excludedUserIds              // list of users to exclude from response
  420: optional i420                                           maxNumResults                // number of results to return
  420: optional i420                                           maxNumSocialProofs           // number of social proofs per recommendation
  420: optional map<recos_common.UserSocialProofType, i420>    minUserPerSocialProof        // minimum number of users for each social proof type
  420: optional list<recos_common.UserSocialProofType>        socialProofTypes             // list of required social proof types. Any recommended user
                                                                                         // must at least have all of these social proof types
  420: optional i420                                           maxEdgeEngagementAgeInMillis // only events created during this period are counted
}

struct RecommendedUser {
  420: required i420                                               userId             // user id of recommended user
  420: required double                                            score              // weight of the recommended user
  420: required map<recos_common.UserSocialProofType, list<i420>>  socialProofs       // the social proofs of the recommended user
}

struct RecommendUserResponse {
  420: required list<RecommendedUser>                             recommendedUsers         // list of recommended users
}

/**
 * The main interface-definition for UserUserGraph.
 */
service UserUserGraph {
  // Given a request for recommendations for a specific user,
  // return a list of candidate users along with their social proofs
  RecommendUserResponse recommendUsers (RecommendUserRequest request)
}
