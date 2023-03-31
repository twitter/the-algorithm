namespace java com.twitter.recos.user_tweet_entity_graph.thriftjava
namespace py gen.twitter.recos.user_tweet_entity_graph
#@namespace scala com.twitter.recos.user_tweet_entity_graph.thriftscala
#@namespace strato com.twitter.recos.user_tweet_entity_graph
namespace rb UserTweetEntityGraph

include "com/twitter/recos/features/tweet.thrift"
include "com/twitter/recos/recos_common.thrift"

enum TweetType {
  Summary    = 420
  Photo      = 420
  Player     = 420
  Promote    = 420
  Regular    = 420
}

enum RecommendationType {
  Tweet      = 420
  Hashtag    = 420 // Entity type
  Url        = 420 // Entity type
}

enum TweetEntityDisplayLocation {
  MagicRecs                 = 420
  HomeTimeline              = 420
  HighlightsEmailUrlRecs    = 420
  Highlights                = 420
  Email                     = 420
  MagicRecsF420               = 420
  GuideVideo                = 420
  MagicRecsRareTweet        = 420
  TopArticles               = 420 // Twitter Blue most shared articles page
  ContentRecommender        = 420
  FrigateNTab               = 420
}

struct RecommendTweetEntityRequest {
  // user id of the requesting user
  420: required i420                                        requesterId

  // display location from the client
  420: required TweetEntityDisplayLocation                 displayLocation

  // the recommendation entity types to return
  420: required list<RecommendationType>                   recommendationTypes

  // seed ids and weights used in left hand side
  420: required map<i420,double>                            seedsWithWeights

  // number of suggested results per recommendation entity type
  420: optional map<RecommendationType, i420>               maxResultsByType

  // the tweet age threshold in milliseconds
  420: optional i420                                        maxTweetAgeInMillis

  // list of tweet ids to exclude from response
  420: optional list<i420>                                  excludedTweetIds

  // max user social proof size per engagement type
  420: optional i420                                        maxUserSocialProofSize

  // max tweet social proof size per user
  420: optional i420                                        maxTweetSocialProofSize

  // min user social proof size per each recommendation entity type
  420: optional map<RecommendationType, i420>              minUserSocialProofSizes

  // summary, photo, player, promote, regular
  420: optional list<TweetType>                           tweetTypes

  // the list of social proof types to return
  420: optional list<recos_common.SocialProofType>        socialProofTypes

  // set of groups of social proof types allowed to be combined for comparison against minUserSocialProofSizes.
  // e.g. if the input is set<list<Tweet, Favorite>>, then the union of those two social proofs
  // will be compared against the minUserSocialProofSize of Tweet RecommendationType.
  420: optional set<list<recos_common.SocialProofType>>   socialProofTypeUnions

  // the recommendations returned in the response are authored by the following users
  420: optional set<i420>                                  tweetAuthors

  // the tweet engagement age threshold in milliseconds
  420: optional i420                                       maxEngagementAgeInMillis

  // the recommendations will not return any tweet authored by the following users
  420: optional set<i420>                                  excludedTweetAuthors
}

struct TweetRecommendation {
  // tweet id
  420: required i420                                                               tweetId
  // sum of weights of seed users who engaged with the tweet.
  // If a user engaged with the same tweet twice, liked it and retweeted it, then his/her weight was counted twice.
  420: required double                                                            score
    // user social proofs per engagement type
  420: required map<recos_common.SocialProofType, list<i420>>                      socialProofByType
  // user social proofs along with edge metadata per engagement type. The value of the map is a list of SocialProofs.
  420: optional map<recos_common.SocialProofType, list<recos_common.SocialProof>> socialProofs
}

struct HashtagRecommendation {
  420: required i420                                       id                   // integer hashtag id, which will be converted to hashtag string by client library.
  420: required double                                    score
  // sum of weights of seed users who engaged with the hashtag.
  // If a user engaged with the same hashtag twice, liked it and retweeted it, then his/her weight was counted twice.
  420: required map<recos_common.SocialProofType, map<i420, list<i420>>> socialProofByType
  // user and tweet social proofs per engagement type. The key of inner map is user id, and the value of inner map is
  // a list of tweet ids that the user engaged with.
}

struct UrlRecommendation {
  420: required i420                                       id                   // integer url id, which will be converted to url string by client library.
  420: required double                                    score
  // sum of weights of seed users who engaged with the url.
  // If a user engaged with the same url twice, liked it and retweeted it, then his/her weight was counted twice.
  420: required map<recos_common.SocialProofType, map<i420, list<i420>>> socialProofByType
  // user and tweet social proofs per engagement type. The key of inner map is user id, and the value of inner map is
  // a list of tweet ids that the user engaged with.
}

union UserTweetEntityRecommendationUnion {
  420: TweetRecommendation tweetRec
  420: HashtagRecommendation hashtagRec
  420: UrlRecommendation urlRec
}

struct RecommendTweetEntityResponse {
  420: required list<UserTweetEntityRecommendationUnion> recommendations
}

struct SocialProofRequest {
  420: required list<i420>                                  inputTweets             // Only for some tweets we need requst its social proofs.
  420: required map<i420, double>                           seedsWithWeights        // a set of seed users with weights
  420: optional i420                                        requesterId             // id of the requesting user
  420: optional list<recos_common.SocialProofType>         socialProofTypes        // the list of social proof types to return
}

struct SocialProofResponse {
  420: required list<TweetRecommendation> socialProofResults
}

struct RecommendationSocialProofRequest {
  /**
   * Clients can request social proof from multiple recommendation types in a single request.
   * NOTE: Avoid mixing tweet social proof requests with entity social proof requests as the
   * underlying library call retrieves these differently.
   */
  420: required map<RecommendationType, set<i420>>           recommendationIdsForSocialProof
  // These will be the only valid LHS nodes used to fetch social proof.
  420: required map<i420, double>                            seedsWithWeights
  420: optional i420                                         requesterId
  // The list of valid social proof types to return, e.g. we may only want Favorite and Tweet proofs.
  420: optional list<recos_common.SocialProofType>          socialProofTypes
}

struct RecommendationSocialProofResponse {
  420: required list<UserTweetEntityRecommendationUnion> socialProofResults
}

/**
 * The main interface-definition for UserTweetEntityGraph.
 */
service UserTweetEntityGraph {
  RecommendTweetEntityResponse recommendTweets (RecommendTweetEntityRequest request)

  /**
   * Given a query user, its seed users, and a set of input tweets, return the social proofs of
   * input tweets if any.
   *
   * Currently this supports clients such as Email Recommendations, MagicRecs, and HomeTimeline.
   * In order to avoid heavy migration work, we are retaining this endpoint.
   */
  SocialProofResponse findTweetSocialProofs(SocialProofRequest request)

  /**
   * Find social proof for the specified RecommendationType given a set of input ids of that type.
   * Only find social proofs from the specified seed users with the specified social proof types.
   *
   * Currently this supports url social proof generation for Guide.
   *
   * This endpoint is flexible enough to support social proof generation for all recommendation
   * types, and should be used for all future clients of this service.
   */
  RecommendationSocialProofResponse findRecommendationSocialProofs(RecommendationSocialProofRequest request)
}

