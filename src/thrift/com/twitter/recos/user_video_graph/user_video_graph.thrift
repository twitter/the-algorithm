namespace java com.twitter.recos.user_video_graph.thriftjava
namespace py gen.twitter.recos.user_video_graph
#@namespace scala com.twitter.recos.user_video_graph.thriftscala
#@namespace strato com.twitter.recos.user_video_graph
namespace rb UserVideoGraph

include "com/twitter/recos/features/tweet.thrift"
include "com/twitter/recos/recos_common.thrift"


struct TweetBasedRelatedTweetRequest {
  1: required i64                                   tweetId               // query tweet id
  2: optional i32                                   maxResults            // number of suggested results to return
  3: optional list<i64>                             excludeTweetIds       // list of tweet ids to exclude from response
  4: optional i32                                   minQueryDegree        // min degree of query tweet
  5: optional i32                                   maxNumSamplesPerNeighbor // max number of sampled users who engaged with the query tweet
  6: optional i32                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  7: optional i32                                   minResultDegree       // min degree of related tweet candidate 
  8: optional double                                minScore              // min score of related tweet candidate
  9: optional i32                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate 
}

struct ProducerBasedRelatedTweetRequest {
  1: required i64                                   producerId            // query producer id
  2: optional i32                                   maxResults            // number of suggested results to return
  3: optional list<i64>                             excludeTweetIds       // list of tweet ids to exclude from response
  4: optional i32                                   minQueryDegree        // min degree of query producer, e.g. number of followers
  5: optional i32                                   maxNumFollowers       // max number of sampled users who follow the query producer 
  6: optional i32                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  7: optional i32                                   minResultDegree       // min degree of related tweet candidate 
  8: optional double                                minScore              // min score of related tweet candidate
  9: optional i32                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate 
}

struct ConsumersBasedRelatedTweetRequest {
  1: required list<i64>                             consumerSeedSet       // query consumer userId set
  2: optional i32                                   maxResults            // number of suggested results to return
  3: optional list<i64>                             excludeTweetIds       // list of tweet ids to exclude from response
  4: optional i32                                   minCooccurrence       // min co-occurrence of related tweet candidate 
  5: optional i32                                   minResultDegree       // min degree of related tweet candidate  
  6: optional double                                minScore              // min score of related tweet candidate
  7: optional i32                                   maxTweetAgeInHours    // max tweet age in hours of related tweet candidate
}

struct RelatedTweet {
  1: required i64                          tweetId
  2: required double                       score
  3: optional tweet.GraphFeaturesForTweet  relatedTweetGraphFeatures
}

struct RelatedTweetResponse {
  1: required list<RelatedTweet>           tweets
  2: optional tweet.GraphFeaturesForQuery  queryTweetGraphFeatures
}

/**
 * The main interface-definition for UserVideoGraph.
 */
service UserVideoGraph {
  RelatedTweetResponse tweetBasedRelatedTweets (TweetBasedRelatedTweetRequest request)
  RelatedTweetResponse producerBasedRelatedTweets (ProducerBasedRelatedTweetRequest request)
  RelatedTweetResponse consumersBasedRelatedTweets (ConsumersBasedRelatedTweetRequest request)
}

