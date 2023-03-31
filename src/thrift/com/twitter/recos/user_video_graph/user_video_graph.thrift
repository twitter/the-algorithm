namespace java com.twitter.recos.user_video_graph.thriftjava
namespace py gen.twitter.recos.user_video_graph
#@namespace scala com.twitter.recos.user_video_graph.thriftscala
#@namespace strato com.twitter.recos.user_video_graph
namespace rb UserVideoGraph

include "com/twitter/recos/features/tweet.thrift"
include "com/twitter/recos/recos_common.thrift"


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
 * The main interface-definition for UserVideoGraph.
 */
service UserVideoGraph {
  RelatedTweetResponse tweetBasedRelatedTweets (TweetBasedRelatedTweetRequest request)
  RelatedTweetResponse producerBasedRelatedTweets (ProducerBasedRelatedTweetRequest request)
  RelatedTweetResponse consumersBasedRelatedTweets (ConsumersBasedRelatedTweetRequest request)
}

