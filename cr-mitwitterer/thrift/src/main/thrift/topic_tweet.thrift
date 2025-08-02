namespace java com.twitter.cr_mitwitterer.thriftjava
#@namespace scala com.twitter.cr_mitwitterer.thriftscala
#@namespace strato com.twitter.cr_mitwitterer

include "com/twitter/product_mitwitterer/core/client_contetwittert.thrift"
include "product.thrift"
include "product_contetwittert.thrift"
include "source_type.thrift"


struct TopicTweetRequest {
    1: required client_contetwittert.ClientContetwittert clientContetwittert
    2: required product.Product product
    3: required list<i64> topicIds
    5: optional product_contetwittert.ProductContetwittert productContetwittert
    6: optional list<i64> etwittercludedTweetIds (personalDataType = 'TweetId')
} (persisted='true', hasPersonalData='true')

struct TopicTweet {
    1: required i64 tweetId (personalDataType = 'TweetId')
    2: required double score
    3: required source_type.SimilarityEngineType similarityEngineType
} (persisted='true', hasPersonalData = 'true')

struct TopicTweetResponse {
    1: required map<i64, list<TopicTweet>> tweets
} (persisted='true')

