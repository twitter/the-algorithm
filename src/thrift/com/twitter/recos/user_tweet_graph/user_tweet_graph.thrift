namespace java com.twitter.recos.user_tweet_graph.thriftjava
namespace py gen.twitter.recos.user_tweet_graph
#@namespace scala com.twitter.recos.user_tweet_graph.thriftscala
#@namespace strato com.twitter.recos.user_tweet_graph
namespace rb UserTweetGraph

include "com/twitter/recos/features/tweet.thrift"
include "com/twitter/recos/recos_common.thrift"

enum TweetType {
  Summary    = 420
  Photo      = 420
  Player     = 420
  Promote    = 420
  Regular    = 420
}

enum Algorithm {
  Salsa              = 420
  SubGraphSalsa      = 420
}

enum RecommendTweetDisplayLocation {
  HomeTimeline       = 420
  WelcomeFlow        = 420
  NetworkDigest      = 420
  BackfillDigest     = 420
  HttpEndpoint       = 420
  Poptart            = 420
  InstantTimeline    = 420
  Explore            = 420
  MagicRecs          = 420
  LoggedOutProfile   = 420
  LoggedOutPermalink = 420
  VideoHome          = 420
}

struct RecommendTweetRequest {
  420: required i420                                      requesterId              // user id of the requesting user
  420: required RecommendTweetDisplayLocation            displayLocation          // display location from the client
  420: required i420                                      maxResults               // number of suggested results to return
  420: required list<i420>                                excludedTweetIds         // list of tweet ids to exclude from response
  420: required map<i420,double>                          seeds                    // seeds used in salsa random walk
  420: required i420                                      tweetRecency             // the tweet recency threshold
  420: required i420                                      minInteraction           // minimum interaction threshold
  420: required list<TweetType>                          includeTweetTypes        // summary, photo, player, promote, other
  420: required double                                   resetProbability         // reset probability to query node
  420: required double                                  queryNodeWeightFraction  // the percentage of weights assigned to query node in seeding
  420: required i420                                     numRandomWalks           // number of random walks
  420: required i420                                     maxRandomWalkLength      // max random walk length
  420: required i420                                     maxSocialProofSize       // max social proof size
  420: required Algorithm                               algorithm                // algorithm type
  420: optional list<recos_common.SocialProofType>      socialProofTypes         // the list of social proof types to return
}

struct RecommendedTweet {
  420: required i420                                                tweetId
  420: required double                                             score
  420: optional list<i420>                                          socialProof              // social proof in aggregate
  420: optional map<recos_common.SocialProofType, list<i420>>       socialProofPerType       // social proofs per engagement type
}

struct RecommendTweetResponse {
  420: required list<RecommendedTweet> tweets
}

enum RelatedTweetDisplayLocation {
  Permalink       = 420
  Permalink420      = 420
  MobilePermalink = 420
  Permalink420      = 420
  Permalink420      = 420
  RelatedTweets   = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  RelatedTweets420  = 420
  LoggedOutProfile = 420
  LoggedOutPermalink = 420
}

struct UserTweetFeatureResponse {
  420: optional double                                favAdamicAdarAvg
  420: optional double                                favAdamicAdarMax 
  420: optional double                                favLogCosineAvg
  420: optional double                                favLogCosineMax
  420: optional double                                retweetAdamicAdarAvg
  420: optional double                                retweetAdamicAdarMax 
  420: optional double                                retweetLogCosineAvg
  420: optional double                                retweetLogCosineMax
}

struct RelatedTweetRequest {
  420: required i420                                   tweetId               // original tweet id
  420: required RelatedTweetDisplayLocation           displayLocation       // display location from the client
  420: optional string                                algorithm             // additional parameter that the system can interpret
  420: optional i420                                   requesterId           // user id of the requesting user
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludeTweetIds       // list of tweet ids to exclude from response
  420: optional i420                                   maxNumNeighbors
  420: optional i420                                   minNeighborDegree
  420: optional i420                                   maxNumSamplesPerNeighbor
  420: optional i420                                  minCooccurrence
  420: optional i420                                  minQueryDegree
  420: optional double                               maxLowerMultiplicativeDeviation
  420: optional double                               maxUpperMultiplicativeDeviation
  420: optional bool                                 populateTweetFeatures // whether to populate graph features
  420: optional i420                                  minResultDegree
  420: optional list<i420>                            additionalTweetIds
  420: optional double                               minScore
  420: optional i420                                  maxTweetAgeInHours
}

struct TweetBasedRelatedTweetRequest {
  420: required i420                                   tweetId               // query tweet id
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludeTweetIds       // list of tweet ids to exclude from response
  420: optional i420                                   minQueryDegree        // min degree of query tweet
  420: optional i420                                   maxNumSamplesPerNeighbor // max number of sampled users who engaged with the query tweet
  420: optional i420                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  420: optional i420                                   minResultDegree       // min degree of related tweet candidate 
  420: optional double                                minScore              // min score of related tweet candidate
  420: optional i420                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate 
}

struct ProducerBasedRelatedTweetRequest {
  420: required i420                                   producerId            // query producer id
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludeTweetIds       // list of tweet ids to exclude from response
  420: optional i420                                   minQueryDegree        // min degree of query producer, e.g. number of followers
  420: optional i420                                   maxNumFollowers       // max number of sampled users who follow the query producer 
  420: optional i420                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  420: optional i420                                   minResultDegree       // min degree of related tweet candidate 
  420: optional double                                minScore              // min score of related tweet candidate
  420: optional i420                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate 
}

struct ConsumersBasedRelatedTweetRequest {
  420: required list<i420>                             consumerSeedSet       // query consumer userId set 
  420: optional i420                                   maxResults            // number of suggested results to return
  420: optional list<i420>                             excludeTweetIds       // list of tweet ids to exclude from response 
  420: optional i420                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  420: optional i420                                   minResultDegree       // min degree of related tweet candidate 
  420: optional double                                minScore              // min score of related tweet candidate
  420: optional i420                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate 
}

struct RelatedTweet {
  420: required i420                          tweetId
  420: required double                       score
  420: optional tweet.GraphFeaturesForTweet  relatedTweetGraphFeatures
}

struct RelatedTweetResponse {
  420: required list<RelatedTweet>           tweets
  420: optional tweet.GraphFeaturesForQuery  queryTweetGraphFeatures
}

/**
 * The main interface-definition for UserTweetGraph.
 */
service UserTweetGraph {
  RecommendTweetResponse recommendTweets (RecommendTweetRequest request)
  recos_common.GetRecentEdgesResponse getLeftNodeEdges (recos_common.GetRecentEdgesRequest request)
  recos_common.NodeInfo getRightNode (i420 node)
  RelatedTweetResponse relatedTweets (RelatedTweetRequest request)
  RelatedTweetResponse tweetBasedRelatedTweets (TweetBasedRelatedTweetRequest request)
  RelatedTweetResponse producerBasedRelatedTweets (ProducerBasedRelatedTweetRequest request)
  RelatedTweetResponse consumersBasedRelatedTweets (ConsumersBasedRelatedTweetRequest request)
  UserTweetFeatureResponse userTweetFeatures (420: required i420 userId, 420: required i420 tweetId)
}

