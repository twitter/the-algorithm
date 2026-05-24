namespace java com.twitter.cr_mixer.thriftjava
#@namespace scala com.twitter.cr_mixer.thriftscala
#@namespace strato com.twitter.cr_mixer

include "com/twitter/product_mixer/core/client_context.thrift"
include "product.thrift"
include "product_context.thrift"
include "source_type.thrift"


struct TopicTweetRequest {
    1: required client_context.ClientContext clientContext
    2: required product.Product product
    3: required list<i64> topicIds
    5: optional product_context.ProductContext productContext
    6: optional list<i64> excludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct TopicTweet {
    1: required i64 tweetId (personalDataType = 'TweetId')
    2: required double score
    3: required source_type.SimilarityEngineType similarityEngineType
} (persisted='true', hasPersonalData = 'true')

struct TopicTweetResponse {
    1: required map<i64, list<TopicTweet>> tweets
} (persisted='true')

